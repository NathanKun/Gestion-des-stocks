package src.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import src.gds.Product;

import src.gds.Supplier;

/**
 * Data access object for Supplier
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 *
 */
public class SupplierDAO extends DAO {
	/**
	 * add product in the date base
	 * 
	 * @param supplier
	 *            contain the new supplier o add
	 * @return retour 1 if the adding is ok and 0 if not
	 */
	public int addSupplier(Supplier supplier) {
		/*
		 * Connection con = null; PreparedStatement ps = null; int retour = 0;
		 * // connection to date base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement(
		 * "INSERT INTO supplier_spr (spr_id, spr_name) VALUES(sprid_seq.NEXTVAL,?)"
		 * ); // ps.setLong(1, supplier.getId()); ps.setString(1,
		 * supplier.getName());
		 * 
		 * // excecution of the requiere retour = ps.executeUpdate(); } catch
		 * (Exception e) { e.printStackTrace(); } finally { // close
		 * preparedStatement and connexion try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { System.out.println("closing problem"); }
		 * try { if (con != null) con.close(); } catch (Exception ignore) {
		 * System.out.println("closing problem"); } } return retour;
		 */
		return this.addLine("Supplier", supplier);
	}

	/**
	 * return a specific supplier by his identifiant
	 * 
	 * @param id
	 *            identifiant of the supplier
	 * @return the supplier
	 */
	public Supplier getSupplier(long id) {
		/*
		 * Connection con = null; PreparedStatement ps = null; ResultSet rs =
		 * null; Supplier retour = null;
		 * 
		 * // connection to the data base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement("SELECT *FROM supplier_spr WHERE spr_id = ?");
		 * ps.setLong(1, id); rs = ps.executeQuery(); if (rs.next()) retour =
		 * new Supplier(rs.getLong("spr_id"), rs.getString("spr_name"),
		 * this.getSupplierProductList(id)); } catch (Exception ee) {
		 * ee.printStackTrace(); } finally { // closing of ResultSet,
		 * PreparedStatement and connection try { if (rs != null) rs.close(); }
		 * catch (Exception ignore) { } try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { } try { if (con != null) con.close(); }
		 * catch (Exception ignore) { } } return retour;
		 */
		String sql = "SELECT *FROM supplier_spr WHERE spr_id = ?";
		return (Supplier) this.getOne("Supplier", sql, id);
	}

	/**
	 * allow to have the full list of the suppliers presents in the data base
	 * 
	 * @return the list of all the suppliers in the data base
	 */
	public ArrayList<Supplier> getSupplierList() {
		/*
		 * Connection con = null; PreparedStatement ps = null; ResultSet rs =
		 * null; ArrayList<Supplier> retour = new ArrayList<Supplier>(); //
		 * connection to the data base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement("SELECT *FROM Supplier_spr");
		 * 
		 * // request execution rs = ps.executeQuery();
		 * 
		 * // we cross all the line of the results while (rs.next()) { long id =
		 * rs.getLong("spr_id"); retour.add(new Supplier(id,
		 * rs.getString("spr_name"), this .getSupplierProductList(id))); } }
		 * catch (Exception ee) { ee.printStackTrace(); } finally { // closing
		 * rs, PreparedStatement and connection try { if (rs != null)
		 * rs.close(); } catch (Exception ignore) { } try { if (ps != null)
		 * ps.close(); } catch (Exception ignore) { } try { if (con != null)
		 * con.close(); } catch (Exception ignore) { } } return retour;
		 */
		String sql = "SELECT *FROM Supplier_spr";
		return (ArrayList<Supplier>) this.getList("Supplier", sql, 0, 0l);
	}

	/**
	 * delete a supplier in the data base suppliers list
	 * 
	 * @param id
	 *            contain the id of the supplier we want to delete
	 * @return the number of line delete
	 */
	public int deleteSupplier(long id) {
		/*
		 * Connection con = null; PreparedStatement ps = null; int retour = 0;
		 * // connection to date base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement("DELETE FROM supplier_spr WHERE spr_id=?");
		 * ps.setLong(1, id);
		 * 
		 * // excecution of the requiere retour = ps.executeUpdate(); } catch
		 * (Exception e) { e.printStackTrace(); } finally { // close
		 * preparedStatement and connexion try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { System.out.println("closing problem"); }
		 * try { if (con != null) con.close(); } catch (Exception ignore) {
		 * System.out.println("closing problem"); } } return retour;
		 */

		// delete supplier
		// supplier product list deleted on cascade
		return this.deleteLine("Supplier", id);
	}

	public int updateSupplier(Supplier supplier) {
		// Update Supplier
		int retour = updateLine("Supplier", supplier);
		// Update Supplier Product Map
		updateSupplierProductMap(supplier.getProductList(), supplier.getId());

		return retour;
	}

	public int updateSupplierProductMap(HashMap<Long, Double> mapNew, long sprId) {
		int rowUpdated = 0;
		// Update Supplier Product Map
		HashMap<Long, Double> mapOld = this.getSupplier(sprId).getProductList();
		Iterator<Entry<Long, Double>> iteratorOld = mapOld.entrySet().iterator();
		Iterator<Entry<Long, Double>> iteratorNew = mapNew.entrySet().iterator();
		// add new row
		while (iteratorNew.hasNext()) {
			Entry<Long, Double> entry = (Entry<Long, Double>) iteratorNew.next();
			Long key = entry.getKey();
			if (!mapOld.containsKey(key)) {
				this.addSupplierProduct(sprId, entry.getKey(), entry.getValue());
				rowUpdated++;
				// System.out.println("New row");
			} else {
				// update changed row
				// just update price of product in supplier product list
				if (!mapOld.get(key).equals(mapNew.get(key))) {
					this.updateSupplierProductRow(sprId, key, mapNew.get(key));
					rowUpdated++;
					// System.out.println("Changed row");
					// System.out.println(
					// "key = " + key + "old value = " + mapOld.get(key) + "new
					// value = " + mapNew.get(key));
				}
			}
		}
		// delete row that no longer existed
		while (iteratorOld.hasNext()) {
			Entry<Long, Double> entry = (Entry<Long, Double>) iteratorOld.next();
			if (!mapNew.containsKey(entry.getKey())) {
				this.deleteSupplierProduct(sprId, entry.getKey());
				rowUpdated++;
				// System.out.println("Deleted row");
			}
		}
		return rowUpdated;
	}

	public int updateSupplierProductRow(long sprId, long pdtId, double price) {
		// just update price
		int retour;
		double[] param = { (double) sprId, (double) pdtId, price };
		retour = this.updateLine("spl", param);
		return retour;
	}

	/**
	 * add a new product in the supplier's products list
	 * 
	 * @param sprId
	 *            the ID of the supplier to add
	 * @param pdtId
	 *            the ID of the product for add
	 * @param price
	 *            the price of the product
	 * @return the number of products add in the table
	 */
	// if return 0 means this product is already existed
	// in the list of this supplier
	// Should show a warning panel
	public int addSupplierProduct(long sprId, long pdtId, Double price) {
		/*
		 * HashMap<Long, Double> map = this.getSupplierProductList(sprId); if
		 * (map.containsKey(pdtId)) return 0; Connection con = null;
		 * PreparedStatement ps = null; int retour = 0; // connection to date
		 * base try { con = DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement(
		 * "INSERT INTO sprpdtlist_spl (spl_id, spl_spr_id, spl_pdt_id, spl_pdt_price) VALUES(splid_seq.NEXTVAL,?,?,?)"
		 * ); // ps.setLong(1, idGenerator_Sprpdtlist_spl()); ps.setLong(1,
		 * sprId); ps.setLong(2, pdtId); ps.setDouble(3, price);
		 * 
		 * // excecution of the requiere retour = ps.executeUpdate(); } catch
		 * (Exception e) { e.printStackTrace(); } finally { // close
		 * preparedStatement and connexion try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { System.out.println("closing problem"); }
		 * try { if (con != null) con.close(); } catch (Exception ignore) {
		 * System.out.println("closing problem"); } } return retour;
		 */
		double[] param = { Double.valueOf(sprId), Double.valueOf(pdtId), price };
		return this.addLine("spl", param);
	}

	/**
	 * get the list of product of a supplier from the db
	 * 
	 * @param sprId
	 *            ID of the supplier for get the list
	 * @return the list of product of the supplier
	 */
	public HashMap<Long, Double> getSupplierProductMap(long sprId) {
		/*
		 * Connection con = null; PreparedStatement ps = null; ResultSet rs =
		 * null; HashMap<Long, Double> retour = new HashMap<Long, Double>();
		 * 
		 * // connection to the data base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement(
		 * "SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?"); ps.setLong(1,
		 * sprId); rs = ps.executeQuery(); while (rs.next())
		 * retour.put(rs.getLong("spl_pdt_id"), rs.getDouble("spl_pdt_price"));
		 * } catch (Exception ee) { ee.printStackTrace(); } finally { // closing
		 * of ResultSet, PreparedStatement and connection try { if (rs != null)
		 * rs.close(); } catch (Exception ignore) { } try { if (ps != null)
		 * ps.close(); } catch (Exception ignore) { } try { if (con != null)
		 * con.close(); } catch (Exception ignore) { } } return retour;
		 */
		String sql = "SELECT * FROM sprpdtlist_spl WHERE spl_spr_id = ?";
		HashMap<Long, Double> sprpdtlist = (HashMap<Long, Double>) this.getList("spl", sql, 1, sprId);
		return sprpdtlist;
	}

	/**
	 * delete a product from the product list of a supplier
	 * 
	 * @param sprId
	 *            id of supplier for delete a product
	 * @param pdtId
	 *            id of product for delete
	 * @return the number of line deleted
	 */
	// TODO untested
	public int deleteSupplierProduct(long sprId, long pdtId) {
		/*
		 * Connection con = null; PreparedStatement ps = null; int retour = 0;
		 * // connection to date base try { con =
		 * DriverManager.getConnection(URL, LOGIN, PASS); ps =
		 * con.prepareStatement("DELETE FROM sprpdtlist_spl " +
		 * "WHERE spl_spr_id = ? AND spl_pdt_id = ?"); ps.setLong(1, sprId);
		 * ps.setLong(2, pdtId);
		 * 
		 * // excecution of the requiere retour = ps.executeUpdate(); } catch
		 * (Exception e) { e.printStackTrace(); } finally { // close
		 * preparedStatement and connexion try { if (ps != null) ps.close(); }
		 * catch (Exception ignore) { System.out.println("closing problem"); }
		 * try { if (con != null) con.close(); } catch (Exception ignore) {
		 * System.out.println("closing problem"); } } return retour;
		 */

		// need to pass two param
		long[] param = { sprId, pdtId };
		return this.deleteLine("spl", param);
	}

	public int updateSupplierProduct(Supplier supplier) {
		return updateLine("Supplier", supplier);
	}

	/**
	 * generate a new id for the supplier list product
	 * 
	 * @return retour the new id of the sprpdtlist_spl
	 */
	public long idGenerator_Sprpdtlist_spl() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(spl_id) FROM sprpdtlist_spl");

			// excecution of the requiere
			rs = ps.executeQuery();
			// recuperation of number
			rs.next();
			retour = rs.getLong(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connection
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return (retour + 1);
	}

	/**
	 * generate a new id for a supplier
	 * 
	 * @return retour the new id of the supplier
	 */
	public long idGenerator_Supplier_spr() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT MAX(spr_id) FROM supplier_spr");

			// excecution of the requiere
			rs = ps.executeQuery();
			// recuperation of number
			rs.next();
			retour = rs.getLong(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connection
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return (retour + 1);
	}

	/**
	 * main method, for testing
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		SupplierDAO dao = new SupplierDAO();
		// System.out.println("id gen next spl_id = " +
		// dao.idGenerator_Sprpdtlist_spl());
		// System.out.println("id gen next spr_id = " +
		// dao.idGenerator_Supplier_spr());
		// dao.addSupplier(new Supplier("sprrrrrr"));

		// System.out.println("Get List : ");
		// System.out.println(dao.getSupplierList().toString());
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
		System.out.println("Update Supplier 1");
		HashMap<Long, Double> oldmap = dao.getSupplierProductMap(1);
		oldmap.remove(7l);
		oldmap.remove(6l);
		oldmap.put(3l, 3d);
		oldmap.put(4l, 4d);
		oldmap.put(5l, 5d);
		oldmap.put(2l, 2.22d);
		oldmap.put(1l, 1.1d);
		System.out.println(dao.updateSupplier(new Supplier(1, "UpdatedNameOOOO", oldmap)));

		// System.out.println(dao.updateSupplierProductRow(1l, 3l, 111.11d));
	}
}
