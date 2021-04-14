package high.concurrent.shop.controller;

import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.error.EmBusinessError;
import high.concurrent.shop.mq.MqProducer;
import high.concurrent.shop.response.ReturnModel;
import high.concurrent.shop.service.OrderService;
import high.concurrent.shop.service.model.OrderModel;
import high.concurrent.shop.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ***
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //封装下单请求
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public ReturnModel createOrder(@RequestParam(name="productId")Integer productId,
                                   @RequestParam(name="amount")Integer amount,
                                   @RequestParam(name="promoId",required = false)Integer promoId) throws BusinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }

        //获取用户的登陆信息
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");

        //OrderModel orderModel = orderService.createOrder(userModel.getId(),productId,promoId,amount);
        if(!mqProducer.transactionAsyncReduceStock(userModel.getId(),productId,promoId,amount)){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"下单失败");
        }
        return ReturnModel.create(null);
    }
}
