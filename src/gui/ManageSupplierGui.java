package gui;

import dao.SupplierDAO;
import gds.Supplier;
import gds.User;
import util.Regex;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManageSupplierGui extends SearchSupplierGui implements ActionListener {

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
	 * The user who logged in.
	 */
	private User user;

	/**
	 * Create the dialog.
	 */
	public ManageSupplierGui(User user) {
		super();
		this.setTitle("Supplier Management");
		this.user = user;
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
		supplierList = supplierDao.getSupplierList();
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
			if (JOptionPane.showConfirmDialog(null, text, "Comfirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == 0) {
				// y for 0, n for 1
				if (supplierDao.deleteSupplier(selectedSupplier.getId()) == 1) {
					JOptionPane.showConfirmDialog(null, "Supplier deleted", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				// refresh the list after deleting
				refreshList();
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Please select a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * remove the product selected from a supplier.
	 */
	private void removeProduct() {
		if (seletedProductId != 0) {
			if (JOptionPane.showConfirmDialog(null, "Do you really want to remove this product from this supplier?",
					"Comfirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				if (supplierDao.deleteSupplierProduct(selectedSupplier.getId(), seletedProductId) == 1) {
					JOptionPane.showConfirmDialog(null, "Product deleted", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					seletedProductId = 0;
					chargeProductListTable();
				} else {
					JOptionPane.showConfirmDialog(null, "Something wrong.", "Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Please selected a product.", "Error", JOptionPane.DEFAULT_OPTION,
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
				JOptionPane.showConfirmDialog(null, "Please select a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (ev.getSource() == jbDelete) {
			// delete a supplier
			deleteSupplier();
		} else if (ev.getSource() == jbAffectProduct) {
			// affect a product to a supplier
			if (selectedSupplier != null){
				new SearchProductForAffect(this, selectedSupplier.getId());
				this.setVisible(false);
			} else {
				JOptionPane.showConfirmDialog(null, "Please select a supplier.", "Error", JOptionPane.DEFAULT_OPTION,
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
			ManageSupplierGui dialog = new ManageSupplierGui(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

/**
 * The dialog for create or edit a supplier.
 * 
 * @author HE Junyang
 *
 */
class SupplierDialog extends JDialog implements ActionListener {

	/**
	 * main content panel.
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * text field for the supplier id.
	 */
	private JTextField jtfId;
	/**
	 * text field for the supplier name.
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
	 * the supplier selected.
	 */
	private Supplier spr;

	/**
	 * Create the dialog.
	 */
	public SupplierDialog(Frame owner, boolean modal, Supplier spr) {
		super(owner, modal);
		this.spr = spr;

		this.setSize(400, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		jlId = new JLabel("ID : ");
		jlId.setSize(50, 20);
		jlId.setLocation(10, 35);
		jlName = new JLabel("Name : ");
		jlName.setBounds(10, 65, 50, 20);

		if (spr == null) {
			jlTitle = new JLabel("New supplier :");
		} else {
			jlTitle = new JLabel("Edit a supplier :");
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
		if (spr != null) {
			jtfId.setText(String.valueOf(spr.getId()));
			jtfName.setText(spr.getName());
		} else {
			jtfId.setText(String.valueOf(SupplierDAO.nextSprId()));
		}

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * update or create the supplier in db.
	 */
	private void saveSupplier() {
		// if text field is empty
		if (jtfName.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(null, "Supplier name can't be void.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		} else {
			// Detect if the name is already existed
			SupplierDAO dao = new SupplierDAO();
			ArrayList<Supplier> list = dao.getSupplierList();
			boolean nameExist = false;
			for (Supplier spr : list) {
				if (spr.getName().equals(jtfName.getText())) {
					nameExist = true;
				}
			}
			if (!nameExist) {
				// update or create the supplier
				if (spr != null) {
					spr.setName(jtfName.getText());
					dao.updateSupplier(spr);
					JOptionPane.showConfirmDialog(null, "Supplier updated.", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					dao.addSupplier(new Supplier(Long.parseLong(jtfId.getText()), jtfName.getText()));
					JOptionPane.showConfirmDialog(null, "Supplier added.", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
				((ManageSupplierGui) this.getOwner()).refreshList();
				this.dispose();
			} else {
				JOptionPane.showConfirmDialog(null, "Supplier name is already existed.", "Error",
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
			saveSupplier();
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
			SupplierDialog dialog = new SupplierDialog(null, true, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

/**
 * A window for selected a product and affect it to a supplier.
 * 
 * @author HE Junyang
 *
 */
class SearchProductForAffect extends SearchProductGui {
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
							if (new SupplierDAO().addSupplierProduct(sprId, selectedProduct.getId(),
									Double.parseDouble(jtfPrice.getText())) == 1) {
								JOptionPane.showConfirmDialog(null, "Product affected", "Confirm",
										JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
								dispose();
								owner.chargeProductListTable();
								owner.setVisible(true);
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
