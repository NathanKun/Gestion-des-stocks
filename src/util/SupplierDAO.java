package src.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.GDS.Product;
import src.GDS.Supplier;

//import projetDeveloppementLogiciel.Supplier;

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
	 * @return retour- 1 if the adding is ok and 0 if not
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
	public List<Supplier> getSupplierList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Supplier> retour = new ArrayList<Supplier>();
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
	 * @return retour the new id of the
	 */
	public long idGenerator() {
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
	 * add a new product in the supplier's products list
	 * 
	 * @param id
	 *            the id of the product to add
	 * @param price
	 *            the price of the product
	 * @return the number of products add in the table
	 */
	public int addProduct(long id, Product product, Double price) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(
					"INSERT INTO sprpdtlist_spl (spl_id, spl_spr_id, spl_pdt_id, spl_pdt_price) VALUES(?,?,?,?)");
			ps.setLong(1, idGenerator());
			ps.setLong(2, id);
			ps.setLong(3, product.getId());
			ps.setDouble(4, product.getPrice());

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
}
