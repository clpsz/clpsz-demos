# example

DTP by TCC

## start MySQL
```shell
docker run --rm -d --name order_db -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql:5.7
docker run --rm -d --name item_db -e MYSQL_ROOT_PASSWORD=root -p 3307:3306 mysql:5.7
```

```shell
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3306 -uroot < order_db__local3306.sql
export MYSQL_PWD=root; mysql -h127.0.0.1 -P3307 -uroot < item_db__local3307.sql
```

## run test case

