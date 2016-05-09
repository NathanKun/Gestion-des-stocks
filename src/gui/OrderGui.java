package gui;

import dao.OrderDao;
import dao.ProductDao;
import gds.Order;
import gds.OrderProduct;
import gds.Product;
import gds.User;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * graphical user interface of Order menu window.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class OrderGui extends JFrame implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2723427906401070121L;
	/**
	 * main container.
	 */
	private JPanel jpMain = new JPanel();
	/**
	 * main background.
	 */
	private JLabel jlBgMain = new JLabel();
	/**
	 * Button : Link to main page.
	 */
	private JButton jbReturn = new JButton("Return");
	/**
	 * Button : Link to create order page.
	 */
	private JButton jbNew = new JButton("New order");
	/**
	 * Button : search order .
	 */
	private JButton jbDisplayAllOrder = new JButton("Display all Orders");
	/**
	 * Button : Link to edit order page.
	 */
	private JButton jbEdit = new JButton("Edit");
	/**
	 * Button : Link to edit order page.
	 */
	private JButton jbCancel = new JButton("Cancel");
	/**
	 * Button : Link to calendar.
	 */
	private JButton jbCalendar = new JButton("Calendar");
	/**
	 * Button : Link to replenishment.
	 */
	private JButton jbReplenish = new JButton("Replenish");
	/**
	 * Button : Settle an order.
	 */
	private JButton jbSettle = new JButton("Settle");
	/**
	 * big font size for buttons.
	 */
	private Font fontBig = new Font(null, 0, 18);
	/**
	 * User who logged in.
	 */
	private User user;
	/**
	 * The table of the list of order.
	 */
	private JTable jtbOrderList = null;
	/**
	 * table model for jtbOrderList.
	 */
	private DefaultTableModel tableModel = null;
	/**
	 * the scroll pane which warp the order list table.
	 */
	private final JScrollPane jspOrderList = new JScrollPane();
	/**
	 * the panel which warp the jspOrderList.
	 */
	private JPanel jpOrderList = new JPanel();
	/**
	 * the id of the order which is selected.
	 */
	private long seletedOrderId = 0;

	/**
	 * constructor.
	 * 
	 * @param user
	 *            The user who logged in
	 */
	public OrderGui(User user) {
		this.user = user;
		init();
		initButtons();
		initTable();
	}

	/**
	 * initialization init JFrame init JPanel jp_main main container init.
	 * background
	 */
	public void init() {
		this.setTitle("Gestion de stocks - Order");
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// confirmation when X on click
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
					// y for 0, n for 1
					// dispose();
					System.exit(0);
				}
			}
		});

		// not resizable, components will use the absolute position
		this.setResizable(false);
		this.setVisible(true);

		jpMain.setLayout(null);
		jpMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// setup background
		jlBgMain.setIcon(new ImageIcon("data\\bg_order.png"));
		jlBgMain.setBounds(0, 0, 1024, 768);
		this.getLayeredPane().add(jlBgMain, new Integer(Integer.MIN_VALUE));
		jpMain = (JPanel) this.getContentPane();
		jpMain.setOpaque(false);
	}

	/**
	 * init buttons.
	 */
	public void initButtons() {
		jbNew.setBounds(780, 250, 200, 50);
		jbEdit.setBounds(780, 350, 200, 50);
		jbCancel.setBounds(780, 550, 200, 50);
		jbDisplayAllOrder.setBounds(780, 150, 200, 50);
		jbCalendar.setBounds(805, 50, 150, 50);
		jbReplenish.setBounds(805, 650, 150, 50);
		jbReturn.setBounds(50, 600, 100, 100);
		jbSettle.setBounds(780, 450, 200, 50);

		jbNew.setFont(fontBig);
		jbEdit.setFont(fontBig);
		jbCancel.setFont(fontBig);
		jbDisplayAllOrder.setFont(fontBig);
		jbCalendar.setFont(fontBig);
		jbReplenish.setFont(fontBig);
		jbReturn.setFont(fontBig);
		jbSettle.setFont(fontBig);

		jbNew.addActionListener(this);
		jbEdit.addActionListener(this);
		jbCancel.addActionListener(this);
		jbDisplayAllOrder.addActionListener(this);
		jbCalendar.addActionListener(this);
		jbReplenish.addActionListener(this);
		jbReturn.addActionListener(this);
		jbSettle.addActionListener(this);

		jpMain.add(jbNew);
		jpMain.add(jbEdit);
		jpMain.add(jbCancel);
		jpMain.add(jbDisplayAllOrder);
		jpMain.add(jbCalendar);
		jpMain.add(jbReplenish);
		jpMain.add(jbReturn);
		jpMain.add(jbSettle);

	}

	/**
	 * init table an it's panel.
	 */
	@SuppressWarnings("serial")
	public void initTable() {
		// init model and table
		String[][] datas = {};
		String[] titles = { "ID", "Client's name", "Date", "Final price", "State" };
		tableModel = new DefaultTableModel(datas, titles) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtbOrderList = new JTable(tableModel);
		jpOrderList.setBounds(201, 150, 522, 450);
		getContentPane().add(jpOrderList);
		jpOrderList.setLayout(null);
		jspOrderList.setBounds(0, 0, 522, 450);
		jpOrderList.add(jspOrderList);
		jspOrderList.setViewportView(jtbOrderList);
		jtbOrderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbOrderList.setCellSelectionEnabled(false);

		getContentPane().add(jbSettle);

		JLabel lblThisIsFor = new JLabel("The Replenish button is for replenish manually.");
		lblThisIsFor.setBounds(256, 685, 316, 15);
		getContentPane().add(lblThisIsFor);

		JLabel lblTheSystemWill = new JLabel(
				"Tips : The system will replenish automaticly when the stock of a product < 15.");
		lblTheSystemWill.setBounds(256, 660, 514, 15);
		getContentPane().add(lblTheSystemWill);

		// get selected order's id when the order is seleted
		ListSelectionModel cellSelectionModel = jtbOrderList.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent ev) {
				long selectedData = 0;

				int[] selectedRow = jtbOrderList.getSelectedRows();
				if (selectedRow.length != 0) {
					selectedData = (long) jtbOrderList.getValueAt(selectedRow[0], 0);
				}
				seletedOrderId = selectedData;
			}
		});

		// Edit the order when double click on it
		jtbOrderList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() == 2) {
					showEditOrderDialog();
				}
			}
		});
	}

	/**
	 * show Dialog to edit an Order.
	 */
	private void showEditOrderDialog() {
		if (seletedOrderId != 0) {
			new OrderDialog(this, true, OrderDao.getOrder(seletedOrderId));
		} else {
			System.out.println("No seleted order");
			JOptionPane.showConfirmDialog(null, "No order seleted", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * list all orders.
	 */
	private void searchButtonOnClick() {
		// clear the table first
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		// add new data
		ArrayList<Order> list = OrderDao.getOrderList();
		for (Order order : list) {
			String str = order.getIsPaid() == true ? "Paid" : "Unpaid";
			Object[] objects = { order.getId(), order.getClientName(), order.getDate(), order.getPriceDiscount(), str };
			tableModel.addRow(objects);
		}
	}

	/**
	 * cancel the selected order.
	 */
	private void cancelButtonOnClick() {
		if (seletedOrderId != 0) {
			if (JOptionPane.showConfirmDialog(null,
					"Do you really want to Cancel this order?\nIt means DELETE this order from our database and it can not redo!",
					"Comfirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				// y for 0, n for 1
				OrderDao.deleteOrder(seletedOrderId);
				this.searchButtonOnClick();
			}
		} else {
			System.out.println("No seleted order");
			JOptionPane.showConfirmDialog(null, "No order seleted", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * settle the selected order.
	 */
	private void settleOrder() {
		if (seletedOrderId != 0) {
			Order order = OrderDao.getOrder(seletedOrderId);
			if (!order.getIsPaid()) {
				// check stock
				boolean isStockEnought = true;
				StringBuilder text = new StringBuilder();
				text.append("Stock not enought.\n");
				for (OrderProduct op : order.getProductList()) {
					Product product = ProductDao.getProduct(op.getProductId());
					if (product.getStock() < op.getQuantity()) {
						isStockEnought = false;
						text.append("Product <<");
						text.append(product.getName());
						text.append(">> id = ");
						text.append(String.valueOf(product.getName()));
						text.append(" need a quantity of ");
						text.append(String.valueOf(op.getQuantity()));
						text.append("\n");
					}
				}

				if (isStockEnought) {
					if (JOptionPane.showConfirmDialog(null, "Settle this order ?", "Comfirm", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == 0) {
						// update order state
						order.setIsPaid(true);
						if (OrderDao.updateOrder(order) == 1) {
							JOptionPane.showConfirmDialog(null, "Order settled!", "Settle Order",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showConfirmDialog(null, "Something wrong. Look at the console.", "Oops",
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
							System.out.println(order);
						}

						// update stocks
						int loopCounter = 0;
						int updateCounter = 0;
						for (OrderProduct op : order.getProductList()) {
							Product product = ProductDao.getProduct(op.getProductId());
							product.reduceStock(op.getQuantity());
							updateCounter = updateCounter + ProductDao.updateProduct(product);
							loopCounter++;
						}
						if (loopCounter != updateCounter) {
							JOptionPane.showConfirmDialog(null, "Something wrong. Look at the console.", "Oops",
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showConfirmDialog(null, "Products' stocks updated!", "Settle Order",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						}

						// replenishment automatic
						replenishAutomatic(order);
					}
				} else {
					JOptionPane.showConfirmDialog(null, text, "Oops", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showConfirmDialog(null, "This order has already been paid.", "Oops",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}

			// refresh the table
			searchButtonOnClick();
		} else {
			System.out.println("No seleted order");
			JOptionPane.showConfirmDialog(null, "No order seleted", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}

	}

	/**
	 * replenish automatic after an order is create.
	 * 
	 * @param order
	 *            the order settled.
	 */
	private void replenishAutomatic(Order order) {
		/*
		 * Known problem: If a client made an order which a product has a
		 * quantity > 15, that order couldn't be settled until a replenishment
		 * manually is made.
		 */
		StringBuffer text = new StringBuffer();

		boolean isReplenished = false;
		for (OrderProduct orderProduct : order.getProductList()) {
			Product pdt = ProductDao.getProduct(orderProduct.getProductId());
			if (pdt.getStock() < 15) {
				isReplenished = true;
				int replenishQuantity = replenishFromSupplier(orderProduct.getProductId());
				pdt.addStock(replenishQuantity);
				ProductDao.updateProduct(pdt);

				text.append("Product <<");
				text.append(pdt.getName());
				text.append(">> with id = ");
				text.append(String.valueOf(pdt.getId()));
				text.append(" replenished quantity ");
				text.append(String.valueOf(replenishQuantity));
				text.append(". Quantity is now ");
				text.append(String.valueOf(pdt.getStock()));
				text.append("\n");
			}
		}
		if (isReplenished) {
			JOptionPane.showConfirmDialog(null, text, "Replenished automatic", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Replenish from a supplier. This method may connect to a server of a
	 * supplier, then make an order to replenish.
	 * 
	 * @param productId
	 *            id of product to replenish.
	 * @return quantity of product replenished.
	 */
	private int replenishFromSupplier(long productId) {
		// Do something here
		// such as connect the supplier and send him the product id / name
		// etc.
		// then return the quantity of product for replenishment

		// in case of testing, I set return 50.
		return 50;
	}

	/**
	 * action perform when buttons on click.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jbReturn) {
			// return button on click
			new MainGui(user);
			dispose();
		} else if (ae.getSource() == jbDisplayAllOrder) {
			// search order button on click
			this.searchButtonOnClick();
		} else if (ae.getSource() == jbNew) {
			// new order button on click
			new OrderDialog(this, true, null);
		} else if (ae.getSource() == jbEdit) {
			// edit order button on click
			showEditOrderDialog();
		} else if (ae.getSource() == jbCancel) {
			// cancel order button on click
			cancelButtonOnClick();
		} else if (ae.getSource() == jbCalendar) {
			// Calendar button on click
			new CalendarGui(user);
			dispose();
		} else if (ae.getSource() == jbSettle) {
			// settle button on click
			settleOrder();
		} else if (ae.getSource() == jbReplenish) {
			// replenish button on click
			new ReplenishManuallyGui(this);
			this.setVisible(false);
		}

	}

	/**
	 * main methode of class, use for testing.
	 * 
	 * @param args
	 *            for main
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		OrderGui orderGui = new OrderGui(null);
	}
}