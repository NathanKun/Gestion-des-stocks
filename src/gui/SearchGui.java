package src.Gui;

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

import src.dao.ProductDAO;
import src.dao.SupplierDAO;
import src.GDS.Product;
import src.GDS.Supplier;
import src.GDS.User;

/**
 * The GUI for the search menu
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class SearchGui extends JFrame implements ActionListener {

	/**
	 * Button : search a product
	 */
	private JButton jb_product = new JButton("Product");
	/**
	 * Button : search a supplier
	 */
	private JButton jb_supplier = new JButton("Supplier");
	/**
	 * Button : return to the main menu
	 */
	private JButton jb_return = new JButton("Return");
	/**
	 * the user who logged in
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
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) // y
																						// for
																						// 0,
																						// n
																						// for
																						// 1
					dispose();
			}
		});

		setBounds(100, 100, 400, 600);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		// configure the components
		JPanel jp_main = new JPanel();
		getContentPane().add(jp_main);
		jp_main.setLayout(null);

		jb_product.setFont(new Font("Dialog", Font.PLAIN, 18));
		jb_product.addActionListener(this);
		jb_product.setBounds(100, 100, 200, 80);
		jp_main.add(jb_product);

		jb_supplier.setFont(new Font("Dialog", Font.PLAIN, 18));
		jb_supplier.setBounds(100, 320, 200, 80);
		jb_supplier.addActionListener(this);
		jp_main.add(jb_supplier);

		jb_return.setFont(new Font("Dialog", Font.PLAIN, 18));
		jb_return.addActionListener(this);
		jb_return.setBounds(10, 500, 100, 40);
		jp_main.add(jb_return);

		this.setVisible(true);
	}

	@Override
	/**
	 * action perform when a button on click
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb_product) {
			new SearchProduct(this, true);
		} else if (e.getSource() == jb_supplier) {
			new SearchSupplier(this, true);
		} else if (e.getSource() == jb_return) {
			new MainGui(user);
			dispose();
		}
	}

	/**
	 * main method for testing
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SearchGui(null);
	}

}

/**
 * Class GUI for the search product Dialog
 * 
 * @author HE Junyang
 *
 */
class SearchProduct extends JDialog {
	/**
	 * content panel
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * textField to enter a product name to search
	 */
	private JTextField jtf_productName;
	/**
	 * product detail table
	 */
	private JTable table;
	/**
	 * search result, product list
	 */
	private JList<String> list;
	/**
	 * model of the list
	 */
	private ListModel<String> listModel;
	/**
	 * ArrayList of all products
	 */
	private ArrayList<Product> productList;
	/**
	 * model of the table
	 */
	private DefaultTableModel modelTableList = null;

	/**
	 * Main method for testing
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		try {
			SearchProduct dialog = new SearchProduct(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		lblProductName.setBounds(10, 10, 100, 15);
		contentPanel.add(lblProductName);

		// table
		String[][][] datas = {};
		String[] titles = { "Key", "Value" };
		modelTableList = new DefaultTableModel(datas, titles);

		table = new JTable(modelTableList);
		table.setPreferredScrollableViewportSize(new Dimension(364, 197));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		// panel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setToolTipText("Product detail");
		scrollPane.setBounds(10, 321, 364, 197);
		contentPanel.add(scrollPane);

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
			public void mouseClicked(MouseEvent e) {
				// if click once
				if (e.getClickCount() == 1) {
					// find the product clicked
					for (Product p : productList) {
						if (p.getName() == list.getSelectedValue()) {
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

		// text field for enter the product's name
		jtf_productName = new JTextField();
		// update the list every time when text changed
		jtf_productName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
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
					if (p.getName().contains(jtf_productName.getText())) {
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
		jtf_productName.setBounds(121, 7, 253, 21);
		contentPanel.add(jtf_productName);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			/*
			 * { JButton okButton = new JButton("OK");
			 * okButton.addActionListener(new ActionListener() { public void
			 * actionPerformed(ActionEvent e) { } });
			 * okButton.setActionCommand("OK"); buttonPane.add(okButton);
			 * getRootPane().setDefaultButton(okButton); }
			 */
			{
				JButton backButton = new JButton("Back");
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				backButton.setActionCommand("Cancel");
				buttonPane.add(backButton);
			}
		}

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}

/**
 * Class GUI for the search supplier Dialog
 * 
 * @author HE Junyang
 *
 */
class SearchSupplier extends JDialog {

	/**
	 * content panel
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * text field fot enter a supplier's name
	 */
	private JTextField jtf_supplierName;
	/**
	 * table for show the detail of a supplier
	 */
	private JTable jtb_sprDetail;
	/**
	 * table for show the list of product which a supplier has
	 */
	private JTable jtb_pdtList;
	/**
	 * list of search result
	 */
	private JList<String> list;
	/**
	 * list model
	 */
	private ListModel<String> listModel;
	/**
	 * list of all supplier
	 */
	private ArrayList<Supplier> supplierList;
	/**
	 * list of all product
	 */
	private ArrayList<Product> productList;
	/**
	 * model for supplier table
	 */
	private DefaultTableModel model_sprDetail = null;
	/**
	 * model for product table
	 */
	private DefaultTableModel model_pdtList = null;

	/**
	 * Main method for testing
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		try {
			SearchSupplier dialog = new SearchSupplier(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

		JLabel lblSupplierName = new JLabel("Supplier name : ");
		lblSupplierName.setBounds(10, 10, 100, 15);
		contentPanel.add(lblSupplierName);

		// sprDetail table
		String[][] datas_sprDetail = {};
		String[] titles = { "Key", "Value" };
		model_sprDetail = new DefaultTableModel(datas_sprDetail, titles);

		jtb_sprDetail = new JTable(model_sprDetail);
		jtb_sprDetail.setPreferredScrollableViewportSize(new Dimension(364, 197));
		jtb_sprDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtb_sprDetail.setCellSelectionEnabled(false);
		JScrollPane jsp_sprDetail = new JScrollPane(jtb_sprDetail);
		jsp_sprDetail.setToolTipText("Supplier's detail");
		jsp_sprDetail.setBounds(10, 185, 364, 92);
		contentPanel.add(jsp_sprDetail);

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
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					for (Supplier s : supplierList) {
						if (s.getName() == list.getSelectedValue()) {
							// set supplier detail table
							for (int i = jtb_sprDetail.getRowCount() - 1; i >= 0; i--) {
								model_sprDetail.removeRow(i);
							}
							Object[] id = { "ID", s.getId() };
							Object[] name = { "Name", s.getName() };
							model_sprDetail.addRow(id);
							model_sprDetail.addRow(name);

							// set product list table
							HashMap<Long, Double> pdtList = new SupplierDAO().getSupplierProductList(s.getId());
							for (int i = jtb_pdtList.getRowCount() - 1; i >= 0; i--) {
								model_pdtList.removeRow(i);
							}

							// iterate over the HashMap
							Iterator<Entry<Long, Double>> iterator = pdtList.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<Long, Double> entry = (Entry<Long, Double>) iterator.next();
								String pdtName = "";
								Long key = entry.getKey();
								for (Product product : productList) {
									if (key == product.getId())
										pdtName = product.getName();
								}
								Object object[] = { key, pdtName, entry.getValue() };
								model_pdtList.addRow(object);
							}
						}
					}
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(10);

		// text field
		jtf_supplierName = new JTextField();
		// update the list every time when text changed
		jtf_supplierName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			private void update() {
				ArrayList<Supplier> filteredList = new ArrayList<Supplier>();
				for (int i = listModel.getSize(); i > 0; i--) {
					((DefaultListModel<String>) listModel).remove(i - 1);
				}
				for (Supplier s : supplierList) {
					if (s.getName().contains(jtf_supplierName.getText())) {
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
		jtf_supplierName.setBounds(121, 7, 253, 21);
		contentPanel.add(jtf_supplierName);

		JLabel lblProductsList = new JLabel("Products list : ");
		lblProductsList.setBounds(10, 287, 100, 15);
		contentPanel.add(lblProductsList);

		// product list table
		String[][][] datas_pdtList = {};
		String[] titles_pdtList = { "ID", "Name", "Price" };
		model_pdtList = new DefaultTableModel(datas_pdtList, titles_pdtList);

		jtb_pdtList = new JTable(model_pdtList);
		jtb_pdtList.setPreferredScrollableViewportSize(new Dimension(364, 197));
		JScrollPane jsp_pdtList = new JScrollPane();
		jsp_pdtList.setBounds(10, 312, 364, 216);
		contentPanel.add(jsp_pdtList);

		jsp_pdtList.setViewportView(jtb_pdtList);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			/*
			 * { JButton okButton = new JButton("OK");
			 * okButton.addActionListener(new ActionListener() { public void
			 * actionPerformed(ActionEvent e) { } });
			 * okButton.setActionCommand("OK"); buttonPane.add(okButton);
			 * getRootPane().setDefaultButton(okButton); }
			 */
			{
				JButton backButton = new JButton("Back");
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				backButton.setActionCommand("Cancel");
				buttonPane.add(backButton);
			}
		}

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}