CREATE TABLE supplier_spr (
    spr_id NUMBER(10) CONSTRAINT PK_spl PRIMARY KEY,
    spr_name VARCHAR2(15)
);

CREATE TABLE product_pdt (
    pdt_id NUMBER(10) CONSTRAINT PK_pdt PRIMARY KEY,
    pdt_name VARCHAR2(15),
    pdt_stock NUMBER(3) NOT NULL
);

CREATE TABLE uesr_usr (
    usr_id VARCHAR2(15) CONSTRAINT PK_usr PRIMARY KEY,
    usr_pw VARCHAR2(15),
    usr_name VARCHAR(15)
);

CREATE TABLE order_odr (
    odr_id NUMBER(10) CONSTRAINT PK_prd PRIMARY KEY,
    odr_price NUMBER(10, 2) NOT NULL,
    odr_priceDis NUMBER(10, 2),
    odr_state VARCHAR2(15),
    odr_clientName VARCHAR2(30),
    odr_date DATE DEFAULT SYSDATE
);

CREATE TABLE odrpdtlist_opl (
    odl_id NUMBER(10) CONSTRAINT PK_opl PRIMARY KEY,
    odl_odr_id NUMBER(10) NOT NULL,
    odl_pdt_id NUMBER(10) NOT NULL,
    CONSTRAINT FK_opl_ordid REFERENCES order_ord (ord_id) ON DELETE CASCADE,
    CONSTRAINT FK_opl_pdtid REFERENCES pruduct_pdt (pdt_id) ON DELETE CASCADE
);

CREATE TABLE sprpdtlist_spl (
    spl_id  NUMBER(10) CONSTRAINT PK_spl PRIMARY KEY,
    spl_spr_id NUMBER(10) NOT NULL,
    spl_pdt_id NUMBER(10) NOT NULL,
    CONSTRAINT FK_spl_sprid REFERENCES supplier (spr_id) ON DELETE CASCADE,
    CONSTRAINT FK_spl_pdtid REFERENCES product (pdt_id) ON DELETE CASCADE
);