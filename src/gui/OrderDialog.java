package gui;

import dao.OrderDao;
import dao.ProductDao;
import gds.Order;
import gds.OrderProduct;
import gds.Product;
import util.Regex;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;

/**
 * OrderDialog class A dialog window to create/edit an order of client.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */

public class OrderDialog extends JDialog implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -755686942852009654L;
	/**
	 * table of products added.
	 */
	private JTable jtbPdtList = null;
	/**
	 * table of products which user search.
	 */
	private JTable jtbPdtSearch = null;
	/**
	 * table model of jtb_pdtList.
	 */
	private DefaultTableModel modelPdtList = null;
	/**
	 * table model of jtb_pdtSearch.
	 */
	private DefaultTableModel modelPdtSearch = null;
	/**
	 * button : add a product by id.
	 */
	private JButton jbAddPdt = new JButton("Add");
	/**
	 * button : create the order.
	 */
	private JButton jbCreateOdr = new JButton("Create");
	/**
	 * button : search a product by name.
	 */
	private JButton jbSearchPdt = new JButton("Search by name");
	/**
	 * button : remove a product on click.
	 */
	private JButton jbRemovePdt = new JButton("Remove");
	/**
	 * button : edit the order.
	 */
	private JButton jbSave = new JButton("Save");
	/**
	 * button : cancel the order.
	 */
	private JButton jbCancel = new JButton("Cancel");
	/**
	 * label : Order ID :.
	 */
	private JLabel jlId = new JLabel("Order ID : ");
	/**
	 * label : State :.
	 */
	private JLabel jlIsPaid = new JLabel("State : ");
	/**
	 * label : Client name :.
	 */
	private JLabel jlCltName = new JLabel("Client name : ");
	/**
	 * label : Date :.
	 */
	private JLabel jlDate = new JLabel("Date : ");
	/**
	 * label : Add product By ID : .
	 */
	private JLabel jlAddPdt = new JLabel("Add product By ID : ");
	/**
	 * label : Search product by name :.
	 */
	private JLabel jlSearchPdt = new JLabel("Search product : ");
	/**
	 * label : Product list :.
	 */
	private JLabel jlPdtList = new JLabel("Product list : ");
	/**
	 * label : Totol price :.
	 */
	private JLabel jlPrice = new JLabel("Totol price : ");
	/**
	 * text field of order id.
	 */
	private JTextField jtfId = new JTextField(/**/);
	/**
	 * text field of id state.
	 */
	private JTextField jtfIsPaid = new JTextField();
	/**
	 * text field of client name.
	 */
	private JTextField jtfCltName = new JTextField();
	/**
	 * text field of date.
	 */
	private JTextField jtfDate = new JTextField();
	/**
	 * text field of add product by id.
	 */
	private JTextField jtfPdtId = new JTextField();
	/**
	 * text field of search product by name.
	 */
	private JTextField jtfSearchPdt = new JTextField();
	/**
	 * text field of total price.
	 */
	private JTextField jtfPrice = new JTextField();
	/**
	 * text field of final price.
	 */
	private JTextField jtfFinalPrice = new JTextField();
	/**
	 * label of final price.
	 */
	private JLabel jlbFinalPrice = new JLabel("Final price :");
	/**
	 * panel contain the top part of window.
	 */
	private JPanel jpUp = new JPanel();
	/**
	 * panel contain the middle part of window.
	 */
	private JPanel jpMiddle = new JPanel();
	/**
	 * panel contain the bottom part of window.
	 */
	private JPanel jpDown = new JPanel();
	/**
	 * the id of the product which is selected in the product list table.
	 */
	private long seletedPdtId = 0;
	/**
	 * date of the order.
	 */
	private java.util.Date date = Calendar.getInstance().getTime();
	/**
	 * spinner for discount percentage.
	 */
	private JSpinner jspinner;
	/**
	 * model for jspinner.
	 */
	private SpinnerNumberModel spinnerModel;
	/**
	 * Scroll pane for product search result table.
	 */
	private JScrollPane jspPdtSearch;
	/**
	 * Scroll pane for product list result.
	 */
	private JScrollPane jspPdtList;
	/**
	 * the order loaded. null if creation of a new order.
	 */
	private Order order;

	/**
	 * contructor.
	 * 
	 * @param owner
	 *            father JFrame of this dialog
	 * @param modal
	 *            modal of dialog
	 * @param order
	 *            order to load by dialog, null if create a new order
	 */
	public OrderDialog(Frame owner, boolean modal, Order order) {
		super(owner, modal);
		this.setTitle("Order");
		this.setSize(490, 750);
		this.setResizable(false);
		this.order = order;
		getContentPane().setLayout(null);

		init();

		chargeDataInTextField();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * initialization init panels, labels, textFields, buttons, models and.
	 * tables
	 * 
	 */
	public void init() {
		initPanelTop();

		initPanelMiddle();

		initPanelBottom();

		initListner();

		initSpinner();
	}

	/**
	 * init top panel.
	 */
	@SuppressWarnings("serial")
	private void initPanelTop() {
		// add components in the up panel
		jpUp.setBounds(12, 5, 455, 138);
		jpUp.setLayout(new GridLayout(6, 3));
		jpUp.add(jlId);
		jpUp.add(jtfId);
		jpUp.add(new JPanel());
		jpUp.add(jlIsPaid);
		jpUp.add(jtfIsPaid);
		jpUp.add(new JPanel());
		jpUp.add(jlCltName);
		jpUp.add(jtfCltName);
		jpUp.add(new JPanel());
		jpUp.add(jlDate);
		jpUp.add(jtfDate);
		jpUp.add(new JPanel());
		jpUp.add(jlAddPdt);
		jpUp.add(jtfPdtId);
		jpUp.add(jbAddPdt);
		jpUp.add(jlSearchPdt);
		jpUp.add(jtfSearchPdt);
		jpUp.add(jbSearchPdt);
		getContentPane().add(jpUp);

		// set not editable for some text field
		jtfIsPaid.setEditable(false);
		jtfId.setEditable(false);
		jtfDate.setEditable(false);

		// model for search result table and selected products table
		String[][] datas = {};
		String[] titlesList = { "ID", "Name", "Price", "Quantity" };
		String[] titlesSearch = { "ID", "Name", "Price", "Stock" };
		modelPdtSearch = new DefaultTableModel(datas, titlesSearch) {
			// set cell non editable
			@Override
			public boolean isCellEditable(int row, int column) {
				// return column == 0 || column == 1 || column == 2 || column ==
				// 3 ? true : false;
				return false;
			}
		};
		modelPdtList = new DefaultTableModel(datas, titlesList) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO colum 3 eidtable is better,
				// but need to add a cell value change listner

				// return column == 3 ? true : false;
				return false;
			}
		};

		// configure search table
		jtbPdtSearch = new JTable(modelPdtSearch);
		jtbPdtSearch.setBounds(1, 27, 450, 0);
		jtbPdtSearch.setPreferredScrollableViewportSize(new Dimension(450, 180));
	}

	/**
	 * init middle panel.
	 */
	private void initPanelMiddle() {
		// configure the middle panel
		jpMiddle.setBounds(12, 155, 475, 230);
		jpMiddle.setLayout(null);
		JLabel label = new JLabel("Search result : ");
		label.setBounds(0, 0, 130, 15);
		jpMiddle.add(label);
		jpMiddle.add(jtbPdtSearch);
		jspPdtSearch = new JScrollPane(jtbPdtSearch);
		jspPdtSearch.setBounds(0, 18, 452, 212);
		jpMiddle.add(jspPdtSearch);
		getContentPane().add(jpMiddle);

		// configure selected product table
		jtbPdtList = new JTable(modelPdtList);
		jtbPdtList.setPreferredScrollableViewportSize(new Dimension(450, 180));
		jtbPdtList.setCellSelectionEnabled(true);
		jspPdtList = new JScrollPane(jtbPdtList);
		jspPdtList.setBounds(0, 21, 452, 212);

		// remove a product from the selected product list when double click on
		// it
		jtbPdtList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() == 2) {
					removePdt();
				}
			}
		});
	}

	/**
	 * init bottom panel.
	 */
	private void initPanelBottom() {
		// configure the bottom panel
		jpDown.setBounds(12, 395, 475, 316);
		// jp_down.setLayout(new BoxLayout(jp_down, BoxLayout.Y_AXIS));
		// jtb_pdtList.setSize(jp_down.getWidth(), jp_down.getHeight());
		jpDown.setPreferredSize(new Dimension(490, 350));
		jpDown.setLayout(null);
		jlPdtList.setBounds(0, 0, 130, 15);
		jlPdtList.setAlignmentX(LEFT_ALIGNMENT);
		jpDown.add(jlPdtList);
		// jp_down.add(jtb_pdtList);
		jpDown.add(jspPdtList);
		jbRemovePdt.setBounds(359, 243, 95, 23);
		jpDown.add(jbRemovePdt);
		jlPrice.setBounds(0, 245, 100, 15);
		jpDown.add(jlPrice);
		jtfPrice.setBounds(90, 243, 119, 22);
		jtfPrice.setPreferredSize(new Dimension(250, 25));
		jpDown.add(jtfPrice);
		jbCreateOdr.setBounds(359, 282, 95, 23);
		jpDown.add(jbCreateOdr);
		getContentPane().add(jpDown);

		// listener for buttons
		jbAddPdt.addActionListener(this);
		jbCreateOdr.addActionListener(this);
		jbRemovePdt.addActionListener(this);
		jbSearchPdt.addActionListener(this);
		jbSave.addActionListener(this);
		jbCancel.addActionListener(this);

		JLabel jlDiscount = new JLabel("Discount :");
		jlDiscount.setBounds(214, 245, 100, 18);
		jpDown.add(jlDiscount);

		jtfFinalPrice.setBounds(90, 283, 176, 22);
		jpDown.add(jtfFinalPrice);
		jtfFinalPrice.setColumns(10);

		jlbFinalPrice.setBounds(0, 285, 100, 18);
		jpDown.add(jlbFinalPrice);

		jbSave.setBounds(272, 282, 81, 23);
		jpDown.add(jbSave);

		jbCancel.setBounds(359, 282, 95, 23);
		jpDown.add(jbCancel);
	}

	/**
	 * add listner for table, list.
	 */
	private void initListner() {
		// table listener
		ListSelectionModel cellSelectionModelPdtList = jtbPdtList.getSelectionModel();
		cellSelectionModelPdtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModelPdtList.addListSelectionListener(new ListSelectionListener() {
			// get selected product's id when a product in selected
			// product
			// table is selected
			public void valueChanged(ListSelectionEvent ev) {
				long selectedData = 0;

				int[] selectedRow = jtbPdtList.getSelectedRows();
				if (selectedRow.length != 0) {
					selectedData = (long) jtbPdtList.getValueAt(selectedRow[0], 0);
				}
				seletedPdtId = selectedData;
			}
		});

		jtbPdtSearch.setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModelPdtSearch = jtbPdtSearch.getSelectionModel();
		cellSelectionModelPdtSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModelPdtSearch.addListSelectionListener(new ListSelectionListener() {
			// set selected product's id in Add product's text field
			// when a
			// product in search result table is selected
			public void valueChanged(ListSelectionEvent ev) {
				long selectedData = 0;

				int[] selectedRow = jtbPdtSearch.getSelectedRows();
				if (selectedRow.length != 0) {
					selectedData = (long) jtbPdtSearch.getValueAt(selectedRow[0], 0);
					jtfPdtId.setText(String.valueOf(selectedData));
				}
			}
		});

		jtbPdtSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() == 2) {
					addProduct();
				}
			}
		});
	}

	/**
	 * Initialization of spinner.
	 */
	private void initSpinner() {
		// spinner
		spinnerModel = new SpinnerNumberModel(100.0, 0.0, 100.0, 1.0);
		jspinner = new JSpinner(spinnerModel);
		jspinner.setBounds(289, 245, 60, 22);

		// spinner listener
		JComponent comp = jspinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		jspinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ev) {
				// set text
				jtfFinalPrice.setText(
						// parse a double to a string
						String.valueOf(
								// limit the double to 2 decimal places
								new BigDecimal(
										// parse the string in text field to
										// double and do the calculate
										Double.parseDouble(jtfPrice.getText()) * (double) spinnerModel.getValue()
												* 0.01d).setScale(2, RoundingMode.HALF_UP).doubleValue()));
			}
		});

		jpDown.add(jspinner);
	}

	/**
	 * Charge data in text field when load an order. Set initial data in text
	 * field when create an order.
	 */
	private void chargeDataInTextField() {

		// Values in some textFields and table are different between create a
		// new order and load an order

		// load a order
		if (order != null) {
			jtfId.setText(String.valueOf(order.getId()));

			if (order.getIsPaid()) {
				jtfIsPaid.setText("Paid");
			} else {
				jtfIsPaid.setText("Unpaid");
			}
			jtfCltName.setText(order.getClientName());
			jtfDate.setText(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(order.getDate()));
			for (OrderProduct orderProduct : order.getProductList()) {
				Product pdt = ProductDao.getProduct(orderProduct.getProductId());
				modelPdtList.addRow(
						new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), orderProduct.getQuantity() });
			}
			jtfPrice.setText(String.valueOf(order.getPrice()));
			jtfFinalPrice.setText(String.valueOf(order.getPriceDiscount()));
			spinnerModel.setValue(100 * order.getPriceDiscount() / order.getPrice());

			jbCreateOdr.setVisible(false);
		} else {
			// new order
			jtfId.setText(String.valueOf(OrderDao.nextOdrId()));
			jtfIsPaid.setText("Unpaid");
			jtfDate.setText(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(date));

			jbSave.setVisible(false);
			jbCancel.setVisible(false);
		}
	}

	/**
	 * Remove a product from the selected product table. Click on the remove
	 * button or double click on the product.
	 */
	private void removePdt() {
		// remove a product
		for (int i = 0; i < modelPdtList.getRowCount(); i++) {
			if ((long) modelPdtList.getValueAt(i, 0) == seletedPdtId) {
				modelPdtList.removeRow(i);
			}
		}

		// calculate total price
		double total = 0;
		for (int i = 0; i < modelPdtList.getRowCount(); i++) {
			total = total + ((double) modelPdtList.getValueAt(i, 2)) * (int) (modelPdtList.getValueAt(i, 3));
		}
		jtfPrice.setText(String.valueOf(total));
		jtfFinalPrice.setText(String.valueOf(total * (double) spinnerModel.getValue() * 0.01));
	}

	/**
	 * Add a product into the selected product table. Click on the add button or
	 * double click on the product.
	 */
	private void addProduct() {
		// if is a long
		if (Regex.isLong(jtfPdtId.getText())) {
			Product pdt = ProductDao.getProduct(Long.parseLong(jtfPdtId.getText()));
			if (pdt != null) {
				// add product
				// if the product is already existed in the added product
				// list
				// then add the quantity
				boolean isExistedInList = false;
				for (int i = 0; i < modelPdtList.getRowCount(); i++) {
					if ((long) modelPdtList.getValueAt(i, 0) == pdt.getId()) {
						isExistedInList = true;
					}
				}
				if (isExistedInList) {
					for (int i = 0; i < modelPdtList.getRowCount(); i++) {
						if ((long) modelPdtList.getValueAt(i, 0) == pdt.getId()) {
							if (pdt.getStock() > (int) modelPdtList.getValueAt(i, 3)) {
								if (pdt.getSupplierId() != 0) {
									modelPdtList.setValueAt((int) modelPdtList.getValueAt(i, 3) + 1, i, 3);
								} else {
									JOptionPane.showConfirmDialog(this,
											"this product doesn't has a supplier now.\n"
													+ "Please contact Admin to choose a supplier for this product.\n"
													+ "Else, this product doesn't have a price.",
											"Opps", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
								}
							} else {
								System.out.println("Stock not enought");
								JOptionPane.showConfirmDialog(this, "Stock not enought", "Opps",
										JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				} else {
					// else add a new line
					if (pdt.getStock() != 0) {
						if (pdt.getSupplierId() != 0) {
							modelPdtList.addRow(new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), 1 });
						} else {
							JOptionPane.showConfirmDialog(this,
									"this product doesn't has a supplier now.\n"
											+ "Please contact Admin to choose a supplier for this product.\n"
											+ "Else, this product doesn't have a price.",
									"Opps", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						}
					} else {
						System.out.println("Stock not enought");
						JOptionPane.showConfirmDialog(this, "Stock not enought", "Opps", JOptionPane.DEFAULT_OPTION,
								JOptionPane.WARNING_MESSAGE);
					}
				}

				// calculate total price
				double total = 0;
				for (int i = 0; i < modelPdtList.getRowCount(); i++) {
					total = total + ((double) modelPdtList.getValueAt(i, 2)) * (int) (modelPdtList.getValueAt(i, 3));
				}
				jtfPrice.setText(String.valueOf(total));
				jtfFinalPrice.setText(String.valueOf(total * (double) spinnerModel.getValue() * 0.01));
			} else {
				System.out.println("pdt not found");
			}
		} else {
			System.out.println("pdt id wrong type");
			JOptionPane.showConfirmDialog(this, "Please enter a correct ID", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Search a product by name.
	 */
	private void searchProductByName() {
		ArrayList<Product> pdtList = ProductDao.getProductList();
		// remove all row
		for (int i = modelPdtSearch.getRowCount() - 1; i >= 0; i--) {
			modelPdtSearch.removeRow(i);
		}
		// add new search result in table
		if (!pdtList.isEmpty()) {
			for (Product pdt : pdtList) {
				if (pdt.getName().contains(jtfSearchPdt.getText())) {
					modelPdtSearch.addRow(new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), pdt.getStock() });
				}
			}

		} else {
			System.out.println("pdt not found");
		}
	}

	/**
	 * Create an order.
	 */
	private void createOrder() {
		// if the selected product table is not empty
		if (jtbPdtList.getRowCount() != 0) {
			// add the selected product in a list
			ArrayList<OrderProduct> productList = new ArrayList<OrderProduct>();
			for (int i = 0; i < modelPdtList.getRowCount(); i++) {
				productList.add(new OrderProduct(Long.parseLong(jtfId.getText()), (Long) modelPdtList.getValueAt(i, 0),
						(int) modelPdtList.getValueAt(i, 3)));
			}
			// Create a new order
			final Order newOrder = new Order(Long.parseLong(jtfId.getText()), Double.parseDouble(jtfPrice.getText()),
					Double.parseDouble(jtfFinalPrice.getText()), jtfCltName.getText(), false,
					new java.sql.Date(date.getTime()), productList);
			System.out.println(newOrder.toString());
			// add order into date base
			OrderDao.addOrder(newOrder);
			JOptionPane.showConfirmDialog(this, "Order created.", "OK", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
			// if no product selected
		} else {
			System.out.println("product list void");
			JOptionPane.showConfirmDialog(this, "No product selected.", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Save the order after edit.
	 */
	private void saveOrder() {
		// if table not void
		if (jtbPdtList.getRowCount() != 0) {
			// create new order object
			ArrayList<OrderProduct> productList = new ArrayList<OrderProduct>();
			for (int i = 0; i < modelPdtList.getRowCount(); i++) {
				productList.add(new OrderProduct(order.getId(), (Long) modelPdtList.getValueAt(i, 0),
						(int) modelPdtList.getValueAt(i, 3)));
			}
			order.setPrice(Double.parseDouble(jtfPrice.getText()));
			order.setPriceDiscount(Double.parseDouble(jtfFinalPrice.getText()));
			order.setProductList(productList);
			order.setClientName(jtfCltName.getText());

			System.out.println(order.toString());

			OrderDao.updateOrder(order);

			JOptionPane.showConfirmDialog(this, "Order Updated.", "OK", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} else {
			System.out.println("product list void");
			JOptionPane.showConfirmDialog(this, "No product selected.", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Cancel the order.
	 */
	private void cancelOrder() {
		if (JOptionPane.showConfirmDialog(this,
				"Do you really want to Cancel this order?\nIt means DELETE this order from our database and it can not redo!",
				"Comfirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			// y for 0, n for 1
			OrderDao.deleteOrder(order.getId());
			JOptionPane.showConfirmDialog(this, "Order deleted", "OK", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		}
	}

	/**
	 * action perform if a button on click.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		// add a product button on click
		if (ev.getSource() == jbAddPdt) {
			addProduct();
		} else if (ev.getSource() == jbRemovePdt) {
			// remove a product button on click
			removePdt();
		} else if (ev.getSource() == jbSearchPdt) {
			// search product by name button on click
			searchProductByName();
		} else if (ev.getSource() == jbCreateOdr) {
			// create the order button on click
			createOrder();
		} else if (ev.getSource() == jbSave) {
			// edit button on click
			saveOrder();
		} else if (ev.getSource() == jbCancel) {
			// cancel button on click
			cancelOrder();
		}
	}

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		OrderProduct orderProduct1 = new OrderProduct(1, 1, 1);
		OrderProduct orderProduct2 = new OrderProduct(1, 2, 2);
		OrderProduct orderProduct3 = new OrderProduct(1, 3, 3);

		ArrayList<OrderProduct> orderProductsList = new ArrayList<OrderProduct>();
		orderProductsList.add(orderProduct1);
		orderProductsList.add(orderProduct2);
		orderProductsList.add(orderProduct3);

		// 1451606400000 => 01/01/2016
		Order order = new Order(12, 321.01, 32, "HE Junyang", true, new java.sql.Date(1451606400000L),
				orderProductsList);

		// test new order
		// OrderDialog orderDialog = new OrderDialog(null, true, null);

		// test load order
		OrderDialog orderDialog = new OrderDialog(null, true, OrderDao.getOrder(4));

	}
}
