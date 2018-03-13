
package hry.coin.mobilecoin.service.impl;


import hry.coin.mobilecoin.model.MobileCustomer;
import hry.coin.mobilecoin.service.MobileCustomerServcie;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("mobileCustomerServcie")
public class MobileCustomerServcieImpl
        extends BaseServiceImpl<MobileCustomer, Long> implements MobileCustomerServcie {

    @Resource(name = "mobileCustomerDao")
    @Override
    public void setDao(BaseDao<MobileCustomer, Long> dao) {
        this.dao = dao;
    }
}