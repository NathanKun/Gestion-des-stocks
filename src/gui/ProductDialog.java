package gui;

import dao.ProductDao;
import gds.Product;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * The dialog for create or edit a product.
 * 
 * @author HE Junyang
 *
 */
public final class ProductDialog extends JDialog implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2256384921011685347L;
	/**
	 * main content panel.
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * text field for the product id.
	 */
	private JTextField jtfId;
	/**
	 * text field for the product name.
	 */
	private JTextField jtfName;
	/**
	 * label : name.
	 */
	private JLabel jlName;
	/**
	 * label : id.
	 */
	private JLabel jlId;
	/**
	 * label : title.
	 */
	private JLabel jlTitle;
	/**
	 * panel of the buttons.
	 */
	private JPanel jpButtonPane;
	/**
	 * OK button.
	 */
	private JButton jbOk;
	/**
	 * cancel button.
	 */
	private JButton jbCancel;
	/**
	 * the product selected.
	 */
	private Product pdt;

	/**
	 * Constructor of the dialog.
	 * 
	 * @param owner
	 *            owner of this dialog
	 * @param modal
	 *            is the father Frame selectable
	 * @param pdt
	 *            the product for edit
	 */
	public ProductDialog(Frame owner, boolean modal, Product pdt) {
		super(owner, modal);
		this.pdt = pdt;

		this.setSize(400, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		jlId = new JLabel("ID : ");
		jlId.setSize(50, 20);
		jlId.setLocation(10, 35);
		jlName = new JLabel("Name : ");
		jlName.setBounds(10, 65, 50, 20);

		if (pdt == null) {
			jlTitle = new JLabel("New product :");
		} else {
			jlTitle = new JLabel("Edit a product :");
		}

		jlTitle.setBounds(10, 5, 100, 20);

		jtfId = new JTextField();
		jtfId.setBounds(70, 35, 246, 20);
		jtfName = new JTextField();
		jtfName.setBounds(70, 65, 246, 20);

		contentPanel.setLayout(null);
		contentPanel.add(jlId);
		contentPanel.add(jtfId);
		contentPanel.add(jlName);
		contentPanel.add(jtfName);
		contentPanel.add(jlTitle);

		jpButtonPane = new JPanel();
		jpButtonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(jpButtonPane, BorderLayout.SOUTH);

		jbOk = new JButton("OK");
		jpButtonPane.add(jbOk);
		getRootPane().setDefaultButton(jbOk);

		jbCancel = new JButton("Cancel");
		jpButtonPane.add(jbCancel);

		jbOk.addActionListener(this);
		jbCancel.addActionListener(this);

		jtfId.setEditable(false);
		// init texts in text fields
		if (pdt != null) {
			jtfId.setText(String.valueOf(pdt.getId()));
			jtfName.setText(pdt.getName());
		} else {
			jtfId.setText(String.valueOf(ProductDao.nextPdtId()));
		}

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * update or create the product in database.
	 */
	private void saveProduct() {
		// if text field is empty
		if (jtfName.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(this, "Product name can't be void.",
					"Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		} else {
			// Detect if the name is already existed
			ArrayList<Product> list = ProductDao.getProductList();
			boolean nameExist = false;
			for (Product pdt : list) {
				if (pdt.getName().equals(jtfName.getText())) {
					nameExist = true;
				}
			}
			if (!nameExist) {
				// update or create the product
				if (pdt != null) {
					pdt.setName(jtfName.getText());
					ProductDao.updateProduct(pdt);
					JOptionPane.showConfirmDialog(this, "Product updated.",
							"Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					ProductDao.addProduct(new Product(Long.parseLong(jtfId
							.getText()), jtfName.getText()));
					JOptionPane.showConfirmDialog(this, "Product added.",
							"Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
				((ManageProductGui) this.getOwner()).refreshList();
				this.dispose();
			} else {
				JOptionPane.showConfirmDialog(this,
						"Product name is already existed.", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * action perform after a button is on clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == jbOk) {
			saveProduct();
		} else if (ev.getSource() == jbCancel) {
			this.dispose();
		}
	}
}