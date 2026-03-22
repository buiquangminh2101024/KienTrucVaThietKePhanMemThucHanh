package handlers

import (
	"net/http"
	"backend/models"
	"backend/database"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func CreateOrder(c *gin.Context) {
	var input struct {
		CustomerName string `json:"customer_name"`
		Items        []struct {
			DishID   uint `json:"dish_id"`
			Quantity int  `json:"quantity"`
		} `json:"items"`
	}

	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// 1. Khởi tạo đơn hàng (Monolith cho phép chạy Transaction dễ dàng)
	err := database.DB.Transaction(func(tx *gorm.DB) error {
		var total float64 = 0
		var orderItems []models.OrderItem

		for _, item := range input.Items {
			var dish models.Dish
			if err := tx.First(&dish, item.DishID).Error; err != nil {
				return err // Món ăn không tồn tại
			}

			unitPrice := dish.Price
			total += unitPrice * float64(item.Quantity)

			orderItems = append(orderItems, models.OrderItem{
				DishID:    item.DishID,
				Quantity:  item.Quantity,
				UnitPrice: unitPrice,
			})
		}

		newOrder := models.Order{
			CustomerName: input.CustomerName,
			TotalAmount:  total,
			Status:       "PENDING",
			OrderItems:   orderItems,
		}

		if err := tx.Create(&newOrder).Error; err != nil {
			return err
		}

		c.JSON(http.StatusCreated, newOrder)
		return nil
	})

	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Không thể tạo đơn hàng"})
	}
}