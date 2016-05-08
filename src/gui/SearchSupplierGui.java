package gui;

import dao.ProductDAO;
import dao.SupplierDAO;
import gds.Product;
import gds.Supplier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 * Class GUI for the search supplier Dialog.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class SearchSupplierGui extends JFrame {

	/**
	 * content panel.
	 */
	private final JPanel contentPanel;
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
	protected JTable jtbPdtList;
	/**
	 * list of search result.
	 */
	private JList<String> list;
	/**
	 * list model.
	 */
	protected ListModel<String> listModel;
	/**
	 * list of all supplier.
	 */
	protected ArrayList<Supplier> supplierList;
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
	 * the panel contains buttons.
	 */
	protected JPanel jpButtonPane;
	/**
	 * button : back to search menu.
	 */
	protected JButton jbBack;
	/**
	 * the supplier selected.
	 */
	protected Supplier selectedSupplier;
	/**
	 * supplier DAO.
	 */
	protected SupplierDAO supplierDao;

	/**
	 * Create the dialog.
	 */
	public SearchSupplierGui() {
		super();
		this.setTitle("Supplier searcher");
		this.setResizable(false);
		contentPanel = (JPanel) getContentPane();
		// get all product and supplier from the database
		supplierDao = new SupplierDAO();
		supplierList = supplierDao.getSupplierList();
		productList = new ProductDAO().getProductList();
		// sort supplier list by name by alphabet
		// productList.sort((o1, o2) -> o1.compareTo(o2));
		supplierList.sort((o1, o2) -> o1.compareTo(o2));

		setBounds(100, 100, 400, 600);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 394, 570);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

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
		jspPdtList.setBounds(10, 312, 374, 216);
		contentPanel.add(jspPdtList);

		jspPdtList.setViewportView(jtbPdtList);
		jtbPdtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jpButtonPane = new JPanel();
		jpButtonPane.setBounds(0, 536, 394, 65);
		contentPanel.add(jpButtonPane);
		
				jbBack = new JButton("Back");
				jbBack.setBounds(284, 0, 100, 23);
				jbBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						dispose();
					}
				});
				jpButtonPane.setLayout(null);
				jbBack.setActionCommand("Cancel");
				jpButtonPane.add(jbBack);
	}

	/**
	 * init the test field for enter supplier's name.
	 */
	private void initTextField() {
		// text field
		jtfSupplierName = new JTextField();
		jtfSupplierName.setBounds(121, 7, 263, 21);
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
		listScroller.setBounds(10, 35, 374, 140);
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
		// list.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent ev) {
		// if (ev.getClickCount() == 1) {
		//
		// }
		// }
		// });

		// add list selection listner
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				for (Supplier s : supplierList) {
					if (s.getName() == list.getSelectedValue()) {
						selectedSupplier = s;
						// set supplier detail table
						for (int i = jtbSprDetail.getRowCount() - 1; i >= 0; i--) {
							modelSprDetail.removeRow(i);
						}
						Object[] id = { "ID", s.getId() };
						Object[] name = { "Name", s.getName() };
						modelSprDetail.addRow(id);
						modelSprDetail.addRow(name);

						chargeProductListTable();
					}
				}
			}
		});

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(10);
	}

	/**
	 * charge data in the product list table.
	 */
	public void chargeProductListTable() {
		// set product list table
		HashMap<Long, Double> pdtList = supplierDao.getSupplierProductMap(selectedSupplier.getId());
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

	/**
	 * init supplier's detail table.
	 */
	private void initSupplierTable() {
		contentPanel.setLayout(null);
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
		jspSprDetail.setBounds(10, 185, 374, 92);
		jspSprDetail.setToolTipText("Supplier's detail");
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
			SearchSupplierGui dialog = new SearchSupplierGui();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}