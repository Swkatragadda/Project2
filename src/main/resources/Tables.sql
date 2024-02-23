
ALTER TABLE PRODUCT DROP constraint CONSTRAINT_18;
DROP TABLE SELLER IF EXISTS;
CREATE TABLE SELLER (
    sellerID int primary key,
    sellerName varchar(255) not null
);

DROP TABLE PRODUCT IF EXISTS;
CREATE TABLE PRODUCT (
productID varchar(255) PRIMARY KEY,
productName varchar(255) not null,
price NUMERIC (20,2) not null,
sellerID int references SELLER(sellerID),
sellerName varchar(255) not null
);




