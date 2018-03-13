
package hry.coin.coin.model;

import hry.core.mvc.model.BaseModel;
import org.web3j.utils.Convert;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "app_coin_transaction")
public class AppCoinTransaction
        extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "hash_")
    private String hash_;
    @Column(name = "nonce")
    private String nonce;

    @Column(name = "blockHash")
    private String blockHash;

    @Column(name = "blockNumber")
    private String blockNumber;
    @Column(name = "transactionIndex")
    /*     */ private String transactionIndex;
    /*     */
    @Column(name = "from_")
    /*     */ private String from_;
    /*     */
    @Column(name = "to_")
    /*     */ private String to_;
    /*     */
    @Column(name = "amount")
    /*     */ private BigDecimal amount;
    /*     */
    @Column(name = "gas")
    /*     */ private BigDecimal gas;
    /*     */
    @Column(name = "gasPrice")
    /*     */ private BigDecimal gasPrice;
    /*     */
    @Column(name = "input")
    /*     */ private String input;
    /*     */
    @Column(name = "coinType")
    /*     */ private String coinType;
    /*     */
    @Column(name = "isconsume")
    /*     */ private int isconsume;

    /*     */
    /*     */
    public Long getId()
    /*     */ {
        /*  84 */
        return this.id;
        /*     */
    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getHash_() {

        return this.hash_;

    }

    public void setHash_(String hash_) {

        this.hash_ = hash_;

    }

    public String getNonce() {
        return this.nonce;

    }

    public void setNonce(String nonce) {

        this.nonce = nonce;

    }

    public String getBlockHash() {

        return this.blockHash;

    }

    public void setBlockHash(String blockHash) {

        this.blockHash = blockHash;

    }

    public String getBlockNumber()
        /*     */ {
        /* 172 */
        return this.blockNumber;
        /*     */
    }


    public void setBlockNumber(String blockNumber) {

        this.blockNumber = blockNumber;

    }

    public String getTransactionIndex()
        /*     */ {
        /* 194 */
        return this.transactionIndex;
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
    public void setTransactionIndex(String transactionIndex)
    /*     */ {
        /* 205 */
        this.transactionIndex = transactionIndex;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public BigDecimal getAmount()
    /*     */ {
        /* 211 */
        return this.amount;
        /*     */
    }

    /*     */
    /*     */
    public void setAmount(BigDecimal amount) {
        /* 215 */
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
    /*     */
    public String getInput()
    /*     */ {
        /* 227 */
        return this.input;
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
    public void setInput(String input)
    /*     */ {
        /* 238 */
        this.input = input;
        /*     */
    }

    /*     */
    /*     */
    public String getCoinType() {
        /* 242 */
        return this.coinType;
        /*     */
    }

    /*     */
    /*     */
    public void setCoinType(String coinType) {
        /* 246 */
        this.coinType = coinType;
        /*     */
    }

    /*     */
    /*     */
    public BigDecimal getGas()
    /*     */ {
        /* 251 */
        return this.gas;
        /*     */
    }

    /*     */
    /*     */
    public BigDecimal getGasPrice() {
        /* 255 */
        return this.gasPrice;
        /*     */
    }

    /*     */
    /*     */
    public void setGas(BigDecimal gas)
    /*     */ {
        /* 260 */
        this.gas = Convert.fromWei(gas.toString(), Convert.Unit.ETHER);
        /*     */
    }

    /*     */
    /*     */
    public void setGasPrice(BigDecimal gasPrice) {
        /* 264 */
        this.gasPrice = Convert.fromWei(gasPrice.toString(), Convert.Unit.ETHER);
        /*     */
    }

    /*     */
    /*     */
    public int getIsconsume() {
        /* 268 */
        return this.isconsume;
        /*     */
    }

    /*     */
    /*     */
    public void setIsconsume(int isconsume) {
        /* 272 */
        this.isconsume = isconsume;
        /*     */
    }

    /*     */
    /*     */
    public String getFrom_() {
        /* 276 */
        return this.from_;
        /*     */
    }

    /*     */
    /*     */
    public void setFrom_(String from_) {
        /* 280 */
        this.from_ = from_;
        /*     */
    }

    /*     */
    /*     */
    public String getTo_() {
        /* 284 */
        return this.to_;
        /*     */
    }

    /*     */
    /*     */
    public void setTo_(String to_) {
        /* 288 */
        this.to_ = to_;
        /*     */
    }
    /*     */
}


/* Location:              E:\coin.war!\WEB-INF\classes\hry\coin\coin\model\AppCoinTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */