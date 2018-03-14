/*    */
package hry.coin.coin.service.impl;
/*    */
/*    */

import hry.coin.coin.dao.AppCoinTransactionDao;
import hry.coin.coin.model.AppCoinTransaction;
import hry.coin.coin.service.AppCoinTransactionService;
import hry.core.mvc.dao.base.BaseDao;
import hry.core.mvc.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service("appCoinTransactionService")
public class AppCoinTransactionServiceImpl
        extends BaseServiceImpl<AppCoinTransaction, Long>
        implements AppCoinTransactionService {

    @Resource(name = "appCoinTransactionDao")
    @Override
    public void setDao(BaseDao<AppCoinTransaction, Long> dao) {
        this.dao = dao;
    }

    @Override
    public List<AppCoinTransaction> consumeTx() {
        return ((AppCoinTransactionDao) this.dao).consumeTx();
    }

    @Override
    public int existNumber(String hash) {
        return ((AppCoinTransactionDao) this.dao).existNumber(hash);
    }

    @Override
    public BigInteger getLastestBlock() {
        return ((AppCoinTransactionDao) this.dao).getLastestBlock();
    }

    @Override
    public BigInteger getLastestBlockByCoinCode(String coinCode) {
        return ((AppCoinTransactionDao) this.dao).getLastestBlockByCoinCode(coinCode);
    }

    @Override
    public List<AppCoinTransaction> listYesterdayRechargeRecord(String coinType) {
        return ((AppCoinTransactionDao) this.dao).listYesterdayRechargeRecord(coinType);
    }

}
