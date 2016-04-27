package src.gui;

import src.gds.Order;
import src.gds.User;
import src.util.MouseTracker;

import java.awt.Color;
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

/**
 * graphical user interface of Main window
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class MainGui extends JFrame implements ActionListener {

	/**
	 * main content panel.
	 */
	private JPanel jpMain = new JPanel();
	/**
	 * background of main page.
	 */
	private JLabel jlBgMain = new JLabel();
	/**
	 * button : link to order menu.
	 */
	private JButton jbMenuOrder = new JButton("Order");
	/**
	 * button : link to search menu.
	 */
	private JButton jbMainSearch = new JButton("Search");
	/**
	 * button : link to settle and shipment page.
	 */
	private JButton jbSettleShipment = new JButton("Settle & Shipment");
	/**
	 * button : link to login windows.
	 */
	private JButton jbLogin = new JButton("Admin Login");
	/**
	 * button : User log out.
	 */
	private JButton jbLogout = new JButton("Logout");
	/**
	 * button : link to help page.
	 */
	private JButton jbHelp = new JButton("HELP");
	/**
	 * button : link to product management page.
	 */
	private JButton jbProductMng = new JButton("Product management");
	/**
	 * button : link to supplier management page.
	 */
	private JButton jbSupplierMng = new JButton("Supplier management");

	/**
	 * Lable : Espace Administrateur. Show after admin logged in
	 */
	private JLabel jlEspaceAdmin = new JLabel("Espace Administrateur");
	/**
	 * Lable : Welcome user_name. Show after admin logged in
	 */
	private JLabel jlWelcomeAdmin = new JLabel();

	/**
	 * the user who logged in, null if nobody logged in.
	 */
	private User user;

	/**
	 * font : big size, for big buttons in the main page.
	 */
	private Font fontBig = new Font(null, 0, 18);

	/**
	 * Constructor of MainGui.
	 * 
	 * @param user
	 *            the user who logged in, null if nobody logged in
	 */
	public MainGui(User user) {
		this.user = user;
		initMain();
		initButtons();
		setupAdminsComponents();

		// Find mouse's position
		MouseTracker mt = new MouseTracker();
		mt.setBounds(0, 0, 1024, 768);
		mt.setOpaque(false);
		jpMain.add(mt);

	}

	/**
	 * Initiate JFrame, setup main container, setup background.
	 */
	public void initMain() {
		this.setTitle("Gestion de stocks");
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
		jlBgMain.setIcon(new ImageIcon("data\\bg_main.jpg"));
		jlBgMain.setBounds(0, 0, 1024, 768);
		this.getLayeredPane().add(jlBgMain, new Integer(Integer.MIN_VALUE));
		jpMain = (JPanel) this.getContentPane();
		jpMain.setOpaque(false);

	}

	/**
	 * Init the buttons.
	 */
	public void initButtons() {
		jbMenuOrder.setBounds(700, 240, 250, 50);
		jbMainSearch.setBounds(700, 300, 250, 50);
		jbSettleShipment.setBounds(700, 360, 250, 50);
		jbLogin.setBounds(60, 640, 150, 60);
		jbLogout.setBounds(60, 640, 150, 60);
		jbHelp.setBounds(850, 600, 100, 100);
		jbProductMng.setBounds(387, 500, 250, 50);
		jbSupplierMng.setBounds(387, 600, 250, 50);

		jbMenuOrder.setFont(fontBig);
		jbSettleShipment.setFont(fontBig);
		jbMainSearch.setFont(fontBig);
		jbLogin.setFont(fontBig);
		jbLogout.setFont(fontBig);
		jbHelp.setFont(fontBig);
		jbProductMng.setFont(fontBig);
		jbSupplierMng.setFont(fontBig);

		jpMain.add(jbMenuOrder);
		jpMain.add(jbMainSearch);
		jpMain.add(jbSettleShipment);
		jpMain.add(jbLogin);
		jpMain.add(jbLogout);
		jpMain.add(jbHelp);
		jpMain.add(jbProductMng);
		jpMain.add(jbSupplierMng);

		jbMenuOrder.addActionListener(this);
		jbSettleShipment.addActionListener(this);
		jbMainSearch.addActionListener(this);
		jbLogin.addActionListener(this);
		jbLogout.addActionListener(this);
		jbHelp.addActionListener(this);
		jbProductMng.addActionListener(this);
		jbSupplierMng.addActionListener(this);
	}

	/**
	 * init admin labels.
	 */
	private void initAdminLabels() {
		jlWelcomeAdmin.setText("Welcome " + user.getName());
		jlEspaceAdmin.setBounds(345, 430, 400, 30);
		jlWelcomeAdmin.setBounds(120, 125, 500, 30);
		Font font = new Font(null, Font.BOLD, 30);
		jlEspaceAdmin.setFont(font);
		jlWelcomeAdmin.setFont(font);
		jlEspaceAdmin.setForeground(Color.WHITE);
		jlWelcomeAdmin.setForeground(Color.WHITE);
		this.add(jlEspaceAdmin);
		this.add(jlWelcomeAdmin);
	}

	/**
	 * set visibility of the buttons after login and logout.
	 */
	public void setupAdminsComponents() {
		if (this.user == null) {
			jbProductMng.setVisible(false);
			jbSupplierMng.setVisible(false);
			jbLogin.setVisible(true);
			jbLogout.setVisible(false);
			jlEspaceAdmin.setVisible(false);
			jlWelcomeAdmin.setVisible(false);
		} else {
			jbProductMng.setVisible(true);
			jbSupplierMng.setVisible(true);
			jbLogin.setVisible(false);
			jbLogout.setVisible(true);
			initAdminLabels();
			jlEspaceAdmin.setVisible(true);
			jlWelcomeAdmin.setVisible(true);
		}
	}

	@Override
	/**
	 * actions perform after a button on click
	 */
	public void actionPerformed(ActionEvent ae) {
		// help button on click
		if (ae.getSource() == jbHelp) {
			// help button on click
		} else if (ae.getSource() == jbLogin) {
			// admin login button on click
			new LoginGui();
			dispose();
		} else if (ae.getSource() == jbLogout) {
			// logout button on click
			this.user = null;
			setupAdminsComponents();
		} else if (ae.getSource() == jbMenuOrder) {
			// order button on click
			new OrderGui(user);
			dispose();
		} else if (ae.getSource() == jbMainSearch) {
			// search button on click
			new SearchGui(this.user);
			dispose();
		} else if (ae.getSource() == jbSettleShipment) {
			// settle&shipment button on click

		} else if (ae.getSource() == jbProductMng) {
			// product management button on click

		} else if (ae.getSource() == jbSupplierMng) {
			// supplier management button on click

		}
	}

	/**
	 * main methode of MainGui, for test.
	 * 
	 * @param args
	 *            for main
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		MainGui mainGui = new MainGui(null);
	}

}
