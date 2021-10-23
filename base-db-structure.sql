CREATE TABLE `promo_types` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`)
);

INSERT INTO `promo_types` (type) VALUES ('Pourcentage'), ('Valeur fixe');

CREATE TABLE `promos` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` INT NOT NULL,
    `x` FLOAT NOT NULL,
    `y` FLOAT,
    `start` DATE NOT NULL,
    `end` DATE NOT NULL,
    `customer_limit` INT,
    `code` VARCHAR(50),
	PRIMARY KEY (`id`),
    FOREIGN KEY (`type`) REFERENCES promo_types(id)
);

INSERT INTO `promos` ( type, x, y, start, end, customer_limit, code ) VALUES (
    1,
    42,
    42,
    NOW(),
    NOW(),
    5,
    'REDUC42'
);

CREATE TABLE Article(
    id INT PRIMARY KEY,
    name VARCHAR(30),
    img VARCHAR(30),
    cat_id int NOT NULL
    );

CREATE TABLE Cart(
    id INT PRIMARY KEY,
    id_article INT NOT NULL
);

ALTER TABLE Cart ADD FOREIGN KEY (id_article) REFERENCES Article(id);

CREATE TABLE Orders(
    id IDENTITY PRIMARY KEY,
    customer_id VARCHAR(30) NOT NULL,
    amount int not null,
    currentStatus varchar(255) 
    check (currentStatus in ('ORDERED', 'READY_TO_DELIVER', 'DELIVERED'))
);