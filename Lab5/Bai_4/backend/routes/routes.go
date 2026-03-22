package routes

import (
	"backend/handlers"

	"github.com/gin-gonic/gin"
)

func Routes(r *gin.Engine) {
	api := r.Group("/api/v1") // Giống app.use("/subjects", ...)

	api.GET("/restaurants", handlers.GetRestaurants) // Xem tất cả nhà hàng & món ăn
	api.GET("/dishes/:id", handlers.GetDishDetail)
	api.POST("/orders", handlers.CreateOrder)
	api.POST("/payments", handlers.ProcessPayment)
}
