package main

import (
	"KT_TKPM_Lab4_Cau3/my-cms-backend/core"
	"KT_TKPM_Lab4_Cau3/my-cms-backend/models"
	"KT_TKPM_Lab4_Cau3/my-cms-backend/plugins"
	"net/http"
	"github.com/gin-gonic/gin"
)

func main() {
	// 1. Khởi tạo Kernel và Plugins
	kernel := &core.Kernel{}
	dbPlug := &plugins.DBPlugin{}
	kernel.RegisterPlugin(dbPlug)
	kernel.Start()

	authPlug := &plugins.AuthPlugin{DB: dbPlug.DB}
	authPlug.Init()

	r := gin.Default()

	// -----------------------------------------------------------
	// CHỨC NĂNG ĐĂNG KÝ & ĐĂNG NHẬP
	// -----------------------------------------------------------

	// Đăng ký tài khoản mới
	r.POST("/register", func(c *gin.Context) {
		var user models.User
		if err := c.ShouldBindJSON(&user); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Dữ liệu không hợp lệ"})
			return
		}
		dbPlug.DB.Create(&user)
		c.JSON(http.StatusCreated, gin.H{"message": "Tạo tài khoản thành công!"})
	})

	// Đăng nhập hệ thống
	r.POST("/login", func(c *gin.Context) {
		var loginData struct {
			Username string `json:"username"`
			Password string `json:"password"`
		}

		if err := c.ShouldBindJSON(&loginData); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": "Vui lòng nhập đủ user và pass"})
			return
		}

		// Gọi Auth Plugin để xác thực
		user, err := authPlug.Authenticate(loginData.Username, loginData.Password)
		if err != nil {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Sai tài khoản hoặc mật khẩu"})
			return
		}

		// Trả về thông tin user (trong thực tế sẽ trả về Token)
		c.JSON(http.StatusOK, gin.H{
			"message": "Đăng nhập thành công",
			"user": gin.H{
				"username": user.Username,
				"role":     user.Role,
			},
		})
	})

	// -----------------------------------------------------------
	// CHỨC NĂNG NỘI DUNG (Cũ)
	// -----------------------------------------------------------

	// Xem nội dung (Public)
	r.GET("/contents", func(c *gin.Context) {
		var contents []models.Content
		dbPlug.DB.Preload("Author").Find(&contents)
		c.JSON(http.StatusOK, contents)
	})

	// Đăng bài (Private - yêu cầu thông tin xác thực qua Header như cũ)
	r.POST("/contents", func(c *gin.Context) {
		u, p := c.GetHeader("X-Username"), c.GetHeader("X-Password")
		user, err := authPlug.Authenticate(u, p)
		if err != nil {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Xác thực thất bại"})
			return
		}

		var content models.Content
		c.ShouldBindJSON(&content)
		content.AuthorID = user.ID
		dbPlug.DB.Create(&content)
		c.JSON(http.StatusOK, gin.H{"status": "Đã đăng bài"})
	})

	r.Run(":8080")
}