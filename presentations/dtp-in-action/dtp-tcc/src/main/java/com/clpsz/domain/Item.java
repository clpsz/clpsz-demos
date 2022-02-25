package com.clpsz.domain;


/**
 * @author clpsz
 */
public class Item {
    private Long itemId;
    private String title;
    private Long price;
    private Long stock;
    private Long createTime;
    private Long updateTime;

    public Item(Long itemId, String title, Long price, Long stock, Long createTime, Long updateTime) {
        this.itemId = itemId;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
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
