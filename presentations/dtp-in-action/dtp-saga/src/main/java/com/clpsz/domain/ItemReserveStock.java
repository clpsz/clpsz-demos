package com.clpsz.domain;


/**
 * @author clpsz
 */
public class ItemReserveStock {
    private Long irsId;
    private Long sagaId;
    private Long itemId;
    private Long reserveStock;
    private String reserveStatus;
    private Long createTime;
    private Long updateTime;

    public ItemReserveStock(Long irsId, Long tccId, Long itemId, Long reserveStock, String reserveStatus, Long createTime, Long updateTime) {
        this.irsId = irsId;
        this.sagaId = tccId;
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

    public Long getSagaId() {
        return sagaId;
    }

    public void setSagaId(Long tccId) {
        this.sagaId = tccId;
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
                ", tccId=" + sagaId +
                ", itemId=" + itemId +
                ", reserveStock=" + reserveStock +
                ", reserveStatus=" + reserveStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
