package gds;

/**
 * class represents a product with a quantity
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class OrderProduct {
	/**
	 * id of the order
	 */
	private long orderId;
	/**
	 * id of a product of an order
	 */
	private long productId;
	/**
	 * quantity of a product in an order
	 */
	private int quantity;

	/*public OrderProduct(long productId, int quantity){
		this.productId = productId;
		this.quantity = quantity;
	}*/
	
	public OrderProduct(long orderId, long productId, int quantity){
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * quantity getter
	 * @return	quantity of product
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * quantity setter
	 * @param quantity quantity of product
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * orderProduct toString
	 */
	@Override
	public String toString() {
		return "OrderProduct [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}

}
