package com.clpsz;


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
