package src.GDS;

import java.sql.Date;
import java.util.*;

/**
 * Order class, represent an order
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class Order {
	/**
	 * contain the order ID
	 */
	private long id;
	/**
	 * contain the total price of the order
	 */
	private double price;
	/**
	 * contain the order discount for the client
	 */
	private int priceDiscount;
	/**
	 * the name of the client
	 */
	private String clientName;
	/**
	 * the state of the order
	 * true - for paid
	 * false - for not paid
	 */
	boolean isPaid;
	/**
	 * contain the date of the order creation
	 */
	private Date date;
	/**
	 * contain the ID of all the order's products
	 */
	private ArrayList<Long> productIdList;
	/**
	 * constructor of the order class
	 * @param id of the new order
	 * @param price - the initial price of the order
	 * @param priceDiscount - the order discount of the order
	 * @param clientName - the name of the client
	 * @param isPaid - is the order paid
	 * @param date - date of the order creation
	 * @param productIdList - the IDs of the order's products
	 */
	public Order(long id, double price, int priceDiscount, String clientName, 
			boolean isPaid, Date date, ArrayList<Long> productIdList) {
		this.clientName = clientName;
		this.date = date;
		this.priceDiscount = priceDiscount;
		this.id = id;
		this.price = price;
		this.isPaid = isPaid;
		this.productIdList = productIdList;
	}
	/**
	 * get the price of the order
	 * @return the order price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * price setter
	 * @param price - the new price of the order
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * priceDiscount getter
	 * @return the order discount for the user
	 */
	public int getpriceDiscount() {
		return priceDiscount;
	}
	/**
	 * name of client's order discount
	 * @return the name of the client
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * client name setter
	 * @param clientName - the new name of the client
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * return the state of the order
	 * @return - state's order
	 */
	public boolean getIsPaid() {
		return isPaid;
	}
	/**
	 * product id list getter
	 * @return product id ArrayList
	 */
	public ArrayList<Long> getProductIdList() {
		return productIdList;
	}
	/**
	 * productIdList setter
	 * @param productIdList new product Id List
	 */
	public void setProductIdList(ArrayList<Long> productIdList) {
		this.productIdList = productIdList;
	}
	/**
	 * Order Id getter
	 * @return	order Id
	 */
	public long getId() {
		return id;
	}
	/**
	 * Order date getter
	 * @return	order date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * priceDiscount getter
	 * @return priceDiscount
	 */
	public int getPriceDiscount() {
		return priceDiscount;
	}
	/**
	 * priceDiscount setter
	 * @param priceDiscount - contain the new order discount value
	 */
	public void setPriceDiscount(int priceDiscount) {
		this.priceDiscount = priceDiscount;
	}
	/**
	 * id setter
	 * @param id new id
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * isPaid setter
	 * @param isPaid new isPaid state
	 */
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	/**
	 * date setter
	 * @param date new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "Order [id=" + id + ", price=" + price + ", priceDiscount=" + priceDiscount + ", clientName="
				+ clientName + ", isPaid=" + isPaid + ", date=" + date + ", productIdList=" + productIdList + "]";
	}

}
