package models

import (
	"time"
)

// Payment thuộc Payment Module
type Payment struct {
	ID            uint      `gorm:"primaryKey" json:"id"`
	OrderID       uint      `gorm:"unique" json:"order_id"`
	PaymentMethod string    `json:"payment_method"` // MOMO, BANK_TRANSFER
	TransactionID string    `json:"transaction_id"`
	Status        string    `json:"status" gorm:"default:SUCCESS"`
	PaidAt        time.Time `json:"paid_at"`
}