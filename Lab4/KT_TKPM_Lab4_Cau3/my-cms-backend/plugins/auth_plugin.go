package plugins

import (
	"errors"
	"KT_TKPM_Lab4_Cau3/my-cms-backend/models"
	"gorm.io/gorm"
)

type AuthPlugin struct {
	DB *gorm.DB // Plugin này cần dùng chung kết nối DB
}

func (ap *AuthPlugin) GetName() string {
	return "Auth & Security Manager"
}

func (ap *AuthPlugin) Init() error {
	// Kiểm tra xem DB đã được cắm vào chưa
	if ap.DB == nil {
		return errors.New("AuthPlugin yêu cầu kết nối Database trước khi chạy")
	}
	return nil
}

// Hàm xác thực User từ database
func (ap *AuthPlugin) Authenticate(username string, password string) (*models.User, error) {
	var user models.User
	// Tìm user trong bảng kt_tkpm_lab4.users
	result := ap.DB.Where("username = ? AND password = ?", username, password).First(&user)
	if result.Error != nil {
		return nil, errors.New("sai tài khoản hoặc mật khẩu")
	}
	return &user, nil
}