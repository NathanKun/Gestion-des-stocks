package dao;

import gds.Order;
import gds.OrderProduct;
import gds.Product;
import gds.Supplier;
import gds.SupplierProductPrice;
import gds.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.glass.ui.CommonDialogs.Type;

/**
 * abstract class for DAO.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
abstract class DAO {
	/**
	 * connection parameter between oracle URL and the DGB, LOGIN and PASS are
	 * constants.
	 */

	// static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	// static final String LOGIN = "system";
	// static final String PASS = "bdd";

	// static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	// static final String LOGIN = "BDD5";
	// static final String PASS = "BDD5";

	static final String URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
	static final String LOGIN = "c##nathankun";
	static final String PASS = "83783548jun";

	/**
	 * Constructor.
	 * 
	 */
	protected DAO() {
		// loading the pilot of DGB
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException exception) {
			System.err.println("impossible to load the database driver, please make sur you "
					+ "have import the .jar file in the project");
		}
	}

	/**
	 * get an object from the data base.
	 * 
	 * @param type
	 *            the type of object
	 * @param sql
	 *            the SQL code
	 * @param item
	 *            the parameter
	 * @return the object got from data base
	 */
	@SuppressWarnings("unchecked")
	protected Object getOne(String type, String sql, Object item) {
		Object retour = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(sql);
			switch (type) {
			case "User":
				ps.setString(1, (String) item);
				break;

			case "SupplierProductPrice":
				SupplierProductPrice param = (SupplierProductPrice) item;
				ps.setLong(1, param.getSprId());
				ps.setLong(2, param.getPdtId());
				break;

			case "BestPrice":
				ps.setLong(1, (long) item);
				ps.setLong(2, (long) item);
				break;

			default:
				ps.setLong(1, (Long) item);
				break;
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				switch (type) {
				case "Product":
					retour = new Product(rs.getLong("pdt_id"), rs.getString("pdt_name"), rs.getInt("pdt_stock"),
							rs.getDouble("pdt_price"), rs.getLong("pdt_spr"), rs.getString("spr_name"));
					break;

				case "Supplier":
					long sprId = rs.getLong("spr_id");
					retour = new Supplier(sprId, rs.getString("spr_name"), (HashMap<Long, Double>) this.getList("spl",
							"SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?", 1, sprId));
					break;

				case "Order":
					long odrId = rs.getLong("odr_id");
					Boolean isPaid = rs.getInt("odr_ispaid") == 1 ? true : false;
					retour = new Order(rs.getLong("odr_id"), rs.getDouble("odr_price"), rs.getDouble("odr_pricedis"),
							rs.getString("odr_clientname"), isPaid, rs.getDate("odr_date"),
							(ArrayList<OrderProduct>) this.getList("opl",
									"SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?", 1, odrId));
					break;

				case "User":
					retour = new User(rs.getString("usr_id"), rs.getString("usr_pw"), rs.getString("usr_name"));
					break;

				case "SupplierProductPrice":
					retour = new SupplierProductPrice(rs.getLong("spl_spr_id"), rs.getLong("spl_pdt_id"),
							rs.getDouble("spl_pdt_price"));
					break;

				case "BestPrice":
					retour = new SupplierProductPrice(rs.getLong("spl_spr_id"), rs.getLong("spl_pdt_id"),
							rs.getDouble("spl_pdt_price"));
					break;

				default:
					System.out.println("String type error");
					break;
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing of ResultSet, PreparedStatement and connection
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ee) {
				System.out.println("closing problem");
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ee) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				System.out.println("closing problem");
			}
		}

		return retour;
	}

	/**
	 * get a list of a type of object.
	 * 
	 * @param type
	 *            the type of object
	 * @param sql
	 *            SQL code
	 * @param parameterNumber
	 *            numbers of parameter
	 * @param id
	 *            the parameter
	 * @return A list of object
	 */
	@SuppressWarnings("unchecked")
	protected Object getList(String type, String sql, int parameterNumber, long id) {
		HashMap<Long, Double> returnMap = new HashMap<Long, Double>();
		ArrayList<Object> returnList = new ArrayList<Object>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement(sql);

			if (parameterNumber == 1) {
				ps.setLong(1, id);
			}
			// requet execution
			rs = ps.executeQuery();

			// we crosse all the line of the results
			switch (type) {
			case "spl":
				while (rs.next()) {
					returnMap.put(rs.getLong("spl_pdt_id"), rs.getDouble("spl_pdt_price"));
					// System.out.println("spl returnMap : " + returnMap);
				}
				break;

			case "opl":
				while (rs.next()) {
					returnList.add(new OrderProduct(rs.getLong("opl_odr_id"), rs.getLong("opl_pdt_id"),
							rs.getInt("opl_pdt_quantity")));
				}
				break;

			case "Order":
				while (rs.next()) {
					long odrId = rs.getLong("odr_id");
					ArrayList<OrderProduct> pdtList = (ArrayList<OrderProduct>) this.getList("opl",
							"SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?", 1, odrId);
					Boolean isPaid = rs.getInt("odr_ispaid") == 1 ? true : false;
					returnList.add(new Order(odrId, rs.getDouble("odr_price"), rs.getDouble("odr_pricedis"),
							rs.getString("odr_clientname"), isPaid, rs.getDate("odr_date"), pdtList));
				}
				break;

			case "Supplier":
				while (rs.next()) {
					long sprId = rs.getLong("spr_id");
					returnList.add(new Supplier(sprId, rs.getString("spr_name"), (HashMap<Long, Double>) this
							.getList("spl", "SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?", 1, sprId)));
					// System.out.println("Supplier List 0: " +
					// returnList.get(0).toString());
				}
				break;

			case "Product":
				while (rs.next()) {
					returnList.add(new Product(rs.getLong("pdt_id"), rs.getString("pdt_name"), rs.getInt("pdt_stock"),
							rs.getDouble("pdt_price"), rs.getLong("pdt_spr"), rs.getString("spr_name")));
				}
				break;

			case "User":
				while (rs.next()) {
					returnList.add(new User(rs.getString("usr_id"), rs.getString("usr_pw"), rs.getString("usr_name")));
				}
				break;

			default:
				System.out.println("String type not correct!");
				;
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing rs, PreparedStatement and connection
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}

		if (type.contains("spl")) {
			// System.out.println("spl : " + returnMap.toString());
			return returnMap;
		} else {
			return returnList;
		}
	}

	/**
	 * Add a line in data base.
	 * 
	 * @param type
	 *            type of object
	 * @param item
	 *            the object
	 * @return numbers of line added.
	 */
	protected int addLine(String type, Object item) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);

			switch (type) {
			case "User":
				User user = (User) item;
				ps = con.prepareStatement("INSERT INTO user_usr (usr_id, usr_pw, usr_name) VALUES (?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getPw());
				ps.setString(3, user.getName());
				break;

			case "Product":
				Product product = (Product) item;
				ps = con.prepareStatement("INSERT INTO product_pdt (pdt_id, pdt_name, pdt_stock) VALUES(?,?,?)");
				ps.setLong(1, product.getId());
				ps.setString(2, product.getName());
				ps.setInt(3, product.getStock());
				break;

			case "Supplier":
				Supplier supplier = (Supplier) item;
				ps = con.prepareStatement("INSERT INTO supplier_spr (spr_id, spr_name) VALUES(?,?)");
				ps.setLong(1, supplier.getId());
				ps.setString(2, supplier.getName());
				break;

			case "Order":
				Order order = (Order) item;
				ps = con.prepareStatement("INSERT INTO order_odr VALUES (?, ?, ?, ?, ?, ?)");
				ps.setDouble(1, order.getId());
				ps.setDouble(2, order.getPrice());
				ps.setDouble(3, order.getPriceDiscount());
				if (order.getIsPaid()) {
					ps.setInt(4, 1);
				} else {
					ps.setInt(4, 0);
				}
				ps.setString(5, order.getClientName());
				ps.setDate(6, order.getDate());
				break;

			case "spl":
				double[] param = (double[]) item;
				ps = con.prepareStatement(
						"SELECT COUNT(*) FROM sprpdtlist_spl WHERE spl_spr_id = ? AND spl_pdt_id = ?");
				ps.setLong(1, (long) param[0]);
				ps.setLong(2, (long) param[1]);
				rs = ps.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0) {
					ps.close();
					ps = con.prepareStatement(
							"INSERT INTO sprpdtlist_spl (spl_id, spl_spr_id, spl_pdt_id, spl_pdt_price) VALUES(splid_seq.NEXTVAL,?,?,?)");
					// ps.setLong(1, idGenerator_Sprpdtlist_spl());
					ps.setLong(1, (long) param[0]);
					ps.setLong(2, (long) param[1]);
					ps.setDouble(3, param[2]);
				} else {
					con.close();
					return 0;
				}
				break;

			case "opl":
				OrderProduct orderProduct = (OrderProduct) item;
				ps = con.prepareStatement(
						"INSERT INTO odrpdtlist_opl (opl_id, opl_odr_id, opl_pdt_id, opl_pdt_quantity) VALUES (oplid_seq.NEXTVAL, ?, ?, ?)");
				ps.setLong(1, orderProduct.getOrderId());
				ps.setLong(2, orderProduct.getProductId());
				ps.setInt(3, orderProduct.getQuantity());
				break;

			default:
				System.out.println("String type error!");
				break;
			}

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ex) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}

	/**
	 * Delete a line in database.
	 * 
	 * @param type
	 *            type of object
	 * @param item
	 *            the object
	 * @return numbers of line deleted
	 */
	protected int deleteLine(String type, Object item) {

		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			switch (type) {
			case "User":
				ps = con.prepareStatement("DELETE FROM user_usr WHERE usr_id=?");
				ps.setString(1, (String) item);
				break;

			case "Supplier":
				ps = con.prepareStatement("DELETE FROM supplier_spr WHERE spr_id=?");
				ps.setLong(1, (long) item);
				break;

			case "Product":
				ps = con.prepareStatement("DELETE FROM product_pdt WHERE pdt_id=?");
				ps.setLong(1, (long) item);
				break;

			case "Order":
				ps = con.prepareStatement("DELETE FROM order_odr WHERE odr_id = ?");
				ps.setLong(1, (long) item);
				break;

			case "spl":
				long[] param = (long[]) item;
				ps = con.prepareStatement("DELETE FROM sprpdtlist_spl WHERE spl_spr_id = ? AND spl_pdt_id = ?");
				ps.setLong(1, param[0]);
				ps.setLong(2, param[1]);
				break;

			case "opl":
				OrderProduct orderProduct = (OrderProduct) item;
				ps = con.prepareStatement("DELETE FROM odrpdtlist_opl WHERE opl_odr_id = ? AND opl_pdt_id = ?");
				ps.setLong(1, orderProduct.getOrderId());
				ps.setLong(2, orderProduct.getProductId());
				break;

			default:
				System.out.println("String type error!!!");
				break;
			}
			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}

	/**
	 * Update a line in database.
	 * 
	 * @param type
	 *            type of object
	 * @param item
	 *            the object
	 * @return numbers of line updated
	 */
	protected int updateLine(String type, Object item) {

		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);

			switch (type) {
			case "User":
				User user = (User) item;
				ps = con.prepareStatement("UPDATE user_usr SET usr_pw=?, usr_name=? WHERE usr_id=?");
				ps.setString(1, user.getPw());
				ps.setString(2, user.getName());
				ps.setString(3, user.getId());
				break;

			case "Product":
				Product product = (Product) item;
				ps = con.prepareStatement(
						"UPDATE product_pdt SET pdt_name = ?, pdt_stock = ?, pdt_spr = ?, pdt_price = ? WHERE pdt_id = ?");
				ps.setString(1, product.getName());
				ps.setInt(2, product.getStock());

				long pdtSpr = product.getSupplierId();
				if (pdtSpr == 0) {
					ps.setNull(3, java.sql.Types.INTEGER);
				} else {
					ps.setLong(3, pdtSpr);
				}
				ps.setDouble(4, product.getPrice());
				ps.setLong(5, product.getId());
				break;

			case "Supplier":
				Supplier supplier = (Supplier) item;
				ps = con.prepareStatement("UPDATE supplier_spr SET spr_name = ? WHERE spr_id = ?");
				ps.setString(1, supplier.getName());
				ps.setLong(2, supplier.getId());
				break;

			case "Order":
				Order order = (Order) item;
				ps = con.prepareStatement(
						"UPDATE order_odr SET odr_price = ?, odr_pricedis = ?, odr_ispaid = ?, odr_clientname = ?, odr_date = ? WHERE odr_id = ?");
				ps.setDouble(1, order.getPrice());
				ps.setDouble(2, order.getPriceDiscount());
				if (order.getIsPaid()) {
					ps.setInt(3, 1);
				} else {
					ps.setInt(3, 0);
				}
				ps.setString(4, order.getClientName());
				ps.setDate(5, order.getDate());
				ps.setLong(6, order.getId());
				break;

			case "spl":
				// just update price
				double[] param = (double[]) item;
				ps = con.prepareStatement(
						"UPDATE sprpdtlist_spl SET spl_pdt_price = ? WHERE spl_spr_id = ? AND spl_pdt_id = ?");
				// ps.setLong(1, idGenerator_Sprpdtlist_spl());
				ps.setDouble(1, param[2]);
				ps.setLong(2, (long) param[0]);
				ps.setLong(3, (long) param[1]);
				break;

			case "opl":
				// just update quantity
				OrderProduct orderProduct = (OrderProduct) item;
				ps = con.prepareStatement(
						"UPDATE odrpdtlist_opl SET opl_pdt_quantity = ? WHERE opl_odr_id = ? AND opl_pdt_id = ?");
				ps.setInt(1, orderProduct.getQuantity());
				ps.setLong(2, orderProduct.getOrderId());
				ps.setLong(3, orderProduct.getProductId());
				break;

			default:
				System.out.println("String type error!");
				break;
			}

			// Execution of the require
			retour = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close preparedStatement and connection
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}

	protected static long nextId(String type) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			switch (type) {
			case "Order":
				ps = con.prepareStatement("SELECT odrid_seq.NEXTVAL FROM dual");
				break;

			case "opl":
				ps = con.prepareStatement("SELECT oplid_seq.NEXTVAL FROM dual");
				break;

			case "Supplier":
				ps = con.prepareStatement("SELECT sprid_seq.NEXTVAL FROM dual");
				break;

			case "spl":
				ps = con.prepareStatement("SELECT splid_seq.NEXTVAL FROM dual");
				break;

			case "Product":
				ps = con.prepareStatement("SELECT pdtid_seq.NEXTVAL FROM dual");
				break;

			default:
				break;
			}

			// excecution of the requiere
			rs = ps.executeQuery();
			// recuperation of number
			rs.next();
			retour = rs.getLong(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close preparedStatement and connection
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return (retour + 1);
	}
}