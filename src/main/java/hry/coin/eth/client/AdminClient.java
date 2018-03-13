package hry.coin.eth.client;

import hry.core.util.log.LogFactory;
import hry.utils.Properties;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.http.HttpService;

public class AdminClient {
    private static String protocol = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_protocol");
    private static String ip = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_ip");
    private static String port = (String) Properties.appcoinMap().get("ETH".toLowerCase() + "_port");
    private static volatile Admin admin;

    public static JsonRpc2_0Admin getClient() {
        if (admin == null) {
            synchronized (Admin.class) {
                if (admin == null) {
                    String url = protocol + ":" + ip + ":" + port;
                    LogFactory.info("获取eth的jsonrpc连接===url=" + url);
                    /***
                     *连接以太坊的钱包节点,得到以太坊钱包管理器
                     * */
                    admin = Admin.build(new HttpService(url));
                }
            }
        }
        return (JsonRpc2_0Admin) admin;
    }
}

