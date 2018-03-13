package hry.coin.coin.dao;

import hry.core.mvc.dao.base.BaseDao;
import hry.exchange.coin.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public abstract interface CoinTransactionDao
        extends BaseDao<Transaction, Long> {
    public abstract int isExists(String paramString);

    public abstract List<Transaction> findTransactionListByconfirm(String paramString1, int paramInt, String paramString2);

    public abstract List<Transaction> findTransactionListIsRollOut(String paramString1, int paramInt, String paramString2);

    public abstract List<Transaction> getAllRechargeTransactionsYesterday(String paramString);

    public abstract BigDecimal isexistHash(String paramString);

    public abstract List<Transaction> listUnconfirmed(String paramString);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\coin\dao\CoinTransactionDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */