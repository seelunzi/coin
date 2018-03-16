
package hry.ex.dmTransaction.controller;


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

@Controller
@RequestMapping({"/dmTransaction/exdmtransaction"})
public class ExDmTransactionController
        extends BaseController<ExDmTransaction, Long> {

    @Resource(name = "exDmTransactionService")
    @Override
    public void setService(BaseService<ExDmTransaction, Long> service) {
        this.service = service;
    }

    @MethodName(name = "查看ExDmTransaction")
    @RequestMapping({"/see/{id}"})
    @MyRequiresPermissions
    @ResponseBody
    public ExDmTransaction see(@PathVariable Long id) {
        ExDmTransaction exDmTransaction = (ExDmTransaction) this.service.get(id);
        return exDmTransaction;
    }

    @MethodName(name = "增加ExDmTransaction")
    @RequestMapping({"/add"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult add(HttpServletRequest request, ExDmTransaction exDmTransaction) {
        return super.save(exDmTransaction);
    }

    @MethodName(name = "修改ExDmTransaction")
    @RequestMapping({"/modify"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult modify(HttpServletRequest request, ExDmTransaction exDmTransaction) {
        return super.update(exDmTransaction);
    }

    @MethodName(name = "删除ExDmTransaction")
    @RequestMapping({"/remove/{ids}"})
    @MyRequiresPermissions
    @ResponseBody
    public JsonResult remove(@PathVariable String ids) {
        return super.deleteBatch(ids);
    }

    @MethodName(name = "列表ExDmTransaction")
    @RequestMapping({"/list"})
    @ResponseBody
    public PageResult list(HttpServletRequest request) {

        QueryFilter filter = new QueryFilter(ExDmTransaction.class, request);
        return super.findPage(filter);
    }
}

