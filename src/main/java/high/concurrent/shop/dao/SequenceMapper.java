package high.concurrent.shop.dao;

import high.concurrent.shop.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    int insert(Sequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    int insertSelective(Sequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    Sequence selectByPrimaryKey(String name);

    Sequence getSequenceByName(String name);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    int updateByPrimaryKeySelective(Sequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    int updateByPrimaryKey(Sequence record);
}