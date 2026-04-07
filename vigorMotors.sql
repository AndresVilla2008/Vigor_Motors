CREATE DATABASE IF NOT EXISTS vigorMotors;
USE vigorMotors;

DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `vehicles`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CLIENT') NOT NULL DEFAULT 'CLIENT',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE `vehicles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  `year` int(11) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `color` varchar(50) DEFAULT NULL,
  `fuel_type` enum('Gasolina','Diesel','Híbrido','Eléctrico') DEFAULT NULL,
  `transmission` enum('Manual','Automática') DEFAULT NULL,
  `mileage` int(11) DEFAULT 0,
  `description` text DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  CONSTRAINT `chk_stock` CHECK (`stock` >= 0),
  CONSTRAINT `chk_price` CHECK (`price` > 0),
  CONSTRAINT `chk_year` CHECK (`year` >= 1900)
);

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `total` decimal(14,2) NOT NULL,
  `status` enum('COMPLETED','CANCELLED') NOT NULL DEFAULT 'COMPLETED',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `fk_orders_user` (`user_id`),
  CONSTRAINT `fk_orders_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `vehicle_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1,
  `price_at_purchase` decimal(12,2) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_items_vehicle` (`vehicle_id`),
  KEY `fk_items_order` (`order_id`),
  CONSTRAINT `fk_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_items_vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`),
  CONSTRAINT `chk_quantity` CHECK (`quantity` > 0)
);

SHOW TABLES;
