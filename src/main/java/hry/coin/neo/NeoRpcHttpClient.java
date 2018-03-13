
package hry.coin.neo;


import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import hry.coin.bds.BdsRpcHttpClient;
import hry.core.util.log.LogFactory;
import hry.utils.Properties;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


public class NeoRpcHttpClient {
    private static String protocol = (String) Properties.appcoinMap().get("NEO".toLowerCase() + "_protocol");
    private static String ip = (String) Properties.appcoinMap().get("NEO".toLowerCase() + "_ip");
    private static String port = (String) Properties.appcoinMap().get("NEO".toLowerCase() + "_port");
    private static volatile JsonRpcHttpClient jsonrpcClient;

    public static String getHttpClient(String methodname, List list) {
        String url = "";
        try {
            url = protocol + "://" + ip + ":" + port + "?jsonrpc=2.0&id=1&method=" + methodname + "&params=" + URLEncoder.encode(new StringBuilder().append(list).append("").toString(), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        String result = "";
        HttpClient client = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("charset", "UTF-8");
            HttpResponse response = client.execute(get);
            if (200 == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

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
