package src.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import src.gds.User;
import src.util.MouseTracker;

/**
 * graphical user interface of Main window
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class MainGui extends JFrame implements ActionListener {

	/**
	 * main content panel
	 */
	private JPanel jp_main = new JPanel();
	/**
	 * background of main page
	 */
	private JLabel jl_bgMain = new JLabel();
	/**
	 * button : link to order menu
	 */
	private JButton jb_main_menuOrder = new JButton("Order");
	/**
	 * button : link to search menu
	 */
	private JButton jb_main_menuSearch = new JButton("Search");
	/**
	 * button : link to settle and shipment page
	 */
	private JButton jb_main_settleShipment = new JButton("Settle & Shipment");
	/**
	 * button : link to login windows
	 */
	private JButton jb_main_login = new JButton("Admin Login");
	/**
	 * button : User log out
	 */
	private JButton jb_main_logout = new JButton("Logout");
	/**
	 * button : link to help page
	 */
	private JButton jb_main_help = new JButton("HELP");
	/**
	 * button : link to product management page
	 */
	private JButton jb_main_productMng = new JButton("Product management");
	/**
	 * button : link to supplier management page
	 */
	private JButton jb_main_supplierMng = new JButton("Supplier management");

	/**
	 * the user who logged in, null if nobody logged in
	 */
	private User user;

	/**
	 * font : big size, for big buttons in the main page
	 */
	private Font fontBig = new Font(null, 0, 18);

	/**
	 * Constructor of MainGui
	 * 
	 * @param user
	 *            the user who logged in, null if nobody logged in
	 */
	public MainGui(User user) {
		this.user = user;
		setupMain();
		setupButtons();
		setupAdminsComponents();

		// Find mouse's position
		MouseTracker mt = new MouseTracker();
		mt.setBounds(0, 0, 1024, 768);
		mt.setOpaque(false);
		jp_main.add(mt);

	}

	/**
	 * Initiate JFrame, setup main container, setup background
	 */
	public void setupMain() {
		this.setTitle("Gestion de stocks");
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		// confirmation when X on click
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0)
					// y for 0, n for 1
					System.exit(0);
			}
		});
		// not resizable, components will use the absolute position
		this.setResizable(false);
		this.setVisible(true);

		jp_main.setLayout(null);
		jp_main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// setup background
		jl_bgMain.setIcon(new ImageIcon("data\\bg_main.png"));
		jl_bgMain.setBounds(0, 0, 1024, 768);
		this.getLayeredPane().add(jl_bgMain, new Integer(Integer.MIN_VALUE));
		jp_main = (JPanel) this.getContentPane();
		jp_main.setOpaque(false);

	}

	/**
	 * setup the buttons
	 */
	public void setupButtons() {
		jb_main_menuOrder.setBounds(700, 180, 250, 50);
		jb_main_menuSearch.setBounds(700, 240, 250, 50);
		jb_main_settleShipment.setBounds(700, 300, 250, 50);
		jb_main_login.setBounds(60, 640, 150, 60);
		jb_main_logout.setBounds(60, 640, 150, 60);
		jb_main_help.setBounds(850, 600, 100, 100);
		jb_main_productMng.setBounds(387, 500, 250, 50);
		jb_main_supplierMng.setBounds(387, 600, 250, 50);

		jb_main_menuOrder.setFont(fontBig);
		jb_main_settleShipment.setFont(fontBig);
		jb_main_menuSearch.setFont(fontBig);
		jb_main_login.setFont(fontBig);
		jb_main_logout.setFont(fontBig);
		jb_main_help.setFont(fontBig);
		jb_main_productMng.setFont(fontBig);
		jb_main_supplierMng.setFont(fontBig);

		jp_main.add(jb_main_menuOrder);
		jp_main.add(jb_main_menuSearch);
		jp_main.add(jb_main_settleShipment);
		jp_main.add(jb_main_login);
		jp_main.add(jb_main_logout);
		jp_main.add(jb_main_help);
		jp_main.add(jb_main_productMng);
		jp_main.add(jb_main_supplierMng);

		jb_main_menuOrder.addActionListener(this);
		jb_main_settleShipment.addActionListener(this);
		jb_main_menuSearch.addActionListener(this);
		jb_main_login.addActionListener(this);
		jb_main_logout.addActionListener(this);
		jb_main_help.addActionListener(this);
		jb_main_productMng.addActionListener(this);
		jb_main_supplierMng.addActionListener(this);
	}

	/**
	 * Divisible the buttons for admin if no admin logged in
	 */
	public void setupAdminsComponents() {
		if (this.user == null) {
			jb_main_productMng.setVisible(false);
			jb_main_supplierMng.setVisible(false);
			jb_main_login.setVisible(true);
			jb_main_logout.setVisible(false);
		} else {
			jb_main_productMng.setVisible(true);
			jb_main_supplierMng.setVisible(true);
			jb_main_login.setVisible(false);
			jb_main_logout.setVisible(true);
		}
	}

	@Override
	/**
	 * actions perform after a button on click
	 */
	public void actionPerformed(ActionEvent ae) {
		// TODO action for buttons
		// help button on click
		if (ae.getSource() == jb_main_help) {

		}
		// admin login button on click
		else if (ae.getSource() == jb_main_login) {
			new LoginGui();
			dispose();
		}
		// logout button on click
		else if (ae.getSource() == jb_main_logout) {
			this.user = null;
			setupAdminsComponents();
		}
		// order button on click
		else if (ae.getSource() == jb_main_menuOrder) {
			new OrderGui(user);
			dispose();
		}
		// search button on click
		else if (ae.getSource() == jb_main_menuSearch) {
			new SearchGui(this.user);
			dispose();
		}
		// settle&shipment button on click
		else if (ae.getSource() == jb_main_settleShipment) {

		}
		// product management button on click
		else if (ae.getSource() == jb_main_productMng) {

		}
		// supplier management button on click
		else if (ae.getSource() == jb_main_supplierMng) {

		}
	}

	/**
	 * main methode of MainGui, for test
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		MainGui mainGui = new MainGui(null);
	}

}
