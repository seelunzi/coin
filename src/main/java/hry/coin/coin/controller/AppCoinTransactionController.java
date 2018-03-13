
package hry.coin.coin.controller;

import hry.coin.coin.model.AppCoinTransaction;
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

@Controller
@RequestMapping({"/coin/appcointransaction"})
public class AppCoinTransactionController
        extends BaseController<AppCoinTransaction, Long> {

    @Resource(name = "appCoinTransactionService")
    @Override
    public void setService(BaseService<AppCoinTransaction, Long> service) {
        this.service = service;
    }

    @MethodName(name = "查看AppCoinTransaction")
    @RequestMapping({"/see/{id}"})
    @MyRequiresPermissions
    @ResponseBody
    public AppCoinTransaction see(@PathVariable Long id) {
        AppCoinTransaction appCoinTransaction = (AppCoinTransaction) this.service.get(id);
        return appCoinTransaction;
    }

    @MethodName(name = "增加AppCoinTransaction")
    @RequestMapping({"/add"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult add(HttpServletRequest request, AppCoinTransaction appCoinTransaction) {
        return super.save(appCoinTransaction);
    }

    @MethodName(name = "修改AppCoinTransaction")
    @RequestMapping({"/modify"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult modify(HttpServletRequest request, AppCoinTransaction appCoinTransaction) {
        return super.update(appCoinTransaction);
    }

    @MethodName(name = "删除AppCoinTransaction")
    @RequestMapping({"/remove/{ids}"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult remove(@PathVariable String ids) {
        return super.deleteBatch(ids);
    }

    @MethodName(name = "列表AppCoinTransaction")
    @RequestMapping({"/list"})
    @ResponseBody
    public PageResult list(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppCoinTransaction.class, request);
        return super.findPage(filter);
    }

}
