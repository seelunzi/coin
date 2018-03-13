
package hry.coin.coin.controller;

import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import com.azazar.bitcoin.jsonrpcclient.Bitcoin.Transaction;
import hry.coin.CoinService;
import hry.coin.bds.BdsServerImpl;
import hry.coin.coin.service.CoinTransactionService;
import hry.coin.eth.service.impl.EtherService;
import hry.coin.impl.CoinServiceImpl;
import hry.coin.impl.JsonrpcClient;
import hry.coin.impl.MyCoinService;
import hry.coin.neo.NeoServiceImpl;
import hry.coin.tv.TvUtil;
import hry.coin.utils.RedisUtil;
import hry.core.annotation.base.MethodName;
import hry.core.util.StringUtil;
import hry.core.util.log.LogFactory;
import hry.core.util.security.Check;
import hry.core.util.sys.ContextUtil;
import hry.dto.model.Wallet;
import hry.ex.digitalmoneyAccount.model.ExDigitalmoneyAccount;
import hry.ex.digitalmoneyAccount.service.ExDigitalmoneyAccountService;
import hry.redis.common.utils.RedisService;
import hry.utils.CommonUtil;
import hry.utils.Properties;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/coin"})
public class CoinController {
    @Resource(name = "redisService")
    private RedisService redisService;

    @Resource(name = "exDigitalmoneyAccountService")
    private ExDigitalmoneyAccountService exDigitalmoneyAccountService;
    private BdsServerImpl bdsServerImpl = new BdsServerImpl();

    private NeoServiceImpl neoServer = new NeoServiceImpl();

    @RequestMapping({"/create"})

    @ResponseBody
    public Map<String, String> create(HttpServletRequest req) {

        String userName = req.getParameter("userName");

        String type = req.getParameter("type");

        String accountNumber = req.getParameter("accountNumber");

        Map<String, String> map = new HashMap();

        String address = null;

        if ((StringUtils.isNotEmpty(userName)) && (StringUtils.isNotEmpty(type)) && (StringUtils.isNotEmpty(accountNumber))) {

            try {

                type = type.toUpperCase();

                if ((type.equalsIgnoreCase("ETH")) || (EtherService.isSmartContractCoin(type))) {

                    String result = this.exDigitalmoneyAccountService.getEthPublicKeyByparams(userName);

                    if (StringUtils.isNotEmpty(result)) {

                        address = result;

                    } else {

                        address = EtherService.createAddress(EtherService.PASSWORD);

                    }
                } else if (type.equalsIgnoreCase("tv")) {
                    address = TvUtil.createAccount(accountNumber);
                } else if (type.equalsIgnoreCase("zec")) {
                    CoinService coinService = new CoinServiceImpl(type);
                    address = coinService.createNewAddress(null);
                } else if (type.equalsIgnoreCase("BDS")) {
                    address = this.bdsServerImpl.getPublicKey(accountNumber);

                } else if (type.equalsIgnoreCase("NEO")) {

                    address = this.neoServer.getPublicKey(accountNumber);

                } else {

                    CoinService coinService = new CoinServiceImpl(type);

                    address = coinService.createNewAddress(accountNumber);

                }

                map.put("address", address);

                map.put("code", "success");

            } catch (Exception e) {

                LogFactory.info("创建失败，请求参数---账户名：" + accountNumber + "   币种：" + type);

                e.printStackTrace();

                map.put("address", address);

                map.put("code", "error");

            }

        } else {

            LogFactory.info("创建币地址参数信息：userName=" + userName + "    type=" + type + "      accountNumber=" + accountNumber);

            map.put("address", address);

            map.put("code", "请求参数无效请检查");

        }

        return map;

    }

    @RequestMapping({"/balance"})
    /*     */
    @ResponseBody
    /*     */ public double balance(HttpServletRequest req)
        /*     */ {

        String type = req.getParameter("type");

        String userName = req.getParameter("userName");

        double d = 0.0D;

        try {

            CoinService coinService = new CoinServiceImpl(type);

            if ((null != userName) && (!"".equals(userName))) {

                d = coinService.getBalance(userName);

            }

            return coinService.getBalance();

        } catch (Exception e) {

            LogFactory.info("查询余额异常");

            e.printStackTrace();
        }

        return 0.0D;

    }

    @RequestMapping(value = {"/sendFrom"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    /*     */
    @ResponseBody
    /*     */ public hry.utils.JsonResult sendFrom(HttpServletRequest request)
        /*     */ {

        hry.utils.JsonResult result = new hry.utils.JsonResult();

        String toAddress = request.getParameter("toAddress");

        String type = request.getParameter("type");
        /* 168 */
        String transactionNum = request.getParameter("transactionNum");
        /* 169 */
        String amount = request.getParameter("amount");

        String getcode = request.getParameter("auth_code");

        String keepDecimalForCoin = request.getParameter("keepDecimalForCoin");

        String memo = request.getParameter("memo");
        /* 173 */
        LogFactory.info("收到订单：" + transactionNum + "_的提币请求");

        String[] params = {toAddress, type, amount, transactionNum, keepDecimalForCoin, memo};

        if (CommonUtil.isNohashEmptyInArr(params)) {

            String authcode = Check.authCode(params);

            if ((StringUtils.isNotEmpty(authcode)) && (getcode.equals(authcode))) {

                try {

                    if (StringUtils.isNotEmpty(type)) {

                        Integer digit = Integer.valueOf(keepDecimalForCoin);

                        amount = CommonUtil.strRoundDown(amount, digit.intValue());

                        type = type.toUpperCase();

                        LogFactory.info("提币数量： digit=" + digit + " amount=" + amount + " type=" + type);

                        if (type.equalsIgnoreCase("ETH")) {

                            result = EtherService.sendFrom(amount, toAddress);

                        } else if (EtherService.isSmartContractCoin(type)) {

                            result = EtherService.smartContract_sendFrom(type, amount, toAddress);

                        } else if (type.equalsIgnoreCase("tv")) {

                            result = TvUtil.sendFrom(amount, toAddress, memo, transactionNum);

                        } else if (type.equalsIgnoreCase("USDT")) {

                            JsonrpcClient client = MyCoinService.getClient(type);

                            result = client.omni_sendFrom(type, amount, toAddress);

                        } else if (type.equalsIgnoreCase("BDS")) {

                            result = this.bdsServerImpl.sendFrom(amount, toAddress, memo);

                        } else {

                            CoinService coinService = new CoinServiceImpl(type);

                            result = coinService.sendFrom(amount, toAddress);

                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                    result.setSuccess(Boolean.valueOf(false));

                    result.setMsg("系統异常");

                }

            } else {

                LogFactory.info("提币安全校验失败----toAddress=" + toAddress + "   type=" + type + "  amount=" + amount + " transactionNum=" + transactionNum + "  keepDecimalForCoin=" + keepDecimalForCoin + "  memo=" + memo);

                result.setMsg("安全校验失败");

                result.setSuccess(Boolean.valueOf(false));

            }

        } else {

            LogFactory.info("提币参数包含空参----toAddress=" + toAddress + "   type=" + type + "  amount=" + amount + " transactionNum=" + transactionNum + "  keepDecimalForCoin=" + keepDecimalForCoin + "  memo=" + memo);

            result.setSuccess(Boolean.valueOf(false));

            result.setMsg("包含空参或参数有误，请检查");

        }
        /* 218 */
        LogFactory.info(com.alibaba.fastjson.JSON.toJSON(result));
        /* 219 */
        return result;
        /*     */
    }

    @RequestMapping({"/getAddressesByAccount"})
    /*     */
    @ResponseBody
    public String getAddressesByAccount(HttpServletRequest req) {

        String result = "";

        String type = req.getParameter("type");

        String account = req.getParameter("account");

        if ((null != type) && ("" != type)) {

            CoinService coinService = new CoinServiceImpl(type);

            if ((null != account) && ("" != account)) {

                List<String> list = coinService.getAddressesByAccount(account);

                result = com.azazar.krotjson.JSON.stringify(list);

                return result;

            }

        }

        return result;

    }

    @RequestMapping({"/listBalance"})

    @ResponseBody
    public String listBalance(HttpServletRequest req) {

        try {

            List<String> list = new ArrayList();

            String type = req.getParameter("type");

            String address = req.getParameter("address");

            if ((StringUtil.isNull(type)) && (StringUtil.isNull(address))) {

                if (type.toUpperCase().equals("ETH")) {

                    list.add("0");

                    address = EtherService.getBasecoin();

                    BigInteger money = EtherService.getBalance(address);

                    list.add(Convert.fromWei(money.toString(), Convert.Unit.ETHER).toString());

                } else {

                    CoinService coinService = new CoinServiceImpl(type);

                    list.add(String.valueOf(coinService.getBalance()));

                    list.add(String.valueOf(coinService.getBalance("")));

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    @RequestMapping({"/listWalletBalance"})

    @ResponseBody
    public List<Wallet> listWalletBalance(HttpServletRequest req) {

        List<Wallet> list = new ArrayList();

        List<String> coins = RedisUtil.listcoin();

        int i = 0;

        for (String coinCode : coins) {

            Wallet wallet = new Wallet();

            LogFactory.info("币名称:" + coinCode);

            if (coinCode.equalsIgnoreCase("ETH")) {

                wallet = EtherService.getEtherWalletInfo();

            } else if (EtherService.isSmartContractCoin(coinCode)) {

                wallet = EtherService.smartContract_getWalletInfo(coinCode);

            } else if (coinCode.equalsIgnoreCase("tv")) {

                wallet = TvUtil.getWalletInfo();

            } else if (coinCode.equalsIgnoreCase("USDT")) {

                JsonrpcClient client = MyCoinService.getClient(coinCode);

                try {

                    wallet = client.getUsdtWalletInfo();

                } catch (NullPointerException e) {

                    LogFactory.info(coinCode + "钱包接口ERROR");

                }

            } else if (coinCode.equalsIgnoreCase("BDS")) {

                wallet = this.bdsServerImpl.getWalletInfo();

            } else {

                CoinService coinService = new CoinServiceImpl(coinCode);

                wallet = coinService.getWalletInfo(coinCode);

            }

            wallet.setId(++i);

            list.add(wallet);

        }

        return list;

    }

    @RequestMapping({"/toColdAccount"})

    @ResponseBody
    public hry.utils.JsonResult toColdAccount(HttpServletRequest req) {

        hry.utils.JsonResult result = new hry.utils.JsonResult();

        Map<String, String> map = new HashMap();

        String type = req.getParameter("type");

        String amount = req.getParameter("amount");

        String fromAddress = "-";

        if ((StringUtils.isNotEmpty(type)) && (StringUtils.isNotEmpty(amount))) {

            type = type.toUpperCase();

            BigDecimal money = new BigDecimal(amount);

            String toAddress = (String) Properties.appcoinMap().get(type.toLowerCase() + "_coldAddress");

            boolean isValidAddress = true;

            if (StringUtils.isNotEmpty(toAddress)) {

                if (isValidAddress) {

                    if (type.equalsIgnoreCase("ETH")) {
                        result = EtherService.send2ColdWallet(toAddress, amount);

                    } else if (EtherService.isSmartContractCoin(type)) {

                        result.setMsg(type + "转入冷钱包失败");

                    } else if (type.equalsIgnoreCase("tv")) {

                        result = TvUtil.send2ColdAddress(toAddress, amount);

                    } else if (type.equalsIgnoreCase("USDT")) {

                        result.setMsg(type + "转入冷钱包失败");

                    } else if (type.equalsIgnoreCase("BDS")) {

                        result = this.bdsServerImpl.send2ColdAddress(toAddress, amount);

                    } else {

                        CoinService coinService = new CoinServiceImpl(type);

                        result = coinService.sendtoAddress(toAddress, Double.valueOf(money.doubleValue()));

                    }

                    if ((result.getSuccess().booleanValue()) && (StringUtils.isNotEmpty(result.getMsg()))) {

                        map.put("toAddress", toAddress);

                        map.put("fromAddress", fromAddress);

                        map.put("txHash", result.getMsg());

                        result.setObj(map);

                    } else {

                        LogFactory.info("转币冷钱包失败：" + com.alibaba.fastjson.JSON.toJSONString(result));

                    }

                } else {

                    result.setMsg(type + "_冷钱包地址无效，请检查");

                }

            } else {

                result.setMsg(type + "_冷钱包地址为空,请检查");

            }

        } else {

            result.setMsg("参数无效，请检查--type=" + type + "   amount=" + amount);

        }

        if (!result.getSuccess().booleanValue()) {

            LogFactory.info("转入冷钱包操作失败：" + result.getMsg());

        }

        return result;

    }

    @RequestMapping({"/list"})

    @ResponseBody
    public String list(HttpServletRequest req) {

        String type = req.getParameter("type");

        String userName = req.getParameter("userName");

        String count = req.getParameter("count");

        String startWith = req.getParameter("start");

        List<Transaction> list = null;

        String result = "";

        try {

            CoinService coinService = new CoinServiceImpl(type);

            if ((null != count) && (!"".equals(count))) {

                list = coinService.listTransactions(userName, Integer.valueOf(count));

                result = com.azazar.krotjson.JSON.stringify(list);

                if ((null != startWith) && (!"".equals(startWith))) {

                    list = coinService.listTransactions(userName, Integer.valueOf(count).intValue(), Integer.valueOf(startWith).intValue());

                    result = com.azazar.krotjson.JSON.stringify(list);

                } else {

                    list = coinService.listTransactions(userName, Integer.valueOf(count));

                    result = com.azazar.krotjson.JSON.stringify(list);

                }

            } else {

                list = coinService.listTransactions(userName, null);

                result = com.azazar.krotjson.JSON.stringify(list);
            }
            System.out.println("====交易记录返回" + result);

            return result;

        } catch (Exception e) {
        }

        return "Call interface error";

    }

    @RequestMapping({"/allList"})

    @ResponseBody
    public String allList(HttpServletRequest req) {

        String type = req.getParameter("type");

        List<Bitcoin.Transaction> list = null;

        String result = "";

        try {

            CoinService coinService = new CoinServiceImpl(type);

            list = coinService.listTransactions(null, null);

            result = com.azazar.krotjson.JSON.stringify(list);

            System.out.println("====" + list.toString());

        } catch (Exception e) {

            System.out.println("err:" + e.getMessage());

        }

        return result;

    }

    @RequestMapping({"/row"})

    @ResponseBody
    public String row(HttpServletRequest req) {

        String orderNO = req.getParameter("orderNo");

        String type = req.getParameter("type");

        String row = "";

        try {

            CoinService coinService = new CoinServiceImpl(type);

            row = coinService.getRawTransaction(orderNO);

        } catch (Exception e) {

            e.printStackTrace();

            row = "";

        }

        System.out.println("查询详细信息：" + row);

        return row;

    }

    @RequestMapping({"/listAccounts"})

    @ResponseBody
    public Map<String, Number> listAccounts(HttpServletRequest req) {

        String type = req.getParameter("type");

        if ((null != type) && ("" != type)) {

            CoinService coinService = new CoinServiceImpl(type);

            Map<String, Number> map = coinService.listaccounts();

            return map;

        }

        return null;

    }

    @RequestMapping({"/getAllUsers"})

    @ResponseBody
    public String getAllUsers(HttpServletRequest req) {

        String result = "";

        String type = req.getParameter("type");

        if ((null != type) && ("" != type)) {

            CoinService coinService = new CoinServiceImpl(type);

            Map<String, Number> map = coinService.listaccounts();

            result = com.azazar.krotjson.JSON.stringify(map);

            return result;

        }

        return result;

    }

    @RequestMapping({"/recordTransaction"})

    @ResponseBody
    public void recordTransaction() {

        String type = "dsc";

        CoinTransactionService txService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");

        hry.utils.JsonResult ret = txService.recordTransaction(type, null, null);

        System.out.println("ret==" + ret);

    }

    @RequestMapping({"/refreshUserCoin"})

    @MethodName(name = "单个用户刷币")

    @ResponseBody
    public hry.utils.JsonResult refreshUserCoin(HttpServletRequest request) {

        hry.utils.JsonResult ret = new hry.utils.JsonResult();
        /* 540 */
        String coinCode = request.getParameter("coinCode");
        /* 541 */
        String account = request.getParameter("account");
        /* 542 */
        String countstr = request.getParameter("count");
        /* 543 */
        int count = Integer.valueOf(countstr).intValue();
        /* 544 */
        if ((StringUtils.isNotEmpty(coinCode)) && (StringUtils.isNotEmpty(account)) && (StringUtils.isNotEmpty(countstr))) {
            /* 545 */
            String[] arrcoin = Properties.listCoinBasedBtc();
            /* 546 */
            if ((arrcoin != null) && (arrcoin.length > 0) && (Json.toJson(arrcoin).contains(coinCode.toUpperCase()))) {
                /* 547 */
                CoinTransactionService txService = (CoinTransactionService) ContextUtil.getBean("coinTransactionService");
                /* 548 */
                if (coinCode.equalsIgnoreCase("USDT")) {
                    /* 549 */
                    ExDigitalmoneyAccountService accountService = (ExDigitalmoneyAccountService) ContextUtil.getBean("exDigitalmoneyAccountService");
                    /* 550 */
                    ExDigitalmoneyAccount coinAccount = accountService.getAccountByAccountNumber(account);
                    /* 551 */
                    if ((coinAccount != null) && (StringUtils.isNotEmpty(coinAccount.getPublicKey()))) {
                        /* 552 */
                        ret = txService.recordTransaction_omni(coinCode, coinAccount.getPublicKey(), 0, 0, 0, 0);
                        /*     */
                    } else {
                        /* 554 */
                        ret.setCode("0000");
                        /*     */
                    }
                    /*     */
                } else {
                    /* 557 */
                    ret = txService.recordTransaction(coinCode, account, Integer.valueOf(count));
                    /*     */
                }
                /*     */
            }
            /*     */
            else {
                /* 561 */
                ret.setCode("0000");
                /* 562 */
                ret.setMsg("网络错误");
                /*     */
            }
            /*     */
        } else {
            /* 565 */
            ret.setMsg("参数不正确");
            /* 566 */
            LogFactory.info("参数：" + Json.toJson(request.getParameterMap()));
            /*     */
        }
        /* 568 */
        return ret;
        /*     */
    }

    /*     */
    /*     */
    @RequestMapping({"/validateaddress"})
    /*     */
    @MethodName(name = "验证钱包地址是否有效")
    /*     */
    @ResponseBody
    /*     */ public hry.core.mvc.model.page.JsonResult validateaddress(HttpServletRequest request)
    /*     */ {
        /* 576 */
        hry.core.mvc.model.page.JsonResult result = new hry.core.mvc.model.page.JsonResult();
        /* 577 */
        String coinCode = request.getParameter("coinCode");
        /* 578 */
        String address = request.getParameter("address");
        /* 579 */
        if ((StringUtils.isNotEmpty(coinCode)) && (StringUtils.isNotEmpty(address))) {
            /* 580 */
            if ((coinCode.equalsIgnoreCase("ETH")) || (EtherService.isSmartContractCoin(coinCode))) {
                /* 581 */
                if (EtherService.isAddress(address)) {
                    /* 582 */
                    result.setSuccess(Boolean.valueOf(true));
                    /* 583 */
                    result.setMsg("验证成功");
                    /*     */
                } else {
                    /* 585 */
                    result.setSuccess(Boolean.valueOf(false));
                    /* 586 */
                    result.setMsg("验证失败");
                    /*     */
                }
                /* 588 */
            } else if (coinCode.equalsIgnoreCase("tv")) {
                /* 589 */
                boolean isvalid = TvUtil.walletCheckAddress(address);
                /* 590 */
                if (isvalid) {
                    /* 591 */
                    result.setSuccess(Boolean.valueOf(true));
                    /* 592 */
                    result.setMsg("验证成功");
                    /*     */
                } else {
                    /* 594 */
                    result.setSuccess(Boolean.valueOf(false));
                    /* 595 */
                    result.setMsg("验证失败");
                    /*     */
                }
                /*     */
            } else {
                /* 598 */
                CoinService coinService = new CoinServiceImpl(coinCode);
                /* 599 */
                String validate = coinService.validateAddress(address);
                /* 600 */
                validate = validate.replace(" ", "");
                /* 601 */
                Map<String, Object> map = StringUtil.str2map(validate);
                /* 602 */
                boolean isvalid = Boolean.parseBoolean(map.get("isvalid").toString());
                /* 603 */
                if (isvalid) {
                    /* 604 */
                    result.setSuccess(Boolean.valueOf(true));
                    /* 605 */
                    result.setMsg("验证成功");
                    /*     */
                } else {
                    /* 607 */
                    result.setSuccess(Boolean.valueOf(false));
                    /* 608 */
                    result.setMsg("验证失败");
                    /*     */
                }
                /*     */
            }
            /*     */
        } else {
            /* 612 */
            String message = "参数不正确，请检查";
            /* 613 */
            result.setMsg(message);
            /* 614 */
            result.setSuccess(Boolean.valueOf(false));
            /*     */
        }
        /* 616 */
        if (!result.getSuccess().booleanValue()) {
            /* 617 */
            String degugger = "\tvalidateaddress方法参数：\tcoinCode=" + coinCode + "    address=" + address;
            /* 618 */
            LogFactory.info(degugger);
            /*     */
        }
        /* 620 */
        return result;
        /*     */
    }

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
    @RequestMapping({"/recaptureGethTx"})
    /*     */
    @ResponseBody
    /*     */ public hry.utils.JsonResult recapture(HttpServletRequest req)
    /*     */ {
        /* 637 */
        hry.utils.JsonResult result = new hry.utils.JsonResult();
        /* 638 */
        String hash = req.getParameter("hash");
        /* 639 */
        if ((StringUtils.isNotEmpty(hash)) && (hash.length() == 66)) {
            /* 640 */
            EtherService.recaptureGethTx(hash);
            /*     */
        } else {
            /* 642 */
            result.setMsg("hash不正确");
            /*     */
        }
        /* 644 */
        return result;
        /*     */
    }

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
    @RequestMapping({"/test"})
    /*     */
    @ResponseBody
    /*     */ public hry.utils.JsonResult test(HttpServletRequest req)
    /*     */ {
        /* 663 */
        hry.utils.JsonResult result = new hry.utils.JsonResult();
        /*     */
        /*     */
        /*     */
        /*     */
        /* 668 */
        String type = "USDT";
        /* 669 */
        CoinService coinService = new CoinServiceImpl(type);
        /* 670 */
        String account = "*";
        /* 671 */
        Integer count = Integer.valueOf(20);
        /* 672 */
        List<Bitcoin.Transaction> list = coinService.listTransactions(account, count);
        /* 673 */
        System.out.println(list.size());
        /* 674 */
        return result;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\coin\controller\CoinController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */