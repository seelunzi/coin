/*     */
package hry.coin.coin.service.impl;
/*     */
/*     */

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
/*     */ public class CoinTransactionServiceImpl
        /*     */ extends BaseServiceImpl<Transaction, Long>
        /*     */ implements CoinTransactionService
        /*     */ {
    /*     */
    @Resource(name = "appOurAccountService")
    /*     */ private AppOurAccountService appOurAccountService;
    /*     */
    @Resource(name = "exDigitalmoneyAccountService")
    /*     */ private ExDigitalmoneyAccountService exDigitalmoneyAccountService;
    /*     */
    @Resource(name = "exDmTransactionService")
    /*     */ private ExDmTransactionService exDmTransactionService;
    /*     */
    @Resource(name = "redisService")
    /*     */ private RedisService redisService;

    /*     */
    /*     */
    @Resource(name = "coinTransactionDao")
    /*     */ public void setDao(BaseDao<Transaction, Long> dao)
    /*     */ {
        /*  72 */
        this.dao = dao;
        /*     */
    }

    /*     */
    /*     */
    public int isExists(String txid)
    /*     */ {
        /*  77 */
        return ((CoinTransactionDao) this.dao).isExists(txid);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public JsonResult recordTransaction(String type, String account, Integer count)
    /*     */ {
        /*  84 */
        int confirmations = 2;
        /*     */
        /*  86 */
        JsonResult result = new JsonResult();
        /*     */
        /*  88 */
        CoinService coinService = new CoinServiceImpl(type);
        /*  89 */
        List<Bitcoin.Transaction> list = coinService.listTransactions(account, count);
        /*  90 */
        if ((list != null) && (list.size() > 0)) {
            /*  91 */
            String txStr = com.azazar.krotjson.JSON.stringify(list);
            /*  92 */
            if ((StringUtils.isNotEmpty(txStr)) && (txStr.startsWith("["))) {
                /*  93 */
                List<String> txList = (List) com.azazar.krotjson.JSON.parse(txStr);
                /*  94 */
                for (String txs : txList) {
                    /*  95 */
                    Map<String, Object> tx2map = StringUtil.str2map(txs);
                    /*  96 */
                    String json = com.alibaba.fastjson.JSON.toJSONString(tx2map);
                    /*  97 */
                    json = json.replaceAll(" ", "");
                    /*  98 */
                    Transaction tx = (Transaction) com.alibaba.fastjson.JSON.parseObject(json, Transaction.class);
                    /*  99 */
                    String txidtype = tx.getTxId() + "_" + tx.getCategory();
                    /* 100 */
                    if ((isExists(txidtype) == 0) && (tx.getConfirmations() >= confirmations) && (tx.getCategory().equals("receive"))) {
                        /* 101 */
                        tx.setTxIdType(txidtype);
                        /* 102 */
                        tx.setCoinType(type);
                        /* 103 */
                        tx.setIsCreateOrder(0);
                        /* 104 */
                        save(tx);
                        /* 105 */
                        JsonResult apiResult = rechargecoin(tx);
                        /* 106 */
                        boolean isSuccess = apiResult.getSuccess().booleanValue();
                        /* 107 */
                        if (isSuccess) {
                            /* 108 */
                            QueryFilter filter = new QueryFilter(Transaction.class);
                            /* 109 */
                            filter.addFilter("txIdType=", txidtype);
                            /* 110 */
                            Transaction transaction = (Transaction) get(filter);
                            /* 111 */
                            transaction.setIsCreateOrder(1);
                            /* 112 */
                            super.update(transaction);
                            /*     */
                        } else {
                            /* 114 */
                            LogFactory.info("充币接口返回结果：" + com.alibaba.fastjson.JSON.toJSONString(apiResult));
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                }
                /* 118 */
                result.setCode("8888");
                /* 119 */
                result.setMsg("成功");
                /*     */
            } else {
                /* 121 */
                result.setCode("0000");
                /* 122 */
                result.setMsg("钱包接口查询数据异常：" + txStr);
                /*     */
            }
            /*     */
        } else {
            /* 125 */
            LogFactory.info("未查询到充币记录      type=" + type);
            /*     */
        }
        /* 127 */
        return result;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    @Deprecated
    /*     */ public boolean updateOrder(Transaction tx, String coinType)
    /*     */ {
        /* 144 */
        boolean result = false;
        /*     */
        try {
            /* 146 */
            ExDigitalmoneyAccount exDigitalmoneyAccount = this.exDigitalmoneyAccountService.getExDigitalmoneyAccountByPublicKey(tx.getAddress(), tx.getCoinType());
            /* 147 */
            ExDmTransaction txs = this.exDmTransactionService.getExDmTransactionByOrderNo(tx.getTxId());
            /* 148 */
            if ((null != exDigitalmoneyAccount) && (txs == null)) {
                /* 149 */
                ExDmTransaction exDmTransaction = new ExDmTransaction();
                /* 150 */
                exDmTransaction.setSurname(exDigitalmoneyAccount.getSurname());
                /* 151 */
                exDmTransaction.setAccountId(exDigitalmoneyAccount.getId());
                /* 152 */
                exDmTransaction.setCoinCode(coinType);
                /* 153 */
                exDmTransaction.setCreated(new Date());
                /* 154 */
                exDmTransaction.setCurrencyType(exDigitalmoneyAccount.getCurrencyType());
                /* 155 */
                exDmTransaction.setCustomerId(exDigitalmoneyAccount.getCustomerId());
                /* 156 */
                exDmTransaction.setCustomerName(exDigitalmoneyAccount.getUserName());
                /* 157 */
                exDmTransaction.setTrueName(exDigitalmoneyAccount.getTrueName());
                /* 158 */
                exDmTransaction.setTime(DateUtil.stampToDate(tx.getTime() + "000"));
                /* 159 */
                exDmTransaction.setTimereceived(DateUtil.stampToDate(tx.getTimeReceived() + "000"));
                /* 160 */
                exDmTransaction.setInAddress(tx.getAddress());
                /* 161 */
                exDmTransaction.setConfirmations(String.valueOf(tx.getConfirmations()));
                /* 162 */
                exDmTransaction.setBlocktime(DateUtil.stampToDate(tx.getBlockTime() + "000"));
                /* 163 */
                exDmTransaction.setFee(new BigDecimal(tx.getFee()));
                /* 164 */
                exDmTransaction.setTransactionMoney(new BigDecimal(tx.getAmount()));
                /* 165 */
                exDmTransaction.setStatus(Integer.valueOf(1));
                /* 166 */
                exDmTransaction.setOrderNo(tx.getTxId());
                /* 167 */
                exDmTransaction.setTransactionNum(IdGenerate.transactionNum("02"));
                /*     */
                /* 169 */
                exDmTransaction.setTransactionType(Integer.valueOf(1));
                /* 170 */
                exDmTransaction.setUserId(exDigitalmoneyAccount.getCustomerId());
                /* 171 */
                exDmTransaction.setWebsite(exDigitalmoneyAccount.getWebsite());
                /*     */
                /* 173 */
                LogFactory.info("保存充币交易订单");
                /*     */
                /* 175 */
                this.exDmTransactionService.save(exDmTransaction);
                /*     */
                /* 177 */
                txs = this.exDmTransactionService.getExDmTransactionByOrderNo(exDmTransaction.getOrderNo());
                /*     */
                /* 179 */
                String rs = this.exDmTransactionService.rechargeCoin(txs);
                /* 180 */
                LogFactory.info("修改资产信息结果：" + rs);
                /* 181 */
                result = true;
                /*     */
            } else {
                /* 183 */
                LogFactory.info("充币记录业务处理-未查询到币账户");
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /* 186 */
            LogFactory.info("异常:" + e.getMessage());
            /* 187 */
            e.printStackTrace();
            /*     */
        }
        /* 189 */
        return result;
        /*     */
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
    public JsonResult recordTransaction_omni(String coinType, String address, int count, int skip, int startblock, int endblock)
        /*     */ {

        JsonResult result = new JsonResult();
        /* 296 */
        LogFactory.info("omni钱包充币记录刷新");
        /*     */
        /* 298 */
        Map<String, String> omnisMap = new HashMap();
        /* 299 */
        omnisMap.put("USDT", "31");
        /* 300 */
        int sysConfirmations = 2;
        /* 301 */
        JsonrpcClient client = MyCoinService.getClient(coinType);
        /* 302 */
        CoinTransactionService coinTransactionService;
        if (client != null) {
            /* 303 */
            if (address == null) {
                /* 304 */
                address = "*";
                /*     */
            }
            /* 306 */
            count = 10;
            /* 307 */
            skip = 0;
            /* 308 */
            startblock = 0;
            /* 309 */
            endblock = 999999;
            /* 310 */
            List<Map<String, Object>> list = client.omni_listTransactions(address, count, skip, startblock, endblock);
            /* 311 */
            coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            /* 312 */
            for (Map<String, Object> map : list)
                /*     */ {
                /* 314 */
                String propertyid = map.get("propertyid").toString();
                /* 315 */
                String txId = map.get("txid").toString();
                /* 316 */
                String txType = map.get("type").toString();
                /* 317 */
                Integer confirmations = Integer.valueOf(map.get("confirmations").toString());
                /* 318 */
                String txidtype = txId + "_" + txType;
                /* 319 */
                if ((isExists(txidtype) == 0) && (confirmations.intValue() >= sysConfirmations) && (propertyid.equals(omnisMap.get(coinType)))) {
                    /* 320 */
                    Transaction t = new Transaction();
                    /* 321 */
                    t.setBlockHash(map.get("blockhash").toString());
                    /* 322 */
                    t.setAddress(map.get("referenceaddress").toString());
                    /* 323 */
                    t.setTxId(txId);
                    /* 324 */
                    t.setCoinType(coinType);
                    /* 325 */
                    t.setCategory(txType);
                    /* 326 */
                    t.setAmount(Double.valueOf(map.get("amount").toString()).doubleValue());
                    /* 327 */
                    t.setFee(Double.valueOf(map.get("fee").toString()).doubleValue());
                    /* 328 */
                    t.setConfirmations(confirmations.intValue());
                    /* 329 */
                    t.setTxIdType(txidtype);
                    /* 330 */
                    t.setIsCreateOrder(0);
                    /*     */
                    /* 332 */
                    coinTransactionService.save(t);
                    /*     */
                    /* 334 */
                    JsonResult apiResult = rechargecoin(t);
                    /* 335 */
                    if (apiResult.getSuccess().booleanValue()) {
                        /* 336 */
                        QueryFilter filter = new QueryFilter(Transaction.class);
                        /* 337 */
                        filter.addFilter("txIdType=", txidtype);
                        /* 338 */
                        Transaction transaction = (Transaction) get(filter);
                        /* 339 */
                        transaction.setIsCreateOrder(1);
                        /* 340 */
                        super.update(transaction);
                        /* 341 */
                        result.setCode("8888");
                        /* 342 */
                        result.setSuccess(Boolean.valueOf(true));
                        /*     */
                    } else {
                        /* 344 */
                        LogFactory.info("充币接口返回结果：" + com.alibaba.fastjson.JSON.toJSONString(apiResult));
                        /* 345 */
                        result.setCode("0000");
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
        } else {
            /* 350 */
            LogFactory.info("获取usdt client为空");
            /*     */
        }
        /* 352 */
        return result;
        /*     */
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
