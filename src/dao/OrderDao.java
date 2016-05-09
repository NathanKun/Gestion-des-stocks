package dao;

import gds.Order;
import gds.OrderProduct;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Data access object for Order.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
final public class OrderDAO extends DAO {

	/**
	 * Add an order in the order_odr table. Add also the product with quantity
	 * in odrpdtlist_opl table.
	 * 
	 * @param order
	 *            the order for add
	 * @return number of lines add
	 */
	public int addOrder(Order order) {
		int retour = this.addLine("Order", order);

		// insert ordpdtlist_opl
		ArrayList<OrderProduct> orderProductList = order.getProductList();
		long odrId = order.getId();
		for (OrderProduct orderProduct : orderProductList) {
			orderProduct.setOrderId(odrId);
			addOrderProduct(orderProduct);
		}
		return retour;
	}

	/**
	 * get an Order by id.
	 * 
	 * @param id
	 *            id of order
	 * @return the object Order
	 */
	public Order getOrder(long id) {
		final String sql = "SELECT * FROM order_odr WHERE odr_id = ?";
		return (Order) this.getOne("Order", sql, id);
	}

	/**
	 * get the list of order.
	 * 
	 * @return the list of order
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Order> getOrderList() {
		String sql = ("SELECT * FROM order_odr");
		return (ArrayList<Order>) this.getList("Order", sql, 0, 0L);
	}

	/**
	 * delete an order This will delete completely an order and the order
	 * product list from database. May be you just need to SET the STATE of an
	 * order number of to Canceled
	 * 
	 * @param id
	 *            id of the order
	 * @return line deleted
	 */
	public int deleteOrder(long id) {
		// delete order
		int retour = this.deleteLine("Order", id);
		// order product list deleted on cascade
		return retour;
	}

	/**
	 * update an order.
	 * 
	 * @param order
	 *            order with new data
	 * @return number of line updated
	 */
	public int updateOrder(Order order) {
		int retour = this.updateLine("Order", order);
		this.updateOrderProductList(order.getProductList(), order.getId());
		return retour;
	}

	/**
	 * update the list of Product for an Order.
	 * 
	 * @param listNew
	 *            new list
	 * @param odrId
	 *            id of the order
	 * @return number of lines updated
	 */
	public int updateOrderProductList(ArrayList<OrderProduct> listNew, long odrId) {
		int updatedRow = 0;
		ArrayList<OrderProduct> listOld = this.getOrderProductList(odrId);
		// delete old list
		for (OrderProduct orderProduct : listOld) {
			this.deleteOrderProduct(orderProduct);
			updatedRow++;
		}
		// add new list
		for (OrderProduct orderProduct : listNew) {
			this.addOrderProduct(orderProduct);
			updatedRow++;
		}

		return updatedRow;
	}

	public int updateOrderProductRow(OrderProduct orderProduct) {
		return this.updateLine("opl", orderProduct);
	}

	/**
	 * Add ONLY ONE LINE into the order product list odrpdtlist_opl. It mean add
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
		return this.addLine("opl", orderProduct);
	}

	/**
	 * get the list of products in an order.
	 * 
	 * @param odrId
	 *            id of the order
	 * @return list of products in an order
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<OrderProduct> getOrderProductList(long odrId) {
		String sql = "SELECT * FROM odrpdtlist_opl WHERE opl_odr_id = ?";
		return (ArrayList<OrderProduct>) this.getList("opl", sql, 1, odrId);
	}

	/**
	 * delete A products in an order.
	 * 
	 * @param orderProduct
	 *            the orderProduct to delete
	 * @returnl number of line deleted
	 */
	public int deleteOrderProduct(OrderProduct orderProduct) {
		return this.deleteLine("opl", orderProduct);
	}

	/**
	 * get the next id for an order.
	 * 
	 * @return retour the next id of the order
	 */
	public static long nextOdrId() {
		return DAO.nextId("Order");
	}

	/**
	 * get the next id for the order product list.
	 * 
	 * @return retour the next id of the the order product list
	 */
	public static long nextOplId() {
		return DAO.nextId("opl");
	}

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		final OrderDAO dao = new OrderDAO();
		// System.out.println("id gen next odr_id = " + dao.idGeneratorOdr());
		// System.out.println("id gen next opl_id = " + dao.idGeneratorOpl());

		ArrayList<OrderProduct> list = new ArrayList<OrderProduct>();
		list.add(new OrderProduct(4, 1, 1));
		list.add(new OrderProduct(4, 2, 2));
		list.add(new OrderProduct(4, 3, 33));
		Order order = new Order(4, 10000, 100, "date test", true, (new Date(new java.util.Date().getTime())), list);

		// dao.addOrderProductList(new OrderProduct(2, 5), 1);
		// System.out.println("Get List : ");
		// System.out.println(dao.getOrderList().toString());
		// System.out.println("Get Order 12 : ");
		// System.out.println(dao.getOrder(12).toString());
		// System.out.println("Add Order : ");
		// System.out.println(dao.addOrder(order));
		// System.out.println("Add Order Product List");
		// System.out.println(dao.addOrderProduct(new OrderProduct(11, 11), 1));
		// System.out.println("Delete Order : ");
		// System.out.println(dao.deleteOrder(2));
		// System.out.println("Delete Order Product: ");
		// System.out.println(dao.deleteOrderProduct(new OrderProduct(1, 1,
		// 1)));
		// System.out.println("Update Order Product Row: ");
		// System.out.println(dao.updateOrderProductRow(new OrderProduct(3, 1,
		// 23)));
		// System.out.println("Update Order Product : ");
		// System.out.println(dao.updateOrderProductList(list, 3));
		// System.out.println("Update Order : ");
		// System.out.println(dao.updateOrder(order));
		System.out.println("Next opl id : ");
		System.out.println(OrderDAO.nextOplId());
		System.out.println("Next odr id : ");
		System.out.println(OrderDAO.nextOdrId());

	}
}
