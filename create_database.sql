CREATE DATABASE captcha;

USE captcha;

CREATE TABLE captcha (
    id INT PRIMARY KEY AUTO_INCREMENT,
    captcha_text VARCHAR(255) NOT NULL,
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
