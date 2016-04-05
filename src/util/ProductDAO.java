
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
	/*
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String LOGIN = "system";
	final static String PASS = "bdd";
	*/
	/*
	 * final static String URL="jdbc:oracle:thin:@localhost:1521:xe"; final
	 * static String LOGIN = "BDD6"; 
	 * final static String PASS = "BDD6";
	 */
	final static String URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
	final static String LOGIN = "c##nathankun";
	final static String PASS = "83783548jun";
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
			ps = con.prepareStatement("INSERT INTO product_pdt (pdt_id, pdt_name,pdt_stock) VALUES(?,?,?)");
			ps.setLong(1, product.getId());
			ps.setString(2, product.getName());
			ps.setInt(3, product.getStock());
			
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
			ps = con.prepareStatement("SELECT * FROM product_pdt LEFT JOIN supplier_spr ON pdt_spr = spr_id WHERE pdt_id = ? ");
			ps.setLong(1, id);
			rs=ps.executeQuery();
			if(rs.next())
				retour = new Product(rs.getLong("pdt_id"),
									rs.getString("pdt_name"),
									rs.getInt("pdt_stock"),
									rs.getDouble("pdt_price"),
									rs.getLong("pdt_spr"),
									rs.getString("spr_name"));
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
	public ArrayList<Product> getProductList(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Product> retour = new ArrayList<Product>();
		//connection to the data base
		try{
			con = DriverManager.getConnection(URL,LOGIN,PASS);
			ps = con.prepareStatement("SELECT * FROM product_pdt LEFT JOIN supplier_spr on spr_id = pdt_spr");
			
			//requet execution
			rs=ps.executeQuery();
			
			//we crosse all the line of the results
			while(rs.next())
				retour.add(new Product(rs.getLong("pdt_id"),
						rs.getString("pdt_name"),
						rs.getInt("pdt_stock"),
						rs.getDouble("pdt_price"),
						rs.getLong("pdt_spr"),
						rs.getString("spr_name")));
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
	

	/**
	 * generate a new id for the product_pdt
	 * 
	 * @return retour the new id of the product
	 */
	public long idGenerator() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(pdt_id) FROM product_pdt");

			// excecution of the requiere
			rs = ps.executeQuery();
			// recuperation of number
			rs.next();
			retour = rs.getLong(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connection
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return (retour + 1);
	}

	
	//TODO int updateProduct(Product pdt)
	
	public static void main(String[] args){
		ProductDAO dao = new ProductDAO();
		System.out.println("id gen next pdt_id = " + dao.idGenerator());
	}
}
