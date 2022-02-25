DROP DATABASE IF EXISTS item_db;
CREATE DATABASE item_db;

USE item_db;


CREATE TABLE `item_table`
(
    `item_id` BIGINT UNSIGNED AUTO_INCREMENT,
    `title` VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT 'title of this item',
    `price` BIGINT UNSIGNED NOT NULL COMMENT 'price of the item',
    `stock` BIGINT UNSIGNED NOT NULL COMMENT 'remaining stock of this item',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='item table';

CREATE TABLE `item_reserve_stock_table`
(
    `irs_id` BIGINT UNSIGNED AUTO_INCREMENT,
    `tcc_id` BIGINT UNSIGNED NOT NULL COMMENT 'reference: tcc_table#tcc_id',
    `item_id` BIGINT UNSIGNED NOT NULL COMMENT 'reference: item_table#item_id',
    `reserve_stock` BIGINT UNSIGNED NOT NULL COMMENT 'reserved stock for this reservation',
    `reserve_status` VARCHAR(16) COLLATE utf8_bin NOT NULL COMMENT '0: init, 1: confirmed, 2: canceled',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`irs_id`),
    UNIQUE KEY `idx_tcc_id` (`tcc_id`),
    KEY `idx_item_id` (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='item reserve stock table';


## can be separate to other DB
CREATE TABLE `tcc_table`
(
    `tcc_id` BIGINT UNSIGNED AUTO_INCREMENT,
    `tcc_status` VARCHAR(16) COLLATE utf8_bin NOT NULL COMMENT '0: init, 1: to confirm, 2: to cancel, 10: finished',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`tcc_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='tcc table';

BEGIN;
INSERT INTO item_reserve_stock_table (tcc_id, item_id, reserve_stock, reserve_status, create_time, update_time) values (1, 1, 1, 'INIT', 1645604905025, 1645604905025);
INSERT INTO tcc_table (tcc_status, create_time, update_time) values ('INIT', 1645604905025, 1645604905025);
INSERT INTO item_table (title, price, stock, create_time, update_time) values ('Fancy Christmas Present', 99, 100, 1645604905025, 1645604905025);
COMMIT;
