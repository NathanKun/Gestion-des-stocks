-- Delete old version tables 
DROP TABLE odrpdtlist_opl;
DROP TABLE sprpdtlist_spl;
DROP TABLE order_odr;
DROP TABLE product_pdt;
DROP TABLE supplier_spr;
DROP TABLE user_usr;

--Delete old version sequences
DROP SEQUENCE sprid_seq;
DROP SEQUENCE pdtid_seq;
DROP SEQUENCE usrid_seq;
DROP SEQUENCE odrid_seq;
DROP SEQUENCE oplid_seq;
DROP SEQUENCE splid_seq;

--Create sequences
CREATE SEQUENCE sprid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE pdtid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE usrid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE odrid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE oplid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE splid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;


--Create new version tables
CREATE TABLE supplier_spr (
    spr_id NUMBER CONSTRAINT PK_spr PRIMARY KEY,
    spr_name VARCHAR2(15)
);

CREATE TABLE product_pdt (
    pdt_id NUMBER CONSTRAINT PK_pdt PRIMARY KEY,
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
    odr_id NUMBER CONSTRAINT PK_prd PRIMARY KEY,
    odr_price NUMBER(10, 2) NOT NULL,
    odr_pricedis NUMBER(10, 2),
    odr_isPaid NUMBER(1),
    odr_clientName VARCHAR2(30),
    odr_date DATE DEFAULT SYSDATE
);

CREATE TABLE odrpdtlist_opl (
    opl_id NUMBER CONSTRAINT pk_opl PRIMARY KEY,
    opl_odr_id NUMBER NOT NULL,
    opl_pdt_id NUMBER NOT NULL,
    opl_pdt_quantity NUMBER(3) NOT NULL,
    CONSTRAINT fk_opl_odrid FOREIGN KEY (opl_odr_id) REFERENCES order_odr (odr_id) ON DELETE CASCADE,
    CONSTRAINT fk_opl_pdtid FOREIGN KEY (opl_pdt_id) REFERENCES product_pdt (pdt_id) ON DELETE CASCADE
);

CREATE TABLE sprpdtlist_spl (
    spl_id NUMBER CONSTRAINT PK_spl PRIMARY KEY,
    spl_spr_id NUMBER NOT NULL,
    spl_pdt_id NUMBER NOT NULL,
    spl_pdt_price NUMBER(10,2) NOT NULL,
    CONSTRAINT fk_spl_sprid FOREIGN KEY (spl_spr_id) REFERENCES supplier_spr (spr_id) ON DELETE CASCADE,
    CONSTRAINT fk_spl_pdtid FOREIGN KEY (spl_pdt_id) REFERENCES product_pdt (pdt_id) ON DELETE CASCADE
  
);


--Insert user for testing
INSERT INTO user_usr VALUES ('a', 'a', 'tester');

--Insert supplier for testing
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'spr1');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'spr TWO');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'spr sixteen');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'tripple kill');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'QUAD CORE');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'FIVE');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, '6six6');

--Insert product for testing
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'pdt1', 11, 1, 111.11);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'pdt2', 22, 2, 22);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'pdt3', 33, 1, 3.3);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'cup', 11, 3, 5);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'pen', 22, 3, 2);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'screen', 7, 4, 288);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'Nexus6p', 6, 4, 599);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'Surface Pro 3', 4, 4, 899);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'wtf', 999, 6, 9999999);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'ten10', 10, 6, 6610);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'MOTO 360', 4, 4, 180);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'IE80', 3, 4, 250);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, 'SNSV', 2, 4, 1024);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, '14 14 14', 10, 5, 6610);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, '15 15 15', 10, 5, 6610);
INSERT INTO product_pdt VALUES (pdtid_seq.NEXTVAL, '16 16 16', 10, 5, 6610);
INSERT INTO product_pdt (pdt_id, pdt_name, pdt_stock) VALUES (pdtid_seq.NEXTVAL, 'pdt3 no spr', 0);

--Insert supplier's product list for testing
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 1, 1, 100.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 1, 2, 222.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 1, 3, 2.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 2, 4, 4.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 2, 5, 55555.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 3, 6, 100.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 3, 7, 222.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 3, 8, 33.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 3, 9, 333.3);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 4, 10, 4.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 4, 11, 55555.10);

INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 1, 5, 1.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 3, 5, 22.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.nextval, 4, 5, 444.10);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.NEXTVAL, 5, 5, 0.55);
INSERT INTO sprpdtlist_spl VALUES (splid_seq.nextval, 6, 5, 66666666);

