package hry.coin.coin.service;

import hry.coin.coin.model.AppCoinTransaction;
import hry.core.mvc.service.base.BaseService;

import java.math.BigInteger;
import java.util.List;

public abstract interface AppCoinTransactionService
        extends BaseService<AppCoinTransaction, Long> {
    public abstract List<AppCoinTransaction> consumeTx();

    public abstract int existNumber(String paramString);

    public abstract BigInteger getLastestBlock();

    public abstract BigInteger getLastestBlockByCoinCode(String paramString);

    public abstract List<AppCoinTransaction> listYesterdayRechargeRecord(String paramString);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\coin\service\AppCoinTransactionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */