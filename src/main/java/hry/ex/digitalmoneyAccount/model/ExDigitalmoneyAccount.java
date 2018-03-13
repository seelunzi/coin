/*     */
package hry.ex.digitalmoneyAccount.model;
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
@Table(name = "ex_digitalmoney_account")
/*     */ public class ExDigitalmoneyAccount
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
    @Column(name = "version")
    /*     */ private Integer version;
    /*     */
    @Column(name = "customerId")
    /*     */ private Long customerId;
    /*     */
    @Column(name = "hotMoney")
    /*     */ private BigDecimal hotMoney;
    /*     */
    @Column(name = "coldMoney")
    /*     */ private BigDecimal coldMoney;
    /*     */
    @Column(name = "userName")
    /*     */ private String userName;
    /*     */
    @Column(name = "accountNum")
    /*     */ private String accountNum;
    /*     */
    @Column(name = "currencyType")
    /*     */ private String currencyType;
    /*     */
    @Column(name = "status")
    /*     */ private Integer status;
    /*     */
    @Column(name = "publicKey")
    /*     */ private String publicKey;
    /*     */
    @Column(name = "privateKey")
    /*     */ private String privateKey;
    /*     */
    @Column(name = "lendMoney")
    /*     */ private BigDecimal lendMoney;
    /*     */
    @Column(name = "coinName")
    /*     */ private String coinName;
    /*     */
    @Column(name = "coinCode")
    /*     */ private String coinCode;
    /*     */
    @Column(name = "website")
    /*     */ private String website;
    /*     */
    @Column(name = "psitioNaveragePrice")
    /*     */ private BigDecimal psitioNaveragePrice;
    /*     */
    @Column(name = "psitioProtectPrice")
    /*     */ private BigDecimal psitioProtectPrice;
    /*     */
    @Column(name = "sumCost")
    /*     */ private BigDecimal sumCost;
    /*     */
    @Column(name = "trueName")
    /*     */ private String trueName;
    /*     */
    @Column(name = "disableMoney")
    /*     */ private BigDecimal disableMoney;
    /*     */
    @Column(name = "surname")
    /*     */ private String surname;

    /*     */
    /*     */
    public Long getId()
    /*     */ {
        /* 104 */
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
        /* 115 */
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
    public Integer getVersion()
    /*     */ {
        /* 126 */
        return this.version;
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
    public void setVersion(Integer version)
    /*     */ {
        /* 137 */
        this.version = version;
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
        /* 148 */
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
        /* 159 */
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
    public BigDecimal getHotMoney()
    /*     */ {
        /* 170 */
        return this.hotMoney;
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
    public void setHotMoney(BigDecimal hotMoney)
    /*     */ {
        /* 181 */
        this.hotMoney = hotMoney;
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
    public BigDecimal getColdMoney()
    /*     */ {
        /* 192 */
        return this.coldMoney;
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
    public void setColdMoney(BigDecimal coldMoney)
    /*     */ {
        /* 203 */
        this.coldMoney = coldMoney;
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
        /* 214 */
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
        /* 225 */
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
    public String getAccountNum()
    /*     */ {
        /* 236 */
        return this.accountNum;
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
    public void setAccountNum(String accountNum)
    /*     */ {
        /* 247 */
        this.accountNum = accountNum;
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
        /* 258 */
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
        /* 269 */
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
    public Integer getStatus()
    /*     */ {
        /* 280 */
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
        /* 291 */
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
    public String getPublicKey()
    /*     */ {
        /* 302 */
        return this.publicKey;
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
    public void setPublicKey(String publicKey)
    /*     */ {
        /* 313 */
        this.publicKey = publicKey;
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
    public String getPrivateKey()
    /*     */ {
        /* 324 */
        return this.privateKey;
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
    public void setPrivateKey(String privateKey)
    /*     */ {
        /* 335 */
        this.privateKey = privateKey;
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
    public BigDecimal getLendMoney()
    /*     */ {
        /* 346 */
        return this.lendMoney;
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
    public void setLendMoney(BigDecimal lendMoney)
    /*     */ {
        /* 357 */
        this.lendMoney = lendMoney;
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
    public String getCoinName()
    /*     */ {
        /* 368 */
        return this.coinName;
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
    public void setCoinName(String coinName)
    /*     */ {
        /* 379 */
        this.coinName = coinName;
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
        /* 390 */
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
        /* 401 */
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
        /* 412 */
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
        /* 423 */
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
    public BigDecimal getPsitioNaveragePrice()
    /*     */ {
        /* 434 */
        return this.psitioNaveragePrice;
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
    public void setPsitioNaveragePrice(BigDecimal psitioNaveragePrice)
    /*     */ {
        /* 445 */
        this.psitioNaveragePrice = psitioNaveragePrice;
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
    public BigDecimal getPsitioProtectPrice()
    /*     */ {
        /* 456 */
        return this.psitioProtectPrice;
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
    public void setPsitioProtectPrice(BigDecimal psitioProtectPrice)
    /*     */ {
        /* 467 */
        this.psitioProtectPrice = psitioProtectPrice;
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
    public BigDecimal getSumCost()
    /*     */ {
        /* 478 */
        return this.sumCost;
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
    public void setSumCost(BigDecimal sumCost)
    /*     */ {
        /* 489 */
        this.sumCost = sumCost;
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
        /* 500 */
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
        /* 511 */
        this.trueName = trueName;
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
    public BigDecimal getDisableMoney()
    /*     */ {
        /* 522 */
        return this.disableMoney;
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
    public void setDisableMoney(BigDecimal disableMoney)
    /*     */ {
        /* 533 */
        this.disableMoney = disableMoney;
        /*     */
    }

    /*     */
    /*     */
    public String getSurname() {
        /* 537 */
        return this.surname;
        /*     */
    }

    /*     */
    /*     */
    public void setSurname(String surname) {
        /* 541 */
        this.surname = surname;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\ex\digitalmoneyAccount\model\ExDigitalmoneyAccount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */