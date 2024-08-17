CREATE TABLE IF NOT EXISTS `users` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` varchar(50) NOT NULL,
    `password` varchar(50) NOT NULL,
    `status` varchar(8) NOT NULL,
    `created_At` timestamp

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO  tb_users (user_id, username, password, status) values (1L, 'ricardo@ferreiras.dev.br', 'password' ,'active');
