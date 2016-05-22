package gui;

import dao.ProductDao;
import dao.SupplierDao;
import gds.Product;
import gds.Supplier;
import gds.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManageSupplierGui extends SearchSupplierGui implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5897289525664057741L;
	/**
	 * Button for launch the jDialog for create a supplier.
	 */
	private JButton jbNew;
	/**
	 * Button for delete a supplier.
	 */
	private JButton jbDelete;
	/**
	 * Button for launch the jDialog for edit a supplier.
	 */
	private JButton jbEdit;
	/**
	 * button for remove a product from a supplier.
	 */
	private JButton jbRemoveProduct;
	/**
	 * button link to affect product gui.
	 */
	private JButton jbAffectProduct;
	/**
	 * the id of the product selected.
	 */
	private long seletedProductId;

	/**
	 * Create the dialog.
	 * 
	 * @param user
	 *            user who logged in
	 */
	public ManageSupplierGui(User user) {
		super();
		this.setTitle("Supplier Management");
		this.setSize(400, 640);
		jpButtonPane.setBounds(0, 536, 394, 65);
		jpButtonPane.setLayout(null);
		this.getContentPane().setLayout(null);

		jbNew = new JButton("New");
		jbDelete = new JButton("Delete");
		jbEdit = new JButton("Edit");
		jbRemoveProduct = new JButton("Remove product");
		jbAffectProduct = new JButton("Affect product");

		jbNew.setBounds(127, 8, 80, 23);
		jbDelete.setBounds(307, 8, 80, 23);
		jbEdit.setBounds(217, 8, 80, 23);
		jbBack.setBounds(307, 41, 80, 23);
		jbRemoveProduct.setBounds(167, 41, 130, 23);
		jbAffectProduct.setBounds(27, 41, 130, 23);

		jpButtonPane.add(jbNew);
		jpButtonPane.add(jbDelete);
		jpButtonPane.add(jbEdit);
		jpButtonPane.add(jbRemoveProduct);
		jpButtonPane.add(jbAffectProduct);

		jbNew.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDelete.addActionListener(this);
		jbRemoveProduct.addActionListener(this);
		jbAffectProduct.addActionListener(this);

		// back button on click -> show main gui
		jbBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
				new MainGui(user);
			}
		});

		// product list listner
		jtbPdtList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent ev) {
				int[] selectedRow = jtbPdtList.getSelectedRows();
				if (selectedRow.length != 0) {
					seletedProductId = (long) jtbPdtList.getValueAt(selectedRow[0], 0);
				}
			}
		});

		// X do the same thing as the back button
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				new MainGui(user);
				dispose();
			}
		});

		this.setVisible(true);
	}

	/**
	 * Refresh the jList of supplier.
	 */
	public void refreshList() {
		supplierList = SupplierDao.getSupplierList();
		// clear the list

		for (int i = listModel.getSize(); i > 0; i--) {
			((DefaultListModel<String>) listModel).remove(i - 1);
		}
		// add filtered result in the list
		for (Supplier s : supplierList) {
			((DefaultListModel<String>) listModel).addElement(s.getName());
		}
	}

	/**
	 * delete the supplier selected.
	 */
	private void deleteSupplier() {
		// if a supplier is selected
		if (selectedSupplier != null) {
			String text = "Do you really want to delete supplier " + selectedSupplier.getName() + " ?";
			// ask if really want to delete a supplier
			if (JOptionPane.showConfirmDialog(this, text, "Comfirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == 0) {
				// reset the products' supplier data which the current supplier
				// of the product is this supplier
				HashMap<Long, Double> productMapOfSupplier = SupplierDao
						.getSupplierProductMap(selectedSupplier.getId());
				Iterator<Entry<Long, Double>> ite = productMapOfSupplier.entrySet().iterator();
				long sprId = selectedSupplier.getId();
				while (ite.hasNext()) {
					Entry<Long, Double> entry = ite.next();
					Product product = ProductDao.getProduct(entry.getKey());
					if (product.getSupplierId() == sprId) {
						product.setSupplierId(0);
						product.setPrice(0.0d);
						product.setSupplierName(null);
						ProductDao.updateProduct(product);
					}
				}

				// delete supplier
				if (SupplierDao.deleteSupplier(selectedSupplier.getId()) == 1) {
					JOptionPane.showConfirmDialog(this, "Supplier deleted", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(this, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				// refresh the list after deleting
				refreshList();
			}
		} else {
			JOptionPane.showConfirmDialog(this, "Please select a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * remove the product selected from a supplier.
	 */
	private void removeProduct() {
		if (seletedProductId != 0) {
			if (JOptionPane.showConfirmDialog(this, "Do you really want to remove this product from this supplier?",
					"Comfirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				if (SupplierDao.deleteSupplierProduct(selectedSupplier.getId(), seletedProductId) == 1) {
					JOptionPane.showConfirmDialog(this, "Product deleted", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);

					// reset the product's supplier data if this supplier is
					// this product's current supplier.
					Product pdt = ProductDao.getProduct(seletedProductId);
					if (pdt.getSupplierId() == selectedSupplier.getId()) {
						pdt.setSupplierId(0L);
						pdt.setSupplierName(null);
						pdt.setPrice(0.00d);
						ProductDao.updateProduct(pdt);
					}

					seletedProductId = 0;
					chargeProductListTable();
				} else {
					JOptionPane.showConfirmDialog(this, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showConfirmDialog(this, "Please selected a product.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * action perform after a button is on clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == jbNew) {
			// add a new supplier
			new SupplierDialog(this, true, null);
		} else if (ev.getSource() == jbEdit) {
			// edit a supplier
			if (selectedSupplier != null) {
				new SupplierDialog(this, true, selectedSupplier);
			} else {
				JOptionPane.showConfirmDialog(this, "Please select a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (ev.getSource() == jbDelete) {
			// delete a supplier
			deleteSupplier();
		} else if (ev.getSource() == jbAffectProduct) {
			// affect a product to a supplier
			if (selectedSupplier != null) {
				new SearchProductForAffect(this, selectedSupplier.getId());
				this.setVisible(false);
			} else {
				JOptionPane.showConfirmDialog(this, "Please select a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (ev.getSource() == jbRemoveProduct) {
			removeProduct();
		}
	}

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		try {
			new ManageSupplierGui(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}