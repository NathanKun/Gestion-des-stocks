package src.dao;

//import src.GDS;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

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
	 * Add an order in the order_odr table Add also the product with quantity in
	 * odrpdtlist_opl table
	 * 
	 * @param order
	 *            the order for add
	 * @return number of lines add
	 */
	public int addOrder(Order order) {
		/*
		 * Connection con = null; PreparedStatement ps = null; int retour = 0;
		 * // connection to date base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement(
		 * "INSERT INTO order_odr VALUES (odrid_seq.NEXTVAL, ?, ?, ?, ?, ?)");
		 * 
		 * // ps.setLong(1, idGeneratorOdr()); ps.setDouble(1,
		 * order.getPrice()); ps.setDouble(2, order.getPriceDiscount()); if
		 * (order.getIsPaid()) ps.setInt(3, 1); else ps.setInt(3, 0);
		 * ps.setString(4, order.getClientName()); ps.setDate(5,
		 * order.getDate());
		 * 
		 * // excecution of the requiere retour = ps.executeUpdate();
		 * 
		 * // insert ordpdtlist_opl ArrayList<OrderProduct> orderProductList =
		 * order.getProductIdList(); long odrId = this.idGeneratorOdr() - 1; for
		 * (OrderProduct orderProduct : orderProductList) {
		 * addOrderProductList(orderProduct, odrId); }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } finally { // close
		 * preparedStatement and connexion try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { System.out.println("closing problem"); }
		 * try { if (con != null) con.close(); } catch (Exception ignore) {
		 * System.out.println("closing problem"); } } return retour;
		 */

		int retour = this.addLine("Order", order);

		// insert ordpdtlist_opl
		ArrayList<OrderProduct> orderProductList = order.getProductList();
		long odrId = this.idGeneratorOdr() - 1;
		for (OrderProduct orderProduct : orderProductList) {
			orderProduct.setOrderId(odrId);
			addOrderProduct(orderProduct);
		}
		return retour;
	}
	
	public Order getOrder(long id) {
		final String sql = "SELECT * FROM order_odr WHERE odr_id = ?";
		return (Order) this.getOne("Order", sql, id);
	}
	
	public ArrayList<Order> getOrderList() {
		/*
		 * Connection con = null; PreparedStatement ps = null; ResultSet rs =
		 * null; ArrayList<Order> retour = new ArrayList<Order>(); //
		 * connectionto the data base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement("SELECT * FROM order_odr");
		 * 
		 * // requet execution rs = ps.executeQuery();
		 * 
		 * // we crosse all the line of the results while (rs.next()) { long
		 * odr_id = rs.getLong("odr_id"); ArrayList<OrderProduct> pdtList =
		 * getOrderProductList(odr_id); Boolean isPaid = rs.getInt("odr_ispaid")
		 * == 1 ? true : false; retour.add(new Order(odr_id,
		 * rs.getDouble("odr_price"), rs.getDouble("odr_pricedis"),
		 * rs.getString("odr_clientname"), isPaid, rs.getDate("odr_date"),
		 * pdtList)); }
		 * 
		 * } catch (Exception ee) { ee.printStackTrace(); } finally { //
		 * closingrs, PreparedStatement and connection try { if (rs != null)
		 * rs.close(); } catch (Exception ignore) { } try { if (ps != null)
		 * ps.close(); } catch (Exception ignore) { } try { if (con != null)
		 * con.close(); } catch (Exception ignore) { } } return retour;
		 */

		String sql = ("SELECT * FROM order_odr");
		return (ArrayList<Order>) this.getList("Order", sql, 0, 0l);
	}

	// Warning : This will delete completely an order and the order product list
	// from database
	// May be you just need to SET the STATE of an order to Canceled
	public int deleteOrder(long id) {
		Order order = new OrderDAO().getOrder(id);
		//delete order
		int retour = this.deleteLine("Order", id);
		//order product list deleted on cascade
		/*ArrayList<OrderProduct> opl = order.getProductList();
		for (OrderProduct orderProduct : opl) {
			this.deleteLine("opl", orderProduct);
		}*/
		return retour;
	}
	
	public int updateOrder(Order order){
		int retour = this.updateLine("Order", order);
		this.updateOrderProductList(order.getProductList(), order.getId());
		return retour;
	}
	
	public int updateOrderProductList(ArrayList<OrderProduct> listNew, long odrId){
		int updatedRow = 0;
		ArrayList<OrderProduct> listOld = this.getOrderProductList(odrId);
		//delete old list
		for(OrderProduct orderProduct : listOld){
			this.deleteOrderProduct(orderProduct);
			updatedRow++;
		}
		//add new list
		for(OrderProduct orderProduct : listNew){
			this.addOrderProduct(orderProduct);
			updatedRow++;
		}
		
		return updatedRow;
	}
	
	public int updateOrderProductRow(OrderProduct orderProduct){
		return this.updateLine("opl", orderProduct);
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
	private int addOrderProduct(OrderProduct orderProduct) {
		/*
		 * Connection con = null; PreparedStatement ps = null; int retour = 0;
		 * // connection to date base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement(
		 * "INSERT INTO odrpdtlist_opl (opl_id, opl_odr_id, opl_pdt_id, opl_pdt_quantity) VALUES (oplid_seq.NEXTVAL, ?, ?, ?)"
		 * ); // ps.setLong(1, idGeneratorOpl()); // System.out.println(
		 * "orderId " + orderId + "getId" + // orderProduct.getId() +
		 * "getQuantity" + orderProduct.getQuantity() // ); ps.setLong(1,
		 * orderId); ps.setLong(2, orderProduct.getId()); ps.setInt(3,
		 * orderProduct.getQuantity());
		 * 
		 * // Execution of the require retour = ps.executeUpdate(); } catch
		 * (Exception e) { e.printStackTrace(); } finally { // close
		 * preparedStatement and connection try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { System.out.println("closing problem"); }
		 * try { if (con != null) con.close(); } catch (Exception ignore) {
		 * System.out.println("closing problem"); } } return retour;
		 */
		return this.addLine("opl", orderProduct);
	}

	public ArrayList<OrderProduct> getOrderProductList(long odrId) {
		/*
		 * Connection con = null; PreparedStatement ps = null; ResultSet rs =
		 * null; ArrayList<OrderProduct> retour = new ArrayList<OrderProduct>();
		 * // connection to the data base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement(
		 * "SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?"); ps.setLong(1,
		 * odrId);
		 * 
		 * // requet execution rs = ps.executeQuery();
		 * 
		 * // we crosse all the line of the results while (rs.next()) {
		 * retour.add(new OrderProduct(rs.getLong("opl_pdt_id"), rs
		 * .getInt("opl_pdt_quantity"))); }
		 * 
		 * } catch (Exception ee) { ee.printStackTrace(); } finally { // closing
		 * rs, PreparedStatement and connection try { if (rs != null)
		 * rs.close(); } catch (Exception ignore) { } try { if (ps != null)
		 * ps.close(); } catch (Exception ignore) { } try { if (con != null)
		 * con.close(); } catch (Exception ignore) { } } return retour;
		 */
		String sql = "SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?";
		return (ArrayList<OrderProduct>) this.getList("opl", sql, 1, odrId);
	}

	public int deleteOrderProduct(OrderProduct orderProduct) {
		return this.deleteLine("opl", orderProduct);
	}
	
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
	
	public static void main(String[] args) {
		OrderDAO dao = new OrderDAO();
		// System.out.println("id gen next odr_id = " + dao.idGeneratorOdr());
		// System.out.println("id gen next opl_id = " + dao.idGeneratorOpl());

		ArrayList<OrderProduct> list = new ArrayList<OrderProduct>();
		list.add(new OrderProduct(4, 1, 1));
		list.add(new OrderProduct(4, 2, 2));
		list.add(new OrderProduct(4, 3, 33));
		Order order = new Order(4, 10000, 100, "update test", true, (new Date(new java.util.Date().getTime())), list);

		// dao.addOrderProductList(new OrderProduct(2, 5), 1);
//		System.out.println("Get List : ");
//		System.out.println(dao.getOrderList().toString());
//		System.out.println("Get Order 1 : ");
//		System.out.println(dao.getOrder(1).toString());
//		System.out.println("Add Order : ");
//		System.out.println(dao.addOrder(order));
//		System.out.println("Add Order Product List");
//		System.out.println(dao.addOrderProduct(new OrderProduct(11, 11), 1));
//		System.out.println("Delete Order : ");
//		System.out.println(dao.deleteOrder(2));
//		System.out.println("Delete Order Product: ");
//		System.out.println(dao.deleteOrderProduct(new OrderProduct(1, 1, 1)));
//		System.out.println("Update Order Product Row: ");
//		System.out.println(dao.updateOrderProductRow(new OrderProduct(3, 1, 23)));
//		System.out.println("Update Order Product : ");
//		System.out.println(dao.updateOrderProductList(list, 3));
		System.out.println("Update Order  : ");
		System.out.println(dao.updateOrder(order));
	}
}
