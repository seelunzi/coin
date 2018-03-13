
package hry.ex.dmTransaction.service.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.core.util.log.LogFactory;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
import hry.ex.dmHotAccountRecord.model.ExDmHotAccountRecord;
import hry.ex.dmHotAccountRecord.service.ExDmHotAccountRecordService;
import hry.ex.dmTransaction.model.ExDmTransaction;
import hry.ex.dmTransaction.service.ExDmTransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("exDmTransactionService")
public class ExDmTransactionServiceImpl
        extends BaseServiceImpl<ExDmTransaction, Long>
        implements ExDmTransactionService {

    @Resource(name = "exDigitalmoneyAccountService")
    private ExDigitalmoneyAccountService exDigitalmoneyAccountService;

    @Resource(name = "exDmTransactionService")
    private ExDmTransactionService exDmTransactionService;

    @Resource(name = "exDmHotAccountRecordService")
    private ExDmHotAccountRecordService exDmHotAccountRecordService;

    @Resource(name = "exDmTransactionDao")
    @Override
    public void setDao(BaseDao<ExDmTransaction, Long> dao) {
        this.dao = dao;
    }

    @Override
    public ExDmTransaction getExDmTransactionByOrderNo(String orderNo) {
        QueryFilter filter = new QueryFilter(ExDmTransaction.class);
        filter.addFilter("orderNo=", orderNo);
        ExDmTransaction dmTransaction = (ExDmTransaction) get(filter);
        if (dmTransaction != null) {
            return dmTransaction;
        }
        return null;
    }

    @Override
    public String rechargeCoin(ExDmTransaction exTxs) {
        StringBuffer result = new StringBuffer("{\"success\":\"true\",\"msg\":");
        try {
            Integer status = exTxs.getStatus();
            if (status.intValue() == 1) {
                ExDigitalmoneyAccount exDigitalmoneyAccount = this.exDigitalmoneyAccountService.getExDigitalmoneyAccountByPublicKey(exTxs.getInAddress(), exTxs.getCoinCode());
                if (exDigitalmoneyAccount == null) {
                    LogFactory.info("未查询到钱包账户");
                    return null;
                }
                BigDecimal hotMoney = exDigitalmoneyAccount.getHotMoney();
                BigDecimal transactionMoney = exTxs.getTransactionMoney();
                BigDecimal k = hotMoney.add(transactionMoney);
                exDigitalmoneyAccount.setHotMoney(k);
                this.exDigitalmoneyAccountService.update(exDigitalmoneyAccount);
                ExDmHotAccountRecord exDmHotAccountRecord = new ExDmHotAccountRecord();
                exDmHotAccountRecord.setAccountId(exDigitalmoneyAccount.getId());
                exDmHotAccountRecord.setCustomerId(exTxs.getCustomerId());
                exDmHotAccountRecord.setUserName(exDigitalmoneyAccount.getUserName());
                exDmHotAccountRecord.setRecordType(Integer.valueOf(1));
                exDmHotAccountRecord.setTransactionMoney(exTxs.getTransactionMoney());
                exDmHotAccountRecord.setStatus(Integer.valueOf(5));
                exDmHotAccountRecord.setRemark("可用余额流水已记录成功 ");
                exDmHotAccountRecord.setCurrencyType(exTxs.getCurrencyType());
                exDmHotAccountRecord.setCoinCode(exTxs.getCoinCode());
                exDmHotAccountRecord.setWebsite(exTxs.getWebsite());
                exDmHotAccountRecord.setTransactionNum(exTxs.getTransactionNum());
                exDmHotAccountRecord.setSaasId(RpcContext.getContext().getAttachment("saasId"));
                this.exDmHotAccountRecordService.save(exDmHotAccountRecord);
                exTxs.setStatus(Integer.valueOf(2));
                exTxs.setUserId(exDigitalmoneyAccount.getCustomerId());
                this.exDmTransactionService.update(exTxs);
                result.append("\"充值记账成功\"");
            } else {
                result.append("\"该订单已通过\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.append("\"异常 :" + e.getMessage() + "  \"");
        } finally {
            result.append("}");
        }
        return result.toString();
    }
}
