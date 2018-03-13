/*     */
package hry.coin.mobilecoin.model;
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
@Table(name = "mobile_customer")
/*     */ public class MobileCustomer
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
    @Column(name = "accountName")
    /*     */ private String accountName;
    /*     */
    @Column(name = "coinAddr")
    /*     */ private String coinAddr;
    /*     */
    @Column(name = "passWord")
    /*     */ private String passWord;
    /*     */
    @Column(name = "mobileId")
    /*     */ private String mobileId;
    /*     */
    @Column(name = "coinType")
    /*     */ private String coinType;
    /*     */
    @Column(name = "accountPassWord")
    /*     */ private String accountPassWord;
    /*     */
    @Column(name = "hotCurrency")
    /*     */ private BigDecimal hotCurrency;
    /*     */
    @Column(name = "coldCurrency")
    /*     */ private BigDecimal coldCurrency;

    /*     */
    /*     */
    public String getAccountName()
    /*     */ {
        /*  55 */
        return this.accountName;
        /*     */
    }

    /*     */
    /*     */
    public void setAccountName(String accountName) {
        /*  59 */
        this.accountName = accountName;
        /*     */
    }

    /*     */
    /*     */
    public String getCoinType() {
        /*  63 */
        return this.coinType;
        /*     */
    }

    /*     */
    /*     */
    public void setCoinType(String coinType) {
        /*  67 */
        this.coinType = coinType;
        /*     */
    }

    /*     */
    /*     */
    public String getMobileId() {
        /*  71 */
        return this.mobileId;
        /*     */
    }

    /*     */
    /*     */
    public void setMobileId(String mobileId) {
        /*  75 */
        this.mobileId = mobileId;
        /*     */
    }

    /*     */
    /*     */
    public Long getId() {
        /*  79 */
        return this.id;
        /*     */
    }

    /*     */
    /*     */
    public void setId(Long id) {
        /*  83 */
        this.id = id;
        /*     */
    }

    /*     */
    /*     */
    public String getCoinAddr() {
        /*  87 */
        return this.coinAddr;
        /*     */
    }

    /*     */
    /*     */
    public void setCoinAddr(String coinAddr) {
        /*  91 */
        this.coinAddr = coinAddr;
        /*     */
    }

    /*     */
    /*     */
    public String getPassWord() {
        /*  95 */
        return this.passWord;
        /*     */
    }

    /*     */
    /*     */
    public void setPassWord(String passWord) {
        /*  99 */
        this.passWord = passWord;
        /*     */
    }

    /*     */
    /*     */
    public String getAccountPassWord() {
        /* 103 */
        return this.accountPassWord;
        /*     */
    }

    /*     */
    /*     */
    public void setAccountPassWord(String accountPassWord) {
        /* 107 */
        this.accountPassWord = accountPassWord;
        /*     */
    }

    /*     */
    /*     */
    public BigDecimal getHotCurrency() {
        /* 111 */
        return this.hotCurrency;
        /*     */
    }

    /*     */
    /*     */
    public void setHotCurrency(BigDecimal hotCurrency) {
        /* 115 */
        this.hotCurrency = hotCurrency;
        /*     */
    }

    /*     */
    /*     */
    public BigDecimal getColdCurrency() {
        /* 119 */
        return this.coldCurrency;
        /*     */
    }

    /*     */
    /*     */
    public void setColdCurrency(BigDecimal coldCurrency) {
        /* 123 */
        this.coldCurrency = coldCurrency;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\mobilecoin\model\MobileCustomer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */