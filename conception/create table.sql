-- Suppression de toutes les tables existantes
SET SERVEROUTPUT ON
DECLARE
 CURSOR cur_tab_names IS SELECT table_name 
 FROM user_tables;
 sql_stmt VARCHAR2(100);
BEGIN
 FOR v_tab_name IN cur_tab_names LOOP
  sql_stmt := 'DROP TABLE ' || sql_stmt || v_tab_name.table_name || ' CASCADE CONSTRAINTS';
  EXECUTE IMMEDIATE (sql_stmt);
  DBMS_OUTPUT.PUT_LINE(v_tab_name.table_name || ' supprime');
  sql_stmt := NULL;
 END LOOP;
END;
/

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
    odr_priceDis NUMBER(10, 2),
    odr_state VARCHAR2(15),
    odr_clientName VARCHAR2(30),
    odr_date DATE DEFAULT SYSDATE
);



CREATE TABLE odrpdtlist_opl (
    ODL_ID NUMBER(10) CONSTRAINT PK_OPL PRIMARY KEY,
    odl_ord_id NUMBER(10) NOT NULL,
    odl_pdt_id NUMBER(10) NOT NULL,
    CONSTRAINT FK_OPL_ORDID FOREIGN KEY (odl_ord_id) REFERENCES ORDER_Odr (Odr_ID) ON DELETE CASCADE,
    CONSTRAINT FK_opl_pdtid FOREIGN KEY (odl_pdt_id) REFERENCES product_pdt (pdt_id) ON DELETE CASCADE
);

CREATE TABLE sprpdtlist_spl (
    spl_id  NUMBER(10) CONSTRAINT PK_spl PRIMARY KEY,
    spl_spr_id NUMBER(10) NOT NULL,
    spl_pdt_id NUMBER(10) NOT NULL,
    spl_pdt_price NUMBER(10,2) NOT NULL,
    CONSTRAINT FK_SPL_SPRID FOREIGN KEY (SPL_SPR_ID) REFERENCES SUPPLIER_SPR (SPR_ID) ON DELETE CASCADE,
    CONSTRAINT FK_SPL_PDTID FOREIGN KEY (SPL_PDT_ID) REFERENCES PRODUCT_pdt (PDT_ID) ON DELETE CASCADE
);
INSERT INTO user_usr VALUES ('a', 'a', 'tester');
INSERT INTO supplier_spr VALUES(1, 'spr1');
INSERT INTO supplier_spr VALUES(2, 'spr TWO');
INSERT INTO supplier_spr VALUES(16, 'spr sixteen');
INSERT INTO product_pdt VALUES (1, 'pdt1', 11, 1, 111.11);
INSERT INTO product_pdt VALUES (2, 'pdt2', 22, 2, 22);
INSERT INTO PRODUCT_PDT VALUES (5, 'pdt5', 55, 1, 5.5);
INSERT INTO product_pdt (pdt_id, pdt_name, pdt_stock) VALUES (3, 'pdt3 no spr', 0);