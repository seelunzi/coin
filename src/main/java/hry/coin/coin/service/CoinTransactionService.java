package hry.coin.coin.service;

import hry.core.mvc.service.base.BaseService;
import hry.exchange.coin.model.Transaction;
import hry.utils.JsonResult;

import java.util.List;

public abstract interface CoinTransactionService
        extends BaseService<Transaction, Long> {
    int isExists(String paramString);

    JsonResult recordTransaction(String paramString1, String paramString2, Integer paramInteger);

    List<Transaction> findTransactionListByconfirm(String paramString1, int paramInt, String paramString2);

    List<Transaction> findTransactionListIsRollOut(String paramString1, int paramInt, String paramString2);

    boolean isexistHash(String paramString);

    List<Transaction> listUnconfirmed(String paramString);

    boolean updateOrder(Transaction paramTransaction, String paramString);

    JsonResult rechargecoin(Transaction paramTransaction);

    JsonResult recordTransaction_omni(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    void recordTransaction_bds(List<Transaction> paramList);
}
