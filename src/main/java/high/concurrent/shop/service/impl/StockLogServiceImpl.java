package high.concurrent.shop.service.impl;

import high.concurrent.shop.dao.StockLogMapper;
import high.concurrent.shop.entity.StockLog;
import high.concurrent.shop.service.StockLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
public class StockLogServiceImpl implements StockLogService {

    @Autowired
    StockLogMapper stockLogMapper;
    @Override
    @Transactional
    public String initStockLog(Integer productId,Integer amount){
        StockLog stockLog= new StockLog();
        stockLog.setProductId(productId);
        stockLog.setAmount(amount);
        stockLog.setStockLogId(UUID.randomUUID().toString().replace("-",""));
        stockLog.setStatus(1);
        stockLogMapper.insertSelective(stockLog);

        return stockLog.getStockLogId();
    }
}
