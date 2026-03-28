@echo off
docker exec -it lab6cau2_v2 psql -U admin -d mydb -c "SELECT * FROM users;"
pause