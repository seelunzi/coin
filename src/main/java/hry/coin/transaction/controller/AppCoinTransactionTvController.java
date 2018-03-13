/*    */
package hry.coin.transaction.controller;
/*    */
/*    */

import hry.coin.transaction.model.AppCoinTransactionTv;
import hry.core.annotation.MyRequiresPermissions;
import hry.core.annotation.base.MethodName;
import hry.core.mvc.controller.base.BaseController;
import hry.core.mvc.model.page.JsonResult;
import hry.core.mvc.model.page.PageResult;
import hry.core.mvc.service.base.BaseService;
import hry.core.util.QueryFilter;
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
@RequestMapping({"/transaction/appcointransactiontv"})
/*    */ public class AppCoinTransactionTvController
        /*    */ extends BaseController<AppCoinTransactionTv, Long>
        /*    */ {
    /*    */
    @Resource(name = "appCoinTransactionTvService")
    /*    */ public void setService(BaseService<AppCoinTransactionTv, Long> service)
    /*    */ {
        /* 39 */
        this.service = service;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "查看AppCoinTransactionTv")
    /*    */
    @RequestMapping({"/see/{id}"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public AppCoinTransactionTv see(@PathVariable Long id) {
        /* 47 */
        AppCoinTransactionTv appCoinTransactionTv = (AppCoinTransactionTv) this.service.get(id);
        /* 48 */
        return appCoinTransactionTv;
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "增加AppCoinTransactionTv")
    /*    */
    @RequestMapping({"/add"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult add(HttpServletRequest request, AppCoinTransactionTv appCoinTransactionTv) {
        /* 56 */
        return super.save(appCoinTransactionTv);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "修改AppCoinTransactionTv")
    /*    */
    @RequestMapping({"/modify"})
    /*    */
    @MyRequiresPermissions
    /*    */
    @ResponseBody
    /*    */ public JsonResult modify(HttpServletRequest request, AppCoinTransactionTv appCoinTransactionTv) {
        /* 64 */
        return super.update(appCoinTransactionTv);
        /*    */
    }

    /*    */
    /*    */
    @MethodName(name = "删除AppCoinTransactionTv")
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
    @MethodName(name = "列表AppCoinTransactionTv")
    /*    */
    @RequestMapping({"/list"})
    /*    */
    @ResponseBody
    /*    */ public PageResult list(HttpServletRequest request) {
        /* 79 */
        QueryFilter filter = new QueryFilter(AppCoinTransactionTv.class, request);
        /* 80 */
        return super.findPage(filter);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\transaction\controller\AppCoinTransactionTvController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */