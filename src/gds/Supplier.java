package gds;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Supplier class, represents a Supplier
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
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
	public Supplier(String name){
		this.id=0l;
		this.name=name;
		productList = new HashMap<Long,Double>();
	}
	/**
	 * get a supplier form data base
	 * @param id			identification of supplier
	 * @param name			name of the supplier
	 * @param productList	product list of the supplier
	 */
	public Supplier(long id, String name, HashMap<Long, Double> productList){
		this.id=id;
		this.name=name;
		this.productList = productList;
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
	 * @param name contain the new name of the supplier
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
	/**
	 * add a new product in the supplier's products list
	 * @param pdtId id of the  product
	 * @param price	contain the price of the product
	 */
	public void addProduct(long pdtId, Double price){
		productList.put(pdtId, price);
		//System.out.println(productList.get(product));
	}
	/**
	 * remove a product from the list of the supplier
	 * @param product contain the product which we want to remove of the list
	 */
	public void deleteProduct(long product){
		productList.remove(product);
	}
	/**
	 * Change the product price by his Id
	 * @param productId id of the product which we set the price
	 * @param price new price of the product
	 */
	public void setProductPrice(long productId, double price){
		for(Entry<Long, Double> entry : productList.entrySet()){
			if(productId==entry.getKey()){
				System.out.println("found");
				productList.put(entry.getKey(), price);
			}
		}
	}
	/**
	 * Supplier id setter
	 * @param id	new supplier id
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * product list setter
	 * @param productList	new product list
	 */
	public void setProductList(HashMap<Long, Double> productList) {
		this.productList = productList;
	}
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", productList=" + productList + "]";
	}
}
