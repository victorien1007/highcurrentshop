package high.concurrent.shop.entity;

public class StockLog {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stock_log.stock_log_id
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    private String stockLogId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stock_log.product_id
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stock_log.amount
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    private Integer amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stock_log.status
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stock_log.stock_log_id
     *
     * @return the value of stock_log.stock_log_id
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public String getStockLogId() {
        return stockLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stock_log.stock_log_id
     *
     * @param stockLogId the value for stock_log.stock_log_id
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public void setStockLogId(String stockLogId) {
        this.stockLogId = stockLogId == null ? null : stockLogId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stock_log.product_id
     *
     * @return the value of stock_log.product_id
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stock_log.product_id
     *
     * @param productId the value for stock_log.product_id
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stock_log.amount
     *
     * @return the value of stock_log.amount
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stock_log.amount
     *
     * @param amount the value for stock_log.amount
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stock_log.status
     *
     * @return the value of stock_log.status
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stock_log.status
     *
     * @param status the value for stock_log.status
     *
     * @mbg.generated Wed Apr 14 11:26:33 CST 2021
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}