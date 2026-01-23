package iuh.fit.se.controllers;

import iuh.fit.se.entities.MusicProduct;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class MusicController {

    @GetMapping
    @Cacheable(value = "music_products") // Lưu kết quả vào Redis với key 'music_products'
    public List<MusicProduct> getProducts() throws InterruptedException {
        // MÔ PHỎNG LỖI HIỆU NĂNG:

        // 1. Giả lập truy vấn Database nặng hoặc tính toán phức tạp
        // Mỗi lần gọi API sẽ mất ít nhất 150ms - 200ms
        Thread.sleep(170);

        // 2. Trả về dữ liệu trực tiếp từ "Database giả lập"
        // mà không có Redis hay Memory Cache
        return List.of(
                new MusicProduct(1L, "Album 21", "Adele", 15.99),
                new MusicProduct(2L, "Midnights", "Taylor Swift", 19.99),
                new MusicProduct(3L, "Justice", "Justin Bieber", 12.50)
        );
    }
}
