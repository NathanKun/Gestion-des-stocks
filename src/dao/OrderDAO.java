package src.dao;

//import src.GDS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import src.gds.Order;
import src.gds.OrderProduct;
import src.gds.Product;

/**
 * Data access object for Order
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class OrderDAO extends DAO {

	/**
	 * generate a new id for an order
	 * 
	 * @return retour the new id of the order
	 */
	public long idGeneratorOdr() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(odr_id) FROM order_odr");

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
	 * generate a new id for the order product list opl_id in odrpdtlist_opl
	 * 
	 * @return retour the new id of the order product list
	 */
	public long idGeneratorOpl() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(opl_id) FROM odrpdtlist_opl");

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
	 * Add ONLY ONE LINE into the order product list odrpdtlist_opl It mean add
	 * a product with it's quantity in the list You need to call this method
	 * several times for an order
	 * 
	 * @param orderProduct
	 *            ONE product with it's quantity in an order
	 * @param orderId
	 *            the id of the order for add
	 * @return number of line added
	 */
	private int addOrderProductList(OrderProduct orderProduct, long orderId) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO odrpdtlist_opl VALUES (oplid_seq.NEXTVAL, ?, ?, ?)");
			// ps.setLong(1, idGeneratorOpl());
			ps.setLong(1, orderId);
			ps.setLong(2, orderProduct.getId());
			ps.setInt(3, orderProduct.getQuantity());

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
	 * Add an order in the order_odr table Add also the product with quantity in
	 * odrpdtlist_opl table
	 * 
	 * @param order
	 *            the order for add
	 * @return number of lines add
	 */
	public int addOrder(Order order) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO order_odr VALUES (odrid_seq.NEXTVAL, ?, ?, ?, ?, ?)");

			// ps.setLong(1, idGeneratorOdr());
			ps.setDouble(1, order.getPrice());
			ps.setDouble(2, order.getPriceDiscount());
			if (order.getIsPaid())
				ps.setInt(3, 1);
			else
				ps.setInt(3, 0);
			ps.setString(4, order.getClientName());
			ps.setDate(5, order.getDate());

			// excecution of the requiere
			retour = ps.executeUpdate();

			// insert ordpdtlist_opl
			for (OrderProduct orderProduct : order.getProductIdList()) {
				addOrderProductList(orderProduct, order.getId());
			}

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

	public ArrayList<OrderProduct> getOrderProductList(long odrId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OrderProduct> retour = new ArrayList<OrderProduct>();
		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?");
			ps.setLong(1, odrId);

			// requet execution
			rs = ps.executeQuery();

			// we crosse all the line of the results
			while (rs.next()) {
				retour.add(new OrderProduct(rs.getLong("opl_pdt_id"), rs
						.getInt("opl_pdt_quantity")));
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
		return retour;
	}

	public ArrayList<Order> getOrderList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Order> retour = new ArrayList<Order>();
		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM order_odr");

			// requet execution
			rs = ps.executeQuery();

			// we crosse all the line of the results
			while (rs.next()) {
				long odr_id = rs.getLong("odr_id");
				ArrayList<OrderProduct> pdtList = getOrderProductList(odr_id);
				Boolean isPaid = rs.getInt("odr_ispaid") == 1 ? true : false;
				retour.add(new Order(odr_id, rs.getDouble("odr_price"), rs
						.getDouble("odr_pricedis"), rs
						.getString("odr_clientname"), isPaid, rs
						.getDate("odr_date"), pdtList));
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
		return retour;
	}

	public static void main(String[] args) {
		OrderDAO dao = new OrderDAO();
		// System.out.println("id gen next odr_id = " + dao.idGeneratorOdr());
		// System.out.println("id gen next opl_id = " + dao.idGeneratorOpl());
		// dao.addOrder(new Order (0, 110, 100, "nameeee", false, new Date(0, 0,
		// 0), null));
		// dao.addOrderProductList(new OrderProduct(2l, 5), 2);
		for(Order odr : dao.getOrderList())
		System.out.println(odr.toString());
	}
}
