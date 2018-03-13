package hry.app.ourAccount.model;

import hry.core.mvc.model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "app_our_account")
@Data
public class AppOurAccount
        extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "accountType")
    private String accountType;

    @Column(name = "openAccountType")
    private String openAccountType;

    @Column(name = "bankName")
    private String bankName;

    @Transient
    private String dicBankName;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "openTime")
    private Date openTime;

    @Column(name = "accountMoney")
    private BigDecimal accountMoney;

    @Column(name = "accountMoneyNew")
    private BigDecimal accountMoneyNew;

    @Column(name = "todayAddedMoney")
    private BigDecimal todayAddedMoney;

    @Column(name = "accountFee")
    private BigDecimal accountFee;

    @Column(name = "hasOutFee")
    private BigDecimal hasOutFee;

    @Column(name = "todayAddedFee")
    private BigDecimal todayAddedFee;

    @Column(name = "currencyType")
    private String currencyType;

    @Column(name = "bankLogo")
    private String bankLogo;

    @Column(name = "isShow")
    private Integer isShow;

    @Column(name = "bankAddress")
    private String bankAddress;

    @Column(name = "remark")
    private String remark;

    @Column(name = "website")
    private String website;

    @Column(name = "retainsMoney")
    private BigDecimal retainsMoney;

    @Transient
    private BigDecimal coinTotalMoney;

    @Transient
    private BigDecimal withdrawMoney;
}
