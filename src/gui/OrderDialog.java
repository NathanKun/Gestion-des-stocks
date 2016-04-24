package src.gui;

import src.dao.OrderDAO;
import src.dao.ProductDAO;
import src.gds.Order;
import src.gds.OrderProduct;
import src.gds.Product;
import src.util.Regex;

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
 * OrderDialog class A dialog window to create/edit an order of client
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */

public class OrderDialog extends JDialog implements ActionListener {

	/**
	 * table of products added
	 */
	private JTable jtb_pdtList = null;
	/**
	 * table of products which user search
	 */
	private JTable jtb_pdtSearch = null;
	/**
	 * table model of jtb_pdtList
	 */
	private DefaultTableModel model_pdtList = null;
	/**
	 * table model of jtb_pdtSearch
	 */
	private DefaultTableModel model_pdtSearch = null;
	/**
	 * button : add a product by id
	 */
	private JButton jb_addPdt = new JButton("Add");
	/**
	 * button : create the order
	 */
	private JButton jb_createOdr = new JButton("Create");
	/**
	 * button : search a product by name
	 */
	private JButton jb_searchPdt = new JButton("Search by name");
	/**
	 * button : remove a product on click
	 */
	private JButton jb_removePdt = new JButton("Remove");
	/**
	 * button : edit the order
	 */
	private JButton jb_edit = new JButton("Edit");
	/**
	 * button : cancel the order
	 */
	private JButton jb_cancel = new JButton("Cancel");
	/**
	 * label : Order ID :
	 */
	private JLabel jl_id = new JLabel("Order ID : ");
	/**
	 * label : State :
	 */
	private JLabel jl_isPaid = new JLabel("State : ");
	/**
	 * label : Client name :
	 */
	private JLabel jl_CltName = new JLabel("Client name : ");
	/**
	 * label : Date :
	 */
	private JLabel jl_date = new JLabel("Date : ");
	/**
	 * label : Add product By ID : "); /**
	 */
	private JLabel jl_addPdt = new JLabel("Add product By ID : ");
	/**
	 * label : Search product by name :
	 */
	private JLabel jl_searchPdt = new JLabel("Search product : ");
	/**
	 * label : Product list :
	 */
	private JLabel jl_pdtList = new JLabel("Product list : ");
	/**
	 * label : Totol price :
	 */
	private JLabel jl_price = new JLabel("Totol price : ");
	/**
	 * text field of order id
	 */
	private JTextField jtf_id = new JTextField(/**/);
	/**
	 * text field of id state
	 */
	private JTextField jtf_isPaid = new JTextField();
	/**
	 * text field of client name
	 */
	private JTextField jtf_CltName = new JTextField();
	/**
	 * text field of date
	 */
	private JTextField jtf_date = new JTextField();
	/**
	 * text field of add product by id
	 */
	private JTextField jtf_pdtId = new JTextField();
	/**
	 * text field of search product by name
	 */
	private JTextField jtf_searchPdt = new JTextField();
	/**
	 * text field of total price
	 */
	private JTextField jtf_price = new JTextField();
	/**
	 * text field of final price
	 */
	private JTextField jtf_finalPrice = new JTextField();
	/**
	 * label of final price
	 */
	private JLabel jlb_finalPrice = new JLabel("Final price :");
	/**
	 * panel contain the top part of window
	 */
	private JPanel jp_up = new JPanel();
	/**
	 * panel contain the middle part of window
	 */
	private JPanel jp_middle = new JPanel();
	/**
	 * panel contain the bottom part of window
	 */
	private JPanel jp_down = new JPanel();
	/**
	 * the id of the product which is selected in the product list table
	 */
	private long seletedPdtId = 0;
	/**
	 * date of the order
	 */
	private java.util.Date date = Calendar.getInstance().getTime();
	/**
	 * spinner for discount percentage
	 */
	private JSpinner jspinner;
	/**
	 * model for jspinner
	 */
	private SpinnerNumberModel spinnerModel;
	/**
	 * the order loaded. null if creation of a new order
	 */
	private Order order;

	/**
	 * contructor
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

		init();
		initSpinner();
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
		getContentPane().setLayout(null);
		// add components in the up panel
		jp_up.setBounds(12, 5, 455, 138);
		jp_up.setLayout(new GridLayout(6, 3));
		jp_up.add(jl_id);
		jp_up.add(jtf_id);
		jp_up.add(new JPanel());
		jp_up.add(jl_isPaid);
		jp_up.add(jtf_isPaid);
		jp_up.add(new JPanel());
		jp_up.add(jl_CltName);
		jp_up.add(jtf_CltName);
		jp_up.add(new JPanel());
		jp_up.add(jl_date);
		jp_up.add(jtf_date);
		jp_up.add(new JPanel());
		jp_up.add(jl_addPdt);
		jp_up.add(jtf_pdtId);
		jp_up.add(jb_addPdt);
		jp_up.add(jl_searchPdt);
		jp_up.add(jtf_searchPdt);
		jp_up.add(jb_searchPdt);
		getContentPane().add(jp_up);

		// set not editable for some text field
		jtf_isPaid.setEditable(false);
		jtf_id.setEditable(false);
		jtf_date.setEditable(false);

		// model for search result table and selected products table
		String[][] datas = {};
		String[] titlesList = { "ID", "Name", "Price", "Quantity" };
		String[] titlesSearch = { "ID", "Name", "Price", "Stock" };
		model_pdtSearch = new DefaultTableModel(datas, titlesSearch) {
			// set cell non editable
			@Override
			public boolean isCellEditable(int row, int column) {
				// return column == 0 || column == 1 || column == 2 || column ==
				// 3 ? true : false;
				return false;
			}
		};
		model_pdtList = new DefaultTableModel(datas, titlesList) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 ? true : false;
			}
		};

		// configure search table
		jtb_pdtSearch = new JTable(model_pdtSearch);
		jtb_pdtSearch.setBounds(1, 27, 450, 0);
		jtb_pdtSearch.setPreferredScrollableViewportSize(new Dimension(450, 180));

		// configure the middle panel
		jp_middle.setBounds(12, 155, 475, 230);
		jp_middle.setLayout(null);
		JLabel label = new JLabel("Search result : ");
		label.setBounds(0, 0, 130, 15);
		jp_middle.add(label);
		jp_middle.add(jtb_pdtSearch);
		JScrollPane scrollPane_1 = new JScrollPane(jtb_pdtSearch);
		scrollPane_1.setBounds(0, 18, 452, 212);
		jp_middle.add(scrollPane_1);
		getContentPane().add(jp_middle);

		// configure selected product table
		jtb_pdtList = new JTable(model_pdtList);
		jtb_pdtList.setPreferredScrollableViewportSize(new Dimension(450, 180));
		jtb_pdtList.setCellSelectionEnabled(true);
		JScrollPane scrollPane = new JScrollPane(jtb_pdtList);
		scrollPane.setBounds(0, 21, 452, 212);

		// remove a product from the selected product list when double click on
		// it
		jtb_pdtList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() == 2) {
					removePdt();
				}
			}
		});

		// configure the bottom panel
		jp_down.setBounds(12, 395, 475, 316);
		// jp_down.setLayout(new BoxLayout(jp_down, BoxLayout.Y_AXIS));
		// jtb_pdtList.setSize(jp_down.getWidth(), jp_down.getHeight());
		jp_down.setPreferredSize(new Dimension(490, 350));
		jp_down.setLayout(null);
		jl_pdtList.setBounds(0, 0, 130, 15);
		jl_pdtList.setAlignmentX(LEFT_ALIGNMENT);
		jp_down.add(jl_pdtList);
		// jp_down.add(jtb_pdtList);
		jp_down.add(scrollPane);
		jb_removePdt.setBounds(359, 243, 95, 23);
		jp_down.add(jb_removePdt);
		jl_price.setBounds(0, 245, 100, 15);
		jp_down.add(jl_price);
		jtf_price.setBounds(90, 243, 119, 22);
		jtf_price.setPreferredSize(new Dimension(250, 25));
		jp_down.add(jtf_price);
		jb_createOdr.setBounds(359, 282, 95, 23);
		jp_down.add(jb_createOdr);
		getContentPane().add(jp_down);

		// listener for buttons
		jb_addPdt.addActionListener(this);
		jb_createOdr.addActionListener(this);
		jb_removePdt.addActionListener(this);
		jb_searchPdt.addActionListener(this);
		jb_edit.addActionListener(this);
		jb_cancel.addActionListener(this);

		JLabel jl_discount = new JLabel("Discount :");
		jl_discount.setBounds(214, 245, 100, 18);
		jp_down.add(jl_discount);

		jtf_finalPrice.setBounds(90, 283, 176, 22);
		jp_down.add(jtf_finalPrice);
		jtf_finalPrice.setColumns(10);

		jlb_finalPrice.setBounds(0, 285, 100, 18);
		jp_down.add(jlb_finalPrice);

		jb_edit.setBounds(266, 282, 81, 23);
		jp_down.add(jb_edit);

		jb_cancel.setBounds(359, 282, 95, 23);
		jp_down.add(jb_cancel);

		// table listener
		ListSelectionModel cellSelectionModel_pdtList = jtb_pdtList.getSelectionModel();
		cellSelectionModel_pdtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel_pdtList.addListSelectionListener(new ListSelectionListener() {
			// get selected product's id when a product in selected
			// product
			// table is selected
			public void valueChanged(ListSelectionEvent ev) {
				long selectedData = 0;

				int[] selectedRow = jtb_pdtList.getSelectedRows();
				int[] selectedColumns = jtb_pdtList.getSelectedColumns();
				if (selectedRow.length != 0) {
					selectedData = (long) jtb_pdtList.getValueAt(selectedRow[0], 0);
				}
				seletedPdtId = selectedData;
			}
		});

		jtb_pdtSearch.setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModel_pdtSearch = jtb_pdtSearch.getSelectionModel();
		cellSelectionModel_pdtSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel_pdtSearch.addListSelectionListener(new ListSelectionListener() {
			// set selected product's id in Add product's text field
			// when a
			// product in search result table is selected
			public void valueChanged(ListSelectionEvent ev) {
				long selectedData = 0;

				int[] selectedRow = jtb_pdtSearch.getSelectedRows();
				int[] selectedColumns = jtb_pdtSearch.getSelectedColumns();
				if (selectedRow.length != 0) {
					selectedData = (long) jtb_pdtSearch.getValueAt(selectedRow[0], 0);
					jtf_pdtId.setText(String.valueOf(selectedData));
				}
			}
		});

		jtb_pdtSearch.addMouseListener(new MouseAdapter() {
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
	public void initSpinner() {
		// spinner
		// from 0 to 9, in 1.0 steps start value 5
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
				jtf_finalPrice.setText(
						// parse a double to a string
						String.valueOf(
								// limit the double to 2 decimal places
								new BigDecimal(
										// parse the string in text field to
										// double and do the calculate
										Double.parseDouble(jtf_price.getText()) * (double) spinnerModel.getValue()
												* 0.01d).setScale(2, RoundingMode.HALF_UP).doubleValue()));
			}
		});

		jp_down.add(jspinner);
	}

	/**
	 * Charge data in text field when load an order. Set initial data in text
	 * field when create an order.
	 */
	public void chargeDataInTextField() {

		// Values in some textFields and table are different between create a
		// new order and load an order

		// load a order
		if (order != null) {
			jtf_id.setText(String.valueOf(order.getId()));

			if (order.getIsPaid()) {
				jtf_isPaid.setText("Paid");
			} else {
				jtf_isPaid.setText("Unpaid");
			}
			jtf_CltName.setText(order.getClientName());
			jtf_date.setText(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(order.getDate()));
			ProductDAO productDao = new ProductDAO();
			for (OrderProduct orderProduct : order.getProductList()) {
				Product pdt = productDao.getProduct(orderProduct.getProductId());
				model_pdtList.addRow(
						new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), orderProduct.getQuantity() });
			}
			jtf_price.setText(String.valueOf(order.getPrice()));
			jtf_finalPrice.setText(String.valueOf(order.getPriceDiscount()));
			spinnerModel.setValue(100 * order.getPriceDiscount() / order.getPrice());

			jb_createOdr.setVisible(false);
		} else {
			// new order
			jtf_id.setText(String.valueOf(new OrderDAO().idGeneratorOdr()));
			jtf_isPaid.setText("Unpaid");
			jtf_date.setText(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(date));

			jb_edit.setVisible(false);
			jb_cancel.setVisible(false);
		}
	}

	/**
	 * Remove a product from the selected product table.
	 * Click on the remove button or double click on the product.
	 */
	private void removePdt() {
		// remove a product
		for (int i = 0; i < model_pdtList.getRowCount(); i++) {
			if ((long) model_pdtList.getValueAt(i, 0) == seletedPdtId) {
				model_pdtList.removeRow(i);
			}
		}

		// calculate total price
		double total = 0;
		for (int i = 0; i < model_pdtList.getRowCount(); i++) {
			total = total + ((double) model_pdtList.getValueAt(i, 2)) * (int) (model_pdtList.getValueAt(i, 3));
		}
		jtf_price.setText(String.valueOf(total));
		jtf_finalPrice.setText(String.valueOf(total * (double) spinnerModel.getValue() * 0.01));
	}
	
	/**
	 * Add a product into the selected product table.
	 * Click on the add button or double click on the product.
	 */
	private void addProduct() {
		// if is a long
		if (Regex.isLong(jtf_pdtId.getText())) {
			Product pdt = new ProductDAO().getProduct(Long.parseLong(jtf_pdtId.getText()));
			if (pdt != null) {
				// add product
				// if the product is already existed in the added product
				// list
				// then add the quantity
				boolean bool = false;
				for (int i = 0; i < model_pdtList.getRowCount(); i++) {
					if ((long) model_pdtList.getValueAt(i, 0) == pdt.getId()) {
						bool = true;
					}
				}
				if (bool) {
					for (int i = 0; i < model_pdtList.getRowCount(); i++) {
						if ((long) model_pdtList.getValueAt(i, 0) == pdt.getId()) {
							if (pdt.getStock() > (int)model_pdtList.getValueAt(i, 3)) {
								model_pdtList.setValueAt((int) model_pdtList.getValueAt(i, 3) + 1, i, 3);
							} else {
								System.out.println("Stock not enought");
								JOptionPane.showConfirmDialog(null, "Stock not enought", "Opps", JOptionPane.DEFAULT_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				} else {
					// else add a new line
					model_pdtList.addRow(new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), 1 });
				}

				// calculate total price
				double total = 0;
				for (int i = 0; i < model_pdtList.getRowCount(); i++) {
					total = total + ((double) model_pdtList.getValueAt(i, 2)) * (int) (model_pdtList.getValueAt(i, 3));
				}
				jtf_price.setText(String.valueOf(total));
				jtf_finalPrice.setText(String.valueOf(total * (double) spinnerModel.getValue() * 0.01));
			} else {
				System.out.println("pdt not found");
			}
		} else {
			System.out.println("pdt id wrong type");
			JOptionPane.showConfirmDialog(null, "Please enter a correct ID", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * action perform if a button on click.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		// add a product button on click
		if (ev.getSource() == jb_addPdt) {
			addProduct();
		} else if (ev.getSource() == jb_removePdt)

		{
			// remove a product button on click
			this.removePdt();
		} else if (ev.getSource() == jb_searchPdt) {
			// search product by name button on click

			ArrayList<Product> pdtList = new ProductDAO().getProductList();
			// remove all row
			for (int i = model_pdtSearch.getRowCount() - 1; i >= 0; i--) {
				model_pdtSearch.removeRow(i);
			}
			// add new search result in table
			if (!pdtList.isEmpty()) {
				for (Product pdt : pdtList) {
					if (pdt.getName().contains(jtf_searchPdt.getText())) {
						model_pdtSearch
								.addRow(new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), pdt.getStock() });
					}
				}

			} else {
				System.out.println("pdt not found");
			}
		} else if (ev.getSource() == jb_createOdr) {
			// create the order button on click

			// if the selected product table is not empty
			if (jtb_pdtList.getRowCount() != 0) {
				OrderDAO orderDao = new OrderDAO();
				// add the selected product in a list
				ArrayList<OrderProduct> productList = new ArrayList<OrderProduct>();
				for (int i = 0; i < model_pdtList.getRowCount(); i++) {
					productList.add(new OrderProduct(orderDao.idGeneratorOdr(), (Long) model_pdtList.getValueAt(i, 0),
							(int) model_pdtList.getValueAt(i, 3)));
				}
				// Create a new order
				Order newOrder = new Order(orderDao.idGeneratorOdr(), Double.parseDouble(jtf_price.getText()),
						Double.parseDouble(jtf_finalPrice.getText()), jtf_CltName.getText(), false,
						new java.sql.Date(date.getTime()), productList);
				System.out.println(newOrder.toString());
				// add order into date base
				orderDao.addOrder(newOrder);
				JOptionPane.showConfirmDialog(null, "Order created.", "OK", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
				// if no product selected
			} else {
				System.out.println("product list void");
				JOptionPane.showConfirmDialog(null, "No product selected.", "Opps", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (ev.getSource() == jb_edit) {
			// edit button on click

			// if table not void
			if (jtb_pdtList.getRowCount() != 0) {
				// create new order
				OrderDAO orderDao = new OrderDAO();
				ArrayList<OrderProduct> productList = new ArrayList<OrderProduct>();
				for (int i = 0; i < model_pdtList.getRowCount(); i++) {
					productList.add(new OrderProduct(orderDao.idGeneratorOdr(), (Long) model_pdtList.getValueAt(i, 0),
							(int) model_pdtList.getValueAt(i, 3)));
				}
				Order newOrder = new Order(orderDao.idGeneratorOdr(), Double.parseDouble(jtf_price.getText()),
						Double.parseDouble(jtf_finalPrice.getText()), jtf_CltName.getText(), false,
						new java.sql.Date(date.getTime()), productList);
				System.out.println(newOrder.toString());

				// TODO orderDAO.updateOrder(newOrder)

				JOptionPane.showConfirmDialog(null, "Order Updated.", "OK", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				System.out.println("product list void");
				JOptionPane.showConfirmDialog(null, "No product selected.", "Opps", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (ev.getSource() == jb_cancel) {
			// cancel button on click

			if (JOptionPane.showConfirmDialog(null, "Do you really want to cancel this order?", "Comfirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				// TODO orderDAO.deleteOrder(order.getId());
			}
		}
	}

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
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
		OrderDialog orderDialog = new OrderDialog(null, true, new OrderDAO().getOrder(4));

	}
}