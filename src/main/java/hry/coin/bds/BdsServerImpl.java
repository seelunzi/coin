
package hry.coin.bds;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import hry.coin.BtsServer;
import hry.core.util.log.LogFactory;
import hry.dto.model.Wallet;
import hry.exchange.coin.model.Transaction;
import hry.utils.JsonResult;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BdsServerImpl
        implements BtsServer {
    private JsonRpcHttpClient client = BdsRpcHttpClient.getClient();

    @Override
    public BigDecimal getBalance(String accountName) {
        BigDecimal balance = BigDecimal.ZERO;
        String methodName = "list_account_balances";
        List<String> list = new ArrayList();
        List result = null;
        list.add(accountName);
        try {
            result = (List) this.client.invoke(methodName, list, List.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if ((result != null) && (!result.isEmpty())) {
            Map<String, Object> map = (Map) result.get(0);
            String amount_ = map.get("amount").toString();
            balance = new BigDecimal(amount_).divide(BigDecimal.valueOf(100000L), 8, 1);
        } else {
            LogFactory.info("未查询到数据accountName=" + accountName);
        }
        return balance;
    }

    @Override
    public String getPublicKey(String AccountNum) {
        String chargeAccount = BdsRpcHttpClient.chargeAccount;
        String memo = AccountNum;
        return "ACCOUNT:" + chargeAccount + ",MEMO:" + memo;
    }

    @Override
    public boolean unlock(String password) {
        boolean result = false;
        String unlockMethodName = "unlock";
        List<String> unlockparams = new ArrayList();
        unlockparams.add(password);
        try {
            Object unlockResult = this.client.invoke(unlockMethodName, unlockparams, Object.class);
            if (unlockResult == null) {
                result = true;
            }
        } catch (ConnectException e) {
            LogFactory.info("unlock-bds钱包拒绝连接");
        } catch (Throwable e) {
            LogFactory.info("unlock-bds钱包接口error");
        }
        return result;
    }

    @Override
    public boolean lock() {
        boolean result = false;
        String unlockMethodName = "lock";
        List<String> unlockparams = new ArrayList();
        try {
            Object unlockResult = this.client.invoke(unlockMethodName, unlockparams, Object.class);
            if (unlockResult == null) {
                result = true;
            }
        } catch (ConnectException e) {
            LogFactory.info("lock-bds钱包拒绝连接");
        } catch (Throwable e) {
            LogFactory.info("lock-bds钱包接口error");
        }
        return result;
    }

    @Override
    public String getAccountHistory(String accountName, String count, String id, String password) {
        String result = null;
        List<Transaction> listtx = new ArrayList();
        if (unlock(password)) {
            String methodName = "get_account_history1";
            List<String> list = new ArrayList();
            list.add(accountName);
            list.add(count);
            list.add(id);
            List<Map> history = new ArrayList();
            try {
                history = (List) this.client.invoke(methodName, list, List.class);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            for (Map<String, Object> l : history) {
                String memo = l.get("memo").toString();
                if (StringUtils.isNotEmpty(memo)) {
                    Map<String, Object> op = (Map) l.get("op");
                    if (op != null) {
                        int block_num = ((Integer) op.get("block_num")).intValue();
                        String txId = op.get("id").toString();
                        List<Map<String, Object>> opsub = (List) op.get("op");
                        if ((opsub != null) && (opsub.get(1) != null)) {
                            Map<String, Object> data = (Map) opsub.get(1);
                            Map<String, Object> feeMap = (Map) data.get("fee");
                            Map<String, Object> amountMap = (Map) data.get("amount");
                            String fee_ = feeMap.get("amount").toString();
                            String amount_ = amountMap.get("amount").toString();
                            Double fee = Double.valueOf(Double.valueOf(fee_).doubleValue() / 100000.0D);
                            Double amount = Double.valueOf(Double.valueOf(amount_).doubleValue() / 100000.0D);
                            int blockNumber = block_num;
                            String address = getPublicKey(memo);
                            String time = System.currentTimeMillis() + "";
                            time = time.substring(0, time.length() - 3);
                            String txIdType = txId + "_transfer";
                            Transaction transaction = new Transaction();
                            transaction.setTime(time);
                            transaction.setTimeReceived(time);
                            transaction.setBlockTime(time);
                            transaction.setAddress(address);
                            transaction.setCoinType("BDS");
                            transaction.setConfirmations(1);
                            transaction.setTxId(txId);
                            transaction.setTxIdType(txIdType);
                            transaction.setFee(fee.doubleValue());
                            transaction.setAmount(amount.doubleValue());
                            transaction.setBlockIndex(blockNumber);
                            listtx.add(transaction);
                            if ((listtx != null) && (!listtx.isEmpty())) {
                                result = Json.toJson(listtx);
                            } else {
                                LogFactory.info("bds 未获得充币数据");
                            }
                        }
                    } else {
                        LogFactory.info("bds-op未查询到数据");
                    }
                } else {
                    LogFactory.info("bds没有备注的交易");
                }
            }
        } else {
            LogFactory.info("解锁失败");
        }
        lock();
        return result;

    }

    @Override
    public String transfer(String fromAccount, String toAccount, String amount, String symbol, String memo) {
        String txId = null;
        String password = BdsRpcHttpClient.walletPassword;
        if (unlock(password)) {
            String methodname = "transfer2";
            List<Object> list = new ArrayList();
            list.add(fromAccount);
            list.add(toAccount);
            list.add(amount);
            list.add(symbol);
            list.add(memo);
            try {
                Object result = this.client.invoke(methodname, list, Object.class);
                List<String> data = (List) result;
                if ((data != null) && (!data.isEmpty())) {
                    txId = (String) data.get(0);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            LogFactory.info("解锁失失败");
        }
        lock();
        return txId;
    }

    @Override
    public JsonResult send2ColdAddress(String toAddress, String amount) {
        JsonResult result = new JsonResult();
        String memo = BdsRpcHttpClient.memo;
        String coldAccount = toAddress;
        String fromAccount = BdsRpcHttpClient.chargeAccount;
        String txHash = transfer(fromAccount, coldAccount, amount, "BDS", memo);
        if (StringUtils.isNotEmpty(txHash)) {
            result.setSuccess(Boolean.valueOf(true));
            result.setMsg(txHash);
        } else {
            result.setMsg("BDS转币接口ERROR");
        }
        return result;
    }

    @Override
    public JsonResult sendFrom(String amount, String toAddress, String memo) {
        JsonResult result = new JsonResult();
        String fromAccount = BdsRpcHttpClient.chargeAccount;
        BigDecimal money = BigDecimal.valueOf(Double.valueOf(amount).doubleValue());
        BigDecimal fee = BigDecimal.valueOf(0.5D);
        BigDecimal chargeAccountMoney = getBalance(fromAccount).subtract(fee);
        if (chargeAccountMoney.compareTo(money) >= 0) {
            String txId = transfer(fromAccount, toAddress, amount, "BDS", memo);
            if (StringUtils.isNotEmpty(txId)) {
                result.setSuccess(Boolean.valueOf(true));
                result.setMsg(txId);
            } else {
                result.setSuccess(Boolean.valueOf(false));
                result.setMsg("错误、提币账户余额不足");
            }
        } else {
            result.setSuccess(Boolean.valueOf(false));
            result.setMsg("提币账户可用余额不足");
        }
        return result;
    }

    @Override
    public Wallet getWalletInfo() {
        Wallet wallet = new Wallet();
        String chargeAccount = BdsRpcHttpClient.chargeAccount;
        String total = getBalance(chargeAccount).toString();
        String toMoney = total;
        String coldAddress = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_coldAddress");
        wallet.setCoinCode("BDS");
        wallet.setColdwalletAddress(coldAddress == null ? "" : coldAddress);
        wallet.setWithdrawalsAddress(chargeAccount == null ? "" : chargeAccount);
        wallet.setWithdrawalsAddressMoney(toMoney);
        wallet.setTotalMoney(total);
        return wallet;
    }
}
