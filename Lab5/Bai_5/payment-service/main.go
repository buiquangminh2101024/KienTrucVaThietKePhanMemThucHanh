package main

import (
	"log"
	"net"

	"payment-service/database"
	paymentGrpc "payment-service/grpc"
	"payment-service/pb"
	"payment-service/routes"

	"github.com/gin-gonic/gin"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

func main() {
	database.InitDB()

	// Start gRPC server
	go startGRPCServer()

	// Start HTTP server
	r := gin.Default()

	// Cấu hình CORS đơn giản
	r.Use(func(c *gin.Context) {
		c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
		c.Writer.Header().Set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
		c.Writer.Header().Set("Access-Control-Allow-Headers", "Content-Type, Authorization")
		if c.Request.Method == "OPTIONS" {
			c.AbortWithStatus(204)
			return
		}
		c.Next()
	})

	routes.Routes(r)

	log.Println("HTTP server starting on :8080")
	r.Run(":8080")
}

func startGRPCServer() {
	lis, err := net.Listen("tcp", ":50053")
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer()
	pb.RegisterPaymentServiceServer(s, &paymentGrpc.PaymentServer{})
	reflection.Register(s)

	log.Println("gRPC server starting on :50053")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
