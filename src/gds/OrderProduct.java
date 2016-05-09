package gds;

/**
 * class represents a product with a quantity.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class OrderProduct {
	/**
	 * id of the order.
	 */
	private long orderId;
	/**
	 * id of a product of an order.
	 */
	private long productId;
	/**
	 * quantity of a product in an order.
	 */
	private int quantity;

	/**
	 * Create an Order Product or get from the database.
	 * 
	 * @param orderId
	 *            id of the order.
	 * @param productId
	 *            id of the product.
	 * @param quantity
	 *            quantity of the product.
	 */
	public OrderProduct(long orderId, long productId, int quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	/**
	 * order id getter.
	 * 
	 * @return order id
	 */
	public long getOrderId() {
		return orderId;
	}

	/**
	 * order id setter.
	 * 
	 * @param orderId
	 *            order id.
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	/**
	 * product id getter.
	 * 
	 * @return product id.
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * product id setter.
	 * 
	 * @param productId
	 *            product id.
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * quantity getter.
	 * 
	 * @return quantity of product
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * quantity setter.
	 * 
	 * @param quantity
	 *            quantity of product
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * orderProduct toString.
	 */
	@Override
	public String toString() {
		return "OrderProduct [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}

}
