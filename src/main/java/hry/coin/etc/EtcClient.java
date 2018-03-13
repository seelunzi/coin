package hry.coin.etc;

import hry.core.util.log.LogFactory;
import hry.utils.Properties;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.http.HttpService;

public class EtcClient {
    private static String protocol = (String) Properties.appcoinMap().get("ETC".toLowerCase() + "_protocol");
    private static String ip = (String) Properties.appcoinMap().get("ETC".toLowerCase() + "_ip");
    private static String port = (String) Properties.appcoinMap().get("ETC".toLowerCase() + "_port");
    private static volatile Admin admin;

    public static JsonRpc2_0Admin getClient() {
        if (admin == null) {
            synchronized (Admin.class) {
                if (admin == null) {
                    LogFactory.info("获取etc的jsonrpc连接");
                    String url = protocol + ":" + ip + ":" + port;
                    admin = Admin.build(new HttpService(url));
                }
            }
        }
        return (JsonRpc2_0Admin) admin;
    }
}
