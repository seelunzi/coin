
package hry.coin.tv;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.core.util.log.LogFactory;
import hry.dto.model.Wallet;
import hry.utils.JsonResult;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TvUtil {
    private static TvClientFactory btsClientFactory = null;
    private static String serverAddr = (String) Properties.appcoinMap().get("tv_ip");
    private static int port = Integer.valueOf((String) Properties.appcoinMap().get("tv_port")).intValue();
    private static String rpcUsername = (String) Properties.appcoinMap().get("tv_rpcuser");
    private static String rpcPassword = (String) Properties.appcoinMap().get("tv_rpcpassword");
    private static String walleName = (String) Properties.appcoinMap().get("tv_walletName");
    private static String wallePassword = (String) Properties.appcoinMap().get("tv_walletPassword");
    public static String withdAccount = (String) Properties.appcoinMap().get("tv_withdAccount");
    public static String coldAddress_memo = (String) Properties.appcoinMap().get("tv_coldAddress_memo");
    public static BigDecimal Fee = new BigDecimal("0.01");
    private static int openTime = 10;

    public static String clientWallet(String method, List<Object> params) {

        return btsClientFactory.send(method, params, serverAddr, port, rpcUsername, rpcPassword);

    }

    public static boolean openAndUnlockWalle() {
        boolean flag = false;
        try {
            String result = "";
            JSONObject info = null;
            List<Object> openParams = new ArrayList();
            openParams.add(walleName);
            result = clientWallet("wallet_open", openParams);
            info = JSONObject.parseObject(result);
            if ((info != null) && (info.containsKey("error"))) {
                System.out.println("钱包开启失败");
                return flag;
            }
            List<Object> unlockParams = new ArrayList();
            unlockParams.add(Integer.valueOf(openTime));
            unlockParams.add(wallePassword);
            result = clientWallet("wallet_unlock", unlockParams);
            info = JSONObject.parseObject(result);
            if ((info == null) || (info.containsKey("error"))) {
                System.out.println("解锁失败");
                return flag;
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static BigDecimal getbalance(String account) {
        BigDecimal balance = BigDecimal.ZERO;
        try {
            if (openAndUnlockWalle()) {
                List<Object> params = new ArrayList();
                params.add(account);
                String result = clientWallet("wallet_account_balance", params);
                JSONObject info = JSONObject.parseObject(result);
                if ((info == null) || (info.containsKey("error"))) {
                    System.out.println("获取余额失败");
                    return balance;
                }
                JSONArray array = info.getJSONArray("result");
                if (array.size() > 0) {
                    JSONArray tempArr = array.getJSONArray(0).getJSONArray(1).getJSONArray(0);
                    balance = new BigDecimal(tempArr.get(1).toString()).divide(new BigDecimal("100000"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    public static String sendCoinToAddr(String account, String coinType, String amount, String coinAddr, String memo) {
        String entryId = "";
        try {
            if (openAndUnlockWalle()) {
                List<Object> sendParams = new ArrayList();
                sendParams.add(amount);
                sendParams.add(coinType);
                sendParams.add(account);
                sendParams.add(coinAddr);
                sendParams.add(memo);
                sendParams.add("");
                String resultinfo = clientWallet("wallet_transfer_to_address", sendParams);
                JSONObject obj = JSONObject.parseObject(resultinfo);
                if ((obj == null) || (obj.containsKey("error"))) {
                    System.out.println("转账失败:" + resultinfo);
                    return entryId;
                }
                entryId = obj.getJSONObject("result").getString("entry_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entryId;
    }

    public static String createAccount(String account) {
        String addr = "";
        try {
            if (openAndUnlockWalle()) {
                List<Object> sendParams = new ArrayList();
                sendParams.add(account);
                String resultinfo = clientWallet("wallet_account_create", sendParams);
                JSONObject obj = JSONObject.parseObject(resultinfo);
                if (obj.containsKey("error")) {
                    System.out.println("创建地址失败:" + resultinfo);
                    return addr;
                }
                addr = obj.getString("result");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }

    public static Long getBlockCount() {
        long blockCount = 0L;
        try {
            List<Object> sendParams = new ArrayList();
            String resultinfo = clientWallet("blockchain_get_block_count", sendParams);
            JSONObject obj = JSONObject.parseObject(resultinfo);
            if ((obj == null) || (obj.containsKey("error"))) {
                System.out.println("获取区块失败:" + resultinfo);
                return Long.valueOf(blockCount);
            }
            blockCount = obj.getLongValue("result");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.valueOf(blockCount);
    }


    public static String getAccountPublicAddress(String accountName) {
        try {
            List<Object> sendParams = new ArrayList();
            sendParams.add(accountName);
            String resultinfo = clientWallet("wallet_get_account_public_address", sendParams);
            JSONObject obj = JSONObject.parseObject(resultinfo);
            if ((obj.containsKey("result")) && (StringUtils.isNotEmpty(obj.getString("result")))) {
                return obj.getString("result");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getTransactionDetail(long startBlookNum, long endBlookNum, String account) {
        startBlookNum = 0L;
        try {
            if (openAndUnlockWalle()) {
                List<Object> sendParams = new ArrayList();
                sendParams.add(account);
                sendParams.add("tv".toUpperCase());
                sendParams.add(Integer.valueOf(0));
                sendParams.add(Long.valueOf(startBlookNum));
                sendParams.add(Long.valueOf(endBlookNum));
                String resultinfo = clientWallet("wallet_account_transaction_history", sendParams);
                JSONObject obj = JSONObject.parseObject(resultinfo);
                if (obj.containsKey("error")) {
                    System.out.println("获取交易信息失败:" + resultinfo);
                }
                JSONArray arr = obj.getJSONArray("result");
                if (arr.size() > 0) {
                    return arr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean walletCheckAddress(String address) {
        boolean result = false;
        if (openAndUnlockWalle()) {
            List<Object> sendParams = new ArrayList();
            sendParams.add(address);
            String resultinfo = clientWallet("wallet_check_address", sendParams);
            JSONObject obj = JSONObject.parseObject(resultinfo);
            result = obj.getBooleanValue("result");
        }
        return result;
    }


    public static JsonResult send2ColdAddress(String toAddress, String amount) {
        JsonResult result = new JsonResult();
        result.setSuccess(Boolean.valueOf(false));
        String fromAddress = withdAccount;
        String memo = coldAddress_memo;
        BigDecimal need = new BigDecimal(amount).add(Fee);
        BigDecimal waccountHotMoney = getbalance(fromAddress);
        if (waccountHotMoney.compareTo(need) >= 0) {
            if (StringUtils.isNotEmpty(memo)) {
                String entryId = sendCoinToAddr(fromAddress, "tv".toUpperCase(), amount, toAddress, memo);
                if (StringUtils.isNotEmpty(entryId)) {
                    result.setSuccess(Boolean.valueOf(true));
                    result.setMsg(entryId);
                } else {
                    result.setMsg("TV钱包接口ERROR");
                }
            } else {
                result.setMsg("TV冷钱包备注不能为空");
            }
        } else {
            result.setMsg("提币账户可用余额不足");
        }
        return result;
    }


    public static Wallet getWalletInfo() {
        Wallet wallet = new Wallet();
        String withdrawalsAddress = withdAccount;
        String account = withdAccount;
        BigDecimal hotMoney = getbalance(account);
        String total = hotMoney.toString();
        String toMoney = total;
        String coldwalletAddress = (String) Properties.appcoinMap().get("tv_coldAddress");
        wallet.setCoinCode("tv".toUpperCase());
        wallet.setColdwalletAddress(coldwalletAddress == null ? "" : coldwalletAddress);
        wallet.setWithdrawalsAddress(withdrawalsAddress == null ? "" : withdrawalsAddress);
        wallet.setTotalMoney(total);
        wallet.setWithdrawalsAddressMoney(toMoney);
        return wallet;
    }


    public static JsonResult sendFrom(String amount, String toAddress, String memo, String transactionNum) {
        JsonResult result = new JsonResult();
        String fromAccount = withdAccount;
        BigDecimal need = new BigDecimal(amount).add(Fee);
        BigDecimal waccountHotMoney = getbalance(fromAccount);
        if (waccountHotMoney.compareTo(need) >= 0) {
            String txid = sendCoinToAddr(fromAccount, "tv".toUpperCase(), amount, toAddress, memo);
            if (StringUtils.isNotEmpty(txid)) {
                result.setSuccess(Boolean.valueOf(true));
                result.setMsg(txid);
            } else {
                String message = "订单" + transactionNum + "处理失败,返回为空";
                LogFactory.info(message);
                result.setSuccess(Boolean.valueOf(false));
                result.setMsg(message);
            }
        } else {
            result.setSuccess(Boolean.valueOf(false));
            result.setMsg("提币账户可用余额不足");
        }
        return result;
    }
}

