package com.clpsz;

import com.clpsz.service.ItemStockService;
import org.junit.Test;

import java.sql.SQLException;

public class TestItemStockService {
    @Test
    public void testReserveStock() {
        ItemStockService itemStockService = new ItemStockService();
        try {
//            itemReserveStockService.updateReserveStatus(1L, 1L, 33);
            itemStockService.tryReserveStock(10L, 1L, 11L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConfirmReserveStock() {
        ItemStockService itemStockService = new ItemStockService();
        try {
//            itemReserveStockService.updateReserveStatus(1L, 1L, 33);
            itemStockService.confirmReserveStock(10L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
