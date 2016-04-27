package src.gui;

import src.dao.ProductDAO;
import src.dao.SupplierDAO;
import src.gds.Product;
import src.gds.Supplier;
import src.gds.SupplierProductPrice;
import src.gds.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.security.Init;

/**
 * The GUI for the search menu.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class SearchGui extends JFrame implements ActionListener {

	/**
	 * Button : search a product.
	 */
	private JButton jbProduct = new JButton("Product");
	/**
	 * Button : search a supplier.
	 */
	private JButton jbSupplier = new JButton("Supplier");
	/**
	 * Button : return to the main menu.
	 */
	private JButton jbReturn = new JButton("Return");
	/**
	 * the user who logged in.
	 */
	private User user = null;

	/**
	 * Create the frame.
	 * 
	 * @param user
	 *            the user who logged in
	 */
	public SearchGui(User user) {
		this.user = user;
		this.setTitle("Search");
		// confirmation when X on click
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
					// y for 0, n for 1
					System.exit(0);
				}
			}
		});

		setBounds(100, 100, 400, 600);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		// configure the components
		JPanel jpMain = new JPanel();
		getContentPane().add(jpMain);
		jpMain.setLayout(null);

		jbProduct.setFont(new Font("Dialog", Font.PLAIN, 18));
		jbProduct.addActionListener(this);
		jbProduct.setBounds(100, 100, 200, 80);
		jpMain.add(jbProduct);

		jbSupplier.setFont(new Font("Dialog", Font.PLAIN, 18));
		jbSupplier.setBounds(100, 320, 200, 80);
		jbSupplier.addActionListener(this);
		jpMain.add(jbSupplier);

		jbReturn.setFont(new Font("Dialog", Font.PLAIN, 18));
		jbReturn.addActionListener(this);
		jbReturn.setBounds(10, 500, 100, 40);
		jpMain.add(jbReturn);

		this.setVisible(true);
	}

	@Override
	/**
	 * action perform when a button on click
	 */
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == jbProduct) {
			new SearchProduct(this, true);
		} else if (ev.getSource() == jbSupplier) {
			new SearchSupplier(this, true);
		} else if (ev.getSource() == jbReturn) {
			new MainGui(user);
			dispose();
		}
	}

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		new SearchGui(null);
	}

}

/**
 * Class GUI for the search product Dialog.
 * 
 * @author HE Junyang
 *
 */
@SuppressWarnings("serial")
class SearchProduct extends JDialog {
	/**
	 * content panel.
	 */
	private final JPanel contentPanel = new JPanel();
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
	private ListModel<String> listModel;
	/**
	 * ArrayList of all products.
	 */
	private ArrayList<Product> productList;
	/**
	 * model of the table.
	 */
	private DefaultTableModel modelTableList = null;
	/**
	 * button : back to search menu.
	 */
	private JButton jbBack;
	/**
	 * button : find the cheapest supplier for this product.
	 */
	private JButton jbCheapestSupplier;
	/**
	 * the product currently selected in the list.
	 */
	private Product seletedProduct = null;

	/**
	 * Create the dialog.
	 * 
	 * @param owner
	 *            the owner of the dialog
	 * @param modal
	 *            is the owner focusable
	 */
	public SearchProduct(Frame owner, boolean modal) {
		super(owner, modal);
		this.setTitle("Product searcher");
		this.setResizable(false);
		this.setBounds(100, 100, 400, 600);

		this.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// get all product in the list
		productList = new ProductDAO().getProductList();

		JLabel lblProductName = new JLabel("Product name : ");
		lblProductName.setBounds(10, 10, 200, 15);
		contentPanel.add(lblProductName);

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
		// panel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setToolTipText("Product detail");
		scrollPane.setBounds(10, 321, 364, 197);
		contentPanel.add(scrollPane);
	}

	/**
	 * init the list of product.
	 */
	private void initProductList() {
		// list
		listModel = new DefaultListModel<String>();
		JScrollPane listScroller = new JScrollPane();
		listScroller.setLocation(10, 35);
		listScroller.setSize(364, 276);
		listScroller.setPreferredSize(new Dimension(364, 323));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		// contentPanel.add(list);
		contentPanel.add(listScroller);
		list = new JList<String>(listModel);
		listScroller.setViewportView(list);

		// add default data in the list
		for (Product p : productList) {
			((DefaultListModel<String>) listModel).addElement(p.getName());
		}

		// add list on click listener
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				// if click once
				if (ev.getClickCount() == 1) {
					// find the product clicked
					for (Product p : productList) {
						if (p.getName() == list.getSelectedValue()) {
							seletedProduct = p;
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
		jtfProductName.setBounds(121, 7, 253, 21);
		contentPanel.add(jtfProductName);
	}

	/**
	 * init the butten pane at bottom.
	 */
	private void initButtonPane() {
		// button pane

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		// back button

		jbBack = new JButton("Back");
		jbBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		jbBack.setActionCommand("Cancel");

		// best price button

		jbCheapestSupplier = new JButton("cheapest supplier");
		jbCheapestSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// Find the best price
				SupplierProductPrice spp = new SupplierDAO().getBestPriceOfAProduct(seletedProduct.getId());

				// Construction of the string
				StringBuffer str = new StringBuffer();
				str.append("Best price of product: ");
				str.append(seletedProduct.getName());
				str.append("\n product ID = ");
				str.append(String.valueOf(spp.getPdtId()));
				str.append("\n is ");
				str.append(String.valueOf(spp.getSprPdtPrice()));
				str.append("\n By supplier ");
				str.append(new SupplierDAO().getSupplier(spp.getSprId()).getName());
				str.append("\n supplier ID =  ");
				str.append(String.valueOf(spp.getSprId()));

				// show the window
				JOptionPane.showConfirmDialog(null, str, "Cheapest Supplier", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		});

		buttonPane.add(jbCheapestSupplier);
		buttonPane.add(jbBack);

	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		try {
			SearchProduct dialog = new SearchProduct(null, true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

/**
 * Class GUI for the search supplier Dialog.
 * 
 * @author HE Junyang
 *
 */
@SuppressWarnings("serial")
class SearchSupplier extends JDialog {

	/**
	 * content panel.
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * text field fot enter a supplier's name.
	 */
	private JTextField jtfSupplierName;
	/**
	 * table for show the detail of a supplier.
	 */
	private JTable jtbSprDetail;
	/**
	 * table for show the list of product which a supplier has.
	 */
	private JTable jtbPdtList;
	/**
	 * list of search result.
	 */
	private JList<String> list;
	/**
	 * list model.
	 */
	private ListModel<String> listModel;
	/**
	 * list of all supplier.
	 */
	private ArrayList<Supplier> supplierList;
	/**
	 * list of all product.
	 */
	private ArrayList<Product> productList;
	/**
	 * model for supplier table.
	 */
	private DefaultTableModel modelSprDetail = null;
	/**
	 * model for product table.
	 */
	private DefaultTableModel modelPdtList = null;

	/**
	 * Create the dialog.
	 * 
	 * @param owner
	 *            owner of this dialog
	 * @param modal
	 *            is the owner focusable
	 */
	public SearchSupplier(Frame owner, boolean modal) {
		super(owner, modal);
		this.setTitle("Supplier searcher");
		this.setResizable(false);

		// get all product and supplier from the db
		supplierList = new SupplierDAO().getSupplierList();
		productList = new ProductDAO().getProductList();

		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		initSupplierTable();

		initSupplierList();

		initTextField();

		initProductListTable();

		initButtonPane();

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * init the button pane at the bottom.
	 */
	private final void initButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		backButton.setActionCommand("Cancel");
		buttonPane.add(backButton);
	}

	/**
	 * init the table of the list of product for a supplier.
	 */
	private void initProductListTable() {
		// product list table
		String[][][] datasPdtList = {};
		String[] titlesPdtList = { "ID", "Name", "Price" };
		modelPdtList = new DefaultTableModel(datasPdtList, titlesPdtList) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		jtbPdtList = new JTable(modelPdtList);
		jtbPdtList.setPreferredScrollableViewportSize(new Dimension(364, 197));
		JScrollPane jspPdtList = new JScrollPane();
		jspPdtList.setBounds(10, 312, 364, 216);
		contentPanel.add(jspPdtList);

		jspPdtList.setViewportView(jtbPdtList);
		jtbPdtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * init the test field for enter supplier's name.
	 */
	private void initTextField() {
		// text field
		jtfSupplierName = new JTextField();
		// update the list every time when text changed
		jtfSupplierName.getDocument().addDocumentListener(new DocumentListener() {

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
				ArrayList<Supplier> filteredList = new ArrayList<Supplier>();
				for (int i = listModel.getSize(); i > 0; i--) {
					((DefaultListModel<String>) listModel).remove(i - 1);
				}
				for (Supplier s : supplierList) {
					if (s.getName().contains(jtfSupplierName.getText())) {
						filteredList.add(s);
					}
				}
				if (!filteredList.isEmpty()) {
					for (Supplier s : filteredList) {
						((DefaultListModel<String>) listModel).addElement(s.getName());
					}
				}
			}
		});
		jtfSupplierName.setBounds(121, 7, 253, 21);
		contentPanel.add(jtfSupplierName);

		JLabel lblProductsList = new JLabel("Products list : ");
		lblProductsList.setBounds(10, 287, 200, 15);
		contentPanel.add(lblProductsList);
	}

	/**
	 * init the list of supplier.
	 */
	private void initSupplierList() {
		// list
		listModel = new DefaultListModel<String>();
		JScrollPane listScroller = new JScrollPane();
		listScroller.setLocation(10, 35);
		listScroller.setSize(364, 140);
		listScroller.setPreferredSize(new Dimension(364, 323));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		// contentPanel.add(list);
		contentPanel.add(listScroller);
		list = new JList<String>(listModel);
		listScroller.setViewportView(list);
		// add default data in the list
		for (Supplier s : supplierList) {
			((DefaultListModel<String>) listModel).addElement(s.getName());
		}
		// add listener
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() == 1) {
					for (Supplier s : supplierList) {
						if (s.getName() == list.getSelectedValue()) {
							// set supplier detail table
							for (int i = jtbSprDetail.getRowCount() - 1; i >= 0; i--) {
								modelSprDetail.removeRow(i);
							}
							Object[] id = { "ID", s.getId() };
							Object[] name = { "Name", s.getName() };
							modelSprDetail.addRow(id);
							modelSprDetail.addRow(name);

							// set product list table
							HashMap<Long, Double> pdtList = new SupplierDAO().getSupplierProductMap(s.getId());
							// System.out.println("s.getId() : " + s.getId());
							for (int i = jtbPdtList.getRowCount() - 1; i >= 0; i--) {
								modelPdtList.removeRow(i);
							}

							// iterate over the HashMap
							Iterator<Entry<Long, Double>> iterator = pdtList.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<Long, Double> entry = (Entry<Long, Double>) iterator.next();
								String pdtName = "";
								Long key = entry.getKey();
								for (Product product : productList) {
									if (key == product.getId()) {
										pdtName = product.getName();
									}
								}
								Object[] object = { key, pdtName, entry.getValue() };
								modelPdtList.addRow(object);
								// System.out.println("object : " + object);
							}
						}
					}
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(10);
	}

	/**
	 * init supplier's detail table.
	 */
	private void initSupplierTable() {
		JLabel lblSupplierName = new JLabel("Supplier name : ");
		lblSupplierName.setBounds(10, 10, 200, 15);
		contentPanel.add(lblSupplierName);

		// sprDetail table
		String[][] datasSprDetail = {};
		String[] titles = { "Key", "Value" };
		modelSprDetail = new DefaultTableModel(datasSprDetail, titles) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		jtbSprDetail = new JTable(modelSprDetail);
		jtbSprDetail.setPreferredScrollableViewportSize(new Dimension(364, 197));
		jtbSprDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbSprDetail.setCellSelectionEnabled(false);
		JScrollPane jspSprDetail = new JScrollPane(jtbSprDetail);
		jspSprDetail.setToolTipText("Supplier's detail");
		jspSprDetail.setBounds(10, 185, 364, 92);
		contentPanel.add(jspSprDetail);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		try {
			SearchSupplier dialog = new SearchSupplier(null, true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}