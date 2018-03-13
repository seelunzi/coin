/*    */
package hry.ex.dmHotAccountRecord.service.impl;
/*    */
/*    */

import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import hry.ex.dmHotAccountRecord.model.ExDmHotAccountRecord;
import hry.ex.dmHotAccountRecord.service.ExDmHotAccountRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
@Service("exDmHotAccountRecordService")
/*    */ public class ExDmHotAccountRecordServiceImpl
        /*    */ extends BaseServiceImpl<ExDmHotAccountRecord, Long>
        /*    */ implements ExDmHotAccountRecordService
        /*    */ {
    /*    */
    @Resource(name = "exDmHotAccountRecordDao")
    /*    */ public void setDao(BaseDao<ExDmHotAccountRecord, Long> dao)
    /*    */ {
        /* 27 */
        this.dao = dao;
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\dmHotAccountRecord\service\impl\ExDmHotAccountRecordServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */