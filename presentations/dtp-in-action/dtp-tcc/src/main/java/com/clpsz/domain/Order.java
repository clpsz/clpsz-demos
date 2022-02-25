package com.clpsz.domain;


/**
 * @author clpsz
 */
public class Order {
    private Long orderId;
    private Long tccId;
    private Long itemId;
    private Long itemCount;
    private Long payedAmount;
    private String orderStatus;
    private Long createTime;
    private Long updateTime;

    public Order(Long orderId, Long tccId, Long itemId, Long itemCount, Long payedAmount, String orderStatus, Long createTime, Long updateTime) {
        this.orderId = orderId;
        this.tccId = tccId;
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.payedAmount = payedAmount;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTccId() {
        return tccId;
    }

    public void setTccId(Long tccId) {
        this.tccId = tccId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

    public Long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Long payedAmount) {
        this.payedAmount = payedAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
