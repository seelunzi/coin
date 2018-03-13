
package hry.coin.quart;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import hry.coin.bds.BdsRpcHttpClient;
import hry.coin.bds.BdsServerImpl;
import hry.coin.coin.model.AppCoinTransaction;
import hry.coin.coin.service.AppCoinTransactionService;
import hry.coin.coin.service.CoinTransactionService;
import hry.coin.neo.NeoEntity;
import hry.coin.neo.NeoServiceImpl;
import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.coin.transaction.service.AppCoinTransactionTvService;
import hry.coin.tv.TvUtil;
import hry.core.util.QueryFilter;
import hry.core.util.log.LogFactory;
import hry.core.util.sys.ContextUtil;
import hry.dto.model.Tvtx;
import hry.dto.model.TvtxAmount;
import hry.dto.model.TvtxLedgerEntries;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
import hry.exchange.coin.model.Transaction;
import hry.utils.JsonResult;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CoinQuart {

    private NeoServiceImpl neoServer = new NeoServiceImpl();

    public void btc_consumeTx() {
        String[] arrcoin = Properties.listCoinBasedBtc();
        CoinTransactionService txService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
        if (arrcoin != null) {
            for (String type : arrcoin) {
                if (type.equalsIgnoreCase("USDT")) {
                    txService.recordTransaction_omni(type, null, 0, 0, 0, 0);
                } else {
                    txService.recordTransaction(type, "*", Integer.valueOf(20));
                }
            }
        }
    }

    public void tv_consumeTx() {
        System.out.println("tv充币记录定时器");
        Long count = Long.valueOf(30L);
        Long endblock = TvUtil.getBlockCount();
        Long startblock = Long.valueOf(endblock.longValue() - count.longValue());
        String coinType = "tv".toUpperCase();
        JSONArray arr = TvUtil.getTransactionDetail(startblock.longValue(), endblock.longValue(), null);
        AppCoinTransactionTvService transactiontvservice;
        ExDigitalmoneyAccountService exDigitalmoneyAccountService;
        CoinTransactionService coinTransactionService;
        if ((arr != null) && (arr.size() != 0)) {
            transactiontvservice = (AppCoinTransactionTvService) ContextUtil.getBean("appCoinTransactionTvService");
            exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
            coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            List<Tvtx> list = JSON.parseArray(arr.toJSONString(), Tvtx.class);
            for (Tvtx l : list) {
                String ledger = l.getLedger_entries();
                String isconfirmed = l.getIs_confirmed();
                String trxId = l.getTrx_id();
                String blockNum = l.getBlock_num();
                BigDecimal fee = BigDecimal.ZERO;
                Boolean noload = Boolean.valueOf(transactiontvservice.notload(trxId));
                if ((noload.booleanValue()) && (StringUtils.isNotEmpty(ledger)) && (StringUtils.isNotEmpty(isconfirmed)) && (Boolean.valueOf(isconfirmed).booleanValue())) {
                    List<TvtxLedgerEntries> txs = JSON.parseArray(ledger, TvtxLedgerEntries.class);
                    if ((txs != null) && (txs.size() > 0) && (txs.get(0) != null)) {
                        TvtxLedgerEntries tx = (TvtxLedgerEntries) txs.get(0);
                        String amountjson = tx.getAmount();
                        String fromAddress = tx.getFrom_account();
                        String toAccount = tx.getTo_account();
                        Boolean isexist = Boolean.valueOf(exDigitalmoneyAccountService.isexist(toAccount.toUpperCase(), coinType));
                        if ((isexist.booleanValue()) && (StringUtils.isNotEmpty(amountjson)) && (StringUtils.isNotEmpty(fromAddress)) && (StringUtils.isNotEmpty(toAccount))) {
                            TvtxAmount am = (TvtxAmount) JSON.parseObject(amountjson, TvtxAmount.class);
                            String amountNum = am.getAmount();
                            if (StringUtils.isNotEmpty(amountNum)) {
                                BigDecimal amount = new BigDecimal(amountNum).divide(new BigDecimal("100000"));
                                AppCoinTransactionTv tv = new AppCoinTransactionTv();
                                tv.setType(coinType);
                                tv.setBlockNum(Integer.valueOf(blockNum));
                                tv.setTrxId(trxId);
                                tv.setFromAddress(fromAddress);
                                tv.setToAccount(toAccount);
                                tv.setAmount(amount);
                                tv.setIsUse(0);
                                tv.setFee(fee);
                                tv.setIsconfirmed(Integer.valueOf(0));
                                LogFactory.info("获取tv充币记录:" + JSON.toJSON(tv));
                                transactiontvservice.save(tv);
                                Transaction transaction = new Transaction();
                                String time = System.currentTimeMillis() + "";
                                time = time.substring(0, time.length() - 3);
                                String address = TvUtil.getAccountPublicAddress(tv.getToAccount());
                                transaction.setTime(time);
                                transaction.setTimeReceived(time);
                                transaction.setBlockTime(time);
                                transaction.setAddress(address);
                                transaction.setCoinType(coinType);
                                transaction.setConfirmations(1);
                                transaction.setTxId(tv.getTrxId());
                                transaction.setFee(TvUtil.Fee.doubleValue());
                                transaction.setAmount(tv.getAmount().doubleValue());
                                JsonResult result = coinTransactionService.rechargecoin(transaction);
                                if (result.getSuccess().booleanValue()) {
                                    QueryFilter filter = new QueryFilter(AppCoinTransactionTv.class);
                                    filter.addFilter("trxId=", trxId);
                                    AppCoinTransactionTv t = (AppCoinTransactionTv) transactiontvservice.get(filter);
                                    t.setIsUse(1);
                                    transactiontvservice.update(t);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void tv_send2coinbase() {
        ExDigitalmoneyAccountService exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
        String wtAccount = TvUtil.withdAccount;
        String wtAddress = TvUtil.getAccountPublicAddress(wtAccount);
        String type = "tv".toUpperCase();
        List<String> list = exDigitalmoneyAccountService.listAccountNumByCoinCode(type);
        LogFactory.info("用户地址list=" + JSON.toJSON(list));
        for (String l : list) {
            l = l.toLowerCase();
            BigDecimal hotMoney = TvUtil.getbalance(l);
            BigDecimal fee = TvUtil.Fee;
            if ((hotMoney != null) && (hotMoney.compareTo(fee) > 0)) {
                String amount = hotMoney.subtract(fee).toString();
                TvUtil.sendCoinToAddr(l, type, amount, wtAddress, "userAccount2wtAccount");
            }
        }
    }

    public void ether_consumeTx() {
        AppCoinTransactionService txService = (AppCoinTransactionService) ContextUtil.getBean("appCoinTransactionService");
        List<AppCoinTransaction> list = txService.consumeTx();
        CoinTransactionService coinTransactionService;
        if ((list != null) && (list.size() > 0)) {
            LogFactory.info("geth充值记录消费：" + Json.toJson(list));
            coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            for (AppCoinTransaction l : list) {
                LogFactory.info("消费ETH充币记录:" + JSON.toJSONString(l));
                try {
                    Transaction tx = new Transaction();
                    tx.setAddress(l.getTo_());
                    tx.setAmount(l.getAmount().doubleValue());
                    tx.setCoinType(l.getCoinType());
                    tx.setFee(0.0D);
                    JsonResult result = coinTransactionService.rechargecoin(tx);
                    if (result.getSuccess().booleanValue()) {
                        l.setIsconsume(1);
                        txService.update(l);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogFactory.info("消费币记录程序报错");
                }
            }
        }
    }


    public void bds_consumeTx() {
        LogFactory.info("bds充币定时器执行");
        BdsServerImpl bdsServerImpl = new BdsServerImpl();
        String accountName = BdsRpcHttpClient.chargeAccount;
        String count = "99999";
        String password = BdsRpcHttpClient.walletPassword;
        String id = BdsRpcHttpClient.id;
        String listtx = bdsServerImpl.getAccountHistory(accountName, count, id, password);
        System.out.println(JSON.toJSON(listtx));
        if (StringUtils.isNotEmpty(listtx)) {
            CoinTransactionService coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            List<Transaction> list = JSON.parseArray(listtx, Transaction.class);
            if ((list != null) && (!list.isEmpty())) {
                coinTransactionService.recordTransaction_bds(list);
            } else {
                LogFactory.info("bds list为空");
            }
        } else {
            LogFactory.info("bds查询历史记录为空");
        }

    }

    public void neo_consumeTx() {
        int endBlockNumber = this.neoServer.getblockcount() - 1;
        int startBlockNumber = endBlockNumber - 10;
        List<Transaction> listTransaction = new ArrayList();
        for (int i = endBlockNumber; i >= startBlockNumber; i--) {
            List<NeoEntity> list = this.neoServer.getblock(i + "");
            for (NeoEntity l : list) {
                Transaction transaction = new Transaction();
                transaction.setTime("");
                transaction.setTimeReceived("");
                transaction.setBlockTime("");
                transaction.setAddress(l.getAddress());
                transaction.setCoinType("BDS");
                transaction.setConfirmations(1);
                transaction.setTxId(l.getTxId());
                transaction.setTxIdType("NEO");
                transaction.setFee(0.0D);
                transaction.setAmount(Double.valueOf(l.getValue()).doubleValue());
                transaction.setBlockIndex(Integer.valueOf(l.getN()).intValue());
                listTransaction.add(transaction);
            }
        }
        if (listTransaction.size() != 0) {
            String listtx = JSON.toJSONString(listTransaction);
            if (StringUtils.isNotEmpty(listtx)) {
                CoinTransactionService coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
                Object list = JSON.parseArray(listtx, Transaction.class);
                if ((list != null) && (!((List) list).isEmpty())) {
                    coinTransactionService.recordTransaction_bds((List) list);
                } else {
                    LogFactory.info("neo list为空");
                }
            } else {
                LogFactory.info("neo查询历史记录为空");
            }
        }
    }

    public void ether_productionTx() {
    }

}