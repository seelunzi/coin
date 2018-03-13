package hry.coin.coin.dao;

import hry.coin.coin.model.AppCoinTransaction;
import hry.core.mvc.dao.base.BaseDao;

import java.math.BigInteger;
import java.util.List;

public abstract interface AppCoinTransactionDao
        extends BaseDao<AppCoinTransaction, Long> {
    public abstract List<AppCoinTransaction> consumeTx();

    public abstract int existNumber(String paramString);

    public abstract BigInteger getLastestBlock();

    public abstract BigInteger getLastestBlockByCoinCode(String paramString);

    public abstract List<AppCoinTransaction> listYesterdayRechargeRecord(String paramString);
}
