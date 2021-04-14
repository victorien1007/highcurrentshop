package high.concurrent.shop.controller;

import high.concurrent.shop.controller.viewobject.ProductVO;
import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.response.ReturnModel;
import high.concurrent.shop.service.CacheService;
import high.concurrent.shop.service.ProductService;
import high.concurrent.shop.service.PromoService;
import high.concurrent.shop.service.model.ProductModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * ***
 */
@Controller("/product")
@RequestMapping("/product")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ProductController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PromoService promoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ProductService productService;

    //创建商品的controller
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public ReturnModel createProduct(@RequestParam(name = "title")String title,
                                  @RequestParam(name = "description")String description,
                                  @RequestParam(name = "price")BigDecimal price,
                                  @RequestParam(name = "stock")Integer stock,
                                  @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        //封装service请求用来创建商品
        ProductModel productModel = new ProductModel();
        productModel.setTitle(title);
        productModel.setDescription(description);
        productModel.setPrice(price);
        productModel.setStock(stock);
        productModel.setImgUrl(imgUrl);

        ProductModel productModelForReturn = productService.createProduct(productModel);
        ProductVO productVO = convertVOFromModel(productModelForReturn);

        return ReturnModel.create(productVO);
    }

    //商品详情页浏览
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public ReturnModel getProduct(@RequestParam(name = "id")Integer id){
        ProductModel productModel = null;

        //先取本地缓存
        productModel = (ProductModel) cacheService.getFromCommonCache("product_"+id);

        if(productModel == null){
            //根据商品的id到redis内获取
            productModel = (ProductModel) redisTemplate.opsForValue().get("product_"+id);

            //若redis内不存在对应的itemModel,则访问下游service
            if(productModel == null){
                productModel = productService.getProductById(id);
                //设置itemModel到redis内
                redisTemplate.opsForValue().set("product_"+id,productModel);
                redisTemplate.expire("product_"+id,10, TimeUnit.MINUTES);
            }
            //填充本地缓存
            cacheService.setCommonCache("product_"+id,productModel);
        }


        ProductVO productVO = convertVOFromModel(productModel);

        return ReturnModel.create(productVO);

    }

    //商品列表页面浏览
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public ReturnModel listProduct(){
        List<ProductModel> productModelList = productService.listProduct();

        //使用stream apiJ将list内的productModel转化为ITEMVO;
        List<ProductVO> productVOList =  productModelList.stream().map(productModel -> {
            ProductVO productVO = this.convertVOFromModel(productModel);
            return productVO;
        }).collect(Collectors.toList());
        return ReturnModel.create(productVOList);
    }



    private ProductVO convertVOFromModel(ProductModel productModel){
        if(productModel == null){
            return null;
        }
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(productModel,productVO);
        if(productModel.getPromoModel() != null){
            //有正在进行或即将进行的秒杀活动
            productVO.setPromoStatus(productModel.getPromoModel().getStatus());
            productVO.setPromoId(productModel.getPromoModel().getId());
            productVO.setStartDate(productModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            productVO.setPromoPrice(productModel.getPromoModel().getPromoProductPrice());
        }else{
            productVO.setPromoStatus(0);
        }
        return productVO;
    }

    @RequestMapping(value = "/publishpromo",method = {RequestMethod.GET})
    @ResponseBody
    public ReturnModel publishPromo(@RequestParam(name = "id")Integer id){
        promoService.publishPromo(id);
        return ReturnModel.create(null);
    }
}
