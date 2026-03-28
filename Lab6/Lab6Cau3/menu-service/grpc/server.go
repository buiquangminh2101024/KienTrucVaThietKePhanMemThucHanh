package grpc

import (
	"context"
	"menu-service/database"
	"menu-service/models"
	"menu-service/pb"
)

type MenuServer struct {
	pb.UnimplementedMenuServiceServer
}

func (s *MenuServer) GetDish(ctx context.Context, req *pb.DishRequest) (*pb.DishResponse, error) {
	var dish models.Dish
	// database.DB là kết nối tới menu-db
	if err := database.DB.First(&dish, req.Id).Error; err != nil {
		return nil, err
	}
	return &pb.DishResponse{
		Id:    uint32(dish.ID),
		Price: dish.Price,
		Name:  dish.Name,
	}, nil
}
