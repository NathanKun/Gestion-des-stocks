package src.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import src.gds.Order;
import src.gds.OrderProduct;
import src.gds.Product;
import src.gds.Supplier;
import src.gds.User;

/**
 * abstract class for DAO
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
abstract class DAO {
	/**
	 * connection parameter between oracle URL and the DGB, LOGIN and PASS are
	 * constants
	 */

	/*
	 * final static String URL = "jdbc:oracle:thin:@localhost:1521:xe"; final
	 * static String LOGIN = "system"; final static String PASS = "bdd";
	 */

	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String LOGIN = "BDD6";
	final static String PASS = "BDD6";

	/*
	 * final static String URL = "jdbc:oracle:thin:@localhost:1521:dbkun"; final
	 * static String LOGIN = "c##nathankun"; final static String PASS =
	 * "83783548jun";
	 */
	/**
	 * Constructor
	 */
	protected DAO() {
		// loading the pilot of DGB
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("impossible to load the BDD pilot, please make sur you have import thr .jar folder in the project");
		}
	}

	protected Object getOne(String type, String sql, Object item) {
		Object retour = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(sql);
			if (!type.contains("User"))
				ps.setLong(1, (Long)item);
			else
				ps.setString(1, (String)item);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (type.contains("Product")) {
					retour = new Product(rs.getLong("pdt_id"),
							rs.getString("pdt_name"), rs.getInt("pdt_stock"),
							rs.getDouble("pdt_price"), rs.getLong("pdt_spr"),
							rs.getString("spr_name"));
				}

				else if (type.contains("Supplier")) {
					long spr_id = rs.getLong("spr_id");
					retour = new Supplier(
							spr_id,
							rs.getString("spr_name"),
							(HashMap<Long, Double>) this
									.getList(
											"spl",
											"SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?",
											1, spr_id));
				}

				else if (type.contains("User")) {
					retour = new User(rs.getString("usr_id"),
							rs.getString("usr_pw"), rs.getString("usr_name"));
				}

				else if (type.contains("Order")) {
					long odr_id = rs.getLong("odr_id");
					Boolean isPaid = rs.getInt("odr_ispaid") == 1 ? true
							: false;
					retour = new Order(
							rs.getLong("odr_id"),
							rs.getDouble("odr_price"),
							rs.getDouble("odr_pricedis"),
							rs.getString("odr_clientname"),
							isPaid,
							rs.getDate("odr_date"),
							(ArrayList<OrderProduct>)this.getList(
									"opl",
									"SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?",
									1, odr_id));
				}
			}
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

	protected Object getList(String type, String sql, int parameterNumber,
			long id) {
		HashMap<Long, Double> returnMap = new HashMap<Long, Double>();
		ArrayList<Object> returnList = new ArrayList<Object>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(sql);

			if (parameterNumber == 1)
				ps.setLong(1, id);
			// requet execution
			rs = ps.executeQuery();

			// we crosse all the line of the results

			if (type.contains("spl")) {
				while (rs.next()){
					returnMap.put(rs.getLong("spl_pdt_id"),
							rs.getDouble("spl_pdt_price"));
				System.out.println("0");}
			}

			else if (type.contains("opl")) {
				while (rs.next())
					returnList.add(new OrderProduct(rs.getLong("opl_pdt_id"),
							rs.getInt("opl_pdt_quantity")));
			}

			else if (type.contains("Order")) {
				long odr_id = rs.getLong("odr_id");
				ArrayList<OrderProduct> pdtList = (ArrayList<OrderProduct>)this.getList(
						"opl",
						"SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?",
						1, odr_id);
				Boolean isPaid = rs.getInt("odr_ispaid") == 1 ? true : false;
				returnList.add(new Order(odr_id, rs.getDouble("odr_price"), rs
						.getDouble("odr_pricedis"), rs
						.getString("odr_clientname"), isPaid, rs
						.getDate("odr_date"), pdtList));
			}

			else if (type.contains("Supplier")) {
				while (rs.next()) {
					long spr_id = rs.getLong("spr_id");
					returnList.add(new Supplier(id, rs.getString("spr_name"),
							(HashMap<Long, Double>)this.getList("spl",
									"SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?",
									1, spr_id)));
					System.out.println(returnList.get(0).toString());
				}
			}

			else if (type.contains("Product")) {
				while (rs.next())
					returnList.add(new Product(rs.getLong("pdt_id"), rs
							.getString("pdt_name"), rs.getInt("pdt_stock"), rs
							.getDouble("pdt_price"), rs.getLong("pdt_spr"), rs
							.getString("spr_name")));
			}

			else if (type.contains("User")) {
				while (rs.next())
					returnList.add(new User(rs.getString("usr_id"), rs
							.getString("usr_pw"), rs.getString("pdt_name")));
			}
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

		if (type.contains("spl")){
			System.out.println(returnMap.toString());
			return returnMap;
		}
		else
			return returnList;
	}
}