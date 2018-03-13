/*    */
package hry.coin.znhy.util;
/*    */
/*    */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Properties;

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
/*    */ public class Consts
        /*    */ {
    /* 19 */   private static Properties p = new Properties();

    /* 20 */   static {
        InputStream in = Consts.class.getResourceAsStream("/config.properties");
        /* 21 */
        InputStreamReader r = new InputStreamReader(in, Charset.forName("UTF-8"));
        /*    */
        try {
            /* 23 */
            p.load(r);
            /* 24 */
            in.close();
            /*    */
        } catch (IOException e) {
            /* 26 */
            e.printStackTrace();
            /*    */
        }
        /*    */
    }

    /*    */
    /*    */
    /* 31 */   public static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    /*    */
    /* 33 */   public static BigInteger GAS_LIMIT = BigInteger.valueOf(4300000L);
    /*    */
    /* 35 */   public static BigInteger ETHER = new BigInteger("1000000000000000000");
    /*    */
    /*    */
    /* 38 */   public static String PASSWORD = p.getProperty("password");
    /*    */
    /* 40 */   public static String PATH = p.getProperty("path");
    /*    */
    /* 42 */   public static String DIRECTORY = p.getProperty("directory");
    /*    */
    /* 44 */   public static String HELLOWORLD_ADDR = p.getProperty("helloworldAddr");
    /*    */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\znhy\util\Consts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */