package com.clpsz;

import org.junit.Test;

import java.sql.SQLException;

public class TestShopController {
    ShopController shopService = new ShopController();


    @Test
    public void testPlaceOrder() {
        try {
            shopService.placeOrder(1L, 100L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
