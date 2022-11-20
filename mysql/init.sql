CREATE TABLE `card` (
                                  `id` int(30) NOT NULL,
                                  `cardholder` varchar(255) NOT NULL,
                                  `cvv` varchar(10) NOT NULL,
                                  `expiry_month` varchar(255) NOT NULL,
                                  `expiry_year` varchar(255) NOT NULL,
                                  `number` varchar(255) NOT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;