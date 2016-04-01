package src.GDS;
/**
 * class product
 * @author Junior FOTSING - HE junyang
 * @version 1.0
 */
public class Product {

	/**
	 * containt product id
	 */
	private long id;
	/**
	 * containt product name
	 */
	private String name;
	/**
	 * containt product price
	 */
	private double price;
	/**
	 * containt available stock for the product
	 */
	private int stock;
	/**
	 * contain the product's supplier identifiant
	 */
	private long supplierId;
	/**
	 * contain the name of product's supplier
	 */
	private String supplierName;
	
	/**
	 * create a new product
	 * @param id product identification
	 * @param name product name
	 */
	public Product(long id, String name){
		this.id=id;
		this.name=name;
		this.stock=0;
		this.price=0;
	}
	/**
	 * return product name
	 * @return string name
	 */
	public String getName() {
		return name;
	}
	/**
	 * set the name of the product
	 * @param name the new name of the product
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * return the product Id
	 * @return String id
	 */
	public long getId() {
		return id;
	}
	/**
	 * return the product price
	 * @return double price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * return the available stock of the product
	 * @return int stock
	 */
	public int getStock() {
		return stock;
	}
	/**
	 * reduce stock quantity by the quantity in parameter
	 * @param quantity the quantity of stock to reduce
	 */
	public void reduceStock(int quantity){
		stock-=quantity;
	}
	public void affectSupplier(Supplier supplier){
		
	}
	public void removeSupplier(long id){
		
	}
	public void setPrice(double price) {
		this.price = price;
	}
}