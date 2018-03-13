/*     */
package hry.ex.dmHotAccountRecord.model;
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
@Table(name = "ex_dm_hot_account_record")
/*     */ public class ExDmHotAccountRecord
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
    @Column(name = "accountId")
    /*     */ private Long accountId;
    /*     */
    @Column(name = "customerId")
    /*     */ private Long customerId;
    /*     */
    @Column(name = "userName")
    /*     */ private String userName;
    /*     */
    @Column(name = "recordType")
    /*     */ private Integer recordType;
    /*     */
    @Column(name = "transactionMoney")
    /*     */ private BigDecimal transactionMoney;
    /*     */
    @Column(name = "transactionNum")
    /*     */ private String transactionNum;
    /*     */
    @Column(name = "status")
    /*     */ private Integer status;
    /*     */
    @Column(name = "remark")
    /*     */ private String remark;
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
    @Column(name = "trueName")
    /*     */ private String trueName;

    /*     */
    /*     */
    public Long getId()
    /*     */ {
        /*  79 */
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
        /*  90 */
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
    public Long getAccountId()
    /*     */ {
        /* 101 */
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
        /* 112 */
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
    public Long getCustomerId()
    /*     */ {
        /* 123 */
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
        /* 134 */
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
    public String getUserName()
    /*     */ {
        /* 145 */
        return this.userName;
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
    public void setUserName(String userName)
    /*     */ {
        /* 156 */
        this.userName = userName;
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
    public Integer getRecordType()
    /*     */ {
        /* 167 */
        return this.recordType;
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
    public void setRecordType(Integer recordType)
    /*     */ {
        /* 178 */
        this.recordType = recordType;
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
        /* 189 */
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
        /* 200 */
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
    public String getTransactionNum()
    /*     */ {
        /* 211 */
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
        /* 222 */
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
    public Integer getStatus()
    /*     */ {
        /* 233 */
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
        /* 244 */
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
    public String getRemark()
    /*     */ {
        /* 255 */
        return this.remark;
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
    public void setRemark(String remark)
    /*     */ {
        /* 266 */
        this.remark = remark;
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
        /* 277 */
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
        /* 288 */
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
        /* 299 */
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
        /* 310 */
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
        /* 321 */
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
        /* 332 */
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
    public String getTrueName()
    /*     */ {
        /* 343 */
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
        /* 354 */
        this.trueName = trueName;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\dmHotAccountRecord\model\ExDmHotAccountRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */