package src.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
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

import src.dao.OrderDAO;
import src.dao.ProductDAO;
import src.gds.Order;
import src.gds.OrderProduct;
import src.gds.Product;
import src.gds.User;
//import src.prototype.Product;
import src.util.Regex;

/**
 * graphical user interface of Order menu window
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class OrderGui extends JFrame implements ActionListener {

	/**
	 * main container
	 */
	private JPanel jp_main = new JPanel();
	/**
	 * main background
	 */
	private JLabel jl_bgMain = new JLabel();
	/**
	 * Button : Link to main page
	 */
	private JButton jb_return = new JButton("Return");
	/**
	 * Button : Link to create order page
	 */
	private JButton jb_new = new JButton("New order");
	/**
	 * Button : Link to research order page
	 */
	private JButton jb_search = new JButton("Search");
	/**
	 * Button : Link to edit order page
	 */
	private JButton jb_edit = new JButton("Edit");
	/**
	 * Button : Link to calendar
	 */
	private JButton jb_calendar = new JButton("Calendar");
	/**
	 *
	 */
	private JButton jb_replenishment = new JButton("Replenishment");

	/**
	 * big font size for buttons
	 */
	private Font fontBig = new Font(null, 0, 18);
	/**
	 * User who logged in
	 */
	private User user;
	
	
	private final JTable jtb_orderList = new JTable();
	private final JScrollPane jsp_orderList = new JScrollPane();

	/**
	 * constructor
	 * 
	 * @param user
	 *            The user who logged in
	 */
	public OrderGui(User user) {
		this.user = user;
		init();
		setupButtons();

	}

	/**
	 * initialization init JFrame init JPanel jp_main main container init
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
			public void windowClosing(WindowEvent e) {
				if (JOptionPane
						.showConfirmDialog(null, "Do you really want to exit?",
								"Comfirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == 0)
					// y for 0, n for 1
					//dispose();
					System.exit(0);
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
	 * init buttons
	 */
	public void setupButtons() {
		jb_new.setBounds(500, 200, 200, 50);
		jb_edit.setBounds(500, 270, 200, 50);
		jb_search.setBounds(500, 340, 200, 50);
		jb_calendar.setBounds(800, 100, 200, 50);
		jb_replenishment.setBounds(500, 500, 200, 50);
		jb_return.setBounds(50, 600, 100, 100);

		jb_new.setFont(fontBig);
		jb_edit.setFont(fontBig);
		jb_search.setFont(fontBig);
		jb_calendar.setFont(fontBig);
		jb_replenishment.setFont(fontBig);
		jb_return.setFont(fontBig);

		jb_new.addActionListener(this);
		jb_edit.addActionListener(this);
		jb_search.addActionListener(this);
		jb_calendar.addActionListener(this);
		jb_replenishment.addActionListener(this);
		jb_return.addActionListener(this);

		jp_main.add(jb_new);
		// jp_main.add(jb_edit);
		jp_main.add(jb_search);
		jp_main.add(jb_calendar);
		jp_main.add(jb_replenishment);
		jp_main.add(jb_return);
		
		JPanel jp_orderList = new JPanel();
		jp_orderList.setBounds(88, 200, 326, 350);
		getContentPane().add(jp_orderList);
		
		jp_orderList.add(jsp_orderList);
		jsp_orderList.setViewportView(jtb_orderList);
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
		// TODO button on click
		// return buttton on click
		if (ae.getSource() == jb_return) {
			new MainGui(user);
			dispose();
		}
		// new order button on click
		else if (ae.getSource() == jb_new) {
			new OrderDialog(this, true, null);
		}

	}
}

