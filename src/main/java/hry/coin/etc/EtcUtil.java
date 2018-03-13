//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hry.coin.etc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.coin.coin.model.AppCoinTransaction;
import hry.coin.coin.service.AppCoinTransactionService;
import hry.core.util.date.DateUtil;
import hry.core.util.http.Httpclient;
import hry.core.util.idgenerate.IdGenerate;
import hry.core.util.log.LogFactory;
import hry.core.util.sys.ContextUtil;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
import hry.ex.dmTransaction.model.ExDmTransaction;
import hry.ex.dmTransaction.service.ExDmTransactionService;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EtcUtil {
    public static final JsonRpc2_0Admin admin = EtcClient.getClient();
    public static Map<String, String> coinMap = Properties.appcoinMap();
    public static final String PASSWORD = (String) Properties.appcoinMap().get("ETC".toLowerCase() + "_password");
    public static final String EARLIEST = (String) Properties.appcoinMap().get("ETC".toLowerCase() + "_EARLIEST");

    public EtcUtil() {
    }

    public static List<String> listAccount() {
        try {
            EthAccounts accounts = (EthAccounts) admin.ethAccounts().send();
            return accounts.getAccounts();
        } catch (IOException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String createAddress(String password) {
        try {
            NewAccountIdentifier account = (NewAccountIdentifier) admin.personalNewAccount(password).send();
            return account.getAccountId();
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static BigInteger getBalance(String address) {
        try {
            EthGetBalance balance = (EthGetBalance) admin.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return balance.getBalance();
        } catch (IOException var2) {
            var2.printStackTrace();
            return new BigInteger("0");
        }
    }

    public static BigInteger getGasPrice() {
        try {
            EthGasPrice ethGasPrice = (EthGasPrice) admin.ethGasPrice().send();
            return ethGasPrice.getGasPrice();
        } catch (IOException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static BigInteger getGasLimit(Transaction t) {
        try {
            return ((EthEstimateGas) admin.ethEstimateGas(t).send()).getAmountUsed();
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static boolean unlockAccount(String address, String password) {
        try {
            PersonalUnlockAccount account = (PersonalUnlockAccount) admin.personalUnlockAccount(address, password).send();
            return account.accountUnlocked();
        } catch (IOException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static String sendTransaction(Transaction t, String password) {
        try {
            return ((EthSendTransaction) admin.personalSendTransaction(t, password).send()).getTransactionHash();
        } catch (IOException var3) {
            System.out.println(var3.getMessage());
            var3.printStackTrace();
            return null;
        }
    }

    /***
     *
     * */
    public static List<AppCoinTransaction> listTxByaddress(Map<String, String> map, String url) throws RuntimeException {
        String result = Httpclient.get(url, map);
        JSONObject data = JSON.parseObject(result);
        if (data != null && data.get("message").equals("OK")) {
            List<AppCoinTransaction> list = JSON.parseArray(data.getString("result"), AppCoinTransaction.class);
            return list;
        } else {
            return null;
        }
    }

    public static void catchUpToLatestAndSubscribeToNewTransactionsObservable() {
        try {
            LogFactory.info("开始订阅ETC交易记录");
            AppCoinTransactionService txService = (AppCoinTransactionService) ContextUtil.getBean("appCoinTransactionService");
            DefaultBlockParameter startblockNumber = DefaultBlockParameterName.EARLIEST;
            BigInteger lastestBlockNumber = txService.getLastestBlockByCoinCode("ETC");
            if (lastestBlockNumber != null) {
                startblockNumber = DefaultBlockParameter.valueOf(lastestBlockNumber);
            } else if (EARLIEST != null) {
                startblockNumber = DefaultBlockParameter.valueOf(new BigInteger(EARLIEST));
            }

            admin.catchUpToLatestAndSubscribeToNewTransactionsObservable((DefaultBlockParameter) startblockNumber).subscribe((tx) -> {
                List<String> list = listAccount();
                String to = tx.getTo();
                String from = tx.getFrom();
                if (StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(from)) {
                    to = to.toLowerCase();
                    if (list.contains(to) && !list.contains(from) && txService.existNumber(tx.getHash()) == 0) {
                        LogFactory.info("订阅到以太坊交易记录：" + JSON.toJSONString(tx));
                        AppCoinTransaction t = new AppCoinTransaction();
                        t.setHash_(tx.getHash());
                        t.setTransactionIndex(tx.getTransactionIndex().toString());
                        t.setBlockNumber(tx.getBlockNumber().toString());
                        t.setBlockHash(tx.getBlockHash());
                        t.setFrom_(tx.getFrom());
                        t.setTo_(to);
                        t.setCoinType("ETC");
                        t.setAmount(Convert.fromWei(tx.getValue().toString(), Unit.ETHER));
                        t.setGas(new BigDecimal(tx.getGas().toString()));
                        t.setGasPrice(new BigDecimal(tx.getGasPrice().toString()));
                        t.setIsconsume(0);
                        txService.save(t);
                        LogFactory.info("保存区块链交易记录：" + JSON.toJSONString(t));
                    }
                }

            });
        } catch (Exception var3) {
            var3.printStackTrace();
            LogFactory.info("ETC订阅出错");
        }

    }

    public static void replayTransactionsObservable() {
        admin.replayTransactionsObservable(DefaultBlockParameter.valueOf(new BigInteger("3914")), DefaultBlockParameterName.LATEST).subscribe((tx) -> {
            System.out.println(JSON.toJSONString(tx));
        });
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void coinRecharge(AppCoinTransaction tx) throws RuntimeException {
        ExDigitalmoneyAccountService exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
        ExDmTransactionService exDmTransactionService = (ExDmTransactionService) ContextUtil.getBean("exDmTransactionService");
        ExDigitalmoneyAccount exDigitalmoneyAccount = exDigitalmoneyAccountService.getExDigitalmoneyAccountByPublicKey(tx.getTo_(), tx.getCoinType());
        if (exDigitalmoneyAccount != null) {
            ExDmTransaction exDmTransaction = new ExDmTransaction();
            exDmTransaction.setAccountId(exDigitalmoneyAccount.getId());
            exDmTransaction.setCoinCode(tx.getCoinType());
            exDmTransaction.setCreated(new Date());
            exDmTransaction.setCurrencyType(exDigitalmoneyAccount.getCurrencyType());
            exDmTransaction.setCustomerId(exDigitalmoneyAccount.getCustomerId());
            exDmTransaction.setCustomerName(exDigitalmoneyAccount.getUserName());
            exDmTransaction.setTrueName(exDigitalmoneyAccount.getTrueName());
            exDmTransaction.setTime(DateUtil.dateToString(new Date()));
            exDmTransaction.setTimereceived(DateUtil.dateToString(new Date()));
            exDmTransaction.setInAddress(tx.getTo_());
            exDmTransaction.setConfirmations(String.valueOf("12"));
            exDmTransaction.setBlocktime(DateUtil.dateToString(new Date()));
            exDmTransaction.setFee(tx.getGas());
            exDmTransaction.setTransactionMoney(tx.getAmount());
            exDmTransaction.setStatus(1);
            exDmTransaction.setOrderNo(tx.getBlockHash());
            exDmTransaction.setTransactionNum(IdGenerate.transactionNum("02"));
            exDmTransaction.setTransactionType(1);
            exDmTransaction.setUserId(exDigitalmoneyAccount.getCustomerId());
            exDmTransaction.setWebsite(exDigitalmoneyAccount.getWebsite());
            ExDmTransaction txs = exDmTransactionService.getExDmTransactionByOrderNo(exDmTransaction.getOrderNo());
            if (null == txs) {
                LogFactory.info("保存以太坊充币订单");
                exDmTransactionService.save(exDmTransaction);
                txs = exDmTransactionService.getExDmTransactionByOrderNo(exDmTransaction.getOrderNo());
                exDmTransactionService.rechargeCoin(txs);
                txs = null;
            }
        }

    }

    public static void send2coinBaseJob() {
        try {
            LogFactory.info("以太经典资金归集到提币地址-01");
            ExDigitalmoneyAccountService exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
            List<String> list = exDigitalmoneyAccountService.listPublicKeyByCoinCode("ETC");
            String fromAddress = null;
            String toAddress = getBasecoin();
            Iterator var4 = list.iterator();

            while (var4.hasNext()) {
                String l = (String) var4.next();
                if (StringUtils.isNotEmpty(l)) {
                    BigInteger totalMoney = getBalance(l);
                    BigInteger price = getGasPrice();
                    Transaction t = new Transaction(l, (BigInteger) null, price, (BigInteger) null, toAddress, totalMoney, (String) null);
                    BigInteger gas = getGasLimit(t);
                    BigInteger fee = price.multiply(gas);
                    BigInteger hotMoney = totalMoney.subtract(fee);
                    if (hotMoney.compareTo(BigInteger.ZERO) > 0) {
                        t = new Transaction(l, (BigInteger) null, price, gas, toAddress, hotMoney, (String) null);
                        String txHash = sendTransaction(t, PASSWORD);
                        LogFactory.info("转入冷钱包：" + txHash);
                    }
                }
            }
        } catch (Exception var13) {
            var13.printStackTrace();
            LogFactory.info("程序错误");
        }

    }

    public static String etcSend2coinBaseJob(String fromAddress, String toAddress, String amount) {
        try {
            BigDecimal money = Convert.toWei(new BigDecimal(amount), Unit.ETHER);
            Transaction t = new Transaction(fromAddress, (BigInteger) null, (BigInteger) null, (BigInteger) null, toAddress, money.toBigInteger(), (String) null);
            return sendTransaction(t, PASSWORD);
        } catch (Exception var5) {
            var5.printStackTrace();
            LogFactory.info("以太坊转入冷钱包地址程序报错");
            return null;
        }
    }

    public static String getBasecoin() {
        try {
            return ((EthCoinbase) admin.ethCoinbase().send()).getAddress();
        } catch (IOException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static boolean isAddress(String address) {
        boolean result = false;
        if (address.length() == 42 && address.startsWith("0x")) {
            result = true;
        }

        return result;
    }
}
