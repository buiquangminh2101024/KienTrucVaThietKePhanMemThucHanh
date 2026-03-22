package grpc

import (
	"context"
	"payment-service/database"
	"payment-service/models"
	"payment-service/pb"
	"time"

	"gorm.io/gorm"
)

type PaymentServer struct {
	pb.UnimplementedPaymentServiceServer
}

func (s *PaymentServer) ProcessPayment(ctx context.Context, req *pb.PaymentRequest) (*pb.PaymentResponse, error) {
	// Tạo bản ghi thanh toán
	payment := models.Payment{
		OrderID:       uint(req.OrderId),
		PaymentMethod: req.PaymentMethod,
		TransactionID: req.TransactionId,
		Status:        "SUCCESS",
		PaidAt:        time.Now(),
	}

	err := database.DB.Transaction(func(tx *gorm.DB) error {
		// 1. Tạo bản ghi thanh toán
		if err := tx.Create(&payment).Error; err != nil {
			return err
		}

		// 2. Cập nhật trạng thái đơn hàng qua gRPC (sẽ được gọi từ handler)
		// Không cần update trực tiếp ở đây vì handler đã xử lý

		return nil
	})

	if err != nil {
		return &pb.PaymentResponse{Success: false}, err
	}

	return &pb.PaymentResponse{Success: true}, nil
}
