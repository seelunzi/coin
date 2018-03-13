/*    */
package hry.app.ourAccount.service.impl;
/*    */
/*    */

import hry.app.ourAccount.model.AppOurAccount;
import hry.app.ourAccount.service.AppOurAccountService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
@Service("appOurAccountService")
/*    */ public class AppOurAccountServiceImpl
        /*    */ extends BaseServiceImpl<AppOurAccount, Long>
        /*    */ implements AppOurAccountService
        /*    */ {
    /*    */
    @Resource(name = "appOurAccountDao")
    /*    */ public void setDao(BaseDao<AppOurAccount, Long> dao)
    /*    */ {
        /* 26 */
        this.dao = dao;
        /*    */
    }

    /*    */
    /*    */
    public AppOurAccount getAccountByfilter(String accountType, String currencyType, String openAccountType)
    /*    */ {
        /* 31 */
        QueryFilter filter = new QueryFilter(AppOurAccount.class);
        /* 32 */
        filter.addFilter("accountType=", accountType);
        /* 33 */
        filter.addFilter("currencyType=", currencyType);
        /* 34 */
        filter.addFilter("openAccountType=", openAccountType);
        /* 35 */
        filter.addFilter("isShow=", "1");
        /* 36 */
        AppOurAccount ourAccount = (AppOurAccount) get(filter);
        /* 37 */
        if (ourAccount != null) {
            /* 38 */
            return ourAccount;
            /*    */
        }
        /* 40 */
        return null;
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\app\ourAccount\service\impl\AppOurAccountServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */