package high.concurrent.shop.service;

import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.service.model.ProductModel;

import java.util.List;

/**
 * ***
 */
public interface ProductService {

    //创建商品
    ProductModel createProduct(ProductModel productModel) throws BusinessException;

    //商品列表浏览
    List<ProductModel> listProduct();

    //商品详情浏览
    ProductModel getProductById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer productId,Integer amount)throws BusinessException;
    //库存增加
    boolean increaseStock(Integer productId,Integer amount)throws BusinessException;

    //商品销量增加
    void increaseSales(Integer productId,Integer amount)throws BusinessException;


    //item及promo model缓存模型
    ProductModel getProductByIdInCache(Integer id);

    //异步更新库存
    boolean asyncDecreaseStock(Integer productId,Integer amount);
}
