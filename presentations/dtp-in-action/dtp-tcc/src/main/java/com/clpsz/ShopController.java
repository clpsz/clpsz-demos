package com.clpsz;


import com.clpsz.domain.Item;
import com.clpsz.enums.TccStatus;
import com.clpsz.service.ItemService;
import com.clpsz.service.ItemStockService;
import com.clpsz.service.OrderService;
import com.clpsz.service.TccService;

import java.sql.SQLException;

/**
 * @author clpsz
 */
public class ShopController {

    TccService tccService = new TccService();

    OrderService orderService = new OrderService();

    ItemService itemService = new ItemService();

    ItemStockService itemStockService = new ItemStockService();

    public void placeOrder(Long itemId, Long itemCount) throws SQLException {
        Long tccId = tccService.insertTcc();

        Item item = itemService.getItemById(itemId);
        long payedAmount = itemCount * item.getPrice();

        boolean tryReserveStockSuccess = itemStockService.tryReserveStock(tccId, itemId, itemCount);
        boolean tryCreateOrderSuccess = orderService.tryCreateOrder(tccId, itemId, itemCount, payedAmount);

        Integer updated;
        if (tryReserveStockSuccess && tryCreateOrderSuccess) {
            updated = tccService.updateTccFromInit(tccId, TccStatus.TO_CONFIRM.getDesc());
            if (updated > 0) {
                boolean itemConfirmSuccess = itemStockService.confirmReserveStock(tccId);
                boolean orderConfirmSuccess = orderService.confirmCreateOrder(tccId);
                if (itemConfirmSuccess && orderConfirmSuccess) {
                    tccService.updateTccToFinished(tccId);
                }
            }
        } else {
            updated = tccService.updateTccFromInit(tccId, TccStatus.TO_CANCEL.getDesc());
            if (updated > 0) {
                boolean itemCancelSuccess = itemStockService.cancelReserveStock(tccId, itemId);
                boolean orderCancelSuccess = orderService.cancelCreateOrder(tccId);
                if (itemCancelSuccess && orderCancelSuccess) {
                    tccService.updateTccToFinished(tccId);
                }
            }
        }
    }
}
