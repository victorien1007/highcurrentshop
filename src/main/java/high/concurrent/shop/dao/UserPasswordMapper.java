package high.concurrent.shop.dao;

import high.concurrent.shop.entity.UserPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Mar 21 20:34:17 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Mar 21 20:34:17 CST 2021
     */
    int insert(UserPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Mar 21 20:34:17 CST 2021
     */
    int insertSelective(UserPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Mar 21 20:34:17 CST 2021
     */
    UserPassword selectByPrimaryKey(Integer id);

    UserPassword selectByUserId(Integer userId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Mar 21 20:34:17 CST 2021
     */
    int updateByPrimaryKeySelective(UserPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Mar 21 20:34:17 CST 2021
     */
    int updateByPrimaryKey(UserPassword record);
}