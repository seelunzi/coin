package hry.coin.neo;

import java.util.List;
import java.util.Map;

public abstract interface NeoService {
    public abstract boolean validateAddress(String paramString);

    public abstract Map getaccountstate(String paramString);

    public abstract List<NeoEntity> getblock(String paramString);

    public abstract int getblockcount();

    public abstract Map sendfrom(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\neo\NeoService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */