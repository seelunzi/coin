
package hry.coin.mobilecoin.model;

import hry.core.mvc.model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "mobile_coin_transaction")
@Data
public class MobileCoinTransaction
        extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "transactionNum")
    private String transactionNum;

    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "transactionType")
    private Integer transactionType;

    @Column(name = "transactionMoney")
    private BigDecimal transactionMoney;

    @Column(name = "status")
    private Integer status;

    @Column(name = "coinCode")
    private String coinCode;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "inAddress")
    private String inAddress;

    @Column(name = "outAddress")
    private String outAddress;

    @Column(name = "confirmations")
    private String confirmations;

    @Column(name = "blocktime")
    private String blocktime;

    @Column(name = "time")
    private String time;

    @Column(name = "timereceived")
    private String timereceived;

    @Column(name = "ourAccountNumber")
    private String ourAccountNumber;

    @Column(name = "orderNo")
    private String orderNo;
}
