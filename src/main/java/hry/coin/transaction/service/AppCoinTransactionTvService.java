package hry.coin.transaction.service;

import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.core.mvc.service.base.BaseService;

import java.util.List;

public abstract interface AppCoinTransactionTvService
        extends BaseService<AppCoinTransactionTv, Long> {
    public abstract boolean notload(String paramString);

    public abstract List<String> listYesterdayRechargeRecord(String paramString);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\transaction\service\AppCoinTransactionTvService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */