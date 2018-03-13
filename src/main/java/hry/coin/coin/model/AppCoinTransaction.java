
package hry.coin.coin.model;
import hry.core.mvc.model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "app_coin_transaction")
@Data
public class AppCoinTransaction
        extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "hash_")
    private String hash_;
    @Column(name = "nonce")
    private String nonce;

    @Column(name = "blockHash")
    private String blockHash;

    @Column(name = "blockNumber")
    private String blockNumber;
    @Column(name = "transactionIndex")
    private String transactionIndex;

    @Column(name = "from_")
    private String from_;

    @Column(name = "to_")
    private String to_;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "gas")
    private BigDecimal gas;

    @Column(name = "gasPrice")
    private BigDecimal gasPrice;

    @Column(name = "input")
    private String input;

    @Column(name = "coinType")
    private String coinType;

    @Column(name = "isconsume")
    private int isconsume;
}
