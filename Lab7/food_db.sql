-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.8.6-MariaDB-ubu2404 - mariadb.org binary distribution
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for food_db
CREATE DATABASE IF NOT EXISTS `food_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `food_db`;

-- Dumping structure for table food_db.Food
CREATE TABLE IF NOT EXISTS `Food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(191) NOT NULL,
  `description` varchar(191) DEFAULT NULL,
  `price` decimal(65,30) NOT NULL,
  `category` varchar(191) NOT NULL,
  `imageUrl` varchar(191) DEFAULT NULL,
  `available` tinyint(1) NOT NULL DEFAULT 1,
  `createdAt` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table food_db.Notification
CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `message` varchar(191) NOT NULL,
  `type` enum('ORDER','PAYMENT','SYSTEM') NOT NULL,
  `isRead` tinyint(1) NOT NULL DEFAULT 0,
  `createdAt` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  KEY `Notification_userId_fkey` (`userId`),
  KEY `Notification_orderId_fkey` (`orderId`),
  CONSTRAINT `Notification_orderId_fkey` FOREIGN KEY (`orderId`) REFERENCES `Order` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `Notification_userId_fkey` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table food_db.Order
CREATE TABLE IF NOT EXISTS `Order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `totalAmount` decimal(65,30) NOT NULL,
  `status` enum('PENDING','CONFIRMED','SHIPPING','COMPLETED','CANCELLED') NOT NULL,
  `paymentMethod` enum('CASH','BANKING','MOMO') NOT NULL,
  `createdAt` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  `updatedAt` datetime(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Order_userId_fkey` (`userId`),
  CONSTRAINT `Order_userId_fkey` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table food_db.OrderItem
CREATE TABLE IF NOT EXISTS `OrderItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `foodId` int(11) NOT NULL,
  `foodName` varchar(191) NOT NULL,
  `foodPrice` decimal(65,30) NOT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` decimal(65,30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `OrderItem_orderId_fkey` (`orderId`),
  KEY `OrderItem_foodId_fkey` (`foodId`),
  CONSTRAINT `OrderItem_foodId_fkey` FOREIGN KEY (`foodId`) REFERENCES `Food` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `OrderItem_orderId_fkey` FOREIGN KEY (`orderId`) REFERENCES `Order` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table food_db.Payment
CREATE TABLE IF NOT EXISTS `Payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `amount` decimal(65,30) NOT NULL,
  `method` enum('CASH','BANKING','MOMO') NOT NULL,
  `status` enum('PENDING','SUCCESS','FAILED') NOT NULL,
  `transactionRef` varchar(191) DEFAULT NULL,
  `paidAt` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Payment_orderId_fkey` (`orderId`),
  CONSTRAINT `Payment_orderId_fkey` FOREIGN KEY (`orderId`) REFERENCES `Order` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table food_db.User
CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(191) NOT NULL,
  `email` varchar(191) NOT NULL,
  `password` varchar(191) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `createdAt` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_username_key` (`username`),
  UNIQUE KEY `User_email_key` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

-- INSERT USER
INSERT INTO `User` (`username`, `email`, `password`, `role`, `createdAt`) VALUES
('user1', 'user1@example.com', 'hashed_password_1', 'USER', NOW()),
('user2', 'user2@example.com', 'hashed_password_2', 'USER', NOW()),
('admin1', 'admin@example.com', 'hashed_password_admin', 'ADMIN', NOW());

-- INSERT FOOD
INSERT INTO `Food` (`name`, `description`, `price`, `category`, `imageUrl`, `available`, `createdAt`) VALUES
('Phở Bò', 'Phở bò truyền thống Hà Nội', 50000.00, 'Mì/Phở', 'https://example.com/pho-bo.jpg', 1, NOW()),
('Cơm Tấm Sài Gòn', 'Cơm tấm với sườn nướng', 45000.00, 'Cơm', 'https://example.com/com-tam.jpg', 1, NOW()),
('Bánh Mỳ Thơm', 'Bánh mỳ nóng với pâté và chả', 25000.00, 'Bánh', 'https://example.com/banh-mi.jpg', 1, NOW()),
('Trà Sữa Trân Châu', 'Trà sữa đen với trân châu', 30000.00, 'Đồ Uống', 'https://example.com/tra-sua.jpg', 1, NOW());

-- INSERT ORDER
INSERT INTO `Order` (`userId`, `totalAmount`, `status`, `paymentMethod`, `createdAt`, `updatedAt`) VALUES
(1, 75000.00, 'PENDING', 'BANKING', NOW(), NOW()),
(2, 110000.00, 'CONFIRMED', 'MOMO', NOW(), NOW());

-- INSERT ORDERITEM
INSERT INTO `OrderItem` (`orderId`, `foodId`, `foodName`, `foodPrice`, `quantity`, `subtotal`) VALUES
(1, 1, 'Phở Bò', 50000.00, 1, 50000.00),
(1, 4, 'Trà Sữa Trân Châu', 30000.00, 1, 30000.00),
(2, 2, 'Cơm Tấm Sài Gòn', 45000.00, 2, 90000.00),
(2, 3, 'Bánh Mỳ Thơm', 25000.00, 1, 25000.00);