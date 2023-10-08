CREATE TABLE IF NOT EXISTS members (
  	memberID INT PRIMARY KEY AUTO_INCREMENT,
    lastName VARCHAR(255) ,
    firstName VARCHAR(255) ,
    email VARCHAR(255) ,
    phoneNumber VARCHAR(20),
    age INT,
    gender VARCHAR(10),
    password VARCHAR(255),
    created_at VARCHAR(50),
  	update_at VARCHAR(50)
);