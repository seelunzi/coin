
package hry.coin.neo;

import com.alibaba.fastjson.JSONObject;
import hry.coin.BtsServer;
import hry.core.util.log.LogFactory;
import hry.dto.model.Wallet;
import hry.utils.JsonResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

public class NeoServiceImpl
        implements BtsServer, NeoService {

    @Override
    public BigDecimal getBalance(String accountName) {
        BigDecimal balance = BigDecimal.ZERO;
        String methodname = "getbalance";
        Object result = "";
        String amount_ = "";
        Map<String, Object> map = new HashMap();
        List list = new ArrayList();

        try {
            List paramlist = new ArrayList();
            Map addressMap = new HashMap();
            String id = "";
            addressMap = getaccountstate(accountName);
            if (null == addressMap) {
                LogFactory.info("未查询到账户资产信息");
                return balance;
            }
            id = addressMap.get("asset") + "";
            if ("0".equals(id)) {
                LogFactory.info("未查询到账户资产信息");
                return balance;
            }
            paramlist.add('"' + id + '"');
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            LogFactory.info(result + "");
            JSONObject json = JSONObject.parseObject(result + "");
            if (json != null) {


                map = (Map) json.get("result");

                amount_ = map.get("balance") + "";

                balance = BigDecimal.valueOf(Double.valueOf(amount_).doubleValue());

            }

        } catch (Throwable e) {

            e.printStackTrace();

            LogFactory.error("生成neo钱包中对应资产的余额信息失败！");

        }

        return balance;

    }

    public String getPublicKey(String userName) {

        String methodname = "getnewaddress";

        Object result = "";

        String address = "";

        List list = new ArrayList();

        try {

            List paramlist = new ArrayList();

            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);

            LogFactory.info(result + "");

            JSONObject json = JSONObject.parseObject(result + "");

            address = json.get("result") + "";

        } catch (Throwable e) {
            e.printStackTrace();
            LogFactory.error("生成neo地址失败！");
        }
        return address;

    }

    @Override
    public boolean unlock(String password)
        /*     */ {
        /* 102 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean lock()
    /*     */ {
        /* 108 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public String getAccountHistory(String accountName, String count, String id, String password)
    /*     */ {
        /* 114 */
        return null;
        /*     */
    }

    /*     */
    /*     */
    public String transfer(String fromAccount, String toAccount, String amount, String symbol, String memo)
    /*     */ {
        /* 119 */
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
    @Override
    public Map getaccountstate(String address)
    /*     */ {
        /* 130 */
        Map resultMap = new HashMap();
        /* 131 */
        String asset = "";
        /* 132 */
        String methodname = "getaccountstate";
        /* 133 */
        Object result = "";
        /* 134 */
        List list = new ArrayList();
        /* 135 */
        Map<String, Object> map = new HashMap();
        /*     */
        try {
            /* 137 */
            List paramlist = new ArrayList();
            /* 138 */
            paramlist.add('"' + address + '"');
            /* 139 */
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            /* 140 */
            LogFactory.info(result + "");
            /* 141 */
            JSONObject json = JSONObject.parseObject(result + "");
            /* 142 */
            map = (Map) json.get("result");
            /* 143 */
            List<Map> listresult = new ArrayList();
            /* 144 */
            listresult = (List) map.get("balances");
            /* 145 */
            if ((listresult == null) || (listresult.size() == 0)) {
                /* 146 */
                return resultMap;
                /*     */
            }
            /* 148 */
            resultMap = (Map) listresult.get(0);
            /*     */
        }
        /*     */ catch (Throwable e)
            /*     */ {
            /* 152 */
            e.printStackTrace();
            /* 153 */
            LogFactory.error("查询账户资产信息失败！");
            /*     */
        }

        return resultMap;

    }

    @Override
    public List<NeoEntity> getblock(String index)
        /*     */ {
        /* 166 */
        List<NeoEntity> listNeo = new ArrayList();
        /* 167 */
        List<Map> listtx = new ArrayList();
        /* 168 */
        String methodname = "getblock";
        /* 169 */
        Object result = "";
        /* 170 */
        String asset = "";
        /* 171 */
        Map<String, Object> map = new HashMap();
        /*     */
        try {
            /* 173 */
            List paramlist = new ArrayList();
            /* 174 */
            paramlist.add(index);
            /* 175 */
            paramlist.add(Integer.valueOf(1));
            /* 176 */
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            /* 177 */
            LogFactory.info(result + "");
            /* 178 */
            JSONObject json = JSONObject.parseObject(result + "");
            /* 179 */
            if (json != null) {
                /* 180 */
                map = (Map) json.get("result");
                /* 181 */
                listtx = (List) map.get("tx");
                /* 182 */
                if (null == listtx) {
                    /* 183 */
                    return listNeo;
                    /*     */
                }
                /* 185 */
                for (int i = 0; i < listtx.size(); i++) {
                    /* 186 */
                    if ("ContractTransaction".equals(((Map) listtx.get(i)).get("type"))) {
                        /* 187 */
                        Map mapVin = new HashMap();
                        /* 188 */
                        mapVin = (Map) ((Map) listtx.get(i)).get("vin");
                        /* 189 */
                        if (null != mapVin)
                            /*     */ {
                            /*     */
                            /* 192 */
                            NeoEntity neoEntity = new NeoEntity();
                            /* 193 */
                            neoEntity.setN(mapVin.get("n") + "");
                            /* 194 */
                            neoEntity.setAsset(mapVin.get("asset") + "");
                            /* 195 */
                            neoEntity.setValue(mapVin.get("value") + "");
                            /* 196 */
                            neoEntity.setAddress(mapVin.get("address") + "");
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */ catch (Throwable e)
            /*     */ {
            /* 204 */
            e.printStackTrace();
            /* 205 */
            LogFactory.error("生成neo钱包中对应资产的余额信息失败！");
            /*     */
        }
        /*     */
        /* 208 */
        return listNeo;
        /*     */
    }

    /*     */
    /*     */
    public boolean validateAddress(String validateaddress)
    /*     */ {
        /* 213 */
        boolean flag = false;
        /* 214 */
        String methodname = "validateaddress";
        /* 215 */
        Object result = "";
        /* 216 */
        List list = new ArrayList();
        /* 217 */
        Map<String, Object> map = new HashMap();
        /*     */
        try {
            /* 219 */
            List paramlist = new ArrayList();
            /* 220 */
            paramlist.add('"' + validateaddress + '"');
            /* 221 */
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            /* 222 */
            LogFactory.info(result + "");
            /* 223 */
            JSONObject json = JSONObject.parseObject(result + "");
            /* 224 */
            map = (Map) json.get("result");
            /* 225 */
            if (((Boolean) map.get("isvalid")).booleanValue()) {
                /* 226 */
                flag = true;
                /*     */
            }
            /*     */
        }
        /*     */ catch (Throwable e)
            /*     */ {
            /* 231 */
            e.printStackTrace();
            /* 232 */
            LogFactory.error("生成neo地址失败！");

        }

        return flag;

    }


    @Override
    public Map sendfrom(String asset_id, String from, String to, String value, String fee) {
        Map mapresult = new HashMap();
        if ((null == fee) || ("".equals(fee))) {
            fee = "0";
        }
        String asset = "";
        BigDecimal balance = BigDecimal.ZERO;
        String methodname = "sendfrom";
        Object result = "";
        String amount_ = "";
        Map<String, Object> map = new HashMap();
        List list = new ArrayList();
        try {
            List paramlist = new ArrayList();
            paramlist.add('"' + asset_id + '"');
            paramlist.add('"' + from + '"');
            paramlist.add('"' + to + '"');
            paramlist.add(value);
            if (!fee.equals("0")) {
                paramlist.add(fee);
            }

            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);

            LogFactory.info(result + "");

            JSONObject json = JSONObject.parseObject(result + "");

            map = (Map) json.get("result");

        } catch (Throwable e) {
            e.printStackTrace();
            LogFactory.error("生成neo钱包中对应资产的余额信息失败！");
        }
        return mapresult;
    }

    @Override
    public int getblockcount() {
        int index = 0;
        List<NeoEntity> listNeo = new ArrayList();
        String methodname = "getblockcount";
        Object result = "";
        String asset = "";
        Map<String, Object> map = new HashMap();
        try {

            List paramlist = new ArrayList();
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            LogFactory.info(result + "");
            JSONObject json = JSONObject.parseObject(result + "");
            if ((json != null) && (json.get("result") != null)) {
                index = Integer.parseInt(json.get("result") + "");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            LogFactory.error("获取主链中区块的数量失败！");

        }
        return index;
    }

    public JsonResult send2ColdAddress(String toAddress, String amount) {

        return null;

    }

    @Override
    public JsonResult sendFrom(String amount, String toAddress, String memo) {
        return null;
    }

    @Override
    public Wallet getWalletInfo() {
        return null;
    }
}
