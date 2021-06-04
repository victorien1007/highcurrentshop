package high.concurrent.shop.service.impl;

import high.concurrent.shop.dao.OrderMapper;
import high.concurrent.shop.dao.SequenceMapper;
import high.concurrent.shop.dao.StockLogMapper;
import high.concurrent.shop.entity.Order;
import high.concurrent.shop.entity.Sequence;
import high.concurrent.shop.entity.StockLog;
import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.error.EmBusinessError;
import high.concurrent.shop.mq.MqProducer;
import high.concurrent.shop.service.ProductService;
import high.concurrent.shop.service.OrderService;
import high.concurrent.shop.service.UserService;
import high.concurrent.shop.service.model.ProductModel;
import high.concurrent.shop.service.model.OrderModel;
import high.concurrent.shop.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ***
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Autowired
    private MqProducer mqProducer;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer productId, Integer promoId, Integer amount,String stockLogId) throws BusinessException {
        //1.校验下单状态,下单的商品是否存在，用户是否合法，购买数量是否正确
        //ProductModel productModel = productService.getProductById(productId);
        ProductModel productModel = productService.getProductByIdInCache(productId);
        if(productModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }

        //UserModel userModel = userService.getUserById(userId);
        UserModel userModel = userService.getUserByIdInCache(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }

        //校验活动信息
        if(promoId != null){
            //（1）校验对应活动是否存在这个适用商品
            if(promoId.intValue() != productModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
                //（2）校验活动是否正在进行中
            }else if(productModel.getPromoModel().getStatus().intValue() != 2) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息还未开始");
            }
        }

        //2.落单减库存
        boolean result = productService.decreaseStock(productId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setProductId(productId);
        orderModel.setAmount(amount);
        if(promoId != null){
            orderModel.setProductPrice(productModel.getPromoModel().getPromoProductPrice());
        }else{
            orderModel.setProductPrice(productModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getProductPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号,订单号
        orderModel.setId(generateOrderNo());
        Order order = convertFromOrderModel(orderModel);
        orderMapper.insertSelective(order);

        //加上商品的销量
        productService.increaseSales(productId,amount);
        //设置流水状态为成功
        StockLog stockLog=stockLogMapper.selectByPrimaryKey(stockLogId);
        if(stockLog==null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }
/*        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {

                //异步更新库存
                boolean mqResult = productService.asyncDecreaseStock(productId,amount);
                if(!mqResult){
                    productService.increaseStock(productId,amount);
                    throw new BusinessException(EmBusinessError.MQ_SEND_FAIL);
                }

            }
        });*/




        //4.返回前端
        return orderModel;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        //中间6位为自增序列
        //获取当前sequence
        int sequence = 0;
        Sequence sequenceDO =  sequenceMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0; i < 6-sequenceStr.length();i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);


        //最后2位为分库分表位,暂时写死
        stringBuilder.append("00");

        return stringBuilder.toString();
    }
    private Order convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderModel, order);
        order.setProductPrice(orderModel.getProductPrice().doubleValue());
        order.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return order;
    }
}
