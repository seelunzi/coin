
package hry.ex.dmTransaction.model;


import hry.core.mvc.model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "ex_dm_transaction")
@Data
public class ExDmTransaction
        extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "transactionNum")
    private String transactionNum;

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "transactionType")
    private Integer transactionType;

    @Column(name = "transactionMoney")
    private BigDecimal transactionMoney;

    @Column(name = "status")
    private Integer status;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "currencyType")
    private String currencyType;

    @Column(name = "coinCode")
    private String coinCode;

    @Column(name = "website")
    private String website;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "inAddress")
    private String inAddress;

    @Column(name = "outAddress")
    private String outAddress;

    @Column(name = "time")
    private String time;

    @Column(name = "confirmations")
    private String confirmations;

    @Column(name = "timereceived")
    private String timereceived;

    @Column(name = "blocktime")
    private String blocktime;

    @Column(name = "rejectionReason")
    private String rejectionReason;

    @Column(name = "ourAccountNumber")
    private String ourAccountNumber;

    @Column(name = "orderNo")
    private String orderNo;

    @Column(name = "trueName")
    private String trueName;

    @Column(name = "surname")
    private String surname;

}
