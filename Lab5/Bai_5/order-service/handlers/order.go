package handlers

import (
	"context"
	"net/http"
	"order-service/database"
	"order-service/models"
	"order-service/pb"
	"os"

	"github.com/gin-gonic/gin"
	"google.golang.org/grpc"
	"gorm.io/gorm"
)

func CreateOrder(c *gin.Context) {
	// Kết nối gRPC tới Menu Service
	conn, _ := grpc.Dial(os.Getenv("MENU_GRPC_ADDR"), grpc.WithInsecure())
	defer conn.Close()
	menuClient := pb.NewMenuServiceClient(conn)

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

	// 2. Khởi tạo đơn hàng (Monolith cho phép chạy Transaction dễ dàng)
	err := database.DB.Transaction(func(tx *gorm.DB) error {
		var total float64 = 0
		var orderItems []models.OrderItem

		for _, item := range input.Items {
			// GỌI gRPC thay vì Query DB
			resp, err := menuClient.GetDish(context.Background(), &pb.DishRequest{Id: uint32(item.DishID)})
			if err != nil {
				c.JSON(404, gin.H{"error": "Món ăn không tồn tại"})
				return err
			}

			unitPrice := resp.Price
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
