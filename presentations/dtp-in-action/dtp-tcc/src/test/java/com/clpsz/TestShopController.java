package com.clpsz;

import org.junit.Test;

import java.sql.SQLException;

public class TestShopController {
    ShopController shopService = new ShopController();


    @Test
    public void testPlaceOrderSucceed() {
        try {
            shopService.placeOrder(1L, 100L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
