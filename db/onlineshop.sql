--------------------------------------------------------------- MysqlDatabase: `onlineshop`-------------------------------------------------------------SET sql_mode = '';----  Table structure for table `cart`--DROP TABLE IF EXISTS `cart`;CREATE TABLE `cart` (	`cart_id` INT (11) NOT NULL AUTO_INCREMENT,	`user_id` INT (11) NOT NULL,	`product_id` INT (11) NOT NULL,	`price` DECIMAL (15, 4) NOT NULL DEFAULT 0.0000,	`quantity` INT (5) NOT NULL,	PRIMARY KEY (`cart_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;ALTER TABLE `cart` ADD CONSTRAINT `fk_cart_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE;ALTER TABLE `cart` ADD CONSTRAINT `fk_cart_product_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON UPDATE CASCADE;INSERT INTO `cart` (`cart_id`, `user_id`, `product_id`, `price`, `quantity`) VALUES ('1', '1', '1', '50.00', '3');SELECT * FROM `cart` WHERE (`cart_id`='1');UPDATE `cart` SET `price`='55.0000' WHERE (`cart_id`='1');DELETE FROM `cart` WHERE (`cart_id`='1');---------------------------------------------------------------  Table structure for table `category`--DROP TABLE IF EXISTS `category`;CREATE TABLE `category` (	`category_id` INT (11) NOT NULL AUTO_INCREMENT,	`parent_id` INT (11) NOT NULL DEFAULT 0,	`name` VARCHAR (255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`sort_order` INT (3) NOT NULL DEFAULT 0,	`status` TINYINT (1) NOT NULL DEFAULT 1,	PRIMARY KEY (`category_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;INSERT INTO `category` (`category_id`, `name`, `sort_order`, `status`) VALUES ('1', 'Laptops', '1', '1');SELECT * FROM `category` WHERE (`category_id`='1');UPDATE `category` SET `sort_order`='2' WHERE (`category_id`='1');DELETE FROM `category` WHERE (`category_id`='1');---------------------------------------------------------------  Table structure for table `manufacturer`--DROP TABLE IF EXISTS `manufacturer`;CREATE TABLE `manufacturer` (	`manufacturer_id` INT (11) NOT NULL AUTO_INCREMENT,	`name` VARCHAR (255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`sort_order` INT (3) NOT NULL DEFAULT 0,	`status` TINYINT (1) NOT NULL DEFAULT 1,	PRIMARY KEY (`manufacturer_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;INSERT INTO `manufacturer` (`manufacturer_id`, `name`, `sort_order`, `status`) VALUES ('1', 'Apple', '1', '1');SELECT * FROM `manufacturer` WHERE (`manufacturer_id`='1');UPDATE `manufacturer` SET `sort_order`='2' WHERE (`manufacturer_id`='1');DELETE FROM `manufacturer` WHERE (`manufacturer_id`='1');---------------------------------------------------------------  Table structure for table `order`--DROP TABLE IF EXISTS `order`;CREATE TABLE `order` (	`order_id` INT (11) NOT NULL AUTO_INCREMENT,	`user_id` INT (11) NOT NULL,	`amount` DECIMAL (15, 4) NOT NULL,	`payment_id` INT (11) NOT NULL,	`date_added` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',	`order_status_id` INT (11) NOT NULL DEFAULT 0,	`date_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',	`tracking_number` VARCHAR (255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',	PRIMARY KEY (`order_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;ALTER TABLE `order` ADD CONSTRAINT `fk_order_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE;ALTER TABLE `order` ADD CONSTRAINT `fk_order_payment_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON UPDATE CASCADE;ALTER TABLE `order` ADD CONSTRAINT `fk_order_order_status_1` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`order_status_id`) ON UPDATE CASCADE;INSERT INTO `order` (`order_id`, `user_id`, `amount`, `payment_id`, `date_added`, `date_modified`) VALUES ('1', '1', '55.00', '1', '2019-04-15 17:10:12', '2019-04-15 17:11:14');SELECT * FROM `order` WHERE (`order_id`='1');UPDATE `order` SET `order_status_id`='1' WHERE (`order_id`='1');DELETE FROM `order` WHERE (`order_id`='1');---------------------------------------------------------------  Table structure for table `order_product`--DROP TABLE IF EXISTS `order_product`;CREATE TABLE `order_product` (	`order_product_id` INT (11) NOT NULL AUTO_INCREMENT,	`order_id` INT (11) NOT NULL,	`product_id` INT (11) NOT NULL,	`price` DECIMAL (15, 4) NOT NULL,	`quantity` INT (5) NOT NULL,	PRIMARY KEY (`order_product_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;ALTER TABLE `order_product` ADD CONSTRAINT `fk_order_product_order_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON UPDATE CASCADE;ALTER TABLE `order_product` ADD CONSTRAINT `fk_order_product_product_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON UPDATE CASCADE;INSERT INTO `order_product` (`order_product_id`, `order_id`, `product_id`, `quantity`, `price`) VALUES ('20', '1', '1', '3', '165.00');SELECT * FROM `order_product` WHERE (`order_product_id`='20');UPDATE `order_product` SET `price`='168.0000' WHERE (`order_product_id`='20');DELETE FROM `order_product` WHERE (`order_product_id`='20');---------------------------------------------------------------  Table structure for table `order_status`--DROP TABLE IF EXISTS `order_status`;CREATE TABLE `order_status` (	`order_status_id` INT (11) NOT NULL AUTO_INCREMENT,	`name` VARCHAR (255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	PRIMARY KEY (`order_status_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;INSERT INTO `order_status` (`order_status_id`, `name`) VALUES ('10', 'Cancel');SELECT * FROM `order_status` WHERE (`order_status_id`='10');UPDATE `order_status` SET `name`='New' WHERE (`order_status_id`='10');DELETE FROM `order_status` WHERE (`order_status_id`='10');---------------------------------------------------------------  Table structure for table `page`--DROP TABLE IF EXISTS `page`;CREATE TABLE `page` (	`page_id` INT (11) NOT NULL AUTO_INCREMENT,	`title` VARCHAR (64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`description` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',	`sort_order` INT (3) NOT NULL DEFAULT 0,	`status` TINYINT (1) NOT NULL DEFAULT 1,	PRIMARY KEY (`page_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;INSERT INTO `page` (`page_id`, `title`, `description`) VALUES ('1', 'Home', '<title>Welcome!</title>')SELECT * FROM `page` WHERE (`page_id`='1');UPDATE `page` SET `description`='<head>\r<title>Welcome!</title>\r</head>' WHERE (`page_id`='1');DELETE FROM `page` WHERE (`page_id`='1')---------------------------------------------------------------  Table structure for table `payment`--DROP TABLE IF EXISTS `payment`;CREATE TABLE `payment` (	`payment_id` INT (11) NOT NULL AUTO_INCREMENT,	`user_id` INT (11) NOT NULL,	`firstname` VARCHAR (32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`lastname` VARCHAR (32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`postcode` INT (10) NOT NULL,	`address` VARCHAR (128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`city` VARCHAR (128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`country` VARCHAR (128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`region` VARCHAR (128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`date_added` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',	PRIMARY KEY (`payment_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;ALTER TABLE `payment` ADD CONSTRAINT `fk_payment_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE;INSERT INTO `payment` (`payment_id`, `user_id`, `firstname`, `lastname`, `postcode`, `address`, `city`, `country`, `region`, `date_added`) VALUES ('2', '1', 'Ivan', 'Ivanov', '246000', 'Suhogo 1', 'Gomel', 'Belarus', 'Gomelskaja', '2019-04-15 17:21:38');SELECT * FROM `payment` WHERE (`payment_id`='2');UPDATE `payment` SET `address`='Suhogo 10' WHERE (`payment_id`='2');DELETE FROM `payment` WHERE (`payment_id`='2');---------------------------------------------------------------  Table structure for table `product`--DROP TABLE IF EXISTS `product`;CREATE TABLE `product` (	`product_id` INT (11) NOT NULL AUTO_INCREMENT,	`name` VARCHAR (255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	`description` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',	`manufacturer_id` INT (11) NOT NULL,	`category_id` INT (11) NOT NULL,	`price` DECIMAL (15, 4) NOT NULL DEFAULT 0.0000,	`quantity` INT (4) NOT NULL DEFAULT 0,	`image` VARCHAR (255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,	`date_added` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',	`sort_order` INT (11) NOT NULL DEFAULT 0,	`status` TINYINT (1) NOT NULL DEFAULT 1,	PRIMARY KEY (`product_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;ALTER TABLE `product` ADD CONSTRAINT `fk_product_manufacturer_1` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`manufacturer_id`) ON UPDATE CASCADE;ALTER TABLE `product` ADD CONSTRAINT `fk_product_category_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON UPDATE CASCADE;INSERT INTO `product` (`product_id`, `name`, `description`, `manufacturer_id`, `category_id`, `price`, `quantity`, `date_added`, `status`) VALUES ('1', 'iPhome', 'Apple iPhone 10s', '1', '2', '1000.00', '100', '2019-04-15 17:23:56', '1');SELECT * FROM `product` WHERE (`product_id`='1');UPDATE `product` SET `image`='catalog/phone/iphone_10_s.jpg' WHERE (`product_id`='1');DELETE FROM `product` WHERE (`product_id`='1');---------------------------------------------------------------  Table structure for table `user`--DROP TABLE IF EXISTS `user`;CREATE TABLE `user` (  `user_id`       INT(11)    NOT NULL AUTO_INCREMENT,  `user_group_id` INT(11)    NOT NULL,  `username`      VARCHAR(20) CHARACTER SET utf8  COLLATE utf8_general_ci    NOT NULL,  `password`      VARCHAR(40) CHARACTER SET utf8  COLLATE utf8_general_ci    NOT NULL,  `email`         VARCHAR(96) CHARACTER SET utf8  COLLATE utf8_general_ci    NOT NULL,  `status`        TINYINT(1) NOT NULL DEFAULT 1,  `date_added`    datetime   NOT NULL DEFAULT '0000-00-00 00:00:00',	PRIMARY KEY (`user_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;ALTER TABLE `user` ADD CONSTRAINT `fk_user_user_group_1` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`user_group_id`) ON UPDATE CASCADE;INSERT INTO `user` (`user_id`, `user_group_id`, `username`, `password`, `email`, `status`, `date_added`) VALUES ('1', '1', 'admin', '4e809e0440f6a314319e3f8be39f3eed9d47ae10', 'admin@online.shop', '1', '2019-04-15 11:55:06');SELECT * FROM `user` WHERE (`user_id`='1');UPDATE `user` SET `username`='Admin' WHERE (`user_id`='1');DELETE FROM `user` WHERE (`user_id`='1');---------------------------------------------------------------  Table structure for table `user_group`--DROP TABLE IF EXISTS `user_group`;CREATE TABLE `user_group` (	`user_group_id` INT (11) NOT NULL AUTO_INCREMENT,	`name` VARCHAR (64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,	PRIMARY KEY (`user_group_id`)) ENGINE = MyISAM DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;INSERT INTO `user_group` (`user_group_id`, `name`) VALUES ('1', 'Administrator');SELECT * FROM `user_group` WHERE (`user_group_id`='1');UPDATE `user_group` SET `user_group_id`='2', `name`='Admin' WHERE (`user_group_id`='1');DELETE FROM `user_group` WHERE (`user_group_id`='2');