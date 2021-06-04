package high.concurrent.shop.service;

import high.concurrent.shop.entity.StockLog;

import java.util.UUID;

public interface StockLogService {

    public String initStockLog(Integer productId,Integer amount);
}
