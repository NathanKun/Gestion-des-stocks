package dao;

import gds.Product;
import gds.Supplier;
import gds.SupplierProductPrice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Data access object for Supplier.
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 *
 */
public abstract class SupplierDao extends Dao {
	/**
	 * add product in the date base.
	 * 
	 * @param supplier
	 *            contain the new supplier o add
	 * @return retour 1 if the adding is ok and 0 if not
	 */
	public static int addSupplier(Supplier supplier) {
		return Dao.addLine("Supplier", supplier);
	}

	/**
	 * return a specific supplier by his identifiant.
	 * 
	 * @param id
	 *            identifiant of the supplier
	 * @return the supplier
	 */
	public static Supplier getSupplier(long id) {
		String sql = "SELECT *FROM supplier_spr WHERE spr_id = ?";
		return (Supplier) Dao.getOne("Supplier", sql, id);
	}

	/**
	 * allow to have the full list of the suppliers presents in the data base.
	 * 
	 * @return the list of all the suppliers in the data base
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Supplier> getSupplierList() {
		String sql = "SELECT *FROM Supplier_spr";
		return (ArrayList<Supplier>) Dao.getList("Supplier", sql, 0, 0L);
	}

	/**
	 * delete a supplier in the data base suppliers list.
	 * 
	 * @param id
	 *            contain the id of the supplier we want to delete
	 * @return the number of line delete
	 */
	public static int deleteSupplier(long id) {
		return Dao.deleteLine("Supplier", id);
	}

	/**
	 * Update a supplier.
	 * 
	 * @param supplier
	 *            supplier with new data
	 * @return number of line updated
	 */
	public static int updateSupplier(Supplier supplier) {
		// Update Supplier
		int retour = updateLine("Supplier", supplier);
		// Update Supplier Product Map
		updateSupplierProductMap(supplier.getProductList(), supplier.getId());

		return retour;
	}

	/**
	 * update the list of products of a supplier.
	 * 
	 * @param mapNew
	 *            new map
	 * @param sprId
	 *            id of the supplier
	 * @return number of line updated
	 */
	public static int updateSupplierProductMap(HashMap<Long, Double> mapNew, long sprId) {
		int rowUpdated = 0;
		// Update Supplier Product Map
		HashMap<Long, Double> mapOld = SupplierDao.getSupplier(sprId).getProductList();
		Iterator<Entry<Long, Double>> iteratorOld = mapOld.entrySet().iterator();
		Iterator<Entry<Long, Double>> iteratorNew = mapNew.entrySet().iterator();
		// add new row
		while (iteratorNew.hasNext()) {
			Entry<Long, Double> entry = (Entry<Long, Double>) iteratorNew.next();
			Long key = entry.getKey();
			if (!mapOld.containsKey(key)) {
				SupplierDao.addSupplierProduct(sprId, entry.getKey(), entry.getValue());
				rowUpdated++;
			} else {
				// update changed row
				// just update price of product in supplier product list
				if (!mapOld.get(key).equals(mapNew.get(key))) {
					SupplierDao.updateSupplierProductRow(sprId, key, mapNew.get(key));
					rowUpdated++;
				}
			}
		}
		// delete row that no longer existed
		while (iteratorOld.hasNext()) {
			Entry<Long, Double> entry = (Entry<Long, Double>) iteratorOld.next();
			if (!mapNew.containsKey(entry.getKey())) {
				SupplierDao.deleteSupplierProduct(sprId, entry.getKey());
				rowUpdated++;
			}
		}
		return rowUpdated;
	}

	/**
	 * update A line of the list of product for a supplier.
	 * 
	 * @param sprId
	 *            id of supplier
	 * @param pdtId
	 *            id of product
	 * @param price
	 *            price of the product for this supplier
	 * @return number of line updated
	 */
	public static int updateSupplierProductRow(long sprId, long pdtId, double price) {
		// just update price
		int retour;
		double[] param = { (double) sprId, (double) pdtId, price };
		retour = Dao.updateLine("spl", param);
		return retour;
	}

	/**
	 * add a new product in the supplier's products list.
	 * 
	 * @param sprId
	 *            the ID of the supplier to add
	 * @param pdtId
	 *            the ID of the product for add
	 * @param price
	 *            the price of the product
	 * @return the number of products add in the table
	 */
	// TODO if return 0 means this product is already existed
	// in the list of this supplier
	// Should show a warning panel
	public static int addSupplierProduct(long sprId, long pdtId, Double price) {
		double[] param = { Double.valueOf(sprId), Double.valueOf(pdtId), price };
		int retour = 0;
		retour = Dao.addLine("spl", param);
		// update the product
		Product product = ProductDao.getProduct(pdtId);
		product.setSupplierId(sprId);
		product.setSupplierName(SupplierDao.getSupplier(sprId).getName());
		if (ProductDao.updateProduct(product) != 1) {
			System.out.println("Update product failed");
		}

		return retour;
	}

	/**
	 * get one row for the table sprpdtlist_spl, knowing sprId and pdtId.
	 * 
	 * @param sprId
	 *            supplier's id
	 * @param pdtId
	 *            pdt's id
	 * @return SupplierProductPrice, with the price
	 */
	public static SupplierProductPrice getSupplierProductPrice(long sprId, long pdtId) {
		SupplierProductPrice supplierProductPrice = new SupplierProductPrice(sprId, pdtId, 0d);
		String sql = "SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ? AND spl_pdt_id = ?";
		return (SupplierProductPrice) Dao.getOne("SupplierProductPrice", sql, supplierProductPrice);
	}

	/**
	 * search the best price of a product in all supplier.
	 * 
	 * @param pdtId
	 *            product's id
	 * @return SupplierProductPrice with supplier's id who give the best price,
	 *         and the price.
	 */
	public static SupplierProductPrice getBestPriceOfAProduct(long pdtId) {
		String sql = "SELECT * FROM sprpdtlist_spl " + "WHERE spl_pdt_id = ? "
				+ "AND spl_pdt_price = ( SELECT MIN(spl_pdt_price) FROM sprpdtlist_spl " + "WHERE spl_pdt_id = ?)";
		return (SupplierProductPrice) Dao.getOne("BestPrice", sql, pdtId);
	}

	/**
	 * get the list of product of a supplier from the db.
	 * 
	 * @param sprId
	 *            ID of the supplier for get the list
	 * @return the list of product of the supplier
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<Long, Double> getSupplierProductMap(long sprId) {
		String sql = "SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?";
		HashMap<Long, Double> sprpdtlist = (HashMap<Long, Double>) Dao.getList("spl", sql, 1, sprId);
		return sprpdtlist;
	}

	/**
	 * delete a product from the product list of a supplier.
	 * 
	 * @param sprId
	 *            id of supplier for delete a product
	 * @param pdtId
	 *            id of product for delete
	 * @return the number of line deleted
	 */
	public static int deleteSupplierProduct(long sprId, long pdtId) {
		// need to pass two param
		long[] param = { sprId, pdtId };
		return Dao.deleteLine("spl", param);
	}

	// public int updateSupplierProduct(Supplier supplier) {
	// return updateLine("Supplier", supplier);
	// }

	public static long nextSprId() {
		return Dao.nextId("Supplier");
	}

	public static long nextSplId() {
		return Dao.nextId("spl");
	}

	/**
	 * main method, for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		// System.out.println("id gen next spl_id = " +
		// dao.idGenerator_Sprpdtlist_spl());
		// System.out.println("id gen next spr_id = " +
		// dao.idGenerator_Supplier_spr());
		// dao.addSupplier(new Supplier("sprrrrrr"));

		System.out.println("Get List : ");
		System.out.println(SupplierDao.getSupplierList().toString());
		// System.out.println("Get Supplier id 2");
		// System.out.println(dao.getSupplier(2).toString());
		// System.out.println("Get Supplier Product Map of spr 2");
		// System.out.println(dao.getSupplierProductMap(2).toString());
		// System.out.println("Add Supplier");
		// System.out.println(dao.addSupplier(new Supplier("add test2")));
		// System.out.println("Add Supplier Product List");
		// System.out.println(dao.addSupplierProduct(1, (Long)1l, (Double)11d));
		// System.out.println(dao.addSupplierProduct(1, (Long)2l, (Double)22d));
		// System.out.println(dao.addSupplierProduct(1, (Long)3l, (Double)33d));
		// System.out.println(dao.addSupplierProduct(1, (Long)4l, (Double)44d));
		// System.out.println(dao.addSupplierProduct(1, (Long)5l, (Double)55d));
		// System.out.println("Delete Supplier");
		// System.out.println(dao.deleteSupplier(1));
		// System.out.println("Update Supplier 1");
		// HashMap<Long, Double> oldmap = dao.getSupplierProductMap(1);
		// oldmap.remove(7L);
		// oldmap.remove(6L);
		// oldmap.put(3L, 3d);
		// oldmap.put(4L, 4d);
		// oldmap.put(5L, 5d);
		// oldmap.put(2L, 2.22d);
		// oldmap.put(1L, 1.1d);
		// System.out.println(dao.updateSupplier(new Supplier(1,
		// "UpdatedNameOOOO", oldmap)));

		// System.out.println(dao.updateSupplierProductRow(1l, 3l, 111.11d));
		//
		// System.out.println("Next spr id : ");
		// System.out.println(SupplierDAO.nextSprId());
		// System.out.println("Next spl id : ");
		// System.out.println(SupplierDAO.nextSplId());
	}
}
