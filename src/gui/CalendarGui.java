package gui;

import dao.OrderDao;
import dao.ProductDao;
import gds.Order;
import gds.OrderProduct;
import gds.Product;
import gds.User;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

public final class CalendarGui extends JFrame {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2663196609962279698L;
	/**
	 * check box is checked, means All product.
	 */
	private static final int ALLPRODUCT = 1;
	/**
	 * check box is unchecked, means one product.
	 */
	private static final int ONEPRODUCT = 2;
	/**
	 * radio button chosen single day.
	 */
	private static final int SINGLEDAY = 1;
	/**
	 * radio button chosen period.
	 */
	private static final int PERIOD = 2;
	/**
	 * check box's state.
	 */
	private static int checkBoxState = ONEPRODUCT;
	/**
	 * radio button's state.
	 */
	private static int radioButtonState = SINGLEDAY;

	/**
	 * main panel.
	 */
	private JPanel jpMain;
	/**
	 * product list panel, at left.
	 */
	private JPanel jpProductList;
	/**
	 * label : Product list.
	 */
	private JLabel jlProductList;
	/**
	 * text field, for filter the product list.
	 */
	private JTextField jtfPdtFilter;
	/**
	 * products'name's JList.
	 */
	private JList<String> jlistProductList;
	/**
	 * model of product JList.
	 */
	private ListModel<String> modelProductList;
	/**
	 * the scroll pane which warp product JList.
	 */
	private JScrollPane jspProductList;

	/**
	 * order table panel.
	 */
	private JPanel jpOrderPane;
	/**
	 * label : product's name / All product / Indication.
	 */
	private JLabel jlProductName;
	/**
	 * Orders list's table.
	 */
	private JTable jtbOrderList;
	/**
	 * model of order list table.
	 */
	private DefaultTableModel modelOrderList;
	/**
	 * scroll pane which warp order list table.
	 */
	private JScrollPane jspOrderList;

	/**
	 * date picker panel.
	 */
	private JPanel jpDatePicker;
	/**
	 * button group of two radio button.
	 */
	private ButtonGroup buttonGroup;
	/**
	 * radio button : single day.
	 */
	private JRadioButton jrbSingleDay;
	/**
	 * radio button : period.
	 */
	private JRadioButton jrbPeriod;
	/**
	 * check box : All product or one product.
	 */
	private JCheckBox jcbOneProductOrAll;
	/**
	 * date picker for the first date.
	 */
	private JXDatePicker datePickerA;
	/**
	 * date pciker for the second date.
	 */
	private JXDatePicker datePickerB;
	/**
	 * search order button.
	 */
	private JButton jbSearch;

	/**
	 * list of all product in db.
	 */
	private ArrayList<Product> productList;
	/**
	 * list of all order in database.
	 */
	private ArrayList<Order> orderList;
	/**
	 * the currently selected product's name.
	 */
	private String selectedProductName;

	/**
	 * Calendar of order.
	 * @param user the User who logged in
	 */
	public CalendarGui(User user) {
		jpMain = new JPanel();
		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		jpMain.setLayout(null);

		initProductListPanel();

		initDatePickerPanel();

		initOrderPanel();

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				new OrderGui(user);
				dispose();
			}
		});
		this.setSize(1024, 768);
		this.setContentPane(jpMain);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Order Calendar");
		this.setVisible(true);
	}

	/**
	 * init the order panel. Add components.
	 */
	@SuppressWarnings("serial")
	private void initOrderPanel() {
		jpOrderPane = new JPanel();
		jpOrderPane.setBounds(246, 45, 762, 684);
		jpOrderPane.setLayout(null);

		jlProductName = new JLabel(
				"Please select a product or check \"All products\" in order to show the order's caclendar.");
		jlProductName.setLocation(0, 0);
		jlProductName.setSize(762, 25);
		jpOrderPane.add(jlProductName);

		String[][] data = null;
		String[] names = { "Date", "ID", "Client's name", "price", "Final price", "State" };
		modelOrderList = new DefaultTableModel(data, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtbOrderList = new JTable(modelOrderList);
		jspOrderList = new JScrollPane();
		jspOrderList.setBounds(0, 35, 762, 649);
		jspOrderList.setViewportView(jtbOrderList);
		jtbOrderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jpOrderPane.add(jspOrderList);

		jpMain.add(jpOrderPane);
	}

	/**
	 * init the product list panel. add components.
	 */
	private void initProductListPanel() {
		// init components
		jpProductList = new JPanel();
		jpProductList.setBounds(10, 10, 226, 719);
		jpProductList.setLayout(null);

		jlProductList = new JLabel("Product list : ");
		jlProductList.setBounds(0, 0, 216, 25);
		jtfPdtFilter = new JTextField();
		jtfPdtFilter.setBounds(0, 35, 216, 25);

		// add products' name in the list
		productList = ProductDao.getProductList();
		modelProductList = new DefaultListModel<String>();
		for (Product pdt : productList) {
			((DefaultListModel<String>) modelProductList).addElement(pdt.getName());
		}
		jlistProductList = new JList<String>(modelProductList);
		jlistProductList.setBounds(0, 0, 130, 500);

		// scroll pane for list
		jspProductList = new JScrollPane();
		jspProductList.setBounds(0, 70, 216, 649);
		jspProductList.setViewportView(jlistProductList);

		// add components
		jpProductList.add(jlProductList);
		jpProductList.add(jtfPdtFilter);
		jpProductList.add(jspProductList);
		jpMain.add(jpProductList);

		// add list listner
		jlistProductList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				selectedProductName = jlistProductList.getSelectedValue();
				if (checkBoxState == ONEPRODUCT) {
					jlProductName.setText(selectedProductName);
				}
			}
		});

		// add text field's document listner
		jtfPdtFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent ev) {
				filterList();
			}

			@Override
			public void insertUpdate(DocumentEvent ev) {
				filterList();
			}

			@Override
			public void changedUpdate(DocumentEvent ev) {
				filterList();
			}

			private void filterList() {
				String text = jtfPdtFilter.getText();
				ArrayList<Product> filteredList = new ArrayList<>();

				// delete old element in ArrayList
				for (Product pdt : filteredList) {
					filteredList.remove(pdt);
				}

				// add new element in ArrayList
				for (Product pdt : productList) {
					if (pdt.getName().contains(text)) {
						filteredList.add(pdt);
					}
				}

				// delete old element in JList
				for (int i = modelProductList.getSize(); i > 0; i--) {
					((DefaultListModel<String>) modelProductList).remove(i - 1);
				}

				// add new element in JList
				for (Product pdt : filteredList) {
					((DefaultListModel<String>) modelProductList).addElement(pdt.getName());
				}
			}
		});
	}

	/**
	 * init the date picker panel. Add components.
	 */
	private void initDatePickerPanel() {
		// date picker panel
		jpDatePicker = new JPanel();
		jpDatePicker.setBounds(246, 10, 762, 25);
		jpMain.add(jpDatePicker);

		datePickerA = new JXDatePicker();
		datePickerB = new JXDatePicker();

		jbSearch = new JButton("Search");

		initRadioButtons();
		jpDatePicker.setLayout(new GridLayout(0, 6, 0, 0));
		jpDatePicker.add(jrbSingleDay);
		jpDatePicker.add(jrbPeriod);

		initCheckBox();
		jpDatePicker.add(jcbOneProductOrAll);
		jpDatePicker.add(datePickerA);
		jpDatePicker.add(datePickerB);

		initSearchButton();
		jpDatePicker.add(jbSearch);

	}

	/**
	 * init the search button. add action listener. Update the table when on
	 * clicked.
	 */
	private void initSearchButton() {

		jbSearch.addActionListener(new ActionListener() {

			ArrayList<Order> orderListFilteredByDate;
			ArrayList<Order> orderListFilteredByProduct;

			@Override
			public void actionPerformed(ActionEvent ev) {
				if (orderList == null) {
					orderList = OrderDao.getOrderList();
				}
				orderListFilteredByDate = new ArrayList<Order>();
				orderListFilteredByProduct = new ArrayList<Order>();
				// if user chose a period
				if (radioButtonState == PERIOD) {
					// if date picker are not void
					if (datePickerA.getDate() != null && datePickerB.getDate() != null) {
						// if date B is after date A
						if (isDateBAfterDateA()) {
							filterPeriodOrder();
							updateTable();
						} else {
							JOptionPane.showConfirmDialog(null, "The seconde date must after the first date.", "Opps",
									JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showConfirmDialog(null, "Date can't be void", "Opps", JOptionPane.DEFAULT_OPTION,
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					// if user chose a single day
					// if date picker is not void
					if (datePickerA.getDate() != null) {
						filterSingleDayOrder();
						updateTable();
					} else {
						JOptionPane.showConfirmDialog(null, "Date can't be void", "Opps", JOptionPane.DEFAULT_OPTION,
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}

			// filter the orders by a date given
			private void filterSingleDayOrder() {
				for (Order order : orderList) {
					// clear ms, s, min, hour
					Calendar cal = Calendar.getInstance();
					cal.setTime(order.getDate());
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					if (cal.getTimeInMillis() == datePickerA.getDate().getTime()) {
						orderListFilteredByDate.add(order);
					}
				}

				filterByProduct();
			}

			// filter the orders by a period given
			private void filterPeriodOrder() {
				for (Order order : orderList) {
					// clear ms, s, min, hour
					Calendar cal = Calendar.getInstance();
					cal.setTime(order.getDate());
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					if (cal.getTimeInMillis() >= datePickerA.getDate().getTime()
							&& cal.getTimeInMillis() <= datePickerB.getDate().getTime()) {
						orderListFilteredByDate.add(order);
					}
				}

				filterByProduct();
			}

			// filter the orders by selected product if check box is unchecked
			private void filterByProduct() {
				if (checkBoxState == ONEPRODUCT) {
					long selectedProductId = 0L;
					for (Product pdt : ProductDao.getProductList()) {
						if (pdt.getName().equals(selectedProductName)) {
							selectedProductId = pdt.getId();
						}
					}
					for (Order order : orderListFilteredByDate) {
						ArrayList<OrderProduct> orderProductList = OrderDao.getOrderProductList(order.getId());
						boolean containsProduct = false;
						for (OrderProduct orderProduct : orderProductList) {
							if (orderProduct.getProductId() == selectedProductId) {
								containsProduct = true;
							}
						}
						if (containsProduct) {
							orderListFilteredByProduct.add(order);
						}
					}
				} else {
					orderListFilteredByProduct = orderListFilteredByDate;
				}
			}

			// update the table after filter the orders
			private void updateTable() {
				for (int i = modelOrderList.getRowCount() - 1; i >= 0; i--) {
					modelOrderList.removeRow(i);
				}
				for (Order order : orderListFilteredByProduct) {
					String state = order.getIsPaid() == true ? "Paid" : "Unpaid";
					Object[] obj = { order.getDate(), order.getId(), order.getClientName(), order.getPrice(),
							order.getPriceDiscount(), state };
					modelOrderList.addRow(obj);
				}
			}
		});
	}

	/**
	 * init check box. add listener
	 */
	private void initCheckBox() {
		jcbOneProductOrAll = new JCheckBox("For all products");
		// Change label when check / uncheck the check box
		jcbOneProductOrAll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				// 1 for checked, 2 for unchecked
				if (ev.getStateChange() == ALLPRODUCT) {
					checkBoxState = ALLPRODUCT;
					jlProductName.setText("All product");
				} else {
					checkBoxState = ONEPRODUCT;
					if (selectedProductName == null) {
						jlProductName.setText(
								"Please select a product or check \"All products\" in order to show the order's caclendar.");
					} else {
						jlProductName.setText(selectedProductName);
					}
				}
			}
		});
	}

	/**
	 * init the radio buttons. add listeners
	 */
	private void initRadioButtons() {
		jrbSingleDay = new JRadioButton("Single Day");
		jrbPeriod = new JRadioButton("Period");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(jrbSingleDay);
		buttonGroup.add(jrbPeriod);

		// add radio buttons listener
		jrbPeriod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if (ev.getActionCommand() == "Period") {
					datePickerB.setVisible(true);
					radioButtonState = PERIOD;
				}
			}
		});
		jrbSingleDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if (ev.getActionCommand() == "Single Day") {
					datePickerB.setVisible(false);
					radioButtonState = SINGLEDAY;
				}
			}
		});

		// set default selection
		buttonGroup.setSelected(jrbSingleDay.getModel(), true);
		datePickerB.setVisible(false);
	}

	/**
	 * is the date of date picker B after the date of date picker A.
	 * 
	 * @return true if yes, false if no.
	 */
	private boolean isDateBAfterDateA() {
		if (datePickerA.getDate().before(datePickerB.getDate())) {
			return true;
		}
		return false;
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new CalendarGui(null);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
