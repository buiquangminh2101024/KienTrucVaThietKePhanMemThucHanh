package models

import (
	"time"
)

// Restaurant thuộc Menu Module
type Restaurant struct {
	ID       uint    `gorm:"primaryKey" json:"id"`
	Name     string  `json:"name"`
	Address  string  `json:"address"`
	IsActive bool    `json:"is_active" gorm:"default:true"`
	Dishes   []Dish  `json:"dishes" gorm:"foreignKey:RestaurantID"`
}

// Dish thuộc Menu Module
type Dish struct {
	ID           uint    `gorm:"primaryKey" json:"id"`
	RestaurantID uint    `json:"restaurant_id"`
	Name         string  `json:"name"`
	Price        float64 `json:"price"`
	Description  string  `json:"description"`
	Quantity     int     `json:"available_quantity" gorm:"column:available_quantity"`
}

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

// Payment thuộc Payment Module
type Payment struct {
	ID            uint      `gorm:"primaryKey" json:"id"`
	OrderID       uint      `gorm:"unique" json:"order_id"`
	PaymentMethod string    `json:"payment_method"` // MOMO, BANK_TRANSFER
	TransactionID string    `json:"transaction_id"`
	Status        string    `json:"status" gorm:"default:SUCCESS"`
	PaidAt        time.Time `json:"paid_at"`
}