package hry.coin.coin.dao;

import hry.coin.coin.model.AppCoinTransaction;
import hry.core.mvc.dao.base.BaseDao;

import java.math.BigInteger;
import java.util.List;

public abstract interface AppCoinTransactionDao
        extends BaseDao<AppCoinTransaction, Long> {
    List<AppCoinTransaction> consumeTx();

    int existNumber(String paramString);

    BigInteger getLastestBlock();

    BigInteger getLastestBlockByCoinCode(String paramString);

    List<AppCoinTransaction> listYesterdayRechargeRecord(String paramString);
}
