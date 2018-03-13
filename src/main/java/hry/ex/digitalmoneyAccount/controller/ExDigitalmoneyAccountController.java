/*    */
package hry.ex.digitalmoneyAccount.controller;
/*    */
/*    */

import hry.core.annotation.MyRequiresPermissions;
import hry.core.annotation.base.MethodName;
import hry.core.mvc.controller.base.BaseController;
import hry.core.mvc.model.page.JsonResult;
import hry.core.mvc.model.page.PageResult;
import hry.core.mvc.service.base.BaseService;
import hry.core.util.QueryFilter;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
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
@Controller
/*    */
@RequestMapping({"/digitalmoneyAccount/exdigitalmoneyaccount"})
/*    */ public class ExDigitalmoneyAccountController
        /*    */ extends BaseController<ExDigitalmoneyAccount, Long>
        /*    */ {
    /*    */
    @Resource(name = "exDigitalmoneyAccountService")
    /*    */ public void setService(BaseService<ExDigitalmoneyAccount, Long> service)
    /*    */ {
        /* 39 */
        this.service = service;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "查看ExDigitalmoneyAccount")
    /*    */
    @RequestMapping({"/see/{id}"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public ExDigitalmoneyAccount see(@PathVariable Long id) {
        /* 47 */
        ExDigitalmoneyAccount exDigitalmoneyAccount = (ExDigitalmoneyAccount) this.service.get(id);
        return exDigitalmoneyAccount;
    }

    /*    */
    /*    */
    @MethodName(name = "增加ExDigitalmoneyAccount")
    /*    */
    @RequestMapping({"/add"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult add(HttpServletRequest request, ExDigitalmoneyAccount exDigitalmoneyAccount) {
        /* 56 */
        return super.save(exDigitalmoneyAccount);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "修改ExDigitalmoneyAccount")
    /*    */
    @RequestMapping({"/modify"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    public JsonResult modify(HttpServletRequest request, ExDigitalmoneyAccount exDigitalmoneyAccount) {
        return super.update(exDigitalmoneyAccount);
    }

    @MethodName(name = "删除ExDigitalmoneyAccount")
    @RequestMapping({"/remove/{ids}"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult remove(@PathVariable String ids) {
        /* 72 */
        return super.deleteBatch(ids);
        /*    */
    }

    @MethodName(name = "列表ExDigitalmoneyAccount")
    @RequestMapping({"/list"})
    @ResponseBody
    public PageResult list(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ExDigitalmoneyAccount.class, request);
        return super.findPage(filter);
        /*    */
    }
    /*    */
}
