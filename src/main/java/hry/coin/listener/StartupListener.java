
package hry.coin.listener;


import hry.coin.eth.service.impl.EtherServiceImpl;
import hry.core.quartz.QuartzJob;
import hry.core.quartz.QuartzManager;
import hry.core.quartz.ScheduleJob;
import hry.core.util.sys.AppUtils;
import hry.utils.Properties;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class StartupListener
        extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {

        super.contextInitialized(event);
        AppUtils.init(event.getServletContext());
        ScheduleJob btc_consumeTx = new ScheduleJob();
        btc_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
        btc_consumeTx.setMethodName("btc_consumeTx");
        QuartzManager.addJob("btc_consumeTx", btc_consumeTx, QuartzJob.class, "0 0/2 * * * ?");
        if (Properties.appcoinMap().containsKey("ETH".toLowerCase() + "_ip")) {
            EtherServiceImpl.loadEthAccounts2Redis();
            ScheduleJob ether_productionTx = new ScheduleJob();
            ether_productionTx.setBeanClass("hry.coin.quart.CoinQuart");
            ether_productionTx.setMethodName("ether_productionTx");
            QuartzManager.addJob("ether_productionTx", ether_productionTx, QuartzJob.class, "0 0/1 * * * ?");
            ScheduleJob ether_consumeTx = new ScheduleJob();
            ether_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            ether_consumeTx.setMethodName("ether_consumeTx");
            QuartzManager.addJob("ether_consumeTx", ether_consumeTx, QuartzJob.class, "0 0/2 * * * ?");
            ScheduleJob send2coinBaseJob = new ScheduleJob();
            send2coinBaseJob.setBeanClass("hry.coin.eth.service.impl.EtherServiceImpl");
            send2coinBaseJob.setMethodName("send2coinBaseJob");
            QuartzManager.addJob("send2coinBaseJob", send2coinBaseJob, QuartzJob.class, "0 0 0/2 * * ?");

        }
        if (Properties.appcoinMap().containsKey("tv_ip")) {
            ScheduleJob tv_consumeTx = new ScheduleJob();
            tv_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            tv_consumeTx.setMethodName("tv_consumeTx");
            QuartzManager.addJob("tv_consumeTx", tv_consumeTx, QuartzJob.class, "0 0/2 * * * ?");
            ScheduleJob tv_send2coinbase = new ScheduleJob();
            tv_send2coinbase.setBeanClass("hry.coin.quart.CoinQuart");
            tv_send2coinbase.setMethodName("tv_send2coinbase");
            QuartzManager.addJob("tv_send2coinbase", tv_send2coinbase, QuartzJob.class, "0 0 0/2 * * ?");
        }
        if ((!Properties.appcoinMap().containsKey("USDT".toLowerCase() + "_ip")) ||
                (Properties.appcoinMap().containsKey("BDS".toLowerCase() + "_ip"))) {
            ScheduleJob bds_consumeTx = new ScheduleJob();
            bds_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            bds_consumeTx.setMethodName("bds_consumeTx");
            QuartzManager.addJob("bds_consumeTx", bds_consumeTx, QuartzJob.class, "0 0/5 * * * ?");
        }

        if (Properties.appcoinMap().containsKey("BDS".toLowerCase() + "_ip")) {
            ScheduleJob neo_consumeTx = new ScheduleJob();
            neo_consumeTx.setBeanClass("hry.coin.quart.CoinQuart");
            neo_consumeTx.setMethodName("neo_consumeTx");
            QuartzManager.addJob("neo_consumeTx", neo_consumeTx, QuartzJob.class, "0 0/5 * * * ?");
        }
    }
}
