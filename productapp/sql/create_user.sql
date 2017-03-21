CREATE TABLE user_account(
id INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
PASSWORD VARCHAR(100) NOT NULL,
active TINYINT(1) NOT NULL DEFAULT 1,
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
modified_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user_account ( NAME,email,PASSWORD) VALUES ('Naresh', 'nareshkumarh@live.com', 'pass123');

SELECT * FROM user_account;