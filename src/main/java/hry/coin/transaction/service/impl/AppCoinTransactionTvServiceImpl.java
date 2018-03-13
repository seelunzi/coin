/*    */
package hry.coin.transaction.service.impl;
/*    */
/*    */

import hry.coin.transaction.dao.AppCoinTransactionTvDao;
import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.coin.transaction.service.AppCoinTransactionTvService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
@Service("appCoinTransactionTvService")
/*    */ public class AppCoinTransactionTvServiceImpl
        /*    */ extends BaseServiceImpl<AppCoinTransactionTv, Long>
        /*    */ implements AppCoinTransactionTvService
        /*    */ {
    /*    */
    @Resource(name = "appCoinTransactionTvDao")
    /*    */ public void setDao(BaseDao<AppCoinTransactionTv, Long> dao)
    /*    */ {
        /* 31 */
        this.dao = dao;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public boolean notload(String trxid)
    /*    */ {
        /* 37 */
        return ((AppCoinTransactionTvDao) this.dao).getcountBytrxid(trxid) == 0;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public List<String> listYesterdayRechargeRecord(String type)
    /*    */ {
        /* 43 */
        return ((AppCoinTransactionTvDao) this.dao).listYesterdayRechargeRecord(type);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\transaction\service\impl\AppCoinTransactionTvServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */