package main

import (
	"github.com/gin-gonic/gin"
	"backend/database"
	"backend/routes"
)

func main() {
	database.InitDB()
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

	r.Run(":8080")
}