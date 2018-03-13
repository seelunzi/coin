
package hry.coin.utils;


import com.alibaba.fastjson.JSON;
import hry.core.util.sys.ContextUtil;
import hry.redis.common.utils.RedisService;

import java.util.List;


public class RedisUtil {

    public static List<String> listcoin() {
        RedisService redisService = (RedisService) ContextUtil.getBean("redisService");
        if (redisService != null) {
            String listcoinstr = redisService.get(ContextUtil.getWebsite() + ":productList");
            if (listcoinstr != null) {
                return JSON.parseArray(listcoinstr, String.class);
            }
            System.out.println("未从redis中查到coins");
            return null;
        }
        System.out.println("redis连接错误");
        return null;
    }

    public static void setValue(String key, String val) {
        RedisService redisService = (RedisService) ContextUtil.getBean("redisService");
        redisService.save(key, val);
    }

    public static String getValue(String key) {
        RedisService redisService = (RedisService) ContextUtil.getBean("redisService");
        return redisService.get(key);
    }
}
