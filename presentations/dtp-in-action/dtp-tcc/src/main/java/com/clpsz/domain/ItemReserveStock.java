package com.clpsz.domain;


/**
 * @author clpsz
 */
public class ItemReserveStock {
    private Long irsId;
    private Long tccId;
    private Long itemId;
    private Long reserveStock;
    private String reserveStatus;
    private Long createTime;
    private Long updateTime;

    public ItemReserveStock(Long irsId, Long tccId, Long itemId, Long reserveStock, String reserveStatus, Long createTime, Long updateTime) {
        this.irsId = irsId;
        this.tccId = tccId;
        this.itemId = itemId;
        this.reserveStock = reserveStock;
        this.reserveStatus = reserveStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getIrsId() {
        return irsId;
    }

    public void setIrsId(Long irsId) {
        this.irsId = irsId;
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

    public Long getReserveStock() {
        return reserveStock;
    }

    public void setReserveStock(Long reserveStock) {
        this.reserveStock = reserveStock;
    }

    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
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

    @Override
    public String toString() {
        return "ItemReserveStock{" +
                "irsId=" + irsId +
                ", tccId=" + tccId +
                ", itemId=" + itemId +
                ", reserveStock=" + reserveStock +
                ", reserveStatus=" + reserveStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
