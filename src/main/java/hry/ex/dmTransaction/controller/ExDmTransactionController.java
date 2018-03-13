/*    */
package hry.ex.dmTransaction.controller;
/*    */
/*    */

import hry.core.annotation.MyRequiresPermissions;
import hry.core.annotation.base.MethodName;
import hry.core.mvc.controller.base.BaseController;
import hry.core.mvc.model.page.JsonResult;
import hry.core.mvc.model.page.PageResult;
import hry.core.mvc.service.base.BaseService;
import hry.core.util.QueryFilter;
import hry.ex.dmTransaction.model.ExDmTransaction;
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
@RequestMapping({"/dmTransaction/exdmtransaction"})
/*    */ public class ExDmTransactionController
        /*    */ extends BaseController<ExDmTransaction, Long>
        /*    */ {
    /*    */
    @Resource(name = "exDmTransactionService")
    /*    */ public void setService(BaseService<ExDmTransaction, Long> service)
    /*    */ {
        /* 39 */
        this.service = service;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "查看ExDmTransaction")
    /*    */
    @RequestMapping({"/see/{id}"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public ExDmTransaction see(@PathVariable Long id) {
        /* 47 */
        ExDmTransaction exDmTransaction = (ExDmTransaction) this.service.get(id);
        /* 48 */
        return exDmTransaction;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "增加ExDmTransaction")
    /*    */
    @RequestMapping({"/add"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult add(HttpServletRequest request, ExDmTransaction exDmTransaction) {
        /* 56 */
        return super.save(exDmTransaction);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "修改ExDmTransaction")
    /*    */
    @RequestMapping({"/modify"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult modify(HttpServletRequest request, ExDmTransaction exDmTransaction) {
        /* 64 */
        return super.update(exDmTransaction);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "删除ExDmTransaction")
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
    @MethodName(name = "列表ExDmTransaction")
    /*    */
    @RequestMapping({"/list"})
    /*    */
    @ResponseBody
    /*    */ public PageResult list(HttpServletRequest request) {
        /* 79 */
        QueryFilter filter = new QueryFilter(ExDmTransaction.class, request);
        /* 80 */
        return super.findPage(filter);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\dmTransaction\controller\ExDmTransactionController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */