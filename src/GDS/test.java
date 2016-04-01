package src.GDS;
import oracle.jdbc.driver.OracleDriver;
import java.util.*;

import src.util.ProductDAO;
import src.util.SupplierDAO;
import src.util.userDAO;

import java.sql.*;
public class test {

	public static void main(String[] args) {
		//User user1 = new User("10","update2","update3");
		//userDAO userd1  = new userDAO();
		//int a= userd1.addUser(user1);
		//userd1.updateUser(user1);
		Product product = new Product(10,"voiture");
		//ProductDAO productd = new ProductDAO();
		//productd.addProduct(product);
		//Supplier supplier = new Supplier(3,"supplier3");
		SupplierDAO supplierd = new SupplierDAO();
		//supplierd.addSupplier(supplier);
		supplierd.addProduct(1, product, 600.6);
	}

}
