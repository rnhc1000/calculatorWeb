CREATE TABLE IF NOT EXISTS `user` (

    `user_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` varchar(50) NOT NULL,
    `password` varchar(50) NOT NULL,
    `status` varchar(8) NOT NULL,
    `created_At` timestamp

)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

