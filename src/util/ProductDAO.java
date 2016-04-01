
package src.util;
import java.sql.*;
import java.util.*;

import src.GDS.Product;

//import projetDeveloppementLogiciel.Product;
/**
 * class ProductDAO
 * @author Junior FOTSING KENGNE - HE Junyang
 * @version 1.0
 */
public class ProductDAO {
	/**
	 * connection parameter between oracle URL and the DGB, LOGIN and PASS are constants
	 */
	final static String URL="jdbc:oraclethin:@localhost:1521:xe";
	final static String LOGIN = "sysem";
	final static String PASS = "system";
	/**
	 *class constructor 
	 */
	public ProductDAO() {
		//loading  the pilot of DGB
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("impossible to load the BDD pilot, please make sur you have import thr .jar folder in the project");
		}
	}
	/**
	 * add product in the date base
	 * @param product - product to add
	 * @return the number of line add in the product list
	 */
	public int addProduct(Product product){
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL,LOGIN,PASS);
			ps = con.prepareStatement("INSERT INTO product_pdt (pdt_id, pdt_name) VALUES(?,?)");
			ps.setLong(1, product.getId());
			ps.setString(2, product.getName());
			
			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try { 
				if(ps != null) ps.close();
			} catch (Exception ignore){
				System.out.println("closing problem");
			}
			try { 
				if(con != null) con.close();
			} catch (Exception ignore){
				System.out.println("closing problem");
			}
		}
		return retour;
	}
	/**
	 * return a specific product by his identifiant
	 * @param id identifiant of the product
	 * @return retourn - the product
	 */
	public Product getProduct (long id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Product retour = null;
		
		// connection to the data base  
		try{
			con = DriverManager.getConnection(URL,LOGIN,PASS);
			ps = con.prepareStatement("SELECT *FROM product_pdt WHERE pdt_id = ?");
			ps.setLong(1, id);
			rs=ps.executeQuery();
			if(rs.next())
				retour = new Product(rs.getLong("pdt_id"),
									rs.getString("pdt_name"));
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			//closing of ResultSet,  PreparedStatement and connection
			try { if(rs!= null) rs.close();
			} catch (Exception ignore){}
			try { if(ps!= null) ps.close();
			} catch (Exception ignore){}
			try { if(con != null) con.close();
			} catch (Exception ignore){}
		}
		return retour;
	}
	
	/**
	 * allow to have the full list of product presents in the data base
	 * @return the list of all the products in the data base
	 */
	public List<Product> getProductList(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Product> retour = new ArrayList<Product>();
		//connection to the data base
		try{
			con = DriverManager.getConnection(URL,LOGIN,PASS);
			ps = con.prepareStatement("SELECT *FROM product_pdt");
			
			//requet execution
			rs=ps.executeQuery();
			
			//we crosse all the line of the results
			while(rs.next())
				retour.add(new Product(rs.getLong("pdt_id"),
						rs.getString("pdt_name")));
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			//closing rs,  PreparedStatement and connection
			try { if(rs!= null) rs.close();
			} catch (Exception ignore){}
			try { if(ps!= null) ps.close();
			} catch (Exception ignore){}
			try { if(con != null) con.close();
			} catch (Exception ignore){}
		}
		return retour;
	}
	/**
	 * delete a product in the data base product list
	 * @param id contain the id of the product we want to delete
	 * @return the number of line delete
	 */
	public int deleteProduct(long id) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL,LOGIN,PASS);
			ps = con.prepareStatement("DELETE FROM product_pdt WHERE pdt_id=?");
			ps.setLong(1, id);
			
			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try { 
				if(ps != null) ps.close();
			} catch (Exception ignore){
				System.out.println("closing problem");
			}
			try { 
				if(con != null) con.close();
			} catch (Exception ignore){
				System.out.println("closing problem");
			}
		}
		return retour;
	}
}
