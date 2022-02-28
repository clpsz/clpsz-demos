package com.clpsz;


import com.clpsz.domain.Item;
import com.clpsz.enums.SagaStatus;
import com.clpsz.service.ItemService;
import com.clpsz.service.ItemStockService;
import com.clpsz.service.OrderService;
import com.clpsz.service.SagaService;

import java.sql.SQLException;

/**
 * @author clpsz
 */
public class ShopController {

    SagaService sagaService = new SagaService();

    OrderService orderService = new OrderService();

    ItemService itemService = new ItemService();

    ItemStockService itemStockService = new ItemStockService();

    public void placeOrder(Long itemId, Long itemCount) throws SQLException {
        Long sagaId = sagaService.insertSaga();

        EventCenter.INSTANCE.startEventLoop();
        EventCenter.INSTANCE.putEvent(new CreateOrderEvent(
                sagaId,
                CreateOrderEvent.INIT,
                itemId,
                itemCount,
                true)
        );
    }
}
