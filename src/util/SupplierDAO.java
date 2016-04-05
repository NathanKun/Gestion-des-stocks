package src.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.GDS.Product;
import src.GDS.Supplier;

/**
 * Data access object for Supplier
 * @author FOTSING KENGNE Junior - HE Junyang
 *
 */
public class SupplierDAO {
	/**
	 * connection parameter between oracle URL and the DGB, LOGIN and PASS are
	 * constants
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
	 * class constructor
	 */
	public SupplierDAO() {
		// loading the pilot of DGB
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println(
					"impossible to load the BDD pilot, please make sur you have import thr .jar folder in the project");
		}
	}

	/**
	 * add product in the date base
	 * 
	 * @param supplier
	 *            contain the new supplier o add
	 * @return retour 1 if the adding is ok and 0 if not
	 */
	public int addSupplier(Supplier supplier) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO supplier_spr (spr_id, spr_name) VALUES(?,?)");
			ps.setLong(1, supplier.getId());
			ps.setString(2, supplier.getName());

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
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
		return retour;
	}

	/**
	 * allow to have the full list of the suppliers presents in the data base
	 * 
	 * @return the list of all the suppliers in the data base
	 */
	public ArrayList<Supplier> getSupplierList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Supplier> retour = new ArrayList<Supplier>();
		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT *FROM Supplier_spr");

			// request execution
			rs = ps.executeQuery();

			// we cross all the line of the results
			while (rs.next())
				retour.add(new Supplier(rs.getLong("spr_id"), rs.getString("spr_name")));
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing rs, PreparedStatement and connection
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
		return retour;
	}

	/**
	 * return a specific supplier by his identifiant
	 * 
	 * @param id
	 *            identifiant of the supplier
	 * @return the supplier
	 */
	public Supplier getSupplier(long id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Supplier retour = null;

		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT *FROM supplier_spr WHERE spr_id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				retour = new Supplier(rs.getLong("spr_id"), rs.getString("spr_name"));
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing of ResultSet, PreparedStatement and connection
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
		return retour;
	}

	/**
	 * delete a supplier in the data base suppliers list
	 * 
	 * @param id
	 *            contain the id of the supplier we want to delete
	 * @return the number of line delete
	 */
	public int deleteSupplier(long id) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM supplier_spr WHERE spr_id=?");
			ps.setLong(1, id);

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
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
		return retour;
	}

	/**
	 * generate a new id for the supplier list product
	 * 
	 * @return retour the new id of the sprpdtlist_spl
	 */
	public long idGenerator_Sprpdtlist_spl() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(spl_id) FROM sprpdtlist_spl");

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

	/**
	 * generate a new id for a supplier
	 * 
	 * @return retour the new id of the supplier
	 */
	public long idGenerator_Supplier_spr() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(spr_id) FROM supplier_spr");

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

	/**
	 * add a new product in the supplier's products list
	 * 
	 * @param sprId
	 *            the ID of the supplier to add
	 * @param pdtId
	 *  		  the ID of the product for add
	 * @param price
	 *            the price of the product
	 * @return the number of products add in the table
	 */
	//TODO untested
	public int addProduct(long sprId, long pdtId, Double price) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(
					"INSERT INTO sprpdtlist_spl (spl_id, spl_spr_id, spl_pdt_id, spl_pdt_price) VALUES(?,?,?,?)");
			ps.setLong(1, idGenerator_Sprpdtlist_spl());
			ps.setLong(2, sprId);
			ps.setLong(3, pdtId);
			ps.setDouble(4, price);

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
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
		return retour;
	}
	
	
	/**
	 * delete a product from the product list of a supplier
	 * @param sprId	id of supplier for delete a product
	 * @param pdtId	id of product for delete
	 * @return the number of line deleted
	 */
	//TODO untested
	public int deleteProduct(long sprId, long pdtId) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(
					"DELETE FROM sprpdtlist_spl "
					+ "WHERE spl_spr_id = ? AND spl_pdt_id = ?");
			ps.setLong(1, sprId);
			ps.setLong(2, pdtId);

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
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
		return retour;
	}
	
	
	
	/**
	 * get the list of product of a supplier from the db
	 * @param sprId	ID of the supplier for get the list
	 * @return	the list of product of the supplier
	 */
	public HashMap<Long, Double> getSupplierProductList(long sprId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Long, Double> retour = new HashMap<Long, Double>();

		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?");
			ps.setLong(1, sprId);
			rs = ps.executeQuery();
			while (rs.next())
				retour.put(rs.getLong("spl_pdt_id"), rs.getDouble("spl_pdt_price"));
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing of ResultSet, PreparedStatement and connection
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
		return retour;
	}
	
	/**
	 * main method, for testing
	 * @param args for main
	 */
	public static void main (String[] args){
		SupplierDAO dao = new SupplierDAO();
		System.out.println("id gen next spl_id = " + dao.idGenerator_Sprpdtlist_spl());
		System.out.println("id gen next spr_id = " + dao.idGenerator_Supplier_spr());
	}
}
