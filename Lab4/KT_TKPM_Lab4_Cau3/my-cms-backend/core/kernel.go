package core

import "fmt"

// Plugin là interface mà mọi module phải tuân theo
type Plugin interface {
	Init() error
	GetName() string
}

// Kernel là "Nhân" của hệ thống
type Kernel struct {
	Plugins []Plugin
}

func (k *Kernel) RegisterPlugin(p Plugin) {
	k.Plugins = append(k.Plugins, p)
	fmt.Printf("Đã cắm Plug-in: %s vào Nhân\n", p.GetName())
}

func (k *Kernel) Start() {
	fmt.Println("Hệ thống Core đang khởi động...")
	for _, p := range k.Plugins {
		p.Init()
	}
	fmt.Println("CMS đã sẵn sàng hoạt động!")
}