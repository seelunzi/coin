package hry.coin.coin.service;

import hry.core.mvc.service.base.BaseService;
import hry.exchange.coin.model.Transaction;
import hry.utils.JsonResult;

import java.util.List;

public abstract interface CoinTransactionService
        extends BaseService<Transaction, Long> {
    public abstract int isExists(String paramString);

    public abstract JsonResult recordTransaction(String paramString1, String paramString2, Integer paramInteger);

    public abstract List<Transaction> findTransactionListByconfirm(String paramString1, int paramInt, String paramString2);

    public abstract List<Transaction> findTransactionListIsRollOut(String paramString1, int paramInt, String paramString2);

    public abstract boolean isexistHash(String paramString);

    public abstract List<Transaction> listUnconfirmed(String paramString);

    public abstract boolean updateOrder(Transaction paramTransaction, String paramString);

    public abstract JsonResult rechargecoin(Transaction paramTransaction);

    public abstract JsonResult recordTransaction_omni(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    public abstract void recordTransaction_bds(List<Transaction> paramList);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\coin\service\CoinTransactionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */