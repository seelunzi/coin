package hry.app.ourAccount.service;

import hry.app.ourAccount.model.AppOurAccount;
import hry.core.mvc.service.base.BaseService;

public interface AppOurAccountService
        extends BaseService<AppOurAccount, Long> {
    AppOurAccount getAccountByfilter(String paramString1, String paramString2, String paramString3);
}