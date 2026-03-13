package models

import "time"

// Bảng User
type User struct {
	ID       uint   `gorm:"primaryKey"`
	Username string `gorm:"unique;not null"`
	Password string `gorm:"not null"` // Trong bài lab có thể để plain text cho nhanh
	Role     string `gorm:"default:editor"`
}

// Bảng Content
type Content struct {
	ID        uint      `gorm:"primaryKey"`
	Title     string    `gorm:"not null"`
	Body      string    `gorm:"type:text"`
	AuthorID  uint      
	Author    User      `gorm:"foreignKey:AuthorID"` // Khóa ngoại nối sang bảng User
	CreatedAt time.Time
}