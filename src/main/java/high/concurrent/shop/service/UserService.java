package high.concurrent.shop.service;

import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.service.model.UserModel;

/**
 * *
 */
public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer id);

    UserModel getUserByIdInCache(Integer id);
    void register(UserModel userModel) throws BusinessException;

    /*
    telphone:用户注册手机
    password:用户加密后的密码
     */
    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}
