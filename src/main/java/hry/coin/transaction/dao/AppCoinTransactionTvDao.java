package hry.coin.transaction.dao;

import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.core.mvc.dao.base.BaseDao;

import java.util.List;

public interface AppCoinTransactionTvDao
        extends BaseDao<AppCoinTransactionTv, Long> {
    int getcountBytrxid(String paramString);

    List<String> listYesterdayRechargeRecord(String paramString);
}