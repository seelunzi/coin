
package hry.coin.tv;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.core.util.log.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class TvClientFactory {
    private static volatile TvClientFactory btsClientFactory;


    public static TvClientFactory getSingleton() {

        if (btsClientFactory == null) {

            synchronized (TvClientFactory.class) {

                if (btsClientFactory == null) {

                    btsClientFactory = new TvClientFactory();

                }

            }

        }

        return btsClientFactory;
    }

    public JSONObject headInfo(String method) {

        JSONObject sendObject = new JSONObject();
        /*  36 */
        sendObject.put("jsonrpc", "2.0");
        /*  37 */
        sendObject.put("id", "1");
        /*  38 */
        sendObject.put("method", method);

        return sendObject;

    }

    public boolean login(PrintWriter os, BufferedReader is, String username, String password) throws IOException {
        /*  43 */
        boolean flg = false;
        /*     */
        /*  45 */
        JSONObject sendObject = headInfo("login");
        /*  46 */
        JSONArray paramsObject = new JSONArray();
        /*  47 */
        paramsObject.add(username);
        /*  48 */
        paramsObject.add(password);
        /*  49 */
        sendObject.put("params", paramsObject);
        /*  50 */
        String sendMessage = sendObject.toJSONString();
        /*  51 */
        os.println(sendMessage);
        /*  52 */
        os.flush();
        /*  53 */
        String returnMessage = is.readLine();
        /*  54 */
        JSONObject returnObject = JSONObject.parseObject(returnMessage);
        /*  55 */
        String result = returnObject.getString("result");
        /*  56 */
        if ("true".equals(result)) {
            /*  57 */
            flg = true;
            /*     */
        } else {
            /*  59 */
            System.out.println("链接钱包失败");
            /*     */
        }
        /*  61 */
        return flg;
        /*     */
    }

    public String send(String method, List<Object> params, String addr, int port, String username, String password) {
        /*  65 */
//         result = null;
        /*  66 */
        JSONObject sendObject = headInfo(method);
        /*  67 */
        JSONArray paramsObject = new JSONArray();
        /*  68 */
        paramsObject.addAll(params);
        /*  69 */
        sendObject.put("params", paramsObject);
        /*  70 */
        String sendMessage = sendObject.toJSONString();
        /*  71 */
        PrintWriter os = null;
        /*  72 */
        BufferedReader is = null;
        /*  73 */
        Socket socket = null;
        /*     */
        try
            /*     */ {
            /*  76 */
            socket = new Socket(addr, port);
            /*  77 */
            os = new PrintWriter(socket.getOutputStream());
            /*  78 */
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*     */
            JSONObject returnObject = null;
            /*  80 */
            if (login(os, is, username, password))
                /*     */ {
                /*     */
                /*  83 */
                os.println(sendMessage);
                /*  84 */
                os.flush();
                /*     */
                /*  86 */
                String returnMessage = is.readLine();
                /*     */
                /*  88 */
                returnObject = JSONObject.parseObject(returnMessage);
            }
            /*  89 */
            return returnObject.toJSONString();
            /*     */
            /*     */
            /*     */
            /*     */
        }
        /*     */ catch (IOException e)
            /*     */ {
            /*     */
            /*     */
            /*     */
            /*  99 */
            LogFactory.info("TV钱包接口连接失败");
            /*     */
        } finally {
            /*     */
            try {
                /* 102 */
                is.close();
                /*     */
            }
            /*     */ catch (Throwable e) {
                /* 105 */
                LogFactory.info("TV钱包接口连接失败");
                /*     */
            }
            /*     */
            try {
                /* 108 */
                os.close();
                /*     */
            }
            /*     */ catch (Throwable e) {
                /* 111 */
                LogFactory.info("TV钱包接口连接失败");
                /*     */
            }
            /*     */
            try {
                /* 114 */
                socket.close();
                /*     */
            }
            /*     */ catch (Throwable e) {
                /* 117 */
                LogFactory.info("TV钱包接口连接失败");
                /*     */
            }
            /*     */
        }
        return null;
    }

}
