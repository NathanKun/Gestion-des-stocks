package gui;

import dao.ProductDao;
import gds.Product;
import gds.Supplier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * A window to search a supplier in order to affect the product selected in this
 * window's owner.
 * 
 * @author HE Junyang
 *
 */
public final class SearchSupplierForChoose extends SearchSupplierGui {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1870771269504970453L;
	/**
	 * button for affect the product selected to a supplier.
	 */
	private JButton jbChosse;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 *            owner of this window.
	 * @param pdtId
	 *            the id of product for choose a supplier.
	 */
	public SearchSupplierForChoose(ManageProductGui owner, long pdtId) {
		super();
		jbBack.setSize(100, 23);

		chargeSupplierList(pdtId);

		jbChosse = new JButton("Chosse");

		jpButtonPane.setBounds(12, 530, 370, 40);
		jpButtonPane.setLayout(null);

		jbBack.setLocation(258, 12);
		jbChosse.setBounds(146, 12, 100, 23);

		jpButtonPane.add(jbChosse);
		getContentPane().setLayout(null);

		jbBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
				owner.setVisible(true);
				owner.refreshList();
			}
		});

		jbChosse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				if (selectedSupplier != null) {
					Product pdt = ProductDao.getProduct(pdtId);
					pdt.setSupplierId(selectedSupplier.getId());
					pdt.setSupplierName(selectedSupplier.getName());
					pdt.setPrice(selectedSupplier.getProductList().get(pdtId));
					if (ProductDao.updateProduct(pdt) == 1) {
						JOptionPane.showConfirmDialog(null, "Supplier chose.\nProduct updated.", "Confirm",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						dispose();
						owner.refreshList();
						owner.setVisible(true);
					} else {
						JOptionPane.showConfirmDialog(null, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "You need to choose a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * charge supplier filtered in the jList. Only show the suppliers which
	 * supply the product selected.
	 * 
	 * @param pdtId
	 *            id of the product selected.
	 */
	private void chargeSupplierList(long pdtId) {

		for (int i = listModel.getSize(); i > 0; i--) {
			((DefaultListModel<String>) listModel).remove(i - 1);
		}

		ArrayList<Supplier> filteredSupplierList = new ArrayList<Supplier>();
		for (Supplier spr : this.supplierList) {
			if (spr.getProductList().containsKey(pdtId)) {
				filteredSupplierList.add(spr);
			}
		}
		supplierList = filteredSupplierList;
		for (Supplier s : supplierList) {
			((DefaultListModel<String>) listModel).addElement(s.getName());
		}
	}
}
