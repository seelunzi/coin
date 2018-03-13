
package hry.coin.mobilecoin.service.impl;

import hry.coin.mobilecoin.model.MobileCoinTransaction;
import hry.coin.mobilecoin.service.MobileCoinTransactionServcie;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("mobileCoinTransactionServcie")
public class MobileCoinTransactionServcieImpl
        extends BaseServiceImpl<MobileCoinTransaction, Long>
        implements MobileCoinTransactionServcie {

    @Resource(name = "mobileCoinTransactionDao")
    @Override
    public void setDao(BaseDao<MobileCoinTransaction, Long> dao) {
        this.dao = dao;
    }
}
