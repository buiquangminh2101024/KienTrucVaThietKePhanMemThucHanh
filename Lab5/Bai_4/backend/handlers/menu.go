package handlers

import (
	"backend/models"
	"backend/database"
	"net/http"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

type MenuHandler struct {
	DB *gorm.DB
}

// GetRestaurants lấy tất cả nhà hàng và các món ăn thuộc nhà hàng đó
func GetRestaurants(c *gin.Context) {
	var restaurants []models.Restaurant
	
	// Sử dụng Preload("Dishes") để GORM tự động JOIN và lấy dữ liệu từ bảng dishes
	if err := database.DB.Preload("Dishes").Find(&restaurants).Error; err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Không thể lấy danh sách thực đơn"})
		return
	}

	c.JSON(http.StatusOK, restaurants)
}

// GetDishDetail lấy chi tiết một món ăn cụ thể theo ID
func GetDishDetail(c *gin.Context) {
	id := c.Param("id")
	var dish models.Dish

	if err := database.DB.First(&dish, id).Error; err != nil {
		if err == gorm.ErrRecordNotFound {
			c.JSON(http.StatusNotFound, gin.H{"error": "Không tìm thấy món ăn"})
		} else {
			c.JSON(http.StatusInternalServerError, gin.H{"error": "Lỗi hệ thống"})
		}
		return
	}

	c.JSON(http.StatusOK, dish)
}