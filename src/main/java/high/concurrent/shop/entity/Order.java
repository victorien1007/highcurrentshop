package high.concurrent.shop.entity;

public class Order {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.user_id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.product_id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.product_price
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    private Double productPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.amount
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    private Integer amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.order_price
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    private Double orderPrice;

    private Integer promoId;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.id
     *
     * @return the value of order_info.id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.id
     *
     * @param id the value for order_info.id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.user_id
     *
     * @return the value of order_info.user_id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.user_id
     *
     * @param userId the value for order_info.user_id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.product_id
     *
     * @return the value of order_info.product_id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.product_id
     *
     * @param productId the value for order_info.product_id
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.product_price
     *
     * @return the value of order_info.product_price
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public Double getProductPrice() {
        return productPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.product_price
     *
     * @param productPrice the value for order_info.product_price
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.amount
     *
     * @return the value of order_info.amount
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.amount
     *
     * @param amount the value for order_info.amount
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_price
     *
     * @return the value of order_info.order_price
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public Double getOrderPrice() {
        return orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_price
     *
     * @param orderPrice the value for order_info.order_price
     *
     * @mbg.generated Sun Mar 21 20:34:45 CST 2021
     */
    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
}