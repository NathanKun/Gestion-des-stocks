
package src.GDS;
import java.util.*;

public class Supplier {
	/**
	 * contain supplier id
	 */
	private long id;
	/**
	 * contain supplier name
	 */
	private String name;
	/**
	 * contain the list of product of the supplier
	 * Long: id of the product
	 * Double: supplier product price
	 */
	private HashMap<Long, Double> productList;
	/**
	 * create a supplier
	 * @param id identification of supplier
	 * @param name name of the supplier
	 */
	public Supplier(long id, String name){
		this.id=id;
		this.name=name;
		productList = new HashMap<Long,Double>();
	}
	/**
	 * return the name of the supplier
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	/**
	 * modify the name of the supplier
	 * @param name Containt the new name of the supplier
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * return the supplier id
	 * @return long id
	 */
	public long getId() {
		return id;
	}
	/**
	 * return the table of the supplier's product with thier price
	 * @return HashMap productList
	 */
	public HashMap<Long, Double> getProductList() {
		return productList;
	}
	/**
	 * modify the attributes of a supplier
	 * @param name the new name of the product
	 */
	public void modify(String name){
		setName(name);
	}
}
