DROP TABLE odrpdtlist_opl;
DROP TABLE sprpdtlist_spl;
DROP TABLE order_odr;
DROP TABLE product_pdt;
DROP TABLE supplier_spr;
DROP TABLE user_usr;
DROP SEQUENCE sprid_seq;
DROP SEQUENCE pdtid_seq;
DROP SEQUENCE usrid_seq;
DROP SEQUENCE odrid_seq;
DROP SEQUENCE oplid_seq;
DROP SEQUENCE splid_seq;
CREATE SEQUENCE sprid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE pdtid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE usrid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE odrid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE oplid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE splid_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE TABLE supplier_spr (
    spr_id NUMBER CONSTRAINT PK_spr PRIMARY KEY,
    spr_name VARCHAR2(50)
);
CREATE TABLE product_pdt (
    pdt_id NUMBER CONSTRAINT PK_pdt PRIMARY KEY,
    pdt_name VARCHAR2(50),
    pdt_stock NUMBER(3) NOT NULL,
    PDT_SPR NUMBER (10),
    pdt_price NUMBER (10,2),
    CONSTRAINT FK_pdt_sprid FOREIGN KEY (pdt_spr) REFERENCES supplier_spr (spr_id) ON DELETE CASCADE
);
CREATE TABLE user_usr (
    usr_id VARCHAR2(50) CONSTRAINT PK_usr PRIMARY KEY,
    usr_pw VARCHAR2(50),
    usr_name VARCHAR(50)
);
CREATE TABLE order_odr (
    odr_id NUMBER CONSTRAINT PK_prd PRIMARY KEY,
    odr_price NUMBER(10, 2) NOT NULL,
    odr_pricedis NUMBER(10, 2),
    odr_isPaid NUMBER(1),
    odr_clientName VARCHAR2(50),
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