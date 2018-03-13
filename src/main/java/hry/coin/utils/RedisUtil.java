/*    */
package hry.coin.utils;
/*    */
/*    */

import com.alibaba.fastjson.JSON;
import hry.core.util.sys.ContextUtil;
import hry.redis.common.utils.RedisService;

import java.util.List;

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
/*    */ public class RedisUtil
        /*    */ {
    /*    */
    public static List<String> listcoin()
    /*    */ {
        /* 24 */
        RedisService redisService = (RedisService) ContextUtil.getBean("redisService");
        /* 25 */
        if (redisService != null) {
            /* 26 */
            String listcoinstr = redisService.get(ContextUtil.getWebsite() + ":productList");
            /* 27 */
            if (listcoinstr != null) {
                /* 28 */
                return JSON.parseArray(listcoinstr, String.class);
                /*    */
            }
            /* 30 */
            System.out.println("未从redis中查到coins");
            /* 31 */
            return null;
            /*    */
        }
        /*    */
        /* 34 */
        System.out.println("redis连接错误");
        /* 35 */
        return null;
        /*    */
    }

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
    public static void setValue(String key, String val)
    /*    */ {
        /* 50 */
        RedisService redisService = (RedisService) ContextUtil.getBean("redisService");
        /* 51 */
        redisService.save(key, val);
        /*    */
    }

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
    public static String getValue(String key)
    /*    */ {
        /* 64 */
        RedisService redisService = (RedisService) ContextUtil.getBean("redisService");
        /* 65 */
        return redisService.get(key);
        /*    */
    }
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\utils\RedisUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */