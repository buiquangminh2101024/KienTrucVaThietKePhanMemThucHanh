@echo off
docker exec -it cau2-mysql-1 mysql -u user -p -e "USE mydb; SHOW TABLES;"