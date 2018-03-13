
package hry.coin.mobilecoin.model;

import hry.core.mvc.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "mobile_coin_transaction")
/*     */ public class MobileCoinTransaction
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
    @Column(name = "accountId")
    /*     */ private Long accountId;
    /*     */
    @Column(name = "accountName")
    /*     */ private String accountName;
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
    @Column(name = "coinCode")
    /*     */ private String coinCode;
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
    @Column(name = "confirmations")
    /*     */ private String confirmations;
    /*     */
    @Column(name = "blocktime")
    /*     */ private String blocktime;
    /*     */
    @Column(name = "time")
    /*     */ private String time;
    /*     */
    @Column(name = "timereceived")
    /*     */ private String timereceived;
    /*     */
    @Column(name = "ourAccountNumber")
    /*     */ private String ourAccountNumber;
    /*     */
    @Column(name = "orderNo")
    /*     */ private String orderNo;

    /*     */
    /*     */
    public Long getId()
    /*     */ {
        /*  92 */
        return this.id;
        /*     */
    }

    /*     */
    /*     */
    public void setId(Long id) {
        /*  96 */
        this.id = id;
        /*     */
    }

    /*     */
    /*     */
    public String getTransactionNum() {
        /* 100 */
        return this.transactionNum;
        /*     */
    }

    /*     */
    /*     */
    public void setTransactionNum(String transactionNum) {
        /* 104 */
        this.transactionNum = transactionNum;
        /*     */
    }

    /*     */
    /*     */
    public Long getAccountId() {
        /* 108 */
        return this.accountId;
        /*     */
    }

    /*     */
    /*     */
    public void setAccountId(Long accountId) {
        /* 112 */
        this.accountId = accountId;
        /*     */
    }

    /*     */
    /*     */
    public String getAccountName() {
        /* 116 */
        return this.accountName;
        /*     */
    }

    /*     */
    /*     */
    public void setAccountName(String accountName) {
        /* 120 */
        this.accountName = accountName;
        /*     */
    }

    /*     */
    /*     */
    public Integer getTransactionType() {
        /* 124 */
        return this.transactionType;
        /*     */
    }

    /*     */
    /*     */
    public void setTransactionType(Integer transactionType) {
        /* 128 */
        this.transactionType = transactionType;
        /*     */
    }

    /*     */
    /*     */
    public BigDecimal getTransactionMoney() {
        /* 132 */
        return this.transactionMoney;
        /*     */
    }

    /*     */
    /*     */
    public void setTransactionMoney(BigDecimal transactionMoney) {
        /* 136 */
        this.transactionMoney = transactionMoney;
        /*     */
    }

    /*     */
    /*     */
    public Integer getStatus() {
        /* 140 */
        return this.status;
        /*     */
    }

    /*     */
    /*     */
    public void setStatus(Integer status) {
        /* 144 */
        this.status = status;
        /*     */
    }

    /*     */
    /*     */
    public String getCoinCode() {
        /* 148 */
        return this.coinCode;

    }

    public void setCoinCode(String coinCode) {
        /* 152 */
        this.coinCode = coinCode;
        /*     */
    }

    /*     */
    /*     */
    public BigDecimal getFee() {
        /* 156 */
        return this.fee;
        /*     */
    }

    /*     */
    /*     */
    public void setFee(BigDecimal fee) {
        /* 160 */
        this.fee = fee;
        /*     */
    }

    /*     */
    /*     */
    public String getInAddress() {
        /* 164 */
        return this.inAddress;
        /*     */
    }

    /*     */
    /*     */
    public void setInAddress(String inAddress) {
        /* 168 */
        this.inAddress = inAddress;
        /*     */
    }

    /*     */
    /*     */
    public String getOutAddress() {
        /* 172 */
        return this.outAddress;

    }

    public void setOutAddress(String outAddress) {

        this.outAddress = outAddress;

    }

    public String getConfirmations() {

        return this.confirmations;

    }

    public void setConfirmations(String confirmations) {

        this.confirmations = confirmations;

    }

    public String getBlocktime() {

        return this.blocktime;

    }

    public void setBlocktime(String blocktime) {

        this.blocktime = blocktime;

    }

    public String getTime() {

        return this.time;

    }

    public void setTime(String time) {
        /* 200 */
        this.time = time;
        /*     */
    }

    public String getTimereceived() {

        return this.timereceived;

    }

    public void setTimereceived(String timereceived) {

        this.timereceived = timereceived;

    }

    public String getOurAccountNumber() {

        return this.ourAccountNumber;
    }

    public void setOurAccountNumber(String ourAccountNumber) {

        this.ourAccountNumber = ourAccountNumber;

    }

    public String getOrderNo() {

        return this.orderNo;

    }

    public void setOrderNo(String orderNo) {

        this.orderNo = orderNo;

    }

}
