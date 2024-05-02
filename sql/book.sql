CREATE TABLE book (
                      PRIMARY KEY (순번),
                      순번 INT,
                      구분 VARCHAR(20),
                      상품명 VARCHAR(255),
                      ItemId VARCHAR(20),
                      ISBN13 VARCHAR(20),
                      부가기호 VARCHAR(20),
                      저자 VARCHAR(100),
                      출판사 VARCHAR(100),
                      출간일 DATE,
                      정가 VARCHAR(100),
                      판매가 VARCHAR(100),
                      대표분류_대분류명 VARCHAR(100),
                      대표분류_영문명 VARCHAR(100)
);


LOAD DATA INFILE '/var/lib/mysql-files//Aladin_Weekly_Best_Sellers_Domestic_Books_20240502.csv'
INTO TABLE book
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

SHOW VARIABLES LIKE 'secure_file_priv';