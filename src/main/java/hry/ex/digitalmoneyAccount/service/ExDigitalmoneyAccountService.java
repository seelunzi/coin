package hry.ex.digitalmoneyAccount.service;

import hry.core.mvc.service.base.BaseService;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;

import java.util.List;

public abstract interface ExDigitalmoneyAccountService
        extends BaseService<ExDigitalmoneyAccount, Long> {
    public abstract ExDigitalmoneyAccount getExDigitalmoneyAccountByPublicKey(String paramString1, String paramString2);

    public abstract boolean isexist(String paramString1, String paramString2);

    public abstract List<String> listAccountNumByCoinCode(String paramString);

    public abstract List<String> listPublicKeyByCoinCode(String paramString);

    public abstract String getEthPublicKeyByparams(String paramString);

    public abstract List<String> listEtherpublickey();

    public abstract ExDigitalmoneyAccount getAccountByAccountNumber(String paramString);
}