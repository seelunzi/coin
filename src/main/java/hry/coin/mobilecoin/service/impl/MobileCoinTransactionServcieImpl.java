/*    */
package hry.coin.mobilecoin.service.impl;
/*    */
/*    */

import hry.coin.mobilecoin.model.MobileCoinTransaction;
import hry.coin.mobilecoin.service.MobileCoinTransactionServcie;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */
@Service("mobileCoinTransactionServcie")
/*    */ public class MobileCoinTransactionServcieImpl
        /*    */ extends BaseServiceImpl<MobileCoinTransaction, Long>
        /*    */ implements MobileCoinTransactionServcie
        /*    */ {
    /*    */
    @Resource(name = "mobileCoinTransactionDao")
    /*    */ public void setDao(BaseDao<MobileCoinTransaction, Long> dao)
    /*    */ {
        /* 18 */
        this.dao = dao;
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\mobilecoin\service\impl\MobileCoinTransactionServcieImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */