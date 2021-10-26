DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS PROMOS;
DROP TABLE IF EXISTS CATEGORY;
DROP TABLE IF EXISTS PROMO_TYPES;
DROP TABLE IF EXISTS USED_PROMO;
DROP TABLE IF EXISTS CUSTOMER;
DROP TABLE IF EXISTS CART;
DROP TABLE IF EXISTS CARTELEMENT;


CREATE TABLE IF NOT EXISTS `promo_types` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(50) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`)
);

INSERT INTO `promo_types` (type, name) VALUES ('PROMOTION', 'Pourcentage'), ('PROMOTION', 'Valeur fixe'), ('OFFRE MARKETING', 'Pourcentage'), ('OFFRE MARKETING', 'Valeur fixe'), ('OFFRE MARKETING', 'X+1 gratuit'), ('OFFRE MARKETING', 'Le 2ème à X%'), ('OFFRE MARKETING', 'Le lot de X à Y€') ;

CREATE TABLE IF NOT EXISTS `category`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30),
    PRIMARY KEY(`id`)
);

INSERT INTO `category` (name) VALUES ('Conserves'), ('Beauté'), ('Condiments');

CREATE TABLE IF NOT EXISTS `promos` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` INT NOT NULL,
    `x` FLOAT NOT NULL,
    `y` FLOAT,
    `start` DATE NOT NULL,
    `end` DATE NOT NULL,
    `customer_limit` INT,
    `code` VARCHAR(20),
    `on_cart` BOOLEAN,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`type` ) REFERENCES promo_types(`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `article`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `reference` VARCHAR(30) NOT NULL,
    `libelle` VARCHAR(30) NOT NULL,
    `marque` VARCHAR(30),
    `perished_date` DATE,
    `cat_id` int NOT NULL,
    `price` decimal NOT NULL,
    `promo` int,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`cat_id`) REFERENCES category(`id`) ON DELETE SET NULL,
    FOREIGN KEY (`promo`) REFERENCES promos(`id`) ON DELETE SET NULL
);

INSERT INTO `article` (reference, libelle, marque, perished_date, cat_id, price)
    VALUES ('THPN1', 'Thon', 'Petit Navire', TO_DATE('17/12/2022', 'DD/MM/YYYY'), 1, 1.99),
    ('KTHZ3', 'Ketchup', 'Heinz', TO_DATE('03/04/2022', 'DD/MM/YYYY'), 3, 1.59),
    ('CHNV2', 'Crème hydratante', 'Nivéa', NULL, 2, 4.99),
    ('HVDA1', 'Haricots Verts', 'Daussy', TO_DATE('01/08/2023', 'DD/MM/YYYY'), 1, 2.09),
    ('MYHZ3', 'Mayonnaise', 'Heinz', TO_DATE('30/11/2021', 'DD/MM/YYYY'), 3, 1.40),
    ('DEAX2', 'Déodorant', 'Axe', NULL, 2, 2.49);

CREATE TABLE IF NOT EXISTS `customer`(
    `id`     INT NOT NULL AUTO_INCREMENT,
    `pseudo` VARCHAR(25)
);

INSERT INTO `customer` (id, pseudo) VALUES (1, 'Gaston');

CREATE TABLE IF NOT EXISTS `used_promo`
(
    `customer_id` INT NOT NULL,
    `promo_id`    INT NOT NULL,
    FOREIGN KEY (`customer_id`) REFERENCES customer (`id`) ON DELETE CASCADE,
    FOREIGN KEY  (`promo_id`)  REFERENCES Promos (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `cart`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `customerid` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customerid`) REFERENCES customer(`id`) ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS `cartelement`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `quantite` INT NOT NULL,
    `article_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`article_id`) REFERENCES article(`id`) ON DELETE CASCADE
);