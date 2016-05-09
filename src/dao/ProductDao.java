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
public abstract class ProductDao extends Dao {

	/**
	 * add product in the date base.
	 * 
	 * @param product
	 *            - product to add
	 * @return the number of line add in the product list
	 */
	// TODO Should verify the product's name is unique
	public static int addProduct(Product product) {
		return Dao.addLine("Product", product);
	}

	/**
	 * return a specific product by his identifiant.
	 * 
	 * @param id
	 *            identifiant of the product
	 * @return retourn - the product
	 */
	public static Product getProduct(long id) {
		String sql = "SELECT * FROM product_pdt LEFT JOIN supplier_spr ON pdt_spr = spr_id WHERE pdt_id = ? ";
		return (Product) Dao.getOne("Product", sql, id);
	}

	/**
	 * allow to have the full list of product presents in the data base.
	 * 
	 * @return the list of all the products in the data base
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Product> getProductList() {
		String sql = "SELECT * FROM product_pdt LEFT JOIN supplier_spr on spr_id = pdt_spr";
		return (ArrayList<Product>) Dao.getList("Product", sql, 0, 0L);
	}

	/**
	 * delete a product in the data base product list.
	 * 
	 * @param id
	 *            contain the id of the product we want to delete
	 * @return the number of line delete
	 */
	public static int deleteProduct(long id) {
		return Dao.deleteLine("Product", id);
	}

	/**
	 * update a product.
	 * 
	 * @param product
	 *            the product for update
	 * @return numbers of line updated
	 */
	public static int updateProduct(Product product) {
		return Dao.updateLine("Product", product);
	}

	/**
	 * get the next product id.
	 * 
	 * @return next product id
	 */
	public static long nextPdtId() {
		return Dao.nextId("Product");
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		// System.out.println("id gen next pdt_id = " + dao.idGenerator());

		// System.out.println("Get Product Id 2 : ");
		// System.out.println(ProductDAO.getProduct(2).toString());
		System.out.println("Get List : ");
		System.out.println(ProductDao.getProductList().toString());
		// System.out.println("Add Product : ");
		// System.out.println(dao.addProduct(new Product("aaasssddd")));
		// System.out.println("Delete Product : ");
		// System.out.println(dao.deleteProduct(1));
		// System.out.println("Update Product : ");
		// System.out.println(dao.updateProduct(new Product(1, "updatedName",
		// 999, 1.11, 2, "123")));
		// System.out.println("Next pdt id : ");
		// System.out.println(ProductDAO.nextPdtId());

	}
}
