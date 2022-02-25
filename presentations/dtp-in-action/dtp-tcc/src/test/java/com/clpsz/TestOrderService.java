package com.clpsz;

import com.clpsz.service.ItemStockService;
import com.clpsz.service.OrderService;
import org.junit.Test;

import java.sql.SQLException;

public class TestOrderService {
    OrderService orderService = new OrderService();

    @Test
    public void testTryCreateOrder() throws SQLException {
        orderService.tryCreateOrder(2L, 1L, 10L, 100L);
    }

    @Test
    public void testConfirmCreateOrder() throws SQLException {
        orderService.confirmCreateOrder(2L);
    }

    @Test
    public void testCancelCreateOrder() throws SQLException {
        orderService.cancelCreateOrder(2L);
    }
}
