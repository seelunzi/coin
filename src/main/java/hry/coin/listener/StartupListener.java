
package hry.coin.listener;


import hry.coin.eth.service.impl.EtherService;
import hry.core.quartz.QuartzJob;
import hry.core.quartz.QuartzManager;
import hry.core.quartz.ScheduleJob;
import hry.core.util.sys.AppUtils;
import hry.utils.Properties;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class StartupListener
        extends ContextLoaderListener {

    public void contextInitialized(ServletContextEvent event) {

        super.contextInitialized(event);
        /*     */
        /*  35 */
        AppUtils.init(event.getServletContext());

        /*  39 */
        ScheduleJob btc_consumeTx = new ScheduleJob();
        /*  40 */
        btc_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
        /*  41 */
        btc_consumeTx.setMethodName("btc_consumeTx");
        /*  42 */
        QuartzManager.addJob("btc_consumeTx", btc_consumeTx, QuartzJob.class, "0 0/2 * * * ?");
        /*  47 */
        if (Properties.appcoinMap().containsKey("ETH".toLowerCase() + "_ip")) {
            /*  48 */
            EtherService.loadEthAccounts2Redis();

            /*  50 */
            ScheduleJob ether_productionTx = new ScheduleJob();
            /*  51 */
            ether_productionTx.setBeanClass("hry.coin.quart.CoinQuart");
            /*  52 */
            ether_productionTx.setMethodName("ether_productionTx");
            /*  53 */
            QuartzManager.addJob("ether_productionTx", ether_productionTx, QuartzJob.class, "0 0/1 * * * ?");
            /*     */
            /*     */
            /*     */
            /*  57 */
            ScheduleJob ether_consumeTx = new ScheduleJob();
            /*  58 */
            ether_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            /*  59 */
            ether_consumeTx.setMethodName("ether_consumeTx");
            /*  60 */
            QuartzManager.addJob("ether_consumeTx", ether_consumeTx, QuartzJob.class, "0 0/2 * * * ?");
            /*     */
            /*     */
            /*  63 */
            ScheduleJob send2coinBaseJob = new ScheduleJob();
            /*  64 */
            send2coinBaseJob.setBeanClass("hry.coin.eth.service.impl.EtherService");
            /*  65 */
            send2coinBaseJob.setMethodName("send2coinBaseJob");
            /*  66 */
            QuartzManager.addJob("send2coinBaseJob", send2coinBaseJob, QuartzJob.class, "0 0 0/2 * * ?");
            /*     */
        }
        /*     */
        /*     */
        /*     */
        /*  71 */
        if (Properties.appcoinMap().containsKey("tv_ip"))
            /*     */ {
            /*  73 */
            ScheduleJob tv_consumeTx = new ScheduleJob();
            /*     */
            /*  75 */
            tv_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            /*  76 */
            tv_consumeTx.setMethodName("tv_consumeTx");
            /*  77 */
            QuartzManager.addJob("tv_consumeTx", tv_consumeTx, QuartzJob.class, "0 0/2 * * * ?");
            /*     */
            /*     */
            /*  80 */
            ScheduleJob tv_send2coinbase = new ScheduleJob();
            /*  81 */
            tv_send2coinbase.setBeanClass("hry.coin.quart.CoinQuart");
            /*  82 */
            tv_send2coinbase.setMethodName("tv_send2coinbase");
            /*  83 */
            QuartzManager.addJob("tv_send2coinbase", tv_send2coinbase, QuartzJob.class, "0 0 0/2 * * ?");
            /*     */
        }
        /*     */
        /*     */
        /*  87 */
        if ((!Properties.appcoinMap().containsKey("USDT".toLowerCase() + "_ip")) ||


                (Properties.appcoinMap().containsKey("BDS".toLowerCase() + "_ip"))) {
            ScheduleJob bds_consumeTx = new ScheduleJob();
            bds_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            bds_consumeTx.setMethodName("bds_consumeTx");
            QuartzManager.addJob("bds_consumeTx", bds_consumeTx, QuartzJob.class, "0 0/5 * * * ?");
        }


        /* 101 */
        if (Properties.appcoinMap().containsKey("BDS".toLowerCase() + "_ip"))
            /*     */ {
            /* 103 */
            ScheduleJob neo_consumeTx = new ScheduleJob();
            /* 104 */
            neo_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            /* 105 */
            neo_consumeTx.setMethodName("neo_consumeTx");
            /* 106 */
            QuartzManager.addJob("neo_consumeTx", neo_consumeTx, QuartzJob.class, "0 0/5 * * * ?");
            /*     */
        }
        /*     */
    }
    /*     */
}
