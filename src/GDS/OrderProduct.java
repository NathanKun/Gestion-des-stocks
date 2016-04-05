package src.GDS;
/**
 * class represents a product with a quantity
 * @author HE Junyang - FOTSING Junior
 *
 */
public class OrderProduct {
	/**
	 * id of a product of an order
	 */
	private long id;
	/**
	 * quantity of a product in an order
	 */
	private int quantity;
	
	public OrderProduct(long id, int quantity){
		this.id = id;
		this.quantity = quantity;
	}
	
	/**
	 * id getter
	 * @return
	 */
	public long getId() {
		return id;
	}
	/**
	 * id setter
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * quantity getter
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * quantity setter
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * orderProduct toString
	 */
	@Override
	public String toString() {
		return "orderProduct [id=" + id + ", quantity=" + quantity + "]";
	}
}
