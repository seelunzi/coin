
package hry.coin.neo;

import lombok.Data;

@Data
public class NeoEntity {
    private String n;
    private String asset;
    private String value;
    private String address;
    private String txId;
}
