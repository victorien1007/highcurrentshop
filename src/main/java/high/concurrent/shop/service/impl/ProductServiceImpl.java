package high.concurrent.shop.service.impl;

import high.concurrent.shop.dao.ProductMapper;
import high.concurrent.shop.dao.ProductStockMapper;
import high.concurrent.shop.entity.Product;
import high.concurrent.shop.entity.ProductStock;
import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.error.EmBusinessError;
import high.concurrent.shop.service.ProductService;
import high.concurrent.shop.service.PromoService;
import high.concurrent.shop.service.model.ProductModel;
import high.concurrent.shop.service.model.PromoModel;
import high.concurrent.shop.validator.ValidationResult;
import high.concurrent.shop.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ***
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PromoService promoService;

    @Autowired
    private ProductStockMapper productStockMapper;

    private Product convertProductDOFromProductModel(ProductModel productModel){
        if(productModel == null){
            return null;
        }
        Product product = new Product();
        BeanUtils.copyProperties(productModel, product);
        product.setPrice(productModel.getPrice().doubleValue());
        return product;
    }
    private ProductStock convertProductStockDOFromProductModel(ProductModel productModel){
        if(productModel == null){
            return null;
        }
        ProductStock productStock = new ProductStock();
        productStock.setProductId(productModel.getId());
        productStock.setStock(productModel.getStock());
        return productStock;
    }

    @Override
    @Transactional
    public ProductModel createProduct(ProductModel productModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(productModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //转化productmodel->dataobject
        Product product = this.convertProductDOFromProductModel(productModel);

        //写入数据库
        productMapper.insertSelective(product);
        productModel.setId(product.getId());

        ProductStock productStock = this.convertProductStockDOFromProductModel(productModel);

        productStockMapper.insertSelective(productStock);

        //返回创建完成的对象
        return this.getProductById(productModel.getId());
    }

    @Override
    public List<ProductModel> listProduct() {
        List<Product> productList = productMapper.listProduct();
        List<ProductModel> productModelList =  productList.stream().map(product -> {
            ProductStock productStock = productStockMapper.selectByProductId(product.getId());
            ProductModel productModel = this.convertModelFromDataObject(product, productStock);
            return productModel;
        }).collect(Collectors.toList());
        return productModelList;
    }

    @Override
    public ProductModel getProductById(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        if(product == null){
            return null;
        }
        //操作获得库存数量
        ProductStock productStock = productStockMapper.selectByProductId(product.getId());


        //将dataobject->model
        ProductModel productModel = convertModelFromDataObject(product, productStock);

        //获取活动商品信息
        PromoModel promoModel = promoService.getPromoByProductId(productModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() != 3){
            productModel.setPromoModel(promoModel);
        }
        return productModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer productId, Integer amount) throws BusinessException {
        int affectedRow =  productStockMapper.decreaseStock(productId,amount);
        if(affectedRow > 0){
            //更新库存成功
            return true;
        }else{
            //更新库存失败
            return false;
        }

    }

    @Override
    @Transactional
    public void increaseSales(Integer productId, Integer amount) throws BusinessException {
        productMapper.increaseSales(productId,amount);
    }

    private ProductModel convertModelFromDataObject(Product product, ProductStock productStock){
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(product,productModel);
        productModel.setPrice(new BigDecimal(product.getPrice()));
        productModel.setStock(productStock.getStock());

        return productModel;
    }

}
