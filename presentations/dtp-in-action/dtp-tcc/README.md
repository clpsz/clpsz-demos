## example

mybatis and springboot

## steps

1. start mysql instance at 127.0.0.1:3306, username=root, password=root
2. start mysql instance at 127.0.0.1:3307, username=root, password=root
3. connect 3306 database, run order_db__local3306.sql script
4. connect 3307 database, run item_db__local3307.sql script
5. start application
6. execute curl "localhost:8081/order?id=1"
7. execute curl "localhost:8081/item?id=1"
8. execute curl "localhost:8081/itemReserveStock?id=1"
9. execute curl "localhost:8081/tcc?id=1"
