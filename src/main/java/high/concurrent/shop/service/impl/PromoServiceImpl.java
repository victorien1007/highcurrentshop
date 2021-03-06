package high.concurrent.shop.service.impl;

import high.concurrent.shop.dao.PromoMapper;
import high.concurrent.shop.entity.Promo;
import high.concurrent.shop.service.ProductService;
import high.concurrent.shop.service.PromoService;
import high.concurrent.shop.service.model.ProductModel;
import high.concurrent.shop.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * ***
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoMapper promoMapper;
    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public PromoModel getPromoByProductId(Integer productId) {
        //获取对应商品的秒杀活动信息
        Promo promo = promoMapper.selectByProductId(productId);

        //dataobject->model
        PromoModel promoModel = convertFromDataObject(promo);
        if(promoModel == null){
            return null;
        }

        //判断当前时间是否秒杀活动即将开始或正在进行
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    @Override
    public void publishPromo(Integer promoId) {
        //活动id获取获得
        Promo promo=promoMapper.selectByPrimaryKey(promoId);

        if(promo.getProductId()==null||promo.getProductId().intValue()==0){
            return;
        }
        ProductModel productModel=productService.getProductById(promo.getProductId());

        //将库存同步到redis内
        redisTemplate.opsForValue().set("promo_product_stock_"+productModel.getId(),productModel.getStock());



    }

    private PromoModel convertFromDataObject(Promo promo){
        if(promo == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promo,promoModel);
        promoModel.setPromoProductPrice(new BigDecimal(promo.getPromoProductPrice()));
        promoModel.setStartDate(new DateTime(promo.getStartDate()));
        promoModel.setEndDate(new DateTime(promo.getEndDate()));
        return promoModel;
    }
}
