package grpc

import (
	"context"
	"order-service/database"
	"order-service/models"
	"order-service/pb"
)

type OrderServer struct {
	pb.UnimplementedOrderServiceServer
}

func (s *OrderServer) GetOrder(ctx context.Context, req *pb.OrderRequest) (*pb.OrderResponse, error) {
	var order models.Order
	// database.DB là kết nối tới order-db
	if err := database.DB.Model(&models.Order{}).Where("id = ?", req.Id).First(&order).Error; err != nil {
		return nil, err
	}
	return &pb.OrderResponse{
		Id:    uint32(order.ID),
		IsHas: true,
	}, nil
}

func (s *OrderServer) UpdateOrderStatus(ctx context.Context, req *pb.UpdateOrderRequest) (*pb.UpdateOrderResponse, error) {
	// database.DB là kết nối tới order-db
	if err := database.DB.Model(&models.Order{}).Where("id = ?", req.Id).Update("status", req.Status).Error; err != nil {
		return nil, err
	}
	return &pb.UpdateOrderResponse{
		Success: true,
	}, nil
}
