
package hry.coin.bds;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import hry.core.util.log.LogFactory;
import hry.utils.Properties;

import java.net.MalformedURLException;
import java.net.URL;

public class BdsRpcHttpClient {
    private static String protocol = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_protocol");
    private static String ip = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_ip");
    private static String port = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_port");
    public static final String chargeAccount = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_chargeAccount");
    public static final String walletPassword = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_walletPassword");
    public static final String id = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_id");
    public static final String memo = (String) Properties.appcoinMap().get("BDS".toLowerCase() + "_coldMemo");
    private static volatile JsonRpcHttpClient jsonrpcClient;

    public static JsonRpcHttpClient getClient() {
        if (jsonrpcClient == null) {
            synchronized (BdsRpcHttpClient.class) {
                if (jsonrpcClient == null) {
                    try {
                        LogFactory.info("获取bds币的rpc连接成功");
                        String url = protocol + "://" + ip + ":" + port;
                        jsonrpcClient = new JsonRpcHttpClient(new URL(url));
                    } catch (MalformedURLException e) {
                        LogFactory.info("bds钱包接口连接失败");
                    }

                }
            }
        }
        return jsonrpcClient;
    }
}