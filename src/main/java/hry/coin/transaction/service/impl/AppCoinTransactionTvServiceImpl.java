
package hry.coin.transaction.service.impl;


import hry.coin.transaction.dao.AppCoinTransactionTvDao;
import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.coin.transaction.service.AppCoinTransactionTvService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appCoinTransactionTvService")
public class AppCoinTransactionTvServiceImpl
        extends BaseServiceImpl<AppCoinTransactionTv, Long>
        implements AppCoinTransactionTvService {
    @Resource(name = "appCoinTransactionTvDao")
    @Override
    public void setDao(BaseDao<AppCoinTransactionTv, Long> dao) {
        this.dao = dao;
    }

    @Override
    public boolean notload(String trxid) {
        return ((AppCoinTransactionTvDao) this.dao).getcountBytrxid(trxid) == 0;
    }

    @Override
    public List<String> listYesterdayRechargeRecord(String type) {
        return ((AppCoinTransactionTvDao) this.dao).listYesterdayRechargeRecord(type);
    }
}
