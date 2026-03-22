package handlers

import (
	"context"
	"net/http"
	"os"
	"payment-service/database"
	"payment-service/models"
	"payment-service/pb"
	"time"

	"github.com/gin-gonic/gin"
	"google.golang.org/grpc"
	"gorm.io/gorm"
)

func ProcessPayment(c *gin.Context) {
	// Kết nối gRPC tới Order Service
	conn, _ := grpc.Dial(os.Getenv("ORDER_GRPC_ADDR"), grpc.WithInsecure())
	defer conn.Close()
	orderClient := pb.NewOrderServiceClient(conn)

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

		// 2. Cập nhật trạng thái đơn hàng qua gRPC
		_, err := orderClient.GetOrder(context.Background(), &pb.OrderRequest{Id: uint32(input.OrderID)})
		if err != nil {
			c.JSON(404, gin.H{"error": "Đơn không tồn tại"})
			return err
		}
		_, err = orderClient.UpdateOrderStatus(context.Background(), &pb.UpdateOrderRequest{Id: uint32(input.OrderID), Status: "PAID"})
		if err != nil {
			c.JSON(404, gin.H{"error": "Không cập nhật đơn được"})
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
