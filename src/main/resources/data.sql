INSERT INTO members (lastName, firstName, email, phoneNumber, age, gender, password, authority) VALUES
('admin', 'admin', 'admin@test.com', '09098765432', 28, '女性', '$2a$10$aDeMCNkr328.58TlTU8.eeq5xSo2AJffkH0JcZIM5qIWsD8.BacoG', 'ADMIN'),
('user', 'user', 'user@test.com', '08011112222', 30, '男性', '$2a$10$aDeMCNkr328.58TlTU8.eeq5xSo2AJffkH0JcZIM5qIWsD8.BacoG', 'USER');

INSERT INTO Product (productID, productName, productDescription, productImage, productPrice, productQuantity, productMaxQuantity, productReleaseDate, productCategoryID) VALUES
(1, '商品A', '商品Aの説明です。', 'https://via.placeholder.com/200', 1000, 1, 5, '2023-01-01', 1),
(2, '商品B', '商品Bの説明です。', 'https://via.placeholder.com/200', 2000, 1, 3, '2023-02-01', 2),
(3, '商品C', '商品Cの説明です。', 'https://via.placeholder.com/200', 3000, 1, 3, '2023-03-01', 3),
(4, '商品D', '商品Dの説明です。', 'https://via.placeholder.com/200', 4000, 1, 1, '2023-03-01', 3);

INSERT INTO ProductCategory (productCategoryID, productCategoryName) VALUES
(1, '電子機器'),
(2, '家具'),
(3, '書籍');
