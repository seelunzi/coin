
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
import java.util.Date;
import java.util.List;

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
/*     */
/*     */
/*     */

public class CoinQuart {
    private NeoServiceImpl neoServer = new NeoServiceImpl();

    public void btc_consumeTx()
        /*     */ {
        /*  56 */
        String[] arrcoin = Properties.listCoinBasedBtc();
        /*  57 */
        CoinTransactionService txService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
        /*  58 */
        if (arrcoin != null)
            /*     */ {

            for (String type : arrcoin) {

                if (type.equalsIgnoreCase("USDT")) {

                    txService.recordTransaction_omni(type, null, 0, 0, 0, 0);

                } else {

                    txService.recordTransaction(type, "*", Integer.valueOf(20));

                }

            }

        }

    }

    public void tv_consumeTx()
        /*     */ {
        /*  86 */
        System.out.println("tv充币记录定时器");
        /*  87 */
        Long count = Long.valueOf(30L);
        /*  88 */
        Long endblock = TvUtil.getBlockCount();
        /*  89 */
        Long startblock = Long.valueOf(endblock.longValue() - count.longValue());
        /*  90 */
        String coinType = "tv".toUpperCase();
        /*  91 */
        JSONArray arr = TvUtil.getTransactionDetail(startblock.longValue(), endblock.longValue(), null);
        /*  92 */
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

                                String time = new Date().getTime() + "";

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

    public void tv_send2coinbase()
        /*     */ {
        /* 185 */
        ExDigitalmoneyAccountService exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
        /* 186 */
        String wtAccount = TvUtil.withdAccount;
        /* 187 */
        String wtAddress = TvUtil.getAccountPublicAddress(wtAccount);
        /* 188 */
        String type = "tv".toUpperCase();
        /* 189 */
        List<String> list = exDigitalmoneyAccountService.listAccountNumByCoinCode(type);
        /* 190 */
        LogFactory.info("用户地址list=" + JSON.toJSON(list));
        /* 191 */
        for (String l : list) {
            /* 192 */
            l = l.toLowerCase();
            /* 193 */
            BigDecimal hotMoney = TvUtil.getbalance(l);
            /* 194 */
            BigDecimal fee = TvUtil.Fee;
            /*     */
            /* 196 */
            if ((hotMoney != null) && (hotMoney.compareTo(fee) > 0)) {
                /* 197 */
                String amount = hotMoney.subtract(fee).toString();
                /*     */
                /* 199 */
                TvUtil.sendCoinToAddr(l, type, amount, wtAddress, "userAccount2wtAccount");
                /*     */
            }
            /*     */
        }
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
    /*     */
    public void ether_consumeTx()
    /*     */ {
        /* 218 */
        AppCoinTransactionService txService = (AppCoinTransactionService) ContextUtil.getBean("appCoinTransactionService");
        /* 219 */
        List<AppCoinTransaction> list = txService.consumeTx();
        /* 220 */
        CoinTransactionService coinTransactionService;
        if ((list != null) && (list.size() > 0)) {
            /* 221 */
            LogFactory.info("geth充值记录消费：" + Json.toJson(list));
            /* 222 */
            coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            /* 223 */
            for (AppCoinTransaction l : list) {
                /* 224 */
                LogFactory.info("消费ETH充币记录:" + JSON.toJSONString(l));
                /*     */
                try {
                    /* 226 */
                    Transaction tx = new Transaction();
                    /* 227 */
                    tx.setAddress(l.getTo_());
                    /* 228 */
                    tx.setAmount(l.getAmount().doubleValue());
                    /* 229 */
                    tx.setCoinType(l.getCoinType());
                    /* 230 */
                    tx.setFee(0.0D);
                    /* 231 */
                    JsonResult result = coinTransactionService.rechargecoin(tx);
                    /* 232 */
                    if (result.getSuccess().booleanValue()) {
                        /* 233 */
                        l.setIsconsume(1);
                        /* 234 */
                        txService.update(l);
                        /*     */
                    }
                    /*     */
                } catch (Exception e) {
                    /* 237 */
                    e.printStackTrace();
                    /* 238 */
                    LogFactory.info("消费币记录程序报错");
                    /*     */
                }
                /*     */
            }
            /*     */
        }
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
    public void bds_consumeTx()
    /*     */ {
        /* 253 */
        LogFactory.info("bds充币定时器执行");
        /* 254 */
        BdsServerImpl bdsServerImpl = new BdsServerImpl();
        /* 255 */
        String accountName = BdsRpcHttpClient.chargeAccount;
        /* 256 */
        String count = "99999";
        /* 257 */
        String password = BdsRpcHttpClient.walletPassword;
        /* 258 */
        String id = BdsRpcHttpClient.id;
        /* 259 */
        String listtx = bdsServerImpl.getAccountHistory(accountName, count, id, password);
        /* 260 */
        System.out.println(JSON.toJSON(listtx));
        /* 261 */
        if (StringUtils.isNotEmpty(listtx)) {
            /* 262 */
            CoinTransactionService coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
            /* 263 */
            List<Transaction> list = JSON.parseArray(listtx, Transaction.class);
            /* 264 */
            if ((list != null) && (!list.isEmpty())) {
                /* 265 */
                coinTransactionService.recordTransaction_bds(list);
                /*     */
            } else {
                /* 267 */
                LogFactory.info("bds list为空");
                /*     */
            }
            /*     */
        } else {
            /* 270 */
            LogFactory.info("bds查询历史记录为空");
            /*     */
        }
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
    public void neo_consumeTx()
    /*     */ {
        /* 283 */
        int endBlockNumber = this.neoServer.getblockcount() - 1;
        /* 284 */
        int startBlockNumber = endBlockNumber - 10;
        /* 285 */
        List<Transaction> listTransaction = new ArrayList();
        /* 286 */
        for (int i = endBlockNumber; i >= startBlockNumber; i--) {
            /* 287 */
            List<NeoEntity> list = this.neoServer.getblock(i + "");
            /* 288 */
            for (NeoEntity l : list) {
                /* 289 */
                Transaction transaction = new Transaction();
                /* 290 */
                transaction.setTime("");
                /* 291 */
                transaction.setTimeReceived("");
                /* 292 */
                transaction.setBlockTime("");
                /* 293 */
                transaction.setAddress(l.getAddress());
                /* 294 */
                transaction.setCoinType("BDS");
                /* 295 */
                transaction.setConfirmations(1);
                /* 296 */
                transaction.setTxId(l.getTxId());
                /* 297 */
                transaction.setTxIdType("NEO");
                /* 298 */
                transaction.setFee(0.0D);
                /* 299 */
                transaction.setAmount(Double.valueOf(l.getValue()).doubleValue());
                /* 300 */
                transaction.setBlockIndex(Integer.valueOf(l.getN()).intValue());
                /* 301 */
                listTransaction.add(transaction);
                /*     */
            }
            /*     */
        }
        /* 304 */
        if (listTransaction.size() != 0) {
            /* 305 */
            String listtx = JSON.toJSONString(listTransaction);
            /* 306 */
            if (StringUtils.isNotEmpty(listtx)) {
                /* 307 */
                CoinTransactionService coinTransactionService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
                /* 308 */
                Object list = JSON.parseArray(listtx, Transaction.class);
                /* 309 */
                if ((list != null) && (!((List) list).isEmpty())) {
                    /* 310 */
                    coinTransactionService.recordTransaction_bds((List) list);
                    /*     */
                } else {
                    /* 312 */
                    LogFactory.info("neo list为空");
                    /*     */
                }
                /*     */
            } else {
                /* 315 */
                LogFactory.info("neo查询历史记录为空");
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    public void ether_productionTx() {
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\quart\CoinQuart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */