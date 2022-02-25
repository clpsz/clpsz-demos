## start MySQL
```shell
docker run --rm -d --name order_db -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql:5.7
docker run --rm -d --name item_db -e MYSQL_ROOT_PASSWORD=root -p 3307:3306 mysql:5.7
```

```shell
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3306 -uroot < order_db__local3306.sql
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3307 -uroot < item_db__local3307.sql
```

## XA - happy path 😀


```shell
# shell1
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3306 -uroot

# in MySQL console

## phase 1
USE order_db;
XA START 'xaexample','order',1;
INSERT INTO order_table 
(item_id, item_count, payed_amount, order_status, create_time,   update_time) values 
(1,       2,          198,           'NORMAL',     1645604905025, 1645604905025);
XA END 'xaexample','order',1;
XA PREPARE 'xaexample','order',1;

## check 
XA RECOVER;

## phase 2, wait shell2
XA COMMIT 'xaexample','order',1;
```

```shell
# shell2
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3307 -uroot

# in MySQL console
USE item_db;
XA START 'xaexample','item',1;
UPDATE item_table SET stock = stock-2, update_time=1645764619000 WHERE item_id=1;
XA END 'xaexample','item',1;
XA PREPARE 'xaexample','item',1;

## check 
XA RECOVER;

# phase2, wait shell1
XA COMMIT 'xaexample','item',1;
```


## XA - block

Stop before commit, create shell3

```shell
# shell3
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3307 -uroot

# in MySQL console
USE item_db;
UPDATE item_table SET title = 'Fancy New Year Present', update_time=1645764619000 WHERE item_id=1;

```

```shell
# shell2
XA ROLLBACK 'xaexample','item',1;
```
