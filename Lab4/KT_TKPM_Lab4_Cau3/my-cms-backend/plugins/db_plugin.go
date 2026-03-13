package plugins

import (
	"fmt"
	"KT_TKPM_Lab4_Cau3/my-cms-backend/models"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type DBPlugin struct {
	DB *gorm.DB
}

func (dbp *DBPlugin) GetName() string {
	return "MariaDB Connection Plugin"
}

func (dbp *DBPlugin) Init() error {
	// Khai báo DSN (Data Source Name)
	// format: user:pass@tcp(127.0.0.1:3306)/dbname?charset=utf8mb4&parseTime=True&loc=Local
	dsn := "root:root@tcp(127.0.0.1:3306)/kt_tkpm_lab4?charset=utf8mb4&parseTime=True&loc=Local"
	
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		return fmt.Errorf("không thể kết nối database: %v", err)
	}

	// Tự động tạo bảng dựa trên struct (AutoMigrate)
	db.AutoMigrate(&models.User{}, &models.Content{})
	
	dbp.DB = db
	fmt.Println("[DB Plugin] Kết nối MariaDB thành công và đã đồng bộ bảng.")
	return nil
}