-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: computer-equipment-store-system
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `attributes`
--

LOCK TABLES `attributes` WRITE;
/*!40000 ALTER TABLE `attributes` DISABLE KEYS */;
INSERT INTO `attributes` VALUES (1,'Brand','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(2,'Model','TEXT',NULL,0,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(3,'Processor','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(4,'RAM','NUMBER','GB',1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(5,'Storage Capacity','NUMBER','GB',1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(6,'Storage Type','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(7,'Graphics Card','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(8,'Screen Size','NUMBER','inch',1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(9,'Resolution','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(10,'Refresh Rate','NUMBER','Hz',1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(11,'Operating System','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(12,'Battery Life','NUMBER','hours',0,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(13,'Weight','NUMBER','kg',0,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(14,'Color','TEXT',NULL,1,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(15,'Connectivity','TEXT',NULL,0,'2026-03-31 12:55:58','2026-03-31 13:16:34',NULL),(16,'Wireless','BOOLEAN',NULL,1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(17,'Bluetooth','BOOLEAN',NULL,1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(18,'Warranty','NUMBER','months',0,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(19,'Power Consumption','NUMBER','W',0,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL),(20,'Touchscreen','BOOLEAN',NULL,1,'2026-03-31 12:55:58','2026-03-31 12:55:58',NULL);
/*!40000 ALTER TABLE `attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (24,1,4,1),(25,2,4,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,2,'2026-03-31 13:28:49','2026-03-31 13:28:49'),(2,1,'2026-04-01 13:29:11','2026-04-01 13:29:11'),(3,3,'2026-04-05 14:13:22','2026-04-05 14:13:22'),(4,4,'2026-04-08 21:25:43','2026-04-08 21:25:43'),(5,5,'2026-04-08 21:43:30','2026-04-08 21:43:30');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Laptops','laptops',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(2,'Desktop Computers','desktop-computers',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(3,'Monitors','monitors',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(4,'Keyboards','keyboards',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(5,'Mice','mice',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(6,'Printers','printers',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(7,'Scanners','scanners',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(8,'Networking Equipment','networking-equipment',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(9,'Computer Components','computer-components',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(10,'Storage Devices','storage-devices',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(11,'RAM Memory','ram-memory',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(12,'Graphics Cards','graphics-cards',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(13,'Power Supplies','power-supplies',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(14,'Cooling Systems','cooling-systems',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(15,'Laptop Accessories','laptop-accessories',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(16,'Headphones','headphones',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(17,'Webcams','webcams',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(18,'Microphones','microphones',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(19,'Gaming Equipment','gaming-equipment',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL),(20,'Office Equipment','office-equipment',1,'2026-03-31 12:47:49','2026-03-31 12:47:49',NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category_attributes`
--

LOCK TABLES `category_attributes` WRITE;
/*!40000 ALTER TABLE `category_attributes` DISABLE KEYS */;
INSERT INTO `category_attributes` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,10),(1,11),(1,12),(1,14),(1,20),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,11),(2,14),(2,19),(3,1),(3,8),(3,9),(3,10),(3,14),(3,19),(4,1),(4,2),(4,14),(4,16),(4,17),(5,1),(5,2),(5,14),(5,16),(5,17);
/*!40000 ALTER TABLE `category_attributes` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,4,'Lenovo Legion 5',2199.99,1,2199.99),(2,2,4,'Lenovo Legion 5',2199.99,1,2199.99),(3,2,11,'Logitech K380 Keyboard',79.99,2,159.98),(4,3,3,'Lenovo IdeaPad 3 15ITL6',1099.99,1,1099.99),(5,4,4,'Lenovo Legion 5',2199.99,2,4399.98),(6,5,4,'Lenovo Legion 5',2199.99,1,2199.99),(7,6,12,'Logitech G Pro Keyboard',199.99,1,199.99),(8,6,7,'Dell OptiPlex 7090',1499.99,1,1499.99),(9,7,4,'Lenovo Legion 5',2199.99,1,2199.99),(10,8,11,'Logitech K380 Keyboard',79.99,1,79.99);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'ORD-20260331133011-5400',2,'DELIVERED',2199.99,'Ivan Ivanov','0899204635','Bulgaria, Ezerche, Khan Krum 6, 7274','2026-03-31 13:30:11','2026-03-31 17:17:46'),(2,'ORD-20260331171517-6571',2,'NEW',2359.97,'Atche Chakarova','0899204635','Bulgaria, Ezerche, Khan Krum 6, 7274','2026-03-31 17:15:17','2026-04-02 15:24:35'),(3,'ORD-20260404194234-2251',2,'DELIVERED',1099.99,'Atche Chakarova','0899204635','Bulgaria, Ezerche, Khan Krum 6, 7274','2026-04-04 19:42:34','2026-04-23 16:20:44'),(4,'ORD-20260405141534-2877',3,'DELIVERED',4399.98,'Ava Alyaovlu','0877804945','Bulgaria, Ezerche, Tanyo Voyvoda 2, 7274','2026-04-05 14:15:34','2026-04-05 14:18:29'),(5,'ORD-20260406112342-1231',2,'DELIVERED',2199.99,'Atche Chakarova','0899204635','Bulgaria, Ezerche, Khan Krum 6, 7274','2026-04-06 11:23:42','2026-04-23 16:02:09'),(6,'ORD-20260408212811-2365',4,'PROCESSING',1699.98,'Aygun Chakarov','0895492262','Bulgaria, Varna, Silistra 2, 9000','2026-04-08 21:28:11','2026-04-08 21:29:26'),(7,'ORD-20260408214436-4257',5,'PROCESSING',2199.99,'Esin Chakarova','0877338040','Bulgaria, Varna, Silistra 2, 9000','2026-04-08 21:44:36','2026-04-08 21:45:11'),(8,'ORD-20260423171944-5794',2,'NEW',79.99,'Atche Chakarova','0899204635','Bulgaria, Ezerche, Khan Krum 6, 7274','2026-04-23 17:19:44','2026-04-23 17:19:44');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_attribute_values`
--

LOCK TABLES `product_attribute_values` WRITE;
/*!40000 ALTER TABLE `product_attribute_values` DISABLE KEYS */;
INSERT INTO `product_attribute_values` VALUES (1,1,1,'Asus',NULL,NULL),(2,1,2,'VivoBook 15 X1502',NULL,NULL),(3,1,3,'Intel Core i5-1235U',NULL,NULL),(4,1,4,NULL,16.000,NULL),(5,1,5,NULL,512.000,NULL),(6,1,6,'SSD',NULL,NULL),(7,1,7,'Intel Iris Xe Graphics',NULL,NULL),(8,1,8,NULL,15.600,NULL),(9,1,9,'1920x1080',NULL,NULL),(10,1,10,NULL,60.000,NULL),(11,1,11,'Windows 11',NULL,NULL),(12,1,12,NULL,8.000,NULL),(13,1,13,NULL,1.700,NULL),(14,1,14,'Silver',NULL,NULL),(15,1,16,NULL,NULL,1),(16,1,17,NULL,NULL,1),(17,1,18,NULL,24.000,NULL),(18,1,20,NULL,NULL,0),(19,2,1,'Asus',NULL,NULL),(20,2,2,'TUF Gaming A15',NULL,NULL),(21,2,3,'AMD Ryzen 7 7735HS',NULL,NULL),(22,2,4,NULL,16.000,NULL),(23,2,5,NULL,1000.000,NULL),(24,2,6,'SSD',NULL,NULL),(25,2,7,'NVIDIA GeForce RTX 4060',NULL,NULL),(26,2,8,NULL,15.600,NULL),(27,2,9,'1920x1080',NULL,NULL),(28,2,10,NULL,144.000,NULL),(29,2,11,'Windows 11',NULL,NULL),(30,2,12,NULL,7.000,NULL),(31,2,13,NULL,2.200,NULL),(32,2,14,'Black',NULL,NULL),(33,2,16,NULL,NULL,1),(34,2,17,NULL,NULL,1),(35,2,18,NULL,24.000,NULL),(36,2,20,NULL,NULL,0),(37,3,1,'Lenovo',NULL,NULL),(38,3,2,'IdeaPad 3 15ITL6',NULL,NULL),(39,3,3,'Intel Core i3-1115G4',NULL,NULL),(40,3,4,NULL,8.000,NULL),(41,3,5,NULL,256.000,NULL),(42,3,6,'SSD',NULL,NULL),(43,3,7,'Intel UHD Graphics',NULL,NULL),(44,3,8,NULL,15.600,NULL),(45,3,9,'1920x1080',NULL,NULL),(46,3,10,NULL,60.000,NULL),(47,3,11,'Windows 11',NULL,NULL),(48,3,12,NULL,6.000,NULL),(49,3,13,NULL,1.650,NULL),(50,3,14,'Gray',NULL,NULL),(51,3,16,NULL,NULL,1),(52,3,17,NULL,NULL,1),(53,3,18,NULL,24.000,NULL),(54,3,20,NULL,NULL,0),(55,4,1,'Lenovo',NULL,NULL),(56,4,2,'Legion 5',NULL,NULL),(57,4,3,'AMD Ryzen 7 6800H',NULL,NULL),(58,4,4,NULL,16.000,NULL),(59,4,5,NULL,1000.000,NULL),(60,4,6,'SSD',NULL,NULL),(61,4,7,'NVIDIA GeForce RTX 3070',NULL,NULL),(62,4,8,NULL,15.600,NULL),(63,4,9,'2560x1440',NULL,NULL),(64,4,10,NULL,165.000,NULL),(65,4,11,'Windows 11',NULL,NULL),(66,4,12,NULL,6.000,NULL),(67,4,13,NULL,2.400,NULL),(68,4,14,'Black',NULL,NULL),(69,4,16,NULL,NULL,1),(70,4,17,NULL,NULL,1),(71,4,18,NULL,24.000,NULL),(72,4,20,NULL,NULL,0),(73,5,1,'HP',NULL,NULL),(74,5,2,'Pavilion 15',NULL,NULL),(75,5,3,'Intel Core i5-1240P',NULL,NULL),(76,5,4,NULL,16.000,NULL),(77,5,5,NULL,512.000,NULL),(78,5,6,'SSD',NULL,NULL),(79,5,7,'Intel Iris Xe Graphics',NULL,NULL),(80,5,8,NULL,15.600,NULL),(81,5,9,'1920x1080',NULL,NULL),(82,5,10,NULL,60.000,NULL),(83,5,11,'Windows 11',NULL,NULL),(84,5,12,NULL,8.000,NULL),(85,5,13,NULL,1.750,NULL),(86,5,14,'Silver',NULL,NULL),(87,5,16,NULL,NULL,1),(88,5,17,NULL,NULL,1),(89,5,18,NULL,24.000,NULL),(90,5,20,NULL,NULL,1),(91,8,1,'Dell',NULL,NULL),(92,8,2,'P2422H',NULL,NULL),(93,8,8,NULL,23.800,NULL),(94,8,9,'1920x1080',NULL,NULL),(95,8,10,NULL,60.000,NULL),(96,8,14,'Black',NULL,NULL),(97,8,15,'HDMI, DisplayPort, VGA, USB',NULL,NULL),(98,8,18,NULL,36.000,NULL),(99,8,19,NULL,48.000,NULL),(100,9,1,'Samsung',NULL,NULL),(101,9,2,'Odyssey G5',NULL,NULL),(102,9,8,NULL,27.000,NULL),(103,9,9,'2560x1440',NULL,NULL),(104,9,10,NULL,144.000,NULL),(105,9,14,'Black',NULL,NULL),(106,9,15,'HDMI, DisplayPort',NULL,NULL),(107,9,18,NULL,24.000,NULL),(108,9,19,NULL,52.000,NULL),(109,10,1,'LG',NULL,NULL),(110,10,2,'UltraWide 29WN600',NULL,NULL),(111,10,8,NULL,29.000,NULL),(112,10,9,'2560x1080',NULL,NULL),(113,10,10,NULL,75.000,NULL),(114,10,14,'Black',NULL,NULL),(115,10,15,'HDMI, DisplayPort',NULL,NULL),(116,10,18,NULL,24.000,NULL),(117,10,19,NULL,40.000,NULL),(118,11,1,'Logitech',NULL,NULL),(119,11,2,'K380',NULL,NULL),(120,11,14,'Dark Gray',NULL,NULL),(121,11,16,NULL,NULL,1),(122,11,17,NULL,NULL,1),(123,11,18,NULL,24.000,NULL),(124,12,1,'Logitech',NULL,NULL),(125,12,2,'G Pro Keyboard',NULL,NULL),(126,12,14,'Black',NULL,NULL),(127,12,16,NULL,NULL,0),(128,12,17,NULL,NULL,0),(129,12,18,NULL,24.000,NULL),(130,13,1,'Logitech',NULL,NULL),(131,13,2,'MX Master 3S',NULL,NULL),(132,13,14,'Graphite',NULL,NULL),(133,13,16,NULL,NULL,1),(134,13,17,NULL,NULL,1),(135,13,18,NULL,24.000,NULL),(136,14,1,'Razer',NULL,NULL),(137,14,2,'DeathAdder V2',NULL,NULL),(138,14,14,'Black',NULL,NULL),(139,14,16,NULL,NULL,0),(140,14,17,NULL,NULL,0),(141,14,18,NULL,24.000,NULL),(142,17,1,'Razer',NULL,NULL),(143,17,2,'BlackShark V2',NULL,NULL),(144,17,14,'Black',NULL,NULL),(145,17,15,'3.5mm Jack, USB Sound Card',NULL,NULL),(146,17,18,NULL,24.000,NULL),(147,18,1,'HyperX',NULL,NULL),(148,18,2,'Cloud II',NULL,NULL),(149,18,14,'Red/Black',NULL,NULL),(150,18,15,'3.5mm Jack, USB',NULL,NULL),(151,18,18,NULL,24.000,NULL),(152,19,1,'HyperX',NULL,NULL),(153,19,2,'QuadCast',NULL,NULL),(154,19,14,'Black/Red',NULL,NULL),(155,19,15,'USB',NULL,NULL),(156,19,18,NULL,24.000,NULL),(157,20,1,'Logitech',NULL,NULL),(158,20,2,'C920 HD Pro',NULL,NULL),(159,20,9,'1920x1080',NULL,NULL),(160,20,14,'Black',NULL,NULL),(161,20,15,'USB',NULL,NULL),(162,20,18,NULL,24.000,NULL);
/*!40000 ALTER TABLE `product_attribute_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,4,'/uploads/products/4/bf68e0a2-024c-4994-8241-c6888d85a9e3.jpg',1);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Asus VivoBook 15 X1502','Lightweight laptop suitable for everyday tasks and office work.',1299.99,10,1,1,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(2,'Asus TUF Gaming A15','Gaming laptop with powerful GPU and high performance.',1999.99,6,1,1,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(3,'Lenovo IdeaPad 3 15ITL6','Affordable laptop for students and basic usage.',1099.99,11,1,1,'2026-03-31 12:51:59','2026-04-04 19:42:34',NULL),(4,'Lenovo Legion 5','High-performance gaming laptop with excellent cooling.',2199.99,1,1,1,'2026-03-31 12:51:59','2026-04-08 21:44:36',NULL),(5,'HP Pavilion 15','Stylish HP laptop for productivity and multimedia.',1399.99,8,1,1,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(6,'HP ProDesk 400 G7','Reliable desktop computer for office environments.',1199.99,7,1,2,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(7,'Dell OptiPlex 7090','Business desktop with stable performance.',1499.99,5,1,2,'2026-03-31 12:51:59','2026-04-08 21:28:11',NULL),(8,'Dell P2422H Monitor','24-inch Full HD monitor with IPS panel.',399.99,15,1,3,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(9,'Samsung Odyssey G5','Curved gaming monitor with high refresh rate.',549.99,9,1,3,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(10,'LG UltraWide 29WN600','UltraWide monitor ideal for multitasking.',479.99,11,1,3,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(11,'Logitech K380 Keyboard','Compact wireless keyboard with multi-device support.',79.99,22,1,4,'2026-03-31 12:51:59','2026-04-23 17:19:46',NULL),(12,'Logitech G Pro Keyboard','Mechanical gaming keyboard with RGB lighting.',199.99,13,1,4,'2026-03-31 12:51:59','2026-04-08 21:28:11',NULL),(13,'Logitech MX Master 3S','Advanced wireless mouse with ergonomic design.',149.99,20,1,5,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(14,'Razer DeathAdder V2','Gaming mouse with high precision sensor.',89.99,22,1,5,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(15,'Acer Nitro 5','Gaming laptop with powerful specs at a good price.',1799.99,7,1,1,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(16,'Acer Aspire 5','Everyday laptop with solid performance and battery life.',1249.99,10,1,1,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(17,'Razer BlackShark V2','Gaming headset with clear sound and microphone.',159.99,13,1,16,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(18,'HyperX Cloud II','Popular gaming headset with surround sound.',139.99,16,1,16,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(19,'HyperX QuadCast','USB microphone for streaming and content creation.',189.99,9,1,18,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL),(20,'Logitech C920 HD Pro','Full HD webcam for video calls and streaming.',129.99,18,1,17,'2026-03-31 12:51:59','2026-03-31 12:51:59',NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service_requests`
--

LOCK TABLES `service_requests` WRITE;
/*!40000 ALTER TABLE `service_requests` DISABLE KEYS */;
INSERT INTO `service_requests` VALUES (1,2,18,'DONE','0899204635','ъокщвнтгкщжашиеяашожгфтнвп','2026-03-31 13:50:23','2026-03-31 13:51:49'),(2,2,2,'IN_PROGRESS','0899204635','iskam da mi opravite desktopa','2026-03-31 17:13:51','2026-04-23 14:22:50'),(3,3,2,'NEW','0877804945','LAPTOP lenovo legion 5 BROKEN COME FIX','2026-04-05 14:14:24','2026-04-05 14:14:24'),(4,2,7,'NEW','0899204635','opit 123456789','2026-04-23 17:33:14','2026-04-23 17:33:14');
/*!40000 ALTER TABLE `service_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'Laptop Diagnostics','Full diagnostics to identify hardware or software issues.',29.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(2,'Computer Repair','Repair of desktop computers including hardware replacement.',79.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(3,'Laptop Screen Replacement','Replacement of broken or damaged laptop screens.',149.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(4,'Operating System Installation','Installation of Windows or Linux operating system.',49.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(5,'Virus Removal','Complete virus and malware removal with system cleanup.',39.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(6,'Data Recovery','Recovery of lost or deleted files from damaged drives.',99.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(7,'SSD Upgrade','Upgrade from HDD to SSD for faster system performance.',59.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(8,'RAM Upgrade','Installation of additional RAM memory modules.',49.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(9,'PC Cleaning Service','Internal cleaning from dust and thermal paste replacement.',44.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(10,'Custom PC Build','Assembly of custom desktop computer based on client requirements.',129.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(11,'Laptop Keyboard Replacement','Replacement of faulty or damaged keyboard.',89.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(12,'Software Installation','Installation and setup of essential software packages.',29.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(13,'Network Setup','Configuration of home or office network.',69.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(14,'Printer Setup','Installation and configuration of printers.',34.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(15,'BIOS Update','Update of BIOS firmware for improved compatibility.',24.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(16,'Driver Installation','Installation and update of device drivers.',19.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(17,'Gaming PC Optimization','Performance tuning for gaming systems.',59.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(18,'Backup and Restore','Backup of important data and system restore.',39.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(19,'Remote IT Support','Remote troubleshooting and technical assistance.',29.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL),(20,'Laptop Battery Replacement','Replacement of worn-out laptop batteries.',79.99,1,'2026-03-31 12:54:46','2026-03-31 12:54:46',NULL);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'atche@abv.bg','$2a$10$iwyjOYLTnb2bSzCw.n7SfunqeK7G4qpggb469b1i1TkWTh7Fef2oi','Atche','Chakarova','0899204635','ADMIN',1,'2026-03-31 13:20:41','2026-03-31 13:21:16'),(2,'atche1202@gmail.com','$2a$10$vf.aa2cF0h/AJmcl9Gy1ouL8G4ewH26o0XQLzcHEPrnvA4Y2NdMkW','Ivana','Ivanova','0899204635','CUSTOMER',1,'2026-03-31 13:28:07','2026-04-22 20:06:58'),(3,'avadaudalia@gmail.com','$2a$10$QLs7xdCcgSEolZGOm6XdR.c2AnW6tfrkwQnpzJ33P7LbCfegVACPm','Ava','Alyaovlu','0877804945','CUSTOMER',1,'2026-04-05 14:13:14','2026-04-05 14:13:14'),(4,'aygyunchakarov@gmail.com','$2a$10$KEYuD4wSnNX7zhDYHFWWbOUpIGSMvkOKSzKlhxN/X.4xmaPdoQuWq','Aygun','Chakarov','0895492262','CUSTOMER',1,'2026-04-08 21:24:33','2026-04-08 21:24:33'),(5,'chakarova.esin@gmail.com','$2a$10$Old2Gi5HCRMwZUXjfy/Wv.gOVpUnmQMzaVyuKbL8sczWFphZkLybG','Esin','Chakarova','0877338040','CUSTOMER',1,'2026-04-08 21:43:02','2026-04-08 21:43:02'),(6,'petur@abv.bg','$2a$10$vH9m7Volrwx1iuv0vxhWLOIu8aeYdmsB/m14aRV9e6IE5Z6EbSP6y','Petur','Petrov','0899204635','ADMIN',1,'2026-04-16 18:28:30','2026-04-16 18:30:00');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-28 23:27:17
