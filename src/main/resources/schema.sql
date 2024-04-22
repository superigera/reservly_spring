CREATE TABLE IF NOT EXISTS members (
  	memberID INT PRIMARY KEY AUTO_INCREMENT,
    lastName VARCHAR(255) ,
    firstName VARCHAR(255) ,
    email VARCHAR(255) ,
    phoneNumber VARCHAR(20),
    age INT,
    gender VARCHAR(10),
    password VARCHAR(255),
    authority VARCHAR(100),
    created_at VARCHAR(50),
  	update_at VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Product (
    productID INT PRIMARY KEY AUTO_INCREMENT,
    productName VARCHAR(255),
    productDescription TEXT,
    productImage VARCHAR(255),
    productPrice INT,
    productQuantity INT,
    productMaxQuantity INT,
    productReleaseDate VARCHAR(50),
    productCategoryID VARCHAR(50),
    created_at VARCHAR(50),
    update_at VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ProductCategory (
    productCategoryID INT PRIMARY KEY AUTO_INCREMENT,
    productCategoryName VARCHAR(50),
    created_at VARCHAR(50),
    update_at VARCHAR(50)
);
