INSERT INTO `promo_types` (type, name) VALUES ('PROMOTION', 'Pourcentage'), ('PROMOTION', 'Valeur fixe'), ('OFFRE MARKETING', 'Pourcentage'), ('OFFRE MARKETING', 'Valeur fixe'), ('OFFRE MARKETING', 'X+1 gratuit'), ('OFFRE MARKETING', 'Le 2ème à X%'), ('OFFRE MARKETING', 'Le lot de X à Y€') ;

INSERT INTO `category` (name) VALUES ('Conserves'), ('Beauté'), ('Condiments');

INSERT INTO `article` (reference, libelle, marque, perished_date, cat_id, price, img)
    VALUES 
    ('THPN1', 'Thon', 'Petit Navire', TO_DATE('17/12/2022', 'DD/MM/YYYY'), 1, 1.99, 'https://static1.chronodrive.com/img/PM/Z/0/01/0Z_168901.jpg'),
    ('KTHZ3', 'Ketchup', 'Heinz', TO_DATE('03/04/2022', 'DD/MM/YYYY'), 3, 1.59, 'https://static1.chronodrive.com/img/PM/Z/0/30/0Z_325730.jpg'),
    ('CHNV2', 'Crème hydratante', 'Nivéa', NULL, 2, 4.99, 'https://static1.chronodrive.com/img/PM/Z/0/58/0Z_162758.jpg'),
    ('HVDA1', 'Haricots Verts', 'Daussy', TO_DATE('01/08/2023', 'DD/MM/YYYY'), 1, 2.09, 'https://static1.chronodrive.com/img/PM/Z/0/39/0Z_168939.jpg'),
    ('MYHZ3', 'Mayonnaise', 'Heinz', TO_DATE('30/11/2021', 'DD/MM/YYYY'), 3, 1.40, 'https://static1.chronodrive.com/img/PM/Z/0/90/0Z_330190.jpg'),
    ('DEAX2', 'Déodorant', 'Axe', NULL, 2, 2.49, 'https://static1.chronodrive.com/img/PM/Z/0/83/0Z_463483.jpg');

INSERT INTO `customer` (id, pseudo) VALUES (1, 'Gaston');