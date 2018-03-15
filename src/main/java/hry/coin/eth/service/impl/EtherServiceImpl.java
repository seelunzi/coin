//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hry.coin.eth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import hry.coin.coin.model.AppCoinTransaction;
import hry.coin.coin.service.AppCoinTransactionService;
import hry.coin.eth.client.AdminClient;
import hry.coin.eth.client.RpcHttpClient;
import hry.coin.utils.RedisUtil;
import hry.core.util.date.DateUtil;
import hry.core.util.http.Httpclient;
import hry.core.util.idgenerate.IdGenerate;
import hry.core.util.log.LogFactory;
import hry.core.util.sys.ContextUtil;
import hry.dto.model.Wallet;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
import hry.ex.dmTransaction.model.ExDmTransaction;
import hry.ex.dmTransaction.service.ExDmTransactionService;
import hry.utils.JsonResult;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.NoRouteToHostException;
import java.util.*;

public class EtherServiceImpl {
    public static final JsonRpc2_0Admin admin = AdminClient.getClient();
    public static JsonRpcHttpClient client = RpcHttpClient.getClient();
    public static Map<String, String> coinMap = Properties.appcoinMap();
    public static final String PASSWORD;
    public static final String DECIMALS = "_DECIMALS";
    public static final String ETH_ACCOUNTS = "eth_accounts";
    public static final String BLOCKNUMBER = "blockNumber";

    public EtherServiceImpl() {
    }

    public static List<String> listAccount() {
        try {
            EthAccounts accounts = (EthAccounts) admin.ethAccounts().send();
            return accounts.getAccounts();
        } catch (Exception var1) {
            LogFactory.info("eth-listAccount 钱包接口异常");
            return null;
        }
    }

    /***
     * 创建以太坊的钱包地址
     * */
    public static String createAddress(String password) {
        String address = null;
        try {
            /**
             * admin新建个人账户，钱包节点的密码
             * */
            NewAccountIdentifier account = (NewAccountIdentifier) admin.personalNewAccount(password).send();
            address = account.getAccountId();
            String eth_accounts_str = RedisUtil.getValue("eth_accounts");
            if (StringUtils.isNotEmpty(address) && StringUtils.isNotEmpty(eth_accounts_str)) {
                /***
                 * 如果redis里面没有以太坊的钱包地址，就放进去
                 * */
                List<String> eth_accounts = JSON.parseArray(eth_accounts_str, String.class);
                if (!eth_accounts.contains(address)) {
                    eth_accounts.add(address);
                    RedisUtil.setValue("eth_accounts", Json.toJson(eth_accounts));
                }
            }
        } catch (NoRouteToHostException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        return address;
    }

    /***
     * 得到以太坊地址的balance
     * */
    public static BigInteger getBalance(String address) {
        BigInteger balance = BigInteger.ZERO;
        try {
            /***
             * 得到以太坊的balance
             * */
            EthGetBalance ethBalance = (EthGetBalance) admin.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            balance = ethBalance.getBalance();
        } catch (NoRouteToHostException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return balance;
    }

    /***
     * 得到以太坊gas手续费
     * */
    public static BigInteger getGasPrice() {
        BigInteger gasPrice = BigInteger.ZERO;
        try {
            EthGasPrice ethGasPrice = (EthGasPrice) admin.ethGasPrice().send();
            gasPrice = ethGasPrice.getGasPrice();
        } catch (NoRouteToHostException var2) {
            var2.printStackTrace();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return gasPrice;
    }

    public static BigInteger getGasLimit(Transaction t) {
        BigInteger gasLimit = BigInteger.ZERO;
        try {
            gasLimit = ((EthEstimateGas) admin.ethEstimateGas(t).send()).getAmountUsed();
        } catch (NoRouteToHostException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return gasLimit;
    }

    public static boolean unlockAccount(String address, String password) {
        Boolean result = false;
        try {
            PersonalUnlockAccount account = (PersonalUnlockAccount) admin.personalUnlockAccount(address, password).send();
            result = account.accountUnlocked();
        } catch (NoRouteToHostException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
        return result;
    }

    public static String sendTransaction(Transaction t, String password) {
        String result = null;
        try {
            result = ((EthSendTransaction) admin.personalSendTransaction(t, password).send()).getTransactionHash();
        } catch (NoRouteToHostException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
        return result;
    }

    /**
     * @deprecated
     */
    @Deprecated
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


    @Deprecated
    public static void catchUpToLatestAndSubscribeToNewTransactionsObservable() {
        String ETH = "ETH";
        LogFactory.info("开始订阅geth交易记录");
        AppCoinTransactionService txService = (AppCoinTransactionService) ContextUtil.getBean("appCoinTransactionService");
        DefaultBlockParameter startblockNumber = DefaultBlockParameterName.EARLIEST;
        BigInteger lastestBlockNumber = null;

        try {
            lastestBlockNumber = txService.getLastestBlock();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        if (lastestBlockNumber != null) {
            startblockNumber = DefaultBlockParameter.valueOf(lastestBlockNumber);
        } else {
            startblockNumber = DefaultBlockParameter.valueOf(getBlockNumber());
        }

        if (admin != null) {
            List<String> eth_accounts = listAccount();
            RedisUtil.setValue("eth_accounts", Json.toJson(eth_accounts));
            admin.catchUpToLatestAndSubscribeToNewTransactionsObservable(startblockNumber).subscribe((tx) -> {
                System.out.println(tx.getBlockNumber());
                String to = tx.getTo();
                String from = tx.getFrom();
                String eth_accounts_str = RedisUtil.getValue("eth_accounts");
                if (eth_accounts_str.contains(to) && txService.existNumber(tx.getHash()) == 0) {
                    AppCoinTransaction txxx = new AppCoinTransaction();
                    txxx.setHash_(tx.getHash());
                    txxx.setTransactionIndex(tx.getTransactionIndex().toString());
                    txxx.setBlockNumber(tx.getBlockNumber().toString());
                    txxx.setBlockHash(tx.getBlockHash());
                    txxx.setFrom_(from);
                    txxx.setTo_(to);
                    txxx.setCoinType(ETH);
                    txxx.setAmount(Convert.fromWei(tx.getValue().toString(), Unit.ETHER));
                    txxx.setGas(new BigDecimal(tx.getGas().toString()));
                    txxx.setGasPrice(new BigDecimal(tx.getGasPrice().toString()));
                    txxx.setIsconsume(0);
                    txService.save(txxx);
                } else if (coinMap.containsKey(to)) {
                    String input = tx.getInput();
                    if (StringUtils.isNotEmpty(input)) {
                        String data = input.substring(0, 9);
                        data = data + input.substring(17, input.length());
                        Function function = new Function("transfer", Arrays.asList(), Arrays.asList(new TypeReference<Address>() {
                        }, new TypeReference<Uint256>() {
                        }));
                        List<Type> params = FunctionReturnDecoder.decode(data, function.getOutputParameters());
                        String toAddress = ((Type) params.get(0)).getValue().toString();
                        String amount = ((Type) params.get(1)).getValue().toString();
                        if (eth_accounts_str.contains(toAddress) && StringUtils.isNotEmpty(amount) && txService.existNumber(tx.getHash()) == 0) {
                            String coinCode = (String) coinMap.get(to);
                            String unit = (String) coinMap.get(coinCode + "_DECIMALS");
                            if (StringUtils.isNotEmpty(coinCode) && StringUtils.isNotEmpty(unit)) {
                                AppCoinTransaction t = new AppCoinTransaction();
                                t.setHash_(tx.getHash());
                                t.setInput(tx.getInput());
                                t.setTransactionIndex(tx.getTransactionIndex().toString());
                                t.setBlockNumber(tx.getBlockNumber().toString());
                                t.setBlockHash(tx.getBlockHash());
                                t.setFrom_(from);
                                t.setTo_(toAddress);
                                t.setCoinType(coinCode);
                                t.setAmount(hry.utils.Convert.fromWei(amount, hry.utils.Convert.Unit.fromString(unit)));
                                t.setGas(new BigDecimal(tx.getGas().toString()));
                                t.setGasPrice(new BigDecimal(tx.getGasPrice().toString()));
                                t.setIsconsume(0);
                                txService.save(t);
                            } else {
                                LogFactory.info("代币配置错误，请检查：  coinCode=" + coinCode + "  unit=" + unit);
                            }
                        }
                    }
                }
            });
        } else {
            LogFactory.info("geth钱包rpc连接ERROR");
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
            }
        }
    }

    public static void send2coinBaseJob() {
        try {
            LogFactory.info("以太坊资金归集到主钱包-01");
            ExDigitalmoneyAccountService exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
            List<String> list = exDigitalmoneyAccountService.listPublicKeyByCoinCode("ETH");
            String fromAddress = null;
            String toAddress = getBasecoin();
            Iterator var4 = list.iterator();
            while (var4.hasNext()) {
                String l = (String) var4.next();
                if (StringUtils.isNotEmpty(l) && !l.equalsIgnoreCase(toAddress)) {
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

    public static JsonResult send2ColdWallet(String toAddress, String amount) {
        JsonResult result = new JsonResult();
        String fromAddress = getBasecoin();
        BigDecimal money = Convert.toWei(new BigDecimal(amount), Unit.ETHER);
        Transaction t = new Transaction(fromAddress, (BigInteger) null, (BigInteger) null, (BigInteger) null, toAddress, money.toBigInteger(), (String) null);
        String hash = sendTransaction(t, PASSWORD);
        if (StringUtils.isNotEmpty(hash)) {
            result.setSuccess(true);
            result.setMsg(hash);
        } else {
            result.setSuccess(false);
            result.setMsg("以太坊钱包接口ERROR");
        }
        return result;
    }

    public static String getBasecoin() {
        String coinbase = null;
        try {
            coinbase = ((EthCoinbase) admin.ethCoinbase().send()).getAddress();
        } catch (NoRouteToHostException var2) {
            LogFactory.info("eth-getBasecoin 钱包接口不通");
        } catch (IOException var3) {
            var3.printStackTrace();
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }
        return coinbase;
    }

    public static BigInteger getBalanceofContract(String fromAddress, String contractAddress) {
        BigInteger balance = BigInteger.ZERO;
        Function fn = new Function("balanceOf", Arrays.asList(new Address(fromAddress)), Collections.emptyList());
        String data = FunctionEncoder.encode(fn);
        Map<String, String> map = new HashMap();
        map.put("to", contractAddress);
        map.put("data", data);
        try {
            String methodName = "eth_call";
            Object[] params = new Object[]{map, "latest"};
            String result = client.invoke(methodName, params, Object.class).toString();
            if (StringUtils.isNotEmpty(result)) {
                balance = Numeric.decodeQuantity(result);
            }
        } catch (Throwable var9) {
            var9.printStackTrace();
            LogFactory.info("查询接口ERROR");
        }
        return balance;
    }

    public static String sendTransactionofContract(String fromAddress, String toAddress, String contractAddress, BigInteger amount) {
        Boolean unlock = unlockAccount(fromAddress, PASSWORD);
        if (unlock) {
            Function fn = new Function("transfer", Arrays.asList(new Address(toAddress), new Uint256(amount)), Collections.emptyList());
            String data = FunctionEncoder.encode(fn);
            Map<String, String> map = new HashMap();
            map.put("from", fromAddress);
            map.put("to", contractAddress);
            map.put("data", data);
            try {
                String methodName = "eth_sendTransaction";
                Object[] params = new Object[]{map};
                String result = client.invoke(methodName, params, Object.class).toString();
                if (StringUtils.isNotEmpty(result)) {
                    return result;
                }
            } catch (Throwable var12) {
                var12.printStackTrace();
            }
        } else {
            LogFactory.info("解锁提币账户失败");
        }
        return null;
    }

    public static boolean isAddress(String address) {
        boolean result = false;
        if (address.length() == 42 && address.startsWith("0x")) {
            result = true;
        }
        return result;
    }

    public static String syncing() {
        try {
            String methodName = "eth_syncing";
            Map<String, String> map = new HashMap();
            Object[] params = new Object[]{map};
            String result = client.invoke(methodName, params, Object.class).toString();
            if (StringUtils.isNotEmpty(result)) {
                return result;
            }
        } catch (Throwable var4) {
            var4.printStackTrace();
        }
        return null;
    }

    public static Wallet getEtherWalletInfo() {
        Wallet wallet = new Wallet();
        String address = getBasecoin();
        BigInteger _money = getBalance(address);
        if (_money != null) {
            BigDecimal money = Convert.fromWei(String.valueOf(_money), Unit.ETHER);
            String toMoney = money.toString();
            List<String> listAccounts = listAccount();
            BigInteger totalMoney = BigInteger.ZERO;

            String total;
            BigInteger accoutMoney;
            for (Iterator var7 = listAccounts.iterator(); var7.hasNext(); totalMoney = totalMoney.add(accoutMoney)) {
                total = (String) var7.next();
                accoutMoney = getBalance(total);
            }

            BigDecimal totalMoney_ = Convert.fromWei(String.valueOf(totalMoney), Unit.ETHER);
            total = totalMoney_.toString();
            String coldAddress = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_coldAddress");
            wallet.setCoinCode("ETH");
            wallet.setWithdrawalsAddress(address);
            wallet.setColdwalletAddress(coldAddress == null ? "" : coldAddress);
            wallet.setWithdrawalsAddressMoney(toMoney);
            wallet.setTotalMoney(total);
        }
        return wallet;
    }

    /****
     * 提币操作
     * */
    public static JsonResult sendFrom(String amount, String toAddress) {
        JsonResult result = new JsonResult();
        String fromAddress = getBasecoin();
        BigDecimal money = Convert.toWei(new BigDecimal(amount), Unit.ETHER);
        BigInteger value = money.toBigInteger();
        BigInteger fromAddressMoney = getBalance(fromAddress);
        BigInteger price = getGasPrice();
        Transaction transaction = new Transaction(fromAddress, BigInteger.valueOf(0L), price, BigInteger.valueOf(0L), toAddress, value, "");
        BigInteger gas = getGasLimit(transaction);
        BigInteger needfunds = price.multiply(gas).add(value);
        if (fromAddressMoney != null && fromAddressMoney.compareTo(needfunds) >= 0) {
            transaction = new Transaction(fromAddress, (BigInteger) null, price, gas, toAddress, value, "");
            String txHash = sendTransaction(transaction, PASSWORD);
            if (StringUtils.isNotEmpty(txHash)) {
                result.setSuccess(true);
                result.setMsg(txHash);
            } else {
                result.setSuccess(false);
                result.setMsg("提币失败");
            }
        } else {
            result.setSuccess(false);
            result.setMsg("提币账户余额不足,至少需要" + Convert.fromWei(new BigDecimal(needfunds.toString()), Unit.ETHER));
        }

        return result;
    }

    /***
     *智能合约提币操作
     * */
    public static JsonResult smartContract_sendFrom(String type, String amount, String toAddress) {
        JsonResult result = new JsonResult();
        type = type.toUpperCase();
        String unit = (String) coinMap.get(type + "_DECIMALS");
        String contractAddress = (String) coinMap.get(type);
        String fromAddress = getBasecoin();
        if (StringUtils.isNotEmpty(unit) && StringUtils.isNotEmpty(contractAddress) && StringUtils.isNotEmpty(fromAddress)) {
            BigInteger rawFromAddressMoney = getBalanceofContract(fromAddress, contractAddress);
            BigDecimal fromAddressMoney = new BigDecimal(rawFromAddressMoney.toString());
            BigDecimal fee = BigDecimal.valueOf(0.005D);
            fromAddressMoney = fromAddressMoney.subtract(fee);
            BigDecimal money = hry.utils.Convert.toWei(new BigDecimal(amount), hry.utils.Convert.Unit.fromString(unit));
            if (fromAddressMoney.compareTo(money) >= 0) {
                String hash = sendTransactionofContract(fromAddress, toAddress, contractAddress, money.toBigInteger());
                if (StringUtils.isNotEmpty(hash)) {
                    result.setSuccess(true);
                    result.setMsg(hash);
                } else {
                    result.setMsg("合约币提币失败");
                }
            } else {
                result.setMsg("提币地址余额不足");
            }
        } else {
            result.setMsg("合约币提币失败,配置错误");
        }

        return result;
    }

    public static Wallet smartContract_getWalletInfo(String coinCode) {
        coinCode = coinCode.toUpperCase();
        Wallet wallet = new Wallet();
        String unit = (String) coinMap.get(coinCode + "_DECIMALS");
        String contractAddress = (String) coinMap.get(coinCode);
        String address = getBasecoin();
        BigInteger _money = getBalanceofContract(address, contractAddress);
        if (_money != null) {
            BigDecimal money = hry.utils.Convert.fromWei(String.valueOf(_money), hry.utils.Convert.Unit.fromString(unit));
            String toMoney = money.toString();
            List<String> listAccounts = listAccount();
            BigInteger totalMoney = BigInteger.ZERO;
            String total;
            BigInteger accoutMoney;
            for (Iterator var10 = listAccounts.iterator(); var10.hasNext(); totalMoney = totalMoney.add(accoutMoney)) {
                total = (String) var10.next();
                accoutMoney = getBalanceofContract(total, contractAddress);
            }
            BigDecimal totalMoney_ = hry.utils.Convert.fromWei(String.valueOf(totalMoney), hry.utils.Convert.Unit.fromString(unit));
            total = totalMoney_.toString();
            String coldAddress = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_coldAddress");
            wallet.setCoinCode(coinCode);
            wallet.setColdwalletAddress(coldAddress == null ? "" : coldAddress);
            wallet.setWithdrawalsAddress(address);
            wallet.setWithdrawalsAddressMoney(toMoney);
            wallet.setTotalMoney(total);
        }
        return wallet;
    }

    public static boolean isSmartContractCoin(String coinType) {
        System.out.println(coinType.toUpperCase());
        String value = (String) Properties.appcoinMap().get(coinType.toUpperCase());
        System.out.println(value);
        return value != null && value.startsWith("0x");
    }

    public static BigInteger getBlockNumber() {
        try {
            return ((EthBlockNumber) admin.ethBlockNumber().send()).getBlockNumber();
        } catch (IOException var1) {
            var1.printStackTrace();
            return BigInteger.ZERO;
        }
    }

    public static Block GetBlockByNumbe(BigInteger number, boolean isdetail) {
        DefaultBlockParameter blockNumber = DefaultBlockParameter.valueOf(number);
        try {
            return ((EthBlock) admin.ethGetBlockByNumber(blockNumber, isdetail).send()).getBlock();
        } catch (IOException var4) {
            LogFactory.info("geth连接错误");
            return null;
        }
    }

    public static void ether_productionTx() {
        BigInteger blockNumber = getBlockNumber().subtract(BigInteger.valueOf(15L));
        System.out.println(blockNumber);
        BigInteger count = BigInteger.valueOf(15L);
        for (int i = 1; i < count.intValue(); ++i) {
            blockNumber = blockNumber.add(BigInteger.ONE);
            Block block = GetBlockByNumbe(blockNumber, true);
            if (block != null) {
                List<TransactionResult> transactions = block.getTransactions();
                Iterator var5 = transactions.iterator();
                while (var5.hasNext()) {
                    TransactionResult l = (TransactionResult) var5.next();
                    org.web3j.protocol.core.methods.response.Transaction tx = (org.web3j.protocol.core.methods.response.Transaction) l;
                    recordGethTx(tx);
                }
            }
        }

    }

    public static org.web3j.protocol.core.methods.response.Transaction ethGetTransactionByHash(String hash) {
        try {
            return (org.web3j.protocol.core.methods.response.Transaction) ((EthTransaction) admin.ethGetTransactionByHash(hash).send()).getTransaction().get();
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static void recaptureGethTx(String hash) {
        org.web3j.protocol.core.methods.response.Transaction tx = ethGetTransactionByHash(hash);
        LogFactory.info("重新刷新geth充币记录：" + hash + "------json:" + Json.toJson(tx));
        recordGethTx(tx);
    }

    public static void recordGethTx(org.web3j.protocol.core.methods.response.Transaction tx) {
        if (tx != null) {
            AppCoinTransactionService txService = (AppCoinTransactionService) ContextUtil.getBean("appCoinTransactionService");
            String to = tx.getTo();
            String from = tx.getFrom();
            String eth_accounts_str = RedisUtil.getValue("eth_accounts");
            if (to != null && eth_accounts_str.contains(to) && txService.existNumber(tx.getHash()) == 0) {
                AppCoinTransaction t = new AppCoinTransaction();
                t.setHash_(tx.getHash());
                t.setTransactionIndex(tx.getTransactionIndex().toString());
                t.setBlockNumber(tx.getBlockNumber().toString());
                t.setBlockHash(tx.getBlockHash());
                t.setFrom_(from);
                t.setTo_(to);
                t.setCoinType("ETH");
                t.setAmount(Convert.fromWei(tx.getValue().toString(), Unit.ETHER));
                t.setGas(new BigDecimal(tx.getGas().toString()));
                t.setGasPrice(new BigDecimal(tx.getGasPrice().toString()));
                t.setIsconsume(0);
                txService.save(t);
            } else if (coinMap.containsKey(to)) {
                String input = tx.getInput();
                if (StringUtils.isNotEmpty(input) && input.length() >= 138) {
                    String data = input.substring(0, 9);
                    data = data + input.substring(17, input.length());
                    Function function = new Function("transfer", Arrays.asList(), Arrays.asList(new TypeReference<Address>() {
                    }, new TypeReference<Uint256>() {
                    }));
                    List<Type> params = FunctionReturnDecoder.decode(data, function.getOutputParameters());
                    String toAddress = ((Type) params.get(0)).getValue().toString();
                    String amount = ((Type) params.get(1)).getValue().toString();
                    if (eth_accounts_str.contains(toAddress) && StringUtils.isNotEmpty(amount) && txService.existNumber(tx.getHash()) == 0) {
                        String coinCode = (String) coinMap.get(to);
                        String unit = (String) coinMap.get(coinCode + "_DECIMALS");
                        if (StringUtils.isNotEmpty(coinCode) && StringUtils.isNotEmpty(unit)) {
                            AppCoinTransaction t = new AppCoinTransaction();
                            t.setHash_(tx.getHash());
                            t.setInput(tx.getInput());
                            t.setTransactionIndex(tx.getTransactionIndex().toString());
                            t.setBlockNumber(tx.getBlockNumber().toString());
                            t.setBlockHash(tx.getBlockHash());
                            t.setFrom_(from);
                            t.setTo_(toAddress);
                            t.setCoinType(coinCode);
                            t.setAmount(hry.utils.Convert.fromWei(amount, hry.utils.Convert.Unit.fromString(unit)));
                            t.setGas(new BigDecimal(tx.getGas().toString()));
                            t.setGasPrice(new BigDecimal(tx.getGasPrice().toString()));
                            t.setIsconsume(0);
                            txService.save(t);
                        } else {
                            LogFactory.info("代币配置错误，请检查：  coinCode=" + coinCode + "  unit=" + unit);
                        }
                    }
                }
            }
        }

    }

    public static void loadEthAccounts2Redis() {
        ExDigitalmoneyAccountService exDigitalmoneyAccountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
        List<String> listPublicKey = exDigitalmoneyAccountService.listEtherpublickey();
        RedisUtil.setValue("eth_accounts", Json.toJson(listPublicKey));
    }

    public static void loadBlockNumber2Redis() {
        AppCoinTransactionService txService = (AppCoinTransactionService) ContextUtil.getBean("appCoinTransactionService");
        BigInteger lastestBlockNumber = txService.getLastestBlock();
        BigInteger blockNumber = null;

        try {
            lastestBlockNumber = txService.getLastestBlock();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if (lastestBlockNumber != null) {
            blockNumber = lastestBlockNumber;
        } else {
            blockNumber = getBlockNumber().subtract(BigInteger.TEN);
        }

        if (blockNumber != null) {
            RedisUtil.setValue("blockNumber", Json.toJson(blockNumber));
        }

    }

    static {
        PASSWORD = (String) coinMap.get("eth_password");
    }
}
