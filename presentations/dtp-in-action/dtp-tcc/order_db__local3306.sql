DROP DATABASE IF EXISTS order_db;
CREATE DATABASE order_db;

USE order_db;

CREATE TABLE `order_table`
(
    `order_id` BIGINT UNSIGNED AUTO_INCREMENT,
    `tcc_id` BIGINT UNSIGNED NOT NULL COMMENT 'tcc_id, reference to table:tcc_table#tcc_id',
    `item_id` BIGINT UNSIGNED NOT NULL COMMENT 'id of the purchased item, reference to table:item_table#item_id',
    `item_count` BIGINT UNSIGNED NOT NULL COMMENT 'number of the item',
    `payed_amount` BIGINT UNSIGNED NOT NULL COMMENT 'number of the item',
    `order_status` VARCHAR(16) COLLATE utf8_bin NOT NULL DEFAULT 0 COMMENT '0: init, 1: normal, 2: canceled, 10: finished',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `idx_tcc_id` (`tcc_id`),
    KEY `idx_item_id` (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='order table';

-- BEGIN;
-- INSERT INTO order_table (item_id, tcc_id, item_count, payed_amount, order_status, create_time, update_time) values (1, 1, 1, 99, 'INIT', 1645604905025, 1645604905025);
-- COMMIT;
