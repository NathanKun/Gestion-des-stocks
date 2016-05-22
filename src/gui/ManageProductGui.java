package gui;

import dao.ProductDao;
import gds.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Class GUI for the product management.
 * 
 * @author HE Junyang - - FOTSING KENGNE Junior
 *
 */
public class ManageProductGui extends SearchProductGui implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3326819848799489937L;
	/**
	 * Button for launch the jDialog for create a product.
	 */
	private JButton jbNew;
	/**
	 * Button for delete a product.
	 */
	private JButton jbDelete;
	/**
	 * Button for launch the jDialog for edit a product.
	 */
	private JButton jbEdit;
	/**
	 * The user who logged in.
	 */
	private User user;
	/**
	 * Button to choose a supplier.
	 */
	private JButton jbChooseSupplier;

	/**
	 * Constructor of the class. Init the components.
	 * 
	 * @param user
	 *            user who logged in
	 */
	public ManageProductGui(User user) {
		super();
		this.setTitle("Product Management");
		this.user = user;
		this.setSize(400, 640);
		jpButtonPane.setSize(374, 65);
		jpButtonPane.setLocation(10, 536);
		jpButtonPane.setLayout(null);
		getContentPane().setLayout(null);

		jbNew = new JButton("New");
		jbDelete = new JButton("Delete");
		jbEdit = new JButton("Edit");

		jbNew.setBounds(24, 41, 80, 23);
		jbDelete.setBounds(204, 41, 80, 23);
		jbEdit.setBounds(114, 41, 80, 23);
		jbBack.setBounds(294, 41, 80, 23);
		jbCheapestSupplier.setBounds(24, 8, 170, 23);

		jpButtonPane.add(jbNew);
		jpButtonPane.add(jbDelete);
		jpButtonPane.add(jbEdit);

		jbChooseSupplier = new JButton("Choose supplier");
		jbChooseSupplier.setBounds(204, 8, 170, 23);
		jpButtonPane.add(jbChooseSupplier);

		jbNew.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDelete.addActionListener(this);
		jbChooseSupplier.addActionListener(this);
		jbCheapestSupplier.addActionListener(this);
		jbBack.addActionListener(this);

		// X do the same thing as the back button
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				new MainGui(user);
				dispose();
			}
		});
	}

	/**
	 * delete the product selected.
	 */
	private void deleteProduct() {
		// if a product is selected
		if (selectedProduct != null) {
			String text = "Do you really want to delete product " + selectedProduct.getName() + " ?";
			// ask if really want to delete a product
			if (JOptionPane.showConfirmDialog(this, text, "Comfirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == 0) {
				// y for 0, n for 1
				if (ProductDao.deleteProduct(selectedProduct.getId()) == 1) {
					JOptionPane.showConfirmDialog(this, "Product deleted", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(this, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				// refresh the list after deleting
				refreshList();
			}
		} else {
			JOptionPane.showConfirmDialog(this, "Please select a product.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * action perform after a button is on clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == jbNew) {
			// add a new product
			new ProductDialog(this, true, null);
		} else if (ev.getSource() == jbEdit) {
			// edit a product
			if (selectedProduct != null) {
				new ProductDialog(this, true, selectedProduct);
			} else {
				JOptionPane.showConfirmDialog(this, "Please select a product.", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (ev.getSource() == jbDelete) {
			// delete a product
			deleteProduct();
		} else if (ev.getSource() == jbChooseSupplier) {
			if (selectedProduct != null) {
				new SearchSupplierForChoose(this, selectedProduct.getId());
				this.setVisible(false);
			} else {
				JOptionPane.showConfirmDialog(this, "Please select a product.", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (ev.getSource() == jbBack) {
			dispose();
			new MainGui(user);
		}
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		new ManageProductGui(null);
	}
}
