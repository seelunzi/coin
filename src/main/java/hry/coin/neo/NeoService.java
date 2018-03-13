package hry.coin.neo;

import java.util.List;
import java.util.Map;

public interface NeoService {
    boolean validateAddress(String paramString);

    Map getaccountstate(String paramString);

    List<NeoEntity> getblock(String paramString);

    int getblockcount();

    Map sendfrom(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
}