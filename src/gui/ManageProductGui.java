package gui;

import dao.ProductDAO;
import dao.SupplierDAO;
import gds.Product;
import gds.Supplier;
import gds.User;
import util.Regex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.acl.Owner;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

/**
 * Class GUI for the product management.
 * 
 * @author HE Junyang - - FOTSING KENGNE Junior
 *
 */
public class ManageProductGui extends SearchProductGui implements ActionListener {

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
	private JButton jbChooseSupplier;

	/**
	 * Constructor of the class. Init the components.
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
			if (JOptionPane.showConfirmDialog(null, text, "Comfirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == 0) {
				// y for 0, n for 1
				if (dao.deleteProduct(selectedProduct.getId()) == 1) {
					JOptionPane.showConfirmDialog(null, "Product deleted", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				// refresh the list after deleting
				refreshList();
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Please select a product.", "Error", JOptionPane.DEFAULT_OPTION,
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
				JOptionPane.showConfirmDialog(null, "Please select a product.", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (ev.getSource() == jbDelete) {
			// delete a product
			deleteProduct();
		} else if (ev.getSource() == jbChooseSupplier) {
			if (selectedProduct != null) {
				new SearchSupplierForAffect(this, selectedProduct.getId());
				this.setVisible(false);
			} else {
				JOptionPane.showConfirmDialog(null, "Please select a product.", "Error", JOptionPane.DEFAULT_OPTION,
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

/**
 * The dialog for create or edit a product.
 * 
 * @author HE Junyang
 *
 */
class ProductDialog extends JDialog implements ActionListener {

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
	 * The owner of this dialog
	 */
	private ManageProductGui owner;
	/**
	 * Create the dialog.
	 */
	public ProductDialog(Frame owner, boolean modal, Product pdt) {
		super(owner, modal);
		this.owner = (ManageProductGui) owner;
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
			jtfId.setText(String.valueOf(ProductDAO.nextPdtId()));
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
			JOptionPane.showConfirmDialog(null, "Product name can't be void.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		} else {
			// Detect if the name is already existed
			ProductDAO dao = new ProductDAO();
			ArrayList<Product> list = dao.getProductList();
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
					dao.updateProduct(pdt);
					JOptionPane.showConfirmDialog(null, "Product updated.", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					dao.addProduct(new Product(Long.parseLong(jtfId.getText()), jtfName.getText()));
					JOptionPane.showConfirmDialog(null, "Product added.", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
				((ManageProductGui) this.getOwner()).refreshList();
				this.dispose();
			} else {
				JOptionPane.showConfirmDialog(null, "Product name is already existed.", "Error",
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

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		try {
			ProductDialog dialog = new ProductDialog(null, true, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

class SearchSupplierForAffect extends SearchSupplierGui {
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
	public SearchSupplierForAffect(ManageProductGui owner, long pdtId) {
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
				ProductDAO dao = new ProductDAO();
				Product pdt = dao.getProduct(pdtId);
				pdt.setSupplierId(selectedSupplier.getId());
				pdt.setSupplierName(selectedSupplier.getName());
				pdt.setPrice(selectedSupplier.getProductList().get(pdtId));
				if (dao.updateProduct(pdt) == 1) {
					JOptionPane.showConfirmDialog(null, "Supplier chose.\nProduct updated.", "Confirm",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					dispose();
					owner.setVisible(true);
				} else {
					JOptionPane.showConfirmDialog(null, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
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

	/**
	 * Main method for testing
	 * 
	 * @param args
	 *            for main.
	 */
	public static void main(String[] args) {
		new SearchSupplierForAffect(null, 6L);
	}
}
