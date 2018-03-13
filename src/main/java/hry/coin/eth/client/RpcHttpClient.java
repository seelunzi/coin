
package hry.coin.eth.client;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import hry.core.util.log.LogFactory;
import hry.utils.Properties;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RpcHttpClient {

    private static String protocol = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_protocol");
    private static String ip = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_ip");
    private static String port = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_port");
    private static volatile JsonRpcHttpClient jsonrpcClient;

    public static JsonRpcHttpClient getClient() {
        if (jsonrpcClient == null) {
            synchronized (RpcHttpClient.class) {
                if (jsonrpcClient == null) {
                    try {
                        String url = protocol + "://" + ip + ":" + port;
                        LogFactory.info("获取eth代币的jsonrpc连接---url=" + url);
                        jsonrpcClient = new JsonRpcHttpClient(new URL(url));
                        Map<String, String> headers = new HashMap();
                        headers.put("Content-Type", "application/json");
                        jsonrpcClient.setHeaders(headers);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonrpcClient;
    }
}
