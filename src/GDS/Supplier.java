
package src.GDS;
import java.util.*;

public class Supplier {
	/**
	 * containt supplier id
	 */
	private long id;
	/**
	 * containt supplier name
	 */
	private String name;
	/**
	 * containt the list of product of the supplier
	 */
	private HashMap<Product, Double> productList;
	/**
	 * create a supplier
	 * @param id identification of supplier
	 * @param name name of the supplier
	 */
	public Supplier(long id, String name){
		this.id=id;
		this.name=name;
		productList = new HashMap<Product, Double>();
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
	public HashMap<Product, Double> getProductList() {
		return productList;
	}
	/**
	 * modify the attributes of a supplier
	 * @param name the new name of the product
	 */
	public void modify(String name){
		setName(name);
	}
	/**
	 * add a new product in the supplier's products list
	 * @param product containt the new product
	 * @param price	containt the price of the product
	 */
	public void addProduct(Product product, Double price){
		productList.put(product, price);
		//System.out.println(productList.get(product));
	}
	/**
	 * remove a product from the list of the supplier
	 * @param product containt the product which we want to remove of the list
	 */
	public void deleteProduct(Product product){
		productList.remove(product);
	}
	/**
	 * Change the product price by his Id
	 * @param productId id of the product which we set the price
	 * @param price new price of the product
	 */
	public void setProductPrice(long productId, double price){
		for(Map.Entry<Product,Double> entry : productList.entrySet()){
			if(productId==entry.getKey().getId()){
				System.out.println("trouvé");
				productList.put(entry.getKey(), price);
			}
		}
	}
}
