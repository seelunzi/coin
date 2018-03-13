
package hry.app.ourAccount.service.impl;

import hry.app.ourAccount.model.AppOurAccount;
import hry.app.ourAccount.service.AppOurAccountService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("appOurAccountService")
public class AppOurAccountServiceImpl
        extends BaseServiceImpl<AppOurAccount, Long>
        implements AppOurAccountService {
    @Resource(name = "appOurAccountDao")
    @Override
    public void setDao(BaseDao<AppOurAccount, Long> dao) {
        this.dao = dao;
    }

    @Override
    public AppOurAccount getAccountByfilter(String accountType, String currencyType, String openAccountType) {
        QueryFilter filter = new QueryFilter(AppOurAccount.class);
        filter.addFilter("accountType=", accountType);
        filter.addFilter("currencyType=", currencyType);
        filter.addFilter("openAccountType=", openAccountType);
        filter.addFilter("isShow=", "1");
        AppOurAccount ourAccount = (AppOurAccount) get(filter);
        if (ourAccount != null) {
            return ourAccount;
        }
        return null;
    }
}
