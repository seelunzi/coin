
package hry.coin.coin.service.impl;
import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import hry.app.ourAccount.service.AppOurAccountService;
import hry.coin.CoinService;
import hry.coin.coin.dao.CoinTransactionDao;
import hry.coin.coin.service.CoinTransactionService;
import hry.coin.impl.CoinServiceImpl;
import hry.coin.impl.JsonrpcClient;
import hry.coin.impl.MyCoinService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.core.util.StringUtil;
import hry.core.util.date.DateUtil;
import hry.core.util.http.Httpclient;
import hry.core.util.idgenerate.IdGenerate;
import hry.core.util.log.LogFactory;
import hry.core.util.sys.ContextUtil;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
import hry.ex.dmTransaction.model.ExDmTransaction;
import hry.ex.dmTransaction.service.ExDmTransactionService;
import hry.exchange.coin.model.Transaction;
import hry.redis.common.utils.RedisService;
import hry.utils.JsonResult;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("coinTransactionService")
public class CoinTransactionServiceImpl
        extends BaseServiceImpl<Transaction, Long>
        implements CoinTransactionService {

    @Resource(name = "appOurAccountService")
    private AppOurAccountService appOurAccountService;

    @Resource(name = "exDigitalmoneyAccountService")
    private ExDigitalmoneyAccountService exDigitalmoneyAccountService;

    @Resource(name = "exDmTransactionService")
    private ExDmTransactionService exDmTransactionService;

    @Resource(name = "redisService")
    private RedisService redisService;

    @Resource(name = "coinTransactionDao")
    @Override
    public void setDao(BaseDao<Transaction, Long> dao) {
        this.dao = dao;
    }

    @Override
    public int isExists(String txid) {
        return ((CoinTransactionDao) this.dao).isExists(txid);
    }

    @Override
    public JsonResult recordTransaction(String type, String account, Integer count) {
        int confirmations = 2;
        JsonResult result = new JsonResult();
        CoinService coinService = new CoinServiceImpl(type);
        List<Bitcoin.Transaction> list = coinService.listTransactions(account, count);
        if ((list != null) && (list.size() > 0)) {
            String txStr = com.azazar.krotjson.JSON.stringify(list);
            if ((StringUtils.isNotEmpty(txStr)) && (txStr.startsWith("["))) {
                List<String> txList = (List) com.azazar.krotjson.JSON.parse(txStr);
                for (String txs : txList) {
                    Map<String, Object> tx2map = StringUtil.str2map(txs);
                    String json = com.alibaba.fastjson.JSON.toJSONString(tx2map);
                    json = json.replaceAll(" ", "");
                    Transaction tx = (Transaction) com.alibaba.fastjson.JSON.parseObject(json, Transaction.class);
                    String txidtype = tx.getTxId() + "_" + tx.getCategory();
                    if ((isExists(txidtype) == 0) && (tx.getConfirmations() >= confirmations) && (tx.getCategory().equals("receive"))) {
                        tx.setTxIdType(txidtype);
                        tx.setCoinType(type);
                        tx.setIsCreateOrder(0);
                        save(tx);
                        JsonResult apiResult = rechargecoin(tx);
                        boolean isSuccess = apiResult.getSuccess().booleanValue();
                        if (isSuccess) {
                            QueryFilter filter = new QueryFilter(Transaction.class);
                            filter.addFilter("txIdType=", txidtype);
                            Transaction transaction = (Transaction) get(filter);
                            transaction.setIsCreateOrder(1);
                            super.update(transaction);
                        } else {
                            LogFactory.info("充币接口返回结果：" + com.alibaba.fastjson.JSON.toJSONString(apiResult));
                        }
                    }
                }
                result.setCode("8888");
                result.setMsg("成功");
            } else {
                result.setCode("0000");
                result.setMsg("钱包接口查询数据异常：" + txStr);
            }
        } else {
            LogFactory.info("未查询到充币记录      type=" + type);
        }
        return result;

    }

    @Deprecated
    @Override
    public boolean updateOrder(Transaction tx, String coinType) {
        boolean result = false;
        try {

            ExDigitalmoneyAccount exDigitalmoneyAccount = this.exDigitalmoneyAccountService.getExDigitalmoneyAccountByPublicKey(tx.getAddress(), tx.getCoinType());

            ExDmTransaction txs = this.exDmTransactionService.getExDmTransactionByOrderNo(tx.getTxId());
            if ((null != exDigitalmoneyAccount) && (txs == null)) {
                ExDmTransaction exDmTransaction = new ExDmTransaction();
                exDmTransaction.setSurname(exDigitalmoneyAccount.getSurname());
                exDmTransaction.setAccountId(exDigitalmoneyAccount.getId());
                exDmTransaction.setCoinCode(coinType);
                exDmTransaction.setCreated(new Date());
                exDmTransaction.setCurrencyType(exDigitalmoneyAccount.getCurrencyType());
                exDmTransaction.setCustomerId(exDigitalmoneyAccount.getCustomerId());
                exDmTransaction.setCustomerName(exDigitalmoneyAccount.getUserName());
                exDmTransaction.setTrueName(exDigitalmoneyAccount.getTrueName());
                exDmTransaction.setTime(DateUtil.stampToDate(tx.getTime() + "000"));
                exDmTransaction.setTimereceived(DateUtil.stampToDate(tx.getTimeReceived() + "000"));
                exDmTransaction.setInAddress(tx.getAddress());
                exDmTransaction.setConfirmations(String.valueOf(tx.getConfirmations()));
                exDmTransaction.setBlocktime(DateUtil.stampToDate(tx.getBlockTime() + "000"));
                exDmTransaction.setFee(new BigDecimal(tx.getFee()));
                exDmTransaction.setTransactionMoney(new BigDecimal(tx.getAmount()));
                exDmTransaction.setStatus(Integer.valueOf(1));
                exDmTransaction.setOrderNo(tx.getTxId());
                exDmTransaction.setTransactionNum(IdGenerate.transactionNum("02"));
                exDmTransaction.setTransactionType(Integer.valueOf(1));
                exDmTransaction.setUserId(exDigitalmoneyAccount.getCustomerId());
                exDmTransaction.setWebsite(exDigitalmoneyAccount.getWebsite());
                LogFactory.info("保存充币交易订单");
                this.exDmTransactionService.save(exDmTransaction);
                txs = this.exDmTransactionService.getExDmTransactionByOrderNo(exDmTransaction.getOrderNo());
                String rs = this.exDmTransactionService.rechargeCoin(txs);
                LogFactory.info("修改资产信息结果：" + rs);
                result = true;
            } else {
                LogFactory.info("充币记录业务处理-未查询到币账户");
            }
        } catch (Exception e) {
            LogFactory.info("异常:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JsonResult rechargecoin(Transaction tx) {
        JsonResult jsonResult = new JsonResult();
        try {
            String url = (String) Properties.appMap().get("notify_recharge_url");
            if (StringUtils.isNotEmpty(url)) {
                String publicKey = tx.getAddress();
                String coinCode = tx.getCoinType();
                ExDigitalmoneyAccount coinAccount = this.exDigitalmoneyAccountService.getExDigitalmoneyAccountByPublicKey(publicKey, coinCode);
                if (coinAccount != null) {
                    Double txamount = Double.valueOf(tx.getAmount());
                    Double txfee = Double.valueOf(tx.getFee());
                    String transactionMoney = new BigDecimal(txamount.toString()).toPlainString();
                    String fee = new BigDecimal(txfee != null ? txfee.toString() : "0").toPlainString();
                    String userName = coinAccount.getUserName();
                    String surname = coinAccount.getSurname();
                    String trueName = coinAccount.getTrueName();
                    String CurrencyType = coinAccount.getCurrencyType();
                    String inAddress = coinAccount.getPublicKey();
                    String orderNo = tx.getTxId();
                    String remark = "Receive";
                    Map<String, String> map = new HashMap();
                    map.put("transactionMoney", transactionMoney);
                    map.put("fee", fee);
                    map.put("username", userName);
                    map.put("surname", surname);
                    map.put("trueName", trueName);
                    map.put("coinCode", coinCode);
                    map.put("CurrencyType", CurrencyType);
                    map.put("inAddress", inAddress);
                    map.put("orderNo", orderNo);
                    map.put("remark", remark);
                    String result = Httpclient.post(url, map);
                    if (StringUtils.isNotEmpty(result)) {
                        return (JsonResult) com.alibaba.fastjson.JSON.parseObject(result, JsonResult.class);
                    }
                } else {
                    LogFactory.info("publicKey=" + publicKey + " ,coinCode=" + coinCode);
                    LogFactory.info("coinAccount none existent");
                    jsonResult.setMsg("coinAccount none existent");
                }
            } else {
                LogFactory.info("url=" + url);
                jsonResult.setMsg("app.properties file url not found");
            }
        } catch (Exception e) {
            LogFactory.info("tx=" + com.alibaba.fastjson.JSON.toJSONString(tx));
            e.printStackTrace();
            jsonResult.setMsg("program error");
        }
        jsonResult.setSuccess(Boolean.valueOf(false));
        return jsonResult;
    }

    private boolean isDigit(String a) {
        boolean isDigit = false;
        if (Character.isDigit(a.charAt(0))) {
            isDigit = true;
        }
        return isDigit;
    }

    @Override
    public List<Transaction> findTransactionListIsRollOut(String coinType, int istIsRollOut, String category) {
        return ((CoinTransactionDao) this.dao).findTransactionListIsRollOut(coinType, istIsRollOut, category);
    }

    @Override
    public List<Transaction> findTransactionListByconfirm(String coinType, int isCreateOrder, String category) {
        return ((CoinTransactionDao) this.dao).findTransactionListByconfirm(coinType, isCreateOrder, category);
    }

    @Override
    public boolean isexistHash(String hash) {
        BigDecimal count = ((CoinTransactionDao) this.dao).isexistHash(hash);
        return count.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public List<Transaction> listUnconfirmed(String coinCode) {
        return ((CoinTransactionDao) this.dao).listUnconfirmed(coinCode);

    }

    @Override
    public JsonResult recordTransaction_omni(String coinType, String address, int count, int skip, int startblock, int endblock) {
        JsonResult result = new JsonResult();
        LogFactory.info("omni钱包充币记录刷新");
        Map<String, String> omnisMap = new HashMap();
        omnisMap.put("USDT", "31");
        int sysConfirmations = 2;
        JsonrpcClient client = MyCoinService.getClient(coinType);
        CoinTransactionService coinTransactionService;
        if (client != null) {
            if (address == null) {
                address = "*";
            }
            count = 10;
            skip = 0;
            startblock = 0;
            endblock = 999999;
            List<Map<String, Object>> list = client.omni_listTransactions(address, count, skip, startblock, endblock);
            coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            for (Map<String, Object> map : list) {
                String propertyid = map.get("propertyid").toString();
                String txId = map.get("txid").toString();
                String txType = map.get("type").toString();
                Integer confirmations = Integer.valueOf(map.get("confirmations").toString());
                String txidtype = txId + "_" + txType;
                if ((isExists(txidtype) == 0) && (confirmations.intValue() >= sysConfirmations) && (propertyid.equals(omnisMap.get(coinType)))) {
                    Transaction t = new Transaction();
                    t.setBlockHash(map.get("blockhash").toString());
                    t.setAddress(map.get("referenceaddress").toString());
                    t.setTxId(txId);
                    t.setCoinType(coinType);
                    t.setCategory(txType);
                    t.setAmount(Double.valueOf(map.get("amount").toString()).doubleValue());
                    t.setFee(Double.valueOf(map.get("fee").toString()).doubleValue());
                    t.setConfirmations(confirmations.intValue());
                    t.setTxIdType(txidtype);
                    t.setIsCreateOrder(0);
                    coinTransactionService.save(t);
                    JsonResult apiResult = rechargecoin(t);
                    if (apiResult.getSuccess().booleanValue()) {
                        QueryFilter filter = new QueryFilter(Transaction.class);
                        filter.addFilter("txIdType=", txidtype);
                        Transaction transaction = (Transaction) get(filter);
                        transaction.setIsCreateOrder(1);
                        super.update(transaction);
                        result.setCode("8888");
                        result.setSuccess(Boolean.valueOf(true));
                    } else {
                        LogFactory.info("充币接口返回结果：" + com.alibaba.fastjson.JSON.toJSONString(apiResult));
                        result.setCode("0000");
                    }
                }
            }
        } else {
            LogFactory.info("获取usdt client为空");
        }
        return result;
    }

    @Override
    public void recordTransaction_bds(List<Transaction> list) {

        for (Transaction t : list) {
            String txIdType = t.getTxIdType();
            if (isExists(txIdType) == 0) {
                save(t);
                JsonResult apiResult = rechargecoin(t);
                if (apiResult.getSuccess().booleanValue()) {
                    QueryFilter filter = new QueryFilter(Transaction.class);
                    filter.addFilter("txIdType=", t.getTxIdType());
                    Transaction transaction = (Transaction) get(filter);
                    transaction.setIsCreateOrder(1);
                    super.update(transaction);
                } else {
                    LogFactory.info("充币接口返回结果：" + com.alibaba.fastjson.JSON.toJSONString(apiResult));
                }
            } else {
                LogFactory.info("已经存在数据库中");
            }
        }
    }
}
