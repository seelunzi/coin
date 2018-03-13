/*    */
package hry.ex.dmHotAccountRecord.controller;
/*    */
/*    */

import hry.core.annotation.MyRequiresPermissions;
import hry.core.annotation.base.MethodName;
import hry.core.mvc.controller.base.BaseController;
import hry.core.mvc.model.page.JsonResult;
import hry.core.mvc.model.page.PageResult;
import hry.core.mvc.service.base.BaseService;
import hry.core.util.QueryFilter;
import hry.ex.dmHotAccountRecord.model.ExDmHotAccountRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
@Controller
/*    */
@RequestMapping({"/dmHotAccountRecord/exdmhotaccountrecord"})
/*    */ public class ExDmHotAccountRecordController
        /*    */ extends BaseController<ExDmHotAccountRecord, Long>
        /*    */ {
    /*    */
    @Resource(name = "exDmHotAccountRecordService")
    /*    */ public void setService(BaseService<ExDmHotAccountRecord, Long> service)
    /*    */ {
        /* 39 */
        this.service = service;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "查看ExDmHotAccountRecord")
    /*    */
    @RequestMapping({"/see/{id}"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public ExDmHotAccountRecord see(@PathVariable Long id) {
        /* 47 */
        ExDmHotAccountRecord exDmHotAccountRecord = (ExDmHotAccountRecord) this.service.get(id);
        /* 48 */
        return exDmHotAccountRecord;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "增加ExDmHotAccountRecord")
    /*    */
    @RequestMapping({"/add"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult add(HttpServletRequest request, ExDmHotAccountRecord exDmHotAccountRecord) {
        /* 56 */
        return super.save(exDmHotAccountRecord);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "修改ExDmHotAccountRecord")
    /*    */
    @RequestMapping({"/modify"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult modify(HttpServletRequest request, ExDmHotAccountRecord exDmHotAccountRecord) {
        /* 64 */
        return super.update(exDmHotAccountRecord);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "删除ExDmHotAccountRecord")
    /*    */
    @RequestMapping({"/remove/{ids}"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult remove(@PathVariable String ids) {
        /* 72 */
        return super.deleteBatch(ids);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "列表ExDmHotAccountRecord")
    /*    */
    @RequestMapping({"/list"})
    /*    */
    @ResponseBody
    /*    */ public PageResult list(HttpServletRequest request) {
        /* 79 */
        QueryFilter filter = new QueryFilter(ExDmHotAccountRecord.class, request);
        /* 80 */
        return super.findPage(filter);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\dmHotAccountRecord\controller\ExDmHotAccountRecordController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */