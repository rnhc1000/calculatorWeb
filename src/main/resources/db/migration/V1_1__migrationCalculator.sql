CREATE TABLE IF NOT EXISTS `tb_roles` (

    `role_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(50) NOT NULL,
    `authority` varchar(50) NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `tb_operations` (

    `operation_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cost` varchar(50) NOT NULL,
    `operation` varchar(50) NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


