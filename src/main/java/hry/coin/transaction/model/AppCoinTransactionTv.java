/*     */
package hry.coin.transaction.model;
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
@Table(name = "app_coin_transaction_tv")
/*     */ public class AppCoinTransactionTv
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
    @Column(name = "trxId")
    /*     */ private String trxId;
    /*     */
    @Column(name = "blockNum")
    /*     */ private Integer blockNum;
    /*     */
    @Column(name = "fee")
    /*     */ private BigDecimal fee;
    /*     */
    @Column(name = "isconfirmed")
    /*     */ private Integer isconfirmed;
    /*     */
    @Column(name = "amount")
    /*     */ private BigDecimal amount;
    /*     */
    @Column(name = "fromAddress")
    /*     */ private String fromAddress;
    /*     */
    @Column(name = "fromAccount")
    /*     */ private String fromAccount;
    /*     */
    @Column(name = "memo")
    /*     */ private String memo;
    /*     */
    @Column(name = "toAccount")
    /*     */ private String toAccount;
    /*     */
    @Column(name = "isUse")
    /*     */ private int isUse;
    /*     */
    @Column(name = "type")
    /*     */ private String type;

    /*     */
    /*     */
    public Long getId()
    /*     */ {
        /*  72 */
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
        /*  83 */
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
    public String getTrxId()
    /*     */ {
        /*  94 */
        return this.trxId;
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
    public void setTrxId(String trxId)
    /*     */ {
        /* 105 */
        this.trxId = trxId;
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
    public Integer getBlockNum()
    /*     */ {
        /* 116 */
        return this.blockNum;
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
    public void setBlockNum(Integer blockNum)
    /*     */ {
        /* 127 */
        this.blockNum = blockNum;
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
        /* 138 */
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
        /* 149 */
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
    public Integer getIsconfirmed()
    /*     */ {
        /* 160 */
        return this.isconfirmed;
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
    public void setIsconfirmed(Integer isconfirmed)
    /*     */ {
        /* 171 */
        this.isconfirmed = isconfirmed;
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
    public BigDecimal getAmount()
    /*     */ {
        /* 182 */
        return this.amount;
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
    public void setAmount(BigDecimal amount)
    /*     */ {
        /* 193 */
        this.amount = amount;
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
    public String getFromAddress()
    /*     */ {
        /* 204 */
        return this.fromAddress;
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
    public void setFromAddress(String fromAddress)
    /*     */ {
        /* 215 */
        this.fromAddress = fromAddress;
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
    public String getFromAccount()
    /*     */ {
        /* 226 */
        return this.fromAccount;
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
    public void setFromAccount(String fromAccount)
    /*     */ {
        /* 237 */
        this.fromAccount = fromAccount;
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
    public String getMemo()
    /*     */ {
        /* 248 */
        return this.memo;
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
    public void setMemo(String memo)
    /*     */ {
        /* 259 */
        this.memo = memo;
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
    public String getToAccount()
    /*     */ {
        /* 270 */
        return this.toAccount;
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
    public void setToAccount(String toAccount)
    /*     */ {
        /* 281 */
        this.toAccount = toAccount;
        /*     */
    }

    /*     */
    /*     */
    public int getIsUse() {
        /* 285 */
        return this.isUse;
        /*     */
    }

    /*     */
    /*     */
    public void setIsUse(int isUse) {
        /* 289 */
        this.isUse = isUse;
        /*     */
    }

    /*     */
    /*     */
    public String getType() {
        /* 293 */
        return this.type;
        /*     */
    }

    /*     */
    /*     */
    public void setType(String type) {
        /* 297 */
        this.type = type;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\transaction\model\AppCoinTransactionTv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */