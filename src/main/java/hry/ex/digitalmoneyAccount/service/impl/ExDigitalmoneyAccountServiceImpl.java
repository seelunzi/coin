/*    */
package hry.ex.digitalmoneyAccount.service.impl;
/*    */
/*    */

import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.ex.digitalmoneyAccount.dao.ExDigitalmoneyAccountDao;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
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
/*    */
/*    */
@Service("exDigitalmoneyAccountService")
/*    */ public class ExDigitalmoneyAccountServiceImpl
        /*    */ extends BaseServiceImpl<ExDigitalmoneyAccount, Long>
        /*    */ implements ExDigitalmoneyAccountService
        /*    */ {
    /*    */
    @Resource(name = "exDigitalmoneyAccountDao")
    /*    */ public void setDao(BaseDao<ExDigitalmoneyAccount, Long> dao)
    /*    */ {
        /* 33 */
        this.dao = dao;
        /*    */
    }

    /*    */
    /*    */
    public ExDigitalmoneyAccount getExDigitalmoneyAccountByPublicKey(String publicKey, String coinCode)
    /*    */ {
        /* 38 */
        QueryFilter filter = new QueryFilter(ExDigitalmoneyAccount.class);
        /* 39 */
        filter.addFilter("publicKey=", publicKey);
        /* 40 */
        ExDigitalmoneyAccount coinAccount = (ExDigitalmoneyAccount) get(filter);
        /* 41 */
        if (coinAccount != null) {
            /* 42 */
            return coinAccount;
            /*    */
        }
        /* 44 */
        return null;
        /*    */
    }

    /*    */
    /*    */
    public boolean isexist(String AccountNum, String coinType)
    /*    */ {
        /* 49 */
        return ((ExDigitalmoneyAccountDao) this.dao).count(AccountNum, coinType) > 0;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public List<String> listAccountNumByCoinCode(String coinCode)
    /*    */ {
        /* 55 */
        return ((ExDigitalmoneyAccountDao) this.dao).listAccountNumByCoinCode(coinCode);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public List<String> listPublicKeyByCoinCode(String coinCode)
    /*    */ {
        /* 61 */
        return ((ExDigitalmoneyAccountDao) this.dao).listPublicKeyByCoinCode(coinCode);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public String getEthPublicKeyByparams(String userName)
    /*    */ {
        /* 67 */
        return ((ExDigitalmoneyAccountDao) this.dao).getEthPublicKeyByparams(userName);
        /*    */
    }

    /*    */
    /*    */
    public List<String> listEtherpublickey()
    /*    */ {
        /* 72 */
        return ((ExDigitalmoneyAccountDao) this.dao).listEtherpublickey();
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public ExDigitalmoneyAccount getAccountByAccountNumber(String accountNumber)
    /*    */ {
        /* 78 */
        return ((ExDigitalmoneyAccountDao) this.dao).getAccountByAccountNumber(accountNumber);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\digitalmoneyAccount\service\impl\ExDigitalmoneyAccountServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */