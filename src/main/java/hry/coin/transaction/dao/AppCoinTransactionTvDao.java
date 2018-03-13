package hry.coin.transaction.dao;

import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.core.mvc.dao.base.BaseDao;

import java.util.List;

public abstract interface AppCoinTransactionTvDao
        extends BaseDao<AppCoinTransactionTv, Long> {
    public abstract int getcountBytrxid(String paramString);

    public abstract List<String> listYesterdayRechargeRecord(String paramString);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\transaction\dao\AppCoinTransactionTvDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */