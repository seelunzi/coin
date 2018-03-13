package hry.ex.dmTransaction.service;

import hry.core.mvc.service.base.BaseService;
import hry.ex.dmTransaction.model.ExDmTransaction;

public abstract interface ExDmTransactionService
        extends BaseService<ExDmTransaction, Long> {
    public abstract ExDmTransaction getExDmTransactionByOrderNo(String paramString);

    public abstract String rechargeCoin(ExDmTransaction paramExDmTransaction);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\dmTransaction\service\ExDmTransactionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */