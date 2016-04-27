package src.gui;

import src.dao.OrderDAO;
import src.gds.Order;
import src.gds.User;

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
import javax.swing.table.TableModel;

/**
 * graphical user interface of Order menu window
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class OrderGui extends JFrame implements ActionListener {

	/**
	 * main container.
	 */
	private JPanel jp_main = new JPanel();
	/**
	 * main background.
	 */
	private JLabel jl_bgMain = new JLabel();
	/**
	 * Button : Link to main page.
	 */
	private JButton jbReturn = new JButton("Return");
	/**
	 * Button : Link to create order page.
	 */
	private JButton jbNew = new JButton("New order");
	/**
	 * Button : Link to research order page.
	 */
	private JButton jbSearch = new JButton("Search");
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
	private JButton jbReplenishment = new JButton("Replenishment");

	/**
	 * big font size for buttons.
	 */
	private Font fontBig = new Font(null, 0, 18);
	/**
	 * User who logged in.
	 */
	private User user;

	private JTable jtbOrderList = null;
	private DefaultTableModel tableModel = null;
	private final JScrollPane jspOrderList = new JScrollPane();
	private JPanel jpOrderList = new JPanel();
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

		jp_main.setLayout(null);
		jp_main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// setup background
		jl_bgMain.setIcon(new ImageIcon("data\\bg_order.png"));
		jl_bgMain.setBounds(0, 0, 1024, 768);
		this.getLayeredPane().add(jl_bgMain, new Integer(Integer.MIN_VALUE));
		jp_main = (JPanel) this.getContentPane();
		jp_main.setOpaque(false);
	}

	/**
	 * init buttons.
	 */
	public void initButtons() {
		jbNew.setBounds(630, 350, 200, 50);
		jbEdit.setBounds(630, 450, 200, 50);
		jbCancel.setBounds(630, 550, 200, 50);
		jbSearch.setBounds(630, 250, 200, 50);
		jbCalendar.setBounds(780, 50, 200, 50);
		jbReplenishment.setBounds(780, 150, 200, 50);
		jbReturn.setBounds(50, 600, 100, 100);

		jbNew.setFont(fontBig);
		jbEdit.setFont(fontBig);
		jbCancel.setFont(fontBig);
		jbSearch.setFont(fontBig);
		jbCalendar.setFont(fontBig);
		jbReplenishment.setFont(fontBig);
		jbReturn.setFont(fontBig);

		jbNew.addActionListener(this);
		jbEdit.addActionListener(this);
		jbCancel.addActionListener(this);
		jbSearch.addActionListener(this);
		jbCalendar.addActionListener(this);
		jbReplenishment.addActionListener(this);
		jbReturn.addActionListener(this);

		jp_main.add(jbNew);
		jp_main.add(jbEdit);
		jp_main.add(jbCancel);
		jp_main.add(jbSearch);
		jp_main.add(jbCalendar);
		jp_main.add(jbReplenishment);
		jp_main.add(jbReturn);

	}

	/**
	 * init table an it's panel.
	 */
	public void initTable() {
		// init model and table
		String[][] datas = {};
		String[] titles = { "ID", "Client's name", "Date", "Final price" };
		tableModel = new DefaultTableModel(datas, titles) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtbOrderList = new JTable(tableModel);
		jpOrderList.setBounds(183, 250, 400, 350);
		getContentPane().add(jpOrderList);
		jpOrderList.setLayout(null);
		jspOrderList.setBounds(0, 0, 400, 350);
		jpOrderList.add(jspOrderList);
		jspOrderList.setViewportView(jtbOrderList);
		jtbOrderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbOrderList.setCellSelectionEnabled(false);

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
	 * show Dialog to edit an Order
	 */
	private void showEditOrderDialog() {
		if (seletedOrderId != 0) {
			new OrderDialog(this, true, new OrderDAO().getOrder(seletedOrderId));
		} else {
			System.out.println("No seleted order");
			JOptionPane.showConfirmDialog(null, "No order seleted", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Search order.
	 */
	private void searchButtonOnClick() {
		// clear the table first
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		// add new data
		OrderDAO dao = new OrderDAO();
		ArrayList<Order> list = dao.getOrderList();
		for (Order order : list) {
			Object[] objects = { order.getId(), order.getClientName(), order.getDate(), order.getPriceDiscount() };
			tableModel.addRow(objects);
		}
	}
	
	private void cancelButtonOnClick(){
		if (seletedOrderId != 0) {
			if (JOptionPane.showConfirmDialog(null,
					"Do you really want to Cancel this order?\nIt means DELETE this order from our database and it can not redo!",
					"Comfirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				// y for 0, n for 1
				new OrderDAO().deleteOrder(seletedOrderId);
				this.searchButtonOnClick();
			}
		} else{
			System.out.println("No seleted order");
			JOptionPane.showConfirmDialog(null, "No order seleted", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * main methode of class, use for testing
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		OrderGui orderGui = new OrderGui(null);
	}

	/**
	 * action perform when buttons on click
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jbReturn) {
			// return button on click
			new MainGui(user);
			dispose();
		} else if (ae.getSource() == jbSearch) {
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

		}

	}
}
