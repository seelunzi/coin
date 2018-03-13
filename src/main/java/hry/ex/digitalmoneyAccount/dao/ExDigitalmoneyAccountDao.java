package hry.ex.digitalmoneyAccount.dao;

import hry.core.mvc.dao.base.BaseDao;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;

import java.util.List;

public abstract interface ExDigitalmoneyAccountDao
        extends BaseDao<ExDigitalmoneyAccount, Long> {
    public abstract int count(String paramString1, String paramString2);

    public abstract List<String> listAccountNumByCoinCode(String paramString);

    public abstract List<String> listPublicKeyByCoinCode(String paramString);

    public abstract String getEthPublicKeyByparams(String paramString);

    public abstract List<String> listEtherpublickey();

    public abstract ExDigitalmoneyAccount getAccountByAccountNumber(String paramString);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\digitalmoneyAccount\dao\ExDigitalmoneyAccountDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */