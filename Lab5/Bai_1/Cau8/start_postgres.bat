@echo off
docker exec -it lab5_bai1_cau8_1 psql -U admin -d mydb -c "SELECT * FROM users;"
pause