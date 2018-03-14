package hry.coin.coin.dao;

import hry.core.mvc.dao.base.BaseDao;
import hry.exchange.coin.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface CoinTransactionDao
        extends BaseDao<Transaction, Long> {
    int isExists(String paramString);

    List<Transaction> findTransactionListByconfirm(String paramString1, int paramInt, String paramString2);

    List<Transaction> findTransactionListIsRollOut(String paramString1, int paramInt, String paramString2);

    List<Transaction> getAllRechargeTransactionsYesterday(String paramString);

    BigDecimal isexistHash(String paramString);

    List<Transaction> listUnconfirmed(String paramString);
}
