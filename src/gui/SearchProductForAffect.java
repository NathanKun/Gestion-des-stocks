package gui;

import dao.SupplierDao;
import util.Regex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * A window for selected a product and affect it to a supplier.
 * 
 * @author HE Junyang
 *
 */
public class SearchProductForAffect extends SearchProductGui {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4945322460067251036L;
	/**
	 * text field of product price.
	 */
	private JTextField jtfPrice;
	/**
	 * Label if price.
	 */
	private JLabel jlPrice;
	/**
	 * button for affect the product selected to a supplier.
	 */
	private JButton jbAffect;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 *            owner of this window.
	 * @param sprId
	 *            the id of supplier for affect a product.
	 */
	public SearchProductForAffect(ManageSupplierGui owner, long sprId) {
		super();
		this.setSize(400, 630);

		jbBack.setLocation(297, 35);
		jbCheapestSupplier.setLocation(0, 35);
		jpButtonPane.setSize(374, 64);
		jpButtonPane.setLocation(12, 537);

		jbAffect = new JButton("Affect");
		jbAffect.setBounds(297, 5, 77, 23);
		jpButtonPane.add(jbAffect);

		jlPrice = new JLabel("Price :");
		jlPrice.setBounds(0, 8, 55, 18);
		jpButtonPane.add(jlPrice);

		jtfPrice = new JTextField();
		jtfPrice.setBounds(65, 6, 156, 22);
		jpButtonPane.add(jtfPrice);
		jtfPrice.setColumns(10);
		jbCheapestSupplier.setVisible(false);

		jbBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
				owner.setVisible(true);
				owner.refreshList();
			}
		});

		jbAffect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				// if user has selected a product
				if (selectedProduct != null) {
					System.out.println(selectedProduct);
					// if user has entered something in text field
					if (!jtfPrice.getText().isEmpty()) {
						// if that thing in the text field is a correct price
						// (double)
						if (Regex.isDouble(jtfPrice.getText())) {
							// if the product has not already affected to this
							// supplier
							if (SupplierDao.addSupplierProduct(sprId, selectedProduct.getId(),
									Double.parseDouble(jtfPrice.getText())) == 1) {
								JOptionPane.showConfirmDialog(null, "Product affected", "Confirm",
										JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
								jtfPrice.setText("");
							} else {
								// show product already affected message
								JOptionPane.showConfirmDialog(null, "Product already affected", "Error",
										JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
							}
						} else {
							// show price not correct message
							JOptionPane.showConfirmDialog(null, "Please enter a correct price.", "Error",
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
					} else {
						// show price not entered message
						JOptionPane.showConfirmDialog(null,
								"Please enter a price for this product offered by this supplier.", "Error",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				} else {
					// show product not selected message
					JOptionPane.showConfirmDialog(null, "Please select a product offered by this supplier.", "Error",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
