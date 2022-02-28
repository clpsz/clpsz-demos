package com.clpsz;

import org.junit.Test;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class TestShopController {
    ShopController shopService = new ShopController();


    @Test
    public void testPlaceOrderSucceed() throws InterruptedException {
        EventCenter.INSTANCE.startEventLoop();

        try {
            shopService.placeOrder(1L, 100L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(3600);
    }

    @Test
    public void testPlaceOrderFail() {
        try {
            shopService.placeOrder(1L, 200L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
