package high.concurrent.shop.service;

import high.concurrent.shop.service.model.PromoModel;

/**
 * ***
 */
public interface PromoService {
    //根据productid获取即将进行的或正在进行的秒杀活动
    PromoModel getPromoByProductId(Integer productId);
}
