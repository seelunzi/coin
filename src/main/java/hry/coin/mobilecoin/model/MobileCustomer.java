
package hry.coin.mobilecoin.model;


import hry.core.mvc.model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "mobile_customer")
@Data
public class MobileCustomer
        extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "coinAddr")
    private String coinAddr;

    @Column(name = "passWord")
    private String passWord;

    @Column(name = "mobileId")
    private String mobileId;

    @Column(name = "coinType")
    private String coinType;

    @Column(name = "accountPassWord")
    private String accountPassWord;

    @Column(name = "hotCurrency")
    private BigDecimal hotCurrency;

    @Column(name = "coldCurrency")
    private BigDecimal coldCurrency;


}
