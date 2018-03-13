
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
    public static boolean openAndUnlockWalle()
    /*     */ {
        /*  50 */
        boolean flag = false;
        /*     */
        try
            /*     */ {
            /*  53 */
            String result = "";
            /*  54 */
            JSONObject info = null;
            /*  55 */
            List<Object> openParams = new ArrayList();
            /*  56 */
            openParams.add(walleName);
            /*  57 */
            result = clientWallet("wallet_open", openParams);
            /*  58 */
            info = JSONObject.parseObject(result);
            /*  59 */
            if ((info != null) && (info.containsKey("error"))) {
                /*  60 */
                System.out.println("钱包开启失败");
                /*  61 */
                return flag;
                /*     */
            }
            /*     */
            /*  64 */
            List<Object> unlockParams = new ArrayList();
            /*  65 */
            unlockParams.add(Integer.valueOf(openTime));
            /*  66 */
            unlockParams.add(wallePassword);
            /*  67 */
            result = clientWallet("wallet_unlock", unlockParams);
            /*  68 */
            info = JSONObject.parseObject(result);
            /*  69 */
            if ((info == null) || (info.containsKey("error"))) {
                /*  70 */
                System.out.println("解锁失败");
                /*  71 */
                return flag;
                /*     */
            }
            /*  73 */
            flag = true;
            /*     */
        } catch (Exception e) {
            /*  75 */
            e.printStackTrace();
            /*     */
        }
        /*  77 */
        return flag;
        /*     */
    }

    public static BigDecimal getbalance(String account)
        /*     */ {
        /*  87 */
        BigDecimal balance = BigDecimal.ZERO;
        /*     */
        try {
            /*  89 */
            if (openAndUnlockWalle()) {
                /*  90 */
                List<Object> params = new ArrayList();
                /*  91 */
                params.add(account);
                /*  92 */
                String result = clientWallet("wallet_account_balance", params);
                /*  93 */
                JSONObject info = JSONObject.parseObject(result);
                /*  94 */
                if ((info == null) || (info.containsKey("error"))) {
                    /*  95 */
                    System.out.println("获取余额失败");
                    /*  96 */
                    return balance;
                    /*     */
                }
                /*  98 */
                JSONArray array = info.getJSONArray("result");
                /*  99 */
                if (array.size() > 0) {
                    /* 100 */
                    JSONArray tempArr = array.getJSONArray(0).getJSONArray(1).getJSONArray(0);
                    /* 101 */
                    balance = new BigDecimal(tempArr.get(1).toString()).divide(new BigDecimal("100000"));
                    /*     */
                }
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /* 105 */
            e.printStackTrace();
            /*     */
        }
        /* 107 */
        return balance;
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
    public static String sendCoinToAddr(String account, String coinType, String amount, String coinAddr, String memo)
    /*     */ {
        /* 123 */
        String entryId = "";
        /*     */
        try {
            /* 125 */
            if (openAndUnlockWalle()) {
                /* 126 */
                List<Object> sendParams = new ArrayList();
                /* 127 */
                sendParams.add(amount);
                /* 128 */
                sendParams.add(coinType);
                /* 129 */
                sendParams.add(account);
                /* 130 */
                sendParams.add(coinAddr);
                /* 131 */
                sendParams.add(memo);
                /* 132 */
                sendParams.add("");
                /* 133 */
                String resultinfo = clientWallet("wallet_transfer_to_address", sendParams);
                /* 134 */
                JSONObject obj = JSONObject.parseObject(resultinfo);
                /* 135 */
                if ((obj == null) || (obj.containsKey("error"))) {
                    /* 136 */
                    System.out.println("转账失败:" + resultinfo);
                    /* 137 */
                    return entryId;
                    /*     */
                }
                /* 139 */
                entryId = obj.getJSONObject("result").getString("entry_id");
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /* 142 */
            e.printStackTrace();
            /*     */
        }
        /* 144 */
        return entryId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static String createAccount(String account)
    /*     */ {
        /* 154 */
        String addr = "";
        /*     */
        try {
            /* 156 */
            if (openAndUnlockWalle()) {
                /* 157 */
                List<Object> sendParams = new ArrayList();
                /* 158 */
                sendParams.add(account);
                /* 159 */
                String resultinfo = clientWallet("wallet_account_create", sendParams);
                /* 160 */
                JSONObject obj = JSONObject.parseObject(resultinfo);
                /* 161 */
                if (obj.containsKey("error")) {
                    /* 162 */
                    System.out.println("创建地址失败:" + resultinfo);
                    /* 163 */
                    return addr;
                    /*     */
                }
                /* 165 */
                addr = obj.getString("result");
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /* 168 */
            e.printStackTrace();
            /*     */
        }
        /* 170 */
        return addr;
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
    public static Long getBlockCount()
    /*     */ {
        /* 183 */
        long blockCount = 0L;
        /*     */
        try {
            /* 185 */
            List<Object> sendParams = new ArrayList();
            /* 186 */
            String resultinfo = clientWallet("blockchain_get_block_count", sendParams);
            /* 187 */
            JSONObject obj = JSONObject.parseObject(resultinfo);
            /* 188 */
            if ((obj == null) || (obj.containsKey("error"))) {
                /* 189 */
                System.out.println("获取区块失败:" + resultinfo);
                /* 190 */
                return Long.valueOf(blockCount);
                /*     */
            }
            /* 192 */
            blockCount = obj.getLongValue("result");
            /*     */
        } catch (Exception e) {
            /* 194 */
            e.printStackTrace();
            /*     */
        }
        /* 196 */
        return Long.valueOf(blockCount);
        /*     */
    }

    /*     */
    /*     */
    public static String getAccountPublicAddress(String accountName) {
        /*     */
        try {
            /* 201 */
            List<Object> sendParams = new ArrayList();
            /* 202 */
            sendParams.add(accountName);
            /* 203 */
            String resultinfo = clientWallet("wallet_get_account_public_address", sendParams);
            /* 204 */
            JSONObject obj = JSONObject.parseObject(resultinfo);
            /* 205 */
            if ((obj.containsKey("result")) && (StringUtils.isNotEmpty(obj.getString("result")))) {
                /* 206 */
                return obj.getString("result");
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /* 209 */
            e.printStackTrace();
            /*     */
        }
        /* 211 */
        return null;
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
    public static JSONArray getTransactionDetail(long startBlookNum, long endBlookNum, String account)
    /*     */ {
        /* 227 */
        startBlookNum = 0L;
        /*     */
        try {
            /* 229 */
            if (openAndUnlockWalle()) {
                /* 230 */
                List<Object> sendParams = new ArrayList();
                /* 231 */
                sendParams.add(account);
                /* 232 */
                sendParams.add("tv".toUpperCase());
                /* 233 */
                sendParams.add(Integer.valueOf(0));
                /* 234 */
                sendParams.add(Long.valueOf(startBlookNum));
                /* 235 */
                sendParams.add(Long.valueOf(endBlookNum));
                /* 236 */
                String resultinfo = clientWallet("wallet_account_transaction_history", sendParams);
                /* 237 */
                JSONObject obj = JSONObject.parseObject(resultinfo);
                /* 238 */
                if (obj.containsKey("error")) {
                    /* 239 */
                    System.out.println("获取交易信息失败:" + resultinfo);
                    /*     */
                }
                /* 241 */
                JSONArray arr = obj.getJSONArray("result");
                /* 242 */
                if (arr.size() > 0)
                    /*     */ {
                    /* 244 */
                    return arr;
                    /*     */
                }
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /* 248 */
            e.printStackTrace();
            /*     */
        }
        /* 250 */
        return null;
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
    /*     */
    public static boolean walletCheckAddress(String address)
    /*     */ {
        /* 268 */
        boolean result = false;
        /* 269 */
        if (openAndUnlockWalle()) {
            /* 270 */
            List<Object> sendParams = new ArrayList();
            /* 271 */
            sendParams.add(address);
            /* 272 */
            String resultinfo = clientWallet("wallet_check_address", sendParams);
            /* 273 */
            JSONObject obj = JSONObject.parseObject(resultinfo);
            /* 274 */
            result = obj.getBooleanValue("result");
            /*     */
        }
        /* 276 */
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
    public static JsonResult send2ColdAddress(String toAddress, String amount)
    /*     */ {
        /* 291 */
        JsonResult result = new JsonResult();
        /* 292 */
        result.setSuccess(Boolean.valueOf(false));
        /* 293 */
        String fromAddress = withdAccount;
        /* 294 */
        String memo = coldAddress_memo;
        /* 295 */
        BigDecimal need = new BigDecimal(amount).add(Fee);
        /* 296 */
        BigDecimal waccountHotMoney = getbalance(fromAddress);
        /* 297 */
        if (waccountHotMoney.compareTo(need) >= 0) {
            /* 298 */
            if (StringUtils.isNotEmpty(memo)) {
                /* 299 */
                String entryId = sendCoinToAddr(fromAddress, "tv".toUpperCase(), amount, toAddress, memo);
                /* 300 */
                if (StringUtils.isNotEmpty(entryId)) {
                    /* 301 */
                    result.setSuccess(Boolean.valueOf(true));
                    /* 302 */
                    result.setMsg(entryId);
                    /*     */
                } else {
                    /* 304 */
                    result.setMsg("TV钱包接口ERROR");
                    /*     */
                }
                /*     */
            } else {
                /* 307 */
                result.setMsg("TV冷钱包备注不能为空");
                /*     */
            }
            /*     */
        } else {
            /* 310 */
            result.setMsg("提币账户可用余额不足");
            /*     */
        }
        /* 312 */
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
    public static Wallet getWalletInfo()
    /*     */ {
        /* 325 */
        Wallet wallet = new Wallet();
        /* 326 */
        String withdrawalsAddress = withdAccount;
        /* 327 */
        String account = withdAccount;
        /* 328 */
        BigDecimal hotMoney = getbalance(account);
        /* 329 */
        String total = hotMoney.toString();
        /* 330 */
        String toMoney = total;
        /* 331 */
        String coldwalletAddress = (String) Properties.appcoinMap().get("tv_coldAddress");
        /* 332 */
        wallet.setCoinCode("tv".toUpperCase());
        /* 333 */
        wallet.setColdwalletAddress(coldwalletAddress == null ? "" : coldwalletAddress);
        /* 334 */
        wallet.setWithdrawalsAddress(withdrawalsAddress == null ? "" : withdrawalsAddress);
        /* 335 */
        wallet.setTotalMoney(total);
        /* 336 */
        wallet.setWithdrawalsAddressMoney(toMoney);
        /* 337 */
        return wallet;
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
    public static JsonResult sendFrom(String amount, String toAddress, String memo, String transactionNum)
    /*     */ {
        /* 354 */
        JsonResult result = new JsonResult();
        /*     */
        /*     */
        /* 357 */
        String fromAccount = withdAccount;
        /* 358 */
        BigDecimal need = new BigDecimal(amount).add(Fee);
        /* 359 */
        BigDecimal waccountHotMoney = getbalance(fromAccount);
        /* 360 */
        if (waccountHotMoney.compareTo(need) >= 0)
            /*     */ {
            /* 362 */
            String txid = sendCoinToAddr(fromAccount, "tv".toUpperCase(), amount, toAddress, memo);
            /* 363 */
            if (StringUtils.isNotEmpty(txid)) {
                /* 364 */
                result.setSuccess(Boolean.valueOf(true));
                /* 365 */
                result.setMsg(txid);
                /*     */
            } else {
                /* 367 */
                String message = "订单" + transactionNum + "处理失败,返回为空";
                /* 368 */
                LogFactory.info(message);
                /* 369 */
                result.setSuccess(Boolean.valueOf(false));
                /* 370 */
                result.setMsg(message);
                /*     */
            }
            /*     */
        } else {
            /* 373 */
            result.setSuccess(Boolean.valueOf(false));
            /* 374 */
            result.setMsg("提币账户可用余额不足");
            /*     */
        }
        /* 376 */
        return result;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\tv\TvUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */