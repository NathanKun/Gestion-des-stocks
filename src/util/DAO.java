package src.util;
/*
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.GDS.Product;


public class DAO {

	final static String URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
	final static String LOGIN = "c##nathankun";
	final static String PASS = "83783548jun";

	/*
	 * final static String URL = "jdbc:oracle:thin:@localhost:1521:xe"; final
	 * static String LOGIN = "BDD6"; final static String PASS = "BDD6";
	 */

	/*public DAO() {
		// Load database driver
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("Loading failed. Don't forget to import the .jar file to the project.");
		}
	}


	public Product pdtGet(long pdt_id) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Product pdt = null;

		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			
			ps = con.prepareStatement("SELECT * FROM product_pdt "
					+ "INNER JOIN sprpdtlist_spl ON pdt_id = spl_pdt_id "
					+ "INNER JOIN supplier_spr ON spr_id = spl_spr_id"
					+ "WHERE pdt_id = ?");
			ps.setLong(1, pdt_id);
			
			rs = ps.executeQuery();

			pdt = new Product(
						rs.getLong("pdt_id"), 
						rs.getString("pdt_name"), 
						rs.getInt("pdt_stock"), 
						rs.getDouble("spl_pdt_price"), 
						rs.getLong("spr_id"), 
						rs.getString("spr_name"));
			
				
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return pdt;
	}

	
	public ArrayList<Product> pdtGetList() {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();

		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			
			ps = con.prepareStatement("SELECT * FROM product_pdt "
					+ "INNER JOIN sprpdtlist_spl ON pdt_id = spl_pdt_id "
					+ "INNER JOIN supplier_spr ON spr_id = spl_spr_id");
			
			rs = ps.executeQuery();

			while (rs.next()){
				list.add(new Product(
						rs.getLong("pdt_id"), 
						rs.getString("pdt_name"), 
						rs.getInt("pdt_stock"), 
						rs.getDouble("spl_pdt_price"), 
						rs.getLong("spr_id"), 
						rs.getString("spr_name")));
			}
				
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return list;

	}
	
	public int pdtAdd(Product pdt) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		
		try {
			
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO product_pdt (pdt_id, pdt_name, pdt_stock)"
					+ " VALUES (?, ?, ?)");
			ps.setLong(1, pdt.getId());
			ps.setString(2, pdt.getName());
			ps.setInt(3, pdt.getStock());

			retour = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return retour;

	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
*/