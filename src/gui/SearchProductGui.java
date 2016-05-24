package gui;

import dao.ProductDao;
import dao.SupplierDao;
import gds.Product;
import gds.SupplierProductPrice;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Class GUI for the search product Dialog. Designed for extend.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class SearchProductGui extends JFrame {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7093497964493964146L;
	/**
	 * content panel.
	 */
	private JPanel jpMain = new JPanel();
	/**
	 * textField to enter a product name to search.
	 */
	private JTextField jtfProductName;
	/**
	 * product detail table.
	 */
	private JTable table;
	/**
	 * search result, product list.
	 */
	private JList<String> list;
	/**
	 * model of the list.
	 */
	protected ListModel<String> listModel;
	/**
	 * ArrayList of all products.
	 */
	protected ArrayList<Product> productList;
	/**
	 * model of the table.
	 */
	private DefaultTableModel modelTableList = null;
	/**
	 * button : back to search menu.
	 */
	protected JButton jbBack;
	/**
	 * button : find the cheapest supplier for this product.
	 */
	protected JButton jbCheapestSupplier;
	/**
	 * the product currently selected in the list.
	 */
	protected Product selectedProduct = null;
	/**
	 * the panel contains buttons.
	 */
	protected JPanel jpButtonPane;
	/**
	 * label : product name
	 */
	private JLabel jlProductName;
	/**
	 * scroll pane for table
	 */
	private JScrollPane tableScrollPane;
	/**
	 * scroll pane for list
	 */
	private JScrollPane listScrollPane;

	/**
	 * Create the dialog.
	 */
	public SearchProductGui() {
		this.setTitle("Product searcher");
		this.setResizable(false);
		this.setBounds(100, 100, 400, 600);
		getContentPane().setLayout(null);
		jpMain.setBounds(0, 0, 394, 538);
		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(jpMain);
		jpMain.setLayout(null);

		// get all product in the list
		productList = ProductDao.getProductList();

		jlProductName = new JLabel("Product name : ");
		jlProductName.setBounds(10, 10, 200, 15);
		jpMain.add(jlProductName);

		initTable();

		initProductList();

		initTextField();

		initButtonPane();

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * init the table for product's detail.
	 */
	@SuppressWarnings("serial")
	private void initTable() {
		// table
		String[][][] datas = {};
		String[] titles = { "Key", "Value" };
		modelTableList = new DefaultTableModel(datas, titles) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(modelTableList);
		table.setPreferredScrollableViewportSize(new Dimension(364, 197));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setToolTipText("Product detail");
		tableScrollPane.setBounds(10, 321, 374, 207);
		jpMain.add(tableScrollPane);
	}

	/**
	 * init the list of product.
	 */
	private void initProductList() {
		// list
		listModel = new DefaultListModel<String>();
		listScrollPane = new JScrollPane();
		listScrollPane.setLocation(10, 35);
		listScrollPane.setSize(374, 276);
		listScrollPane.setPreferredSize(new Dimension(364, 323));
		listScrollPane.setAlignmentX(LEFT_ALIGNMENT);
		// contentPanel.add(list);
		jpMain.add(listScrollPane);
		list = new JList<String>(listModel);
		listScrollPane.setViewportView(list);

		// sort the list (by name)
		productList.sort((o1, o2) -> o1.compareTo(o2));

		// add default data in the list
		for (Product p : productList) {
			((DefaultListModel<String>) listModel).addElement(p.getName());
		}

		// add list on click listener
		// list.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent ev) {
		// // if click once
		// if (ev.getClickCount() == 1) {
		//
		// }
		// }
		// });

		// add list selection listner
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				refreshTable();
			}
		});

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(10);
	}

	/**
	 * init the test field for input product's name.
	 */
	private void initTextField() {
		// text field for enter the product's name
		jtfProductName = new JTextField();
		// update the list every time when text changed
		jtfProductName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent ev) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent ev) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent ev) {
				update();
			}

			private void update() {
				ArrayList<Product> filteredList = new ArrayList<Product>();
				// clear the list
				for (int i = listModel.getSize(); i > 0; i--) {
					((DefaultListModel<String>) listModel).remove(i - 1);
				}
				// filter
				for (Product p : productList) {
					if (p.getName().contains(jtfProductName.getText())) {
						filteredList.add(p);
					}
				}
				// add filtered result in the list
				if (!filteredList.isEmpty()) {
					for (Product p : filteredList) {
						((DefaultListModel<String>) listModel).addElement(p.getName());
					}
				}
			}
		});
		jtfProductName.setBounds(121, 7, 263, 21);
		jpMain.add(jtfProductName);
	}

	/**
	 * init the butten pane at bottom.
	 */
	private void initButtonPane() {
		// button pane

		jpButtonPane = new JPanel();
		jpButtonPane.setBounds(10, 538, 374, 33);
		getContentPane().add(jpButtonPane);
		// back button

		jbBack = new JButton("Back");
		jbBack.setBounds(297, 5, 77, 23);
		jbBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		jbBack.setActionCommand("Cancel");

		// best price button

		jbCheapestSupplier = new JButton("cheapest supplier");
		jbCheapestSupplier.setBounds(131, 5, 156, 23);
		jbCheapestSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// Find the best price
				if (selectedProduct != null) {
					SupplierProductPrice spp = SupplierDao.getBestPriceOfAProduct(selectedProduct.getId());

					if (spp != null) {
						// Construction of the string
						StringBuffer str = new StringBuffer();
						str.append("Best price of product: ");
						str.append(selectedProduct.getName());
						str.append("\n product ID = ");
						str.append(String.valueOf(spp.getPdtId()));
						str.append("\n is ");
						str.append(String.valueOf(spp.getSprPdtPrice()));
						str.append("\n By supplier ");
						str.append(SupplierDao.getSupplier(spp.getSprId()).getName());
						str.append("\n supplier ID =  ");
						str.append(String.valueOf(spp.getSprId()));

						// show the window
						JOptionPane.showConfirmDialog(null, str, "Cheapest Supplier", JOptionPane.DEFAULT_OPTION,
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showConfirmDialog(null, "No supplier supply this product.", "Oops",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Please select a product.", "Oops", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		jpButtonPane.setLayout(null);

		jpButtonPane.add(jbCheapestSupplier);
		jpButtonPane.add(jbBack);

	}

	/**
	 * Refresh the jList of products.
	 */
	protected void refreshList() {
		productList = ProductDao.getProductList();
		productList.sort((o1, o2) -> o1.compareTo(o2));
		// clear the list

		for (int i = listModel.getSize(); i > 0; i--) {
			((DefaultListModel<String>) listModel).remove(i - 1);
		}
		// add filtered result in the list
		for (Product p : productList) {
			((DefaultListModel<String>) listModel).addElement(p.getName());
		}
	}

	/**
	 * Refresh the JTable of product's detail.
	 */
	protected void refreshTable() {
		// find the product clicked
		for (Product p : productList) {
			if (p.getName() == list.getSelectedValue()) {
				selectedProduct = p;
				// clear the table
				for (int i = table.getRowCount() - 1; i >= 0; i--) {
					modelTableList.removeRow(i);
				}
				// then add selected product's data in the table
				Object[] id = { "ID", p.getId() };
				Object[] name = { "Name", p.getName() };
				Object[] price = { "Price", p.getPrice() };
				Object[] stock = { "Stock", p.getStock() };
				Object[] supplierId = { "Supplier Id", p.getSupplierId() };
				Object[] supplier = { "Supplier", p.getSupplierName() };
				modelTableList.addRow(id);
				modelTableList.addRow(name);
				modelTableList.addRow(price);
				modelTableList.addRow(stock);
				modelTableList.addRow(supplierId);
				modelTableList.addRow(supplier);
			}
		}
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
			new SearchProductGui();
	}
}
