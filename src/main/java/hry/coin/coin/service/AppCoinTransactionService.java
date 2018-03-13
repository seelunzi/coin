package hry.coin.coin.service;

import hry.coin.coin.model.AppCoinTransaction;
import hry.core.mvc.service.base.BaseService;

import java.math.BigInteger;
import java.util.List;

public interface AppCoinTransactionService
        extends BaseService<AppCoinTransaction, Long> {
    List<AppCoinTransaction> consumeTx();

    int existNumber(String paramString);

    BigInteger getLastestBlock();

    BigInteger getLastestBlockByCoinCode(String paramString);

    List<AppCoinTransaction> listYesterdayRechargeRecord(String paramString);
}
