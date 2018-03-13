
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
public class NeoServiceImpl
        implements BtsServer, NeoService {

    @Override
    public BigDecimal getBalance(String accountName) {
        BigDecimal balance = BigDecimal.ZERO;
        String methodname = "getbalance";
        Object result;
        String amount_;
        Map<String, Object> map;
//        List list = new ArrayList();

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

    @Override
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
    public boolean unlock(String password) {
        return false;
    }

    public boolean lock() {
        return false;
    }

    public String getAccountHistory(String accountName, String count, String id, String password) {
        return null;
    }

    public String transfer(String fromAccount, String toAccount, String amount, String symbol, String memo) {
        return null;
    }

    @Override
    public Map getaccountstate(String address) {
        Map resultMap = new HashMap();
        String asset = "";
        String methodname = "getaccountstate";
        Object result = "";
        List list = new ArrayList();
        Map<String, Object> map = new HashMap();
        try {
            List paramlist = new ArrayList();
            paramlist.add('"' + address + '"');
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            LogFactory.info(result + "");
            JSONObject json = JSONObject.parseObject(result + "");
            map = (Map) json.get("result");
            List<Map> listresult = new ArrayList();
            listresult = (List) map.get("balances");
            if ((listresult == null) || (listresult.size() == 0)) {
                return resultMap;
            }
            resultMap = (Map) listresult.get(0);
        } catch (Throwable e) {
            e.printStackTrace();
            LogFactory.error("查询账户资产信息失败！");
        }
        return resultMap;

    }

    @Override
    public List<NeoEntity> getblock(String index) {
        List<NeoEntity> listNeo = new ArrayList();
        List<Map> listtx = new ArrayList();
        String methodname = "getblock";
        Object result = "";
        String asset = "";
        Map<String, Object> map = new HashMap();
        try {
            List paramlist = new ArrayList();
            paramlist.add(index);
            paramlist.add(Integer.valueOf(1));
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            LogFactory.info(result + "");
            JSONObject json = JSONObject.parseObject(result + "");
            if (json != null) {
                map = (Map) json.get("result");
                listtx = (List) map.get("tx");
                if (null == listtx) {
                    return listNeo;
                }
                for (int i = 0; i < listtx.size(); i++) {
                    if ("ContractTransaction".equals(((Map) listtx.get(i)).get("type"))) {
                        Map mapVin = new HashMap();
                        mapVin = (Map) ((Map) listtx.get(i)).get("vin");
                        if (null != mapVin) {
                            NeoEntity neoEntity = new NeoEntity();
                            neoEntity.setN(mapVin.get("n") + "");
                            neoEntity.setAsset(mapVin.get("asset") + "");
                            neoEntity.setValue(mapVin.get("value") + "");
                            neoEntity.setAddress(mapVin.get("address") + "");
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            LogFactory.error("生成neo钱包中对应资产的余额信息失败！");
        }
        return listNeo;
    }

    @Override
    public boolean validateAddress(String validateaddress) {
        boolean flag = false;
        String methodname = "validateaddress";
        Object result = "";
        List list = new ArrayList();
        Map<String, Object> map = new HashMap();
        try {
            List paramlist = new ArrayList();
            paramlist.add('"' + validateaddress + '"');
            result = NeoRpcHttpClient.getHttpClient(methodname, paramlist);
            LogFactory.info(result + "");
            JSONObject json = JSONObject.parseObject(result + "");
            map = (Map) json.get("result");
            if (((Boolean) map.get("isvalid")).booleanValue()) {
                flag = true;
            }
        } catch (Throwable e) {
            e.printStackTrace();
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

    @Override
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
