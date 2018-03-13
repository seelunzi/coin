
package hry.coin.eth.controller;


import hry.coin.eth.service.impl.EtherServiceImpl;
import hry.utils.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.utils.Convert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

@Controller
@RequestMapping({"/ethCoin"})
public class EthCoinController {

    @RequestMapping({"/create"})

    @ResponseBody
    public Map<String, String> create(HttpServletRequest req) {

        String name = req.getParameter("userName");

        System.out.println("创建币的地址 账户名：" + name);

        Map<String, String> map = new HashMap();

        try {

            String address = EtherServiceImpl.createAddress("a00000000");

            if (address != null) {

                map.put("address", address);

                map.put("code", "success");

                System.out.println("创建成功 地址为：" + address);

            } else {

                map.put("address", null);

                map.put("code", "fail");

                System.out.println("创建成功 地址为：" + address);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return map;

    }

    @RequestMapping({"/getBalance"})
    /*     */
    @ResponseBody
    /*     */ public BigDecimal getBalance(HttpServletRequest req)
        /*     */ {
        /*  67 */
        String address = req.getParameter("address");
        /*     */
        try {
            /*  69 */
            BigInteger balance = EtherServiceImpl.getBalance(address);
            /*  70 */
            if (balance != null) {
                /*  71 */
                return Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
                /*     */
            }
            /*     */
        } catch (Exception e) {
            /*  74 */
            e.printStackTrace();
            /*     */
        }
        /*  76 */
        return null;
        /*     */
    }

    @RequestMapping({"/sendFrom"})
    /*     */
    @ResponseBody
    /*     */ public String sendFrom(HttpServletRequest req)
        /*     */ {
        /* 124 */
        return null;

    }

    @RequestMapping({"/listAccounts"})
    /*     */
    @ResponseBody
    /*     */ public List<String> listAccounts(HttpServletRequest req)
        /*     */ {
        /* 138 */
        return EtherServiceImpl.listAccount();
        /*     */
    }

    @RequestMapping({"/unlockAccount"})
    /*     */
    @ResponseBody
    /*     */ public String unlockAccount(HttpServletRequest req)
        /*     */ {
        /* 150 */
        String address = req.getParameter("address");
        /* 151 */
        String password = req.getParameter("password");
        /* 152 */
        List<String> list = new ArrayList();
        /* 153 */
        list.add(address);

        list.add(password);

        if (CommonUtil.isNoHasEmptyInListstr(list)) {

            return EtherServiceImpl.unlockAccount(address, password) ? "解锁成功" : "解锁失败";

        }

        return "参数无效";

    }

    public static void main(String[] args) {
    }

}
