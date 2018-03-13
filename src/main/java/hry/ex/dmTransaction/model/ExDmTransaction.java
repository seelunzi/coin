/*     */
package hry.ex.dmTransaction.model;
/*     */
/*     */

import hry.core.mvc.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
@Table(name = "ex_dm_transaction")
/*     */ public class ExDmTransaction
        /*     */ extends BaseModel
        /*     */ {
    /*     */
    @Id
    /*     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*     */
    @Column(name = "id", unique = true, nullable = false)
    /*     */ private Long id;
    /*     */
    @Column(name = "transactionNum")
    /*     */ private String transactionNum;
    /*     */
    @Column(name = "customerId")
    /*     */ private Long customerId;
    /*     */
    @Column(name = "customerName")
    /*     */ private String customerName;
    /*     */
    @Column(name = "accountId")
    /*     */ private Long accountId;
    /*     */
    @Column(name = "transactionType")
    /*     */ private Integer transactionType;
    /*     */
    @Column(name = "transactionMoney")
    /*     */ private BigDecimal transactionMoney;
    /*     */
    @Column(name = "status")
    /*     */ private Integer status;
    /*     */
    @Column(name = "userId")
    /*     */ private Long userId;
    /*     */
    @Column(name = "currencyType")
    /*     */ private String currencyType;
    /*     */
    @Column(name = "coinCode")
    /*     */ private String coinCode;
    /*     */
    @Column(name = "website")
    /*     */ private String website;
    /*     */
    @Column(name = "fee")
    /*     */ private BigDecimal fee;
    /*     */
    @Column(name = "inAddress")
    /*     */ private String inAddress;
    /*     */
    @Column(name = "outAddress")
    /*     */ private String outAddress;
    /*     */
    @Column(name = "time")
    /*     */ private String time;
    /*     */
    @Column(name = "confirmations")
    /*     */ private String confirmations;
    /*     */
    @Column(name = "timereceived")
    /*     */ private String timereceived;
    /*     */
    @Column(name = "blocktime")
    /*     */ private String blocktime;
    /*     */
    @Column(name = "rejectionReason")
    /*     */ private String rejectionReason;
    /*     */
    @Column(name = "ourAccountNumber")
    /*     */ private String ourAccountNumber;
    /*     */
    @Column(name = "orderNo")
    /*     */ private String orderNo;
    /*     */
    @Column(name = "trueName")
    /*     */ private String trueName;
    /*     */
    @Column(name = "surname")
    /*     */ private String surname;

    /*     */
    /*     */
    public Long getId()
    /*     */ {
        /* 113 */
        return this.id;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setId(Long id)
    /*     */ {
        /* 124 */
        this.id = id;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getTransactionNum()
    /*     */ {
        /* 135 */
        return this.transactionNum;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setTransactionNum(String transactionNum)
    /*     */ {
        /* 146 */
        this.transactionNum = transactionNum;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public Long getCustomerId()
    /*     */ {
        /* 157 */
        return this.customerId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setCustomerId(Long customerId)
    /*     */ {
        /* 168 */
        this.customerId = customerId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getCustomerName()
    /*     */ {
        /* 179 */
        return this.customerName;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setCustomerName(String customerName)
    /*     */ {
        /* 190 */
        this.customerName = customerName;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public Long getAccountId()
    /*     */ {
        /* 201 */
        return this.accountId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setAccountId(Long accountId)
    /*     */ {
        /* 212 */
        this.accountId = accountId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public Integer getTransactionType()
    /*     */ {
        /* 223 */
        return this.transactionType;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setTransactionType(Integer transactionType)
    /*     */ {
        /* 234 */
        this.transactionType = transactionType;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public BigDecimal getTransactionMoney()
    /*     */ {
        /* 245 */
        return this.transactionMoney;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setTransactionMoney(BigDecimal transactionMoney)
    /*     */ {
        /* 256 */
        this.transactionMoney = transactionMoney;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public Integer getStatus()
    /*     */ {
        /* 267 */
        return this.status;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setStatus(Integer status)
    /*     */ {
        /* 278 */
        this.status = status;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public Long getUserId()
    /*     */ {
        /* 289 */
        return this.userId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setUserId(Long userId)
    /*     */ {
        /* 300 */
        this.userId = userId;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getCurrencyType()
    /*     */ {
        /* 311 */
        return this.currencyType;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setCurrencyType(String currencyType)
    /*     */ {
        /* 322 */
        this.currencyType = currencyType;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getCoinCode()
    /*     */ {
        /* 333 */
        return this.coinCode;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setCoinCode(String coinCode)
    /*     */ {
        /* 344 */
        this.coinCode = coinCode;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getWebsite()
    /*     */ {
        /* 355 */
        return this.website;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setWebsite(String website)
    /*     */ {
        /* 366 */
        this.website = website;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public BigDecimal getFee()
    /*     */ {
        /* 377 */
        return this.fee;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setFee(BigDecimal fee)
    /*     */ {
        /* 388 */
        this.fee = fee;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getInAddress()
    /*     */ {
        /* 399 */
        return this.inAddress;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setInAddress(String inAddress)
    /*     */ {
        /* 410 */
        this.inAddress = inAddress;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getOutAddress()
    /*     */ {
        /* 421 */
        return this.outAddress;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setOutAddress(String outAddress)
    /*     */ {
        /* 432 */
        this.outAddress = outAddress;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getTime()
    /*     */ {
        /* 443 */
        return this.time;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setTime(String time)
    /*     */ {
        /* 454 */
        this.time = time;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getConfirmations()
    /*     */ {
        /* 465 */
        return this.confirmations;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setConfirmations(String confirmations)
    /*     */ {
        /* 476 */
        this.confirmations = confirmations;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getTimereceived()
    /*     */ {
        /* 487 */
        return this.timereceived;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setTimereceived(String timereceived)
    /*     */ {
        /* 498 */
        this.timereceived = timereceived;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getBlocktime()
    /*     */ {
        /* 509 */
        return this.blocktime;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setBlocktime(String blocktime)
    /*     */ {
        /* 520 */
        this.blocktime = blocktime;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getRejectionReason()
    /*     */ {
        /* 531 */
        return this.rejectionReason;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setRejectionReason(String rejectionReason)
    /*     */ {
        /* 542 */
        this.rejectionReason = rejectionReason;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getOurAccountNumber()
    /*     */ {
        /* 553 */
        return this.ourAccountNumber;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setOurAccountNumber(String ourAccountNumber)
    /*     */ {
        /* 564 */
        this.ourAccountNumber = ourAccountNumber;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getOrderNo()
    /*     */ {
        /* 575 */
        return this.orderNo;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setOrderNo(String orderNo)
    /*     */ {
        /* 586 */
        this.orderNo = orderNo;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String getTrueName()
    /*     */ {
        /* 597 */
        return this.trueName;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setTrueName(String trueName)
    /*     */ {
        /* 608 */
        this.trueName = trueName;
        /*     */
    }

    /*     */
    /*     */
    public String getSurname() {
        /* 612 */
        return this.surname;
        /*     */
    }

    /*     */
    /*     */
    public void setSurname(String surname) {
        /* 616 */
        this.surname = surname;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\dmTransaction\model\ExDmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */