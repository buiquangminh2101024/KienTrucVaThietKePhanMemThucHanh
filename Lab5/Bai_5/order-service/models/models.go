package models

import (
	"time"
)

// Order thuộc Ordering Module
type Order struct {
	ID           uint        `gorm:"primaryKey" json:"id"`
	CustomerName string      `json:"customer_name"`
	TotalAmount  float64     `json:"total_amount"`
	Status       string      `json:"status" gorm:"default:PENDING"` // PENDING, PAID
	CreatedAt    time.Time   `json:"created_at"`
	OrderItems   []OrderItem `json:"items" gorm:"foreignKey:OrderID"`
}

// OrderItem là bảng trung gian nối Order và Dish
type OrderItem struct {
	ID        uint    `gorm:"primaryKey" json:"id"`
	OrderID   uint    `json:"order_id"`
	DishID    uint    `json:"dish_id"`
	Quantity  int     `json:"quantity"`
	UnitPrice float64 `json:"unit_price"`
}