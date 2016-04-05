-- Delete old version tables 
DROP TABLE odrpdtlist_opl;
DROP TABLE sprpdtlist_spl;
DROP TABLE order_odr;
DROP TABLE product_pdt;
DROP TABLE supplier_spr;
DROP TABLE user_usr;

--Create new version tables
CREATE TABLE supplier_spr (
    spr_id NUMBER(10) CONSTRAINT PK_spr PRIMARY KEY,
    spr_name VARCHAR2(15)
);

CREATE TABLE product_pdt (
    pdt_id NUMBER(10) CONSTRAINT PK_pdt PRIMARY KEY,
    pdt_name VARCHAR2(15),
    pdt_stock NUMBER(3) NOT NULL,
    PDT_SPR NUMBER (10),
    pdt_price NUMBER (10,2),
    CONSTRAINT FK_pdt_sprid FOREIGN KEY (pdt_spr) REFERENCES supplier_spr (spr_id) ON DELETE CASCADE
);

CREATE TABLE user_usr (
    usr_id VARCHAR2(15) CONSTRAINT PK_usr PRIMARY KEY,
    usr_pw VARCHAR2(15),
    usr_name VARCHAR(15)
);

CREATE TABLE order_odr (
    odr_id NUMBER(10) CONSTRAINT PK_prd PRIMARY KEY,
    odr_price NUMBER(10, 2) NOT NULL,
    odr_pricedis NUMBER(10, 2),
    odr_isPaid NUMBER(1),
    odr_clientName VARCHAR2(30),
    odr_date DATE DEFAULT SYSDATE
);

CREATE TABLE odrpdtlist_opl (
    opl_id NUMBER(10) CONSTRAINT pk_opl PRIMARY KEY,
    opl_ord_id NUMBER(10) NOT NULL,
    opl_pdt_id NUMBER(10) NOT NULL,
    opl_pdt_quantity NUMBER(3) NOT NULL,
    CONSTRAINT fk_opl_ordid FOREIGN KEY (opl_ord_id) REFERENCES order_odr (odr_id) ON DELETE CASCADE,
    CONSTRAINT fk_opl_pdtid FOREIGN KEY (opl_pdt_id) REFERENCES product_pdt (pdt_id) ON DELETE CASCADE
);

CREATE TABLE sprpdtlist_spl (
    spl_id  NUMBER(10) CONSTRAINT PK_spl PRIMARY KEY,
    spl_spr_id NUMBER(10) NOT NULL,
    spl_pdt_id NUMBER(10) NOT NULL,
    spl_pdt_price NUMBER(10,2) NOT NULL,
    CONSTRAINT fk_spl_sprid FOREIGN KEY (spl_spr_id) REFERENCES supplier_spr (spr_id) ON DELETE CASCADE,
    CONSTRAINT fk_spl_pdtid FOREIGN KEY (spl_pdt_id) REFERENCES product_pdt (pdt_id) ON DELETE CASCADE
  
);


--Insert user for testing
INSERT INTO user_usr VALUES ('a', 'a', 'tester');

--Insert supplier for testing
INSERT INTO supplier_spr VALUES(1, 'spr1');
INSERT INTO supplier_spr VALUES(2, 'spr TWO');
INSERT INTO supplier_spr VALUES(16, 'spr sixteen');
INSERT INTO supplier_spr VALUES(3, 'tripple kill');
INSERT INTO supplier_spr VALUES(4, 'QUAD CORE');
INSERT INTO supplier_spr VALUES(5, 'FIVE');
INSERT INTO supplier_spr VALUES(6, '6six6');

--Insert product for testing
INSERT INTO product_pdt VALUES (1, 'pdt1', 11, 1, 111.11);
INSERT INTO product_pdt VALUES (2, 'pdt2', 22, 2, 22);
INSERT INTO product_pdt VALUES (5, 'pdt5', 55, 1, 5.5);
INSERT INTO product_pdt VALUES (3, 'cup', 11, 3, 5);
INSERT INTO product_pdt VALUES (4, 'pen', 22, 3, 2);
INSERT INTO product_pdt VALUES (6, 'screen', 7, 4, 288);
INSERT INTO product_pdt VALUES (7, 'Nexus6p', 6, 4, 599);
INSERT INTO product_pdt VALUES (8, 'Surface Pro 3', 4, 4, 899);
INSERT INTO product_pdt VALUES (9, 'wtf', 999, 6, 9999999);
INSERT INTO product_pdt VALUES (10, 'ten10', 10, 6, 6610);
INSERT INTO product_pdt VALUES (11, 'MOTO 360', 4, 4, 180);
INSERT INTO product_pdt VALUES (12, 'IE80', 3, 4, 250);
INSERT INTO product_pdt VALUES (13, 'SNSV', 2, 4, 1024);
INSERT INTO product_pdt VALUES (14, '14 14 14', 10, 5, 6610);
INSERT INTO product_pdt VALUES (15, '15 15 15', 10, 5, 6610);
INSERT INTO product_pdt VALUES (16, '16 16 16', 10, 5, 6610);
INSERT INTO product_pdt (pdt_id, pdt_name, pdt_stock) VALUES (66, 'pdt3 no spr', 0);

--Insert supplier's product list for testing
INSERT INTO sprpdtlist_spl VALUES (1, 1, 1, 100.10);
INSERT INTO sprpdtlist_spl VALUES (2, 1, 2, 222.10);
INSERT INTO sprpdtlist_spl VALUES (3, 2, 3, 33.10);
INSERT INTO sprpdtlist_spl VALUES (4, 2, 4, 4.10);
INSERT INTO sprpdtlist_spl VALUES (5, 2, 5, 55555.10);
INSERT INTO sprpdtlist_spl VALUES (6, 3, 6, 100.10);
INSERT INTO sprpdtlist_spl VALUES (7, 3, 7, 222.10);
INSERT INTO sprpdtlist_spl VALUES (8, 3, 8, 33.10);
INSERT INTO sprpdtlist_spl VALUES (9, 4, 9, 4.10);
INSERT INTO sprpdtlist_spl VALUES (10, 4, 10, 55555.10);

