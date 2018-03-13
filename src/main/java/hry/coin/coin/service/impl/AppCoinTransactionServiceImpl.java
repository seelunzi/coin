/*    */
package hry.coin.coin.service.impl;
/*    */
/*    */

import hry.coin.coin.dao.AppCoinTransactionDao;
import hry.coin.coin.model.AppCoinTransaction;
import hry.coin.coin.service.AppCoinTransactionService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
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
@Service("appCoinTransactionService")
/*    */ public class AppCoinTransactionServiceImpl
        /*    */ extends BaseServiceImpl<AppCoinTransaction, Long>
        /*    */ implements AppCoinTransactionService
        /*    */ {
    /*    */
    @Resource(name = "appCoinTransactionDao")
    /*    */ public void setDao(BaseDao<AppCoinTransaction, Long> dao)
    /*    */ {
        /* 33 */
        this.dao = dao;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public List<AppCoinTransaction> consumeTx()
    /*    */ {
        /* 39 */
        return ((AppCoinTransactionDao) this.dao).consumeTx();
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int existNumber(String hash)
    /*    */ {
        /* 45 */
        return ((AppCoinTransactionDao) this.dao).existNumber(hash);
        /*    */
    }

    /*    */
    /*    */
    public BigInteger getLastestBlock()
    /*    */ {
        /* 50 */
        return ((AppCoinTransactionDao) this.dao).getLastestBlock();
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public BigInteger getLastestBlockByCoinCode(String coinCode)
    /*    */ {
        /* 56 */
        return ((AppCoinTransactionDao) this.dao).getLastestBlockByCoinCode(coinCode);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public List<AppCoinTransaction> listYesterdayRechargeRecord(String coinType)
    /*    */ {
        /* 62 */
        return ((AppCoinTransactionDao) this.dao).listYesterdayRechargeRecord(coinType);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\coin\service\impl\AppCoinTransactionServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */