
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

        }
        /*    */ catch (UnsupportedEncodingException e2) {
            /* 47 */
            e2.printStackTrace();
            /*    */
        }
        /* 49 */
        String result = "";
        /* 50 */
        HttpClient client = new DefaultHttpClient();
        /*    */
        try
            /*    */ {
            /* 53 */
            HttpGet get = new HttpGet(url);
            /*    */
            /* 55 */
            get.setHeader("charset", "UTF-8");
            /* 56 */
            HttpResponse response = client.execute(get);
            /* 57 */
            if (200 == response.getStatusLine().getStatusCode()) {
                /* 58 */
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
                /*    */
            }
            /*    */
            /*    */
        }
        /*    */ catch (ClientProtocolException e)
            /*    */ {
            /* 64 */
            e.printStackTrace();
            /*    */
        }
        /*    */ catch (IOException e) {
            /* 67 */
            e.printStackTrace();
            /*    */
        }
        /* 69 */
        return result;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    public static JsonRpcHttpClient getClient()
    /*    */ {
        /* 79 */
        if (jsonrpcClient == null) {
            /* 80 */
            synchronized (BdsRpcHttpClient.class) {
                /* 81 */
                if (jsonrpcClient == null) {
                    /*    */
                    try {
                        /* 83 */
                        LogFactory.info("获取bds币的rpc连接成功");
                        /* 84 */
                        String url = protocol + "://" + ip + ":" + port;
                        /* 85 */
                        jsonrpcClient = new JsonRpcHttpClient(new URL(url));
                        /*    */
                    }
                    /*    */ catch (MalformedURLException e) {
                        /* 88 */
                        LogFactory.info("bds钱包接口连接失败");
                        /*    */
                    }
                    /*    */
                }
                /*    */
            }
            /*    */
        }

        return jsonrpcClient;

    }

}
