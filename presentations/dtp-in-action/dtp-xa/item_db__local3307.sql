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


BEGIN;
INSERT INTO item_table (title, price, stock, create_time, update_time) values ('Fancy Christmas Present', 99, 100, 1645604905025, 1645604905025);
COMMIT;
