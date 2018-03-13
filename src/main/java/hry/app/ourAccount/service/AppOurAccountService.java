package hry.app.ourAccount.service;

import hry.app.ourAccount.model.AppOurAccount;
import hry.core.mvc.service.base.BaseService;

public abstract interface AppOurAccountService
        extends BaseService<AppOurAccount, Long> {
    public abstract AppOurAccount getAccountByfilter(String paramString1, String paramString2, String paramString3);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\app\ourAccount\service\AppOurAccountService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */