package routes

import (
	"payment-service/handlers"

	"github.com/gin-gonic/gin"
)

func Routes(r *gin.Engine) {
	api := r.Group("/api/v1") // Giống app.use("/subjects", ...)

	api.POST("/payments", handlers.ProcessPayment)
}
