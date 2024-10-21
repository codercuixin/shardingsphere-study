# 首先创建好 master salve replication
## 启动 docker compose
docker-compose up -d
## master 相关操作
ALTER USER 'replication_user'@'%' IDENTIFIED BY 'replication_password';
GRANT REPLICATION SLAVE ON *.* TO 'replication_user'@'%';
FLUSH PRIVILEGES;

SHOW BINARY LOG STATUS;
## slave 相关操作
```
CHANGE REPLICATION SOURCE TO
SOURCE_HOST='mysql-master',
SOURCE_PORT=3306,
SOURCE_USER='replication_user',
SOURCE_PASSWORD='replication_password',
SOURCE_LOG_FILE='mysql-bin.000003',
SOURCE_LOG_POS=873,
GET_SOURCE_PUBLIC_KEY=1;
```
更新上面的 SOURCE_LOG_FILE 和 SOURCE_LOG_POS
```bash
start REPLICA;
show REPLICA STATUS\G;
```
https://dev.to/siddhantkcode/how-to-set-up-a-mysql-master-slave-replication-in-docker-4n0a
