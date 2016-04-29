package dao;

import gds.Product;

import java.util.ArrayList;

//import projetDeveloppementLogiciel.Product;
/**
 * class ProductDAO
 * 
 * @author Junior FOTSING KENGNE - HE Junyang
 * @version 1.0
 */
final public class ProductDAO extends DAO {

	/**
	 * add product in the date base.
	 * 
	 * @param product
	 *            - product to add
	 * @return the number of line add in the product list
	 */
	// TODO Should verify the product's name is unique
	public int addProduct(Product product) {
		return this.addLine("Product", product);
	}

	/**
	 * return a specific product by his identifiant.
	 * 
	 * @param id
	 *            identifiant of the product
	 * @return retourn - the product
	 */
	public Product getProduct(long id) {
		String sql = "SELECT * FROM product_pdt LEFT JOIN supplier_spr ON pdt_spr = spr_id WHERE pdt_id = ? ";
		return (Product) this.getOne("Product", sql, id);
	}

	/**
	 * allow to have the full list of product presents in the data base.
	 * 
	 * @return the list of all the products in the data base
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Product> getProductList() {
		String sql = "SELECT * FROM product_pdt LEFT JOIN supplier_spr on spr_id = pdt_spr";
		return (ArrayList<Product>) this.getList("Product", sql, 0, 0L);
	}

	/**
	 * delete a product in the data base product list.
	 * 
	 * @param id
	 *            contain the id of the product we want to delete
	 * @return the number of line delete
	 */
	public int deleteProduct(long id) {
		return this.deleteLine("Product", id);
	}

	public int updateProduct(Product product) {
		return this.updateLine("Product", product);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		ProductDAO dao = new ProductDAO();
		// System.out.println("id gen next pdt_id = " + dao.idGenerator());

		// System.out.println("Get Product Id 2 : ");
		// System.out.println(dao.getProduct(2).toString());
		// System.out.println("Get List : ");
		// System.out.println(dao.getProductList().toString());
		// System.out.println("Add Product : ");
		// System.out.println(dao.addProduct(new Product("aaasssddd")));
		// System.out.println("Delete Product : ");
		// System.out.println(dao.deleteProduct(1));
		System.out.println("Update Product : ");
		System.out.println(dao.updateProduct(new Product(1, "updatedName", 999, 1.11, 2, "123")));

	}
}
