--Insert supplier for testing
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'spr1');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'spr TWO');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'tripple kill');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'QUAD CORE');
INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'FIVE');
INSERT INTO supplier_spr VALUES(sprid_seq.nextval, '6six6');
--INSERT INTO supplier_spr VALUES(sprid_seq.NEXTVAL, 'spr 777');

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

--Insert order and odrpdtlist_opl
INSERT INTO order_odr VALUES (odrid_seq.nextval, 165.01, 82.5, 0, 'client A', SYSDATE);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 1, 1, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 1, 2, 2);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 1, 3, 3);

INSERT INTO order_odr VALUES (odrid_seq.nextval, 380.63, 190.31, 1, 'client B', SYSDATE);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 2, 3, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 2, 2, 2);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 2, 1, 3);

INSERT INTO order_odr VALUES (odrid_seq.nextval, 70000255, 56000204, 0, 'client 3', SYSDATE);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 3, 5, 6);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 3, 9, 7);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 3, 12, 1);

INSERT INTO order_odr VALUES (odrid_seq.nextval, 10008397, 500419.85, 0, 'client 4', SYSDATE);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 4, 10, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 4, 9, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 4, 8, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 4, 7, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 4, 6, 1);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 4, 5, 1);

INSERT INTO order_odr VALUES (odrid_seq.nextval, 2, 1.98, 1, 'client FIVE', SYSDATE);
INSERT INTO odrpdtlist_opl VALUES (oplid_seq.nextval, 5, 5, 1);

