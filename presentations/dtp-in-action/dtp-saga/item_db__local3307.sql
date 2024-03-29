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
    `saga_id` BIGINT UNSIGNED NOT NULL COMMENT 'reference: saga_table#saga_id',
    `item_id` BIGINT UNSIGNED NOT NULL COMMENT 'reference: item_table#item_id',
    `reserve_stock` BIGINT UNSIGNED NOT NULL COMMENT 'reserved stock for this reservation',
    `reserve_status` VARCHAR(16) COLLATE utf8_bin NOT NULL COMMENT '0: init, 1: confirmed, 2: canceled',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`irs_id`),
    UNIQUE KEY `idx_saga_id` (`saga_id`),
    KEY `idx_item_id` (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='item reserve stock table';


## can be separate to other DB
CREATE TABLE `saga_table`
(
    `saga_id` BIGINT UNSIGNED AUTO_INCREMENT,
    `saga_status` VARCHAR(16) COLLATE utf8_bin NOT NULL COMMENT 'init, succeed, canceled',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`saga_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='saga table';


## can be separate to other DB
CREATE TABLE `saga_log_table`
(
    `saga_log_id` BIGINT UNSIGNED AUTO_INCREMENT,
    `saga_id` BIGINT UNSIGNED NOT NULL COMMENT 'reference: saga_table#saga_id',
    `event` VARCHAR(16) COLLATE utf8_bin NOT NULL COMMENT '',
    `event_status` VARCHAR(16) COLLATE utf8_bin NOT NULL COMMENT 'init, succeed, canceled',
    `create_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row create time, UTC timestamp accuracy up to ms, never should be changed after creation',
    `update_time` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'row last update time, UTC timestamp accuracy up to ms',
    PRIMARY KEY (`saga_log_id`),
    KEY `idx_saga_id` (`saga_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='saga log table';

BEGIN;
INSERT INTO item_table (title, price, stock, create_time, update_time) values ('Fancy Christmas Present', 99, 100, 1645604905025, 1645604905025);
COMMIT;
