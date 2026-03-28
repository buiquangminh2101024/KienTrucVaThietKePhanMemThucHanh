package models

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