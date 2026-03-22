package database

import (
	"fmt"
	"os"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var DB *gorm.DB

func InitDB() {
	dsn := fmt.Sprintf("%s:%s@tcp(%s:3306)/%s?parseTime=true&loc=Local&charset=utf8mb4", 
	os.Getenv("MYSQL_USER"),
	os.Getenv("MYSQL_PASSWORD"),
	os.Getenv("MYSQL_HOST"),
	os.Getenv("MYSQL_DATABASE"))

	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
	
	if (err != nil) {
		panic("Bị lỗi khi kết nối tới database")
	}

	DB = db
}