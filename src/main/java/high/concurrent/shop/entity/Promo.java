package high.concurrent.shop.entity;

import java.util.Date;

public class Promo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column promo.id
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column promo.promo_name
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    private String promoName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column promo.start_date
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    private Date startDate;

    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column promo.product_id
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column promo.promo_product_price
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    private Double promoProductPrice;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column promo.id
     *
     * @return the value of promo.id
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column promo.id
     *
     * @param id the value for promo.id
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column promo.promo_name
     *
     * @return the value of promo.promo_name
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public String getPromoName() {
        return promoName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column promo.promo_name
     *
     * @param promoName the value for promo.promo_name
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public void setPromoName(String promoName) {
        this.promoName = promoName == null ? null : promoName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column promo.start_date
     *
     * @return the value of promo.start_date
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column promo.start_date
     *
     * @param startDate the value for promo.start_date
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column promo.product_id
     *
     * @return the value of promo.product_id
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column promo.product_id
     *
     * @param productId the value for promo.product_id
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column promo.promo_product_price
     *
     * @return the value of promo.promo_product_price
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */
    public Double getPromoProductPrice() {
        return promoProductPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column promo.promo_product_price
     *
     * @param promoProductPrice the value for promo.promo_product_price
     *
     * @mbg.generated Mon Mar 21 20:34:45 CST 2021
     */

    public void setPromoProductPrice(Double promoProductPrice) {
        this.promoProductPrice = promoProductPrice;
    }
}