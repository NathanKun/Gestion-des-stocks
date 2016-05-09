package gui;

import dao.ProductDao;
import util.Regex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * The replenish manually gui.
 * 
 * @author HE Junyang
 *
 */
public class ReplenishManuallyGui extends SearchProductGui {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8985150112994820870L;
	/**
	 * text field for enter the quantity for replenishment.
	 */
	private JTextField jtfQuantity;
	/**
	 * label : replenish quantity.
	 */
	private JLabel jlReplenishQuantity;
	/**
	 * button for replenish.
	 */
	private JButton jbReplenish;

	/**
	 * Constructor of gui.
	 */
	public ReplenishManuallyGui(OrderGui owner) {
		this.setSize(400, 640);
		this.setResizable(false);
		this.setTitle("Replenish");
		this.getContentPane().setLayout(null);

		jbBack.setSize(104, 28);
		jbCheapestSupplier.setLocation(102, 38);
		jbBack.setLocation(270, 38);
		jpButtonPane.setBounds(10, 538, 374, 73);

		jtfQuantity = new JTextField();
		jtfQuantity.setBounds(156, 4, 103, 22);
		jpButtonPane.add(jtfQuantity);
		jtfQuantity.setColumns(10);

		jlReplenishQuantity = new JLabel("Replenish Quantity:");
		jlReplenishQuantity.setBounds(0, 6, 164, 18);
		jpButtonPane.add(jlReplenishQuantity);

		jbReplenish = new JButton("Replenish");
		jbReplenish.setBounds(271, 1, 103, 28);
		jpButtonPane.add(jbReplenish);
		jbReplenish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				// check isInteger
				if (Regex.isInteger(jtfQuantity.getText())) {
					if (Integer.parseInt(jtfQuantity.getText()) + selectedProduct.getStock() < 999) {
						selectedProduct.addStock(Integer.parseInt(jtfQuantity.getText()));
						ProductDao.updateProduct(selectedProduct);
						JOptionPane.showConfirmDialog(null, "Product replenished.", "Confirm",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						jtfQuantity.setText("");
						refreshTable();

					} else {
						JOptionPane.showConfirmDialog(null, "Stock shouldn't over 999.", "Errors",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Please enter an integer.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		jbBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
				owner.setVisible(true);
			}
		});

		jbCheapestSupplier.setVisible(false);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 *            for main.
	 */
	public static void main(String[] args) {
		new ReplenishManuallyGui(null);
	}
}