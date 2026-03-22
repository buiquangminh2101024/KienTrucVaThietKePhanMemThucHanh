package handlers

import (
	"backend/database"
	"backend/models"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func ProcessPayment(c *gin.Context) {
	var input models.Payment
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// Gán thời gian hiện tại để tránh giá trị 0000-00-00
    input.PaidAt = time.Now()

	err := database.DB.Transaction(func(tx *gorm.DB) error {
		// 1. Tạo bản ghi thanh toán
		if err := tx.Create(&input).Error; err != nil {
			return err
		}

		// 2. Cập nhật trạng thái đơn hàng (Trong Monolith ta làm việc này trực tiếp)
		if err := tx.Model(&models.Order{}).Where("id = ?", input.OrderID).Update("status", "PAID").Error; err != nil {
			return err
		}

		return nil
	})

	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Thanh toán thất bại"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Thanh toán hoàn tất!"})
}