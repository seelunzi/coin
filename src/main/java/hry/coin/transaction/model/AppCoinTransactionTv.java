
package hry.coin.transaction.model;


import hry.core.mvc.model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "app_coin_transaction_tv")
@Data
public class AppCoinTransactionTv
        extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "trxId")
    private String trxId;

    @Column(name = "blockNum")
    private Integer blockNum;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "isconfirmed")
    private Integer isconfirmed;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "fromAddress")
    private String fromAddress;

    @Column(name = "fromAccount")
    private String fromAccount;

    @Column(name = "memo")
    private String memo;

    @Column(name = "toAccount")
    private String toAccount;

    @Column(name = "isUse")
    private int isUse;

    @Column(name = "type")
    private String type;
}
