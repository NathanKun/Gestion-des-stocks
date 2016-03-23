package src;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainGui extends JFrame implements ActionListener {

	// main content panel
	private JPanel jp_main = new JPanel();

	private JLabel jl_bgMain = new JLabel();

	private JButton jb_main_menuOrder = new JButton("Order");
	private JButton jb_main_menuSearch = new JButton("Search");
	private JButton jb_main_settleShipment = new JButton("Settle & Shipment");
	private JButton jb_main_login = new JButton("Admin Login");
	private JButton jb_main_help = new JButton("HELP");
	// TODO Buttons for admin, show after admin login

	private Font fontBig = new Font(null, 0, 20);

	public MainGui(User user) {
		setupMain();
		setupButtons();
	}

	public void setupMain() {
		this.setTitle("Gestion de stocks");
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public void setupButtons() {
		jb_main_menuOrder.setBounds(700, 180, 250, 50);
		jb_main_menuSearch.setBounds(700, 240, 250, 50);
		jb_main_settleShipment.setBounds(700, 300, 250, 50);
		jb_main_login.setBounds(60, 640, 150, 60);
		jb_main_help.setBounds(850, 600, 100, 100);

		jb_main_menuOrder.setFont(fontBig);
		jb_main_settleShipment.setFont(fontBig);
		jb_main_menuSearch.setFont(fontBig);
		jb_main_login.setFont(fontBig);
		jb_main_help.setFont(fontBig);

		jp_main.add(jb_main_menuOrder);
		jp_main.add(jb_main_menuSearch);
		jp_main.add(jb_main_settleShipment);
		jp_main.add(jb_main_login);
		jp_main.add(jb_main_help);

		jb_main_menuOrder.addActionListener(this);
		jb_main_settleShipment.addActionListener(this);
		jb_main_menuSearch.addActionListener(this);
		jb_main_login.addActionListener(this);
		jb_main_help.addActionListener(this);

		// TODO Buttons for admin, show up after admin login

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == jb_main_help) {
			dispose();
		} else if (ae.getSource() == jb_main_login) {
			new LoginGui();
			dispose();
		} else if (ae.getSource() == jb_main_menuOrder) {
			dispose();
		} else if (ae.getSource() == jb_main_menuSearch) {
			dispose();
		} else if (ae.getSource() == jb_main_settleShipment) {
			dispose();
		}
	}

	public static void main(String[] args) {
		MainGui mainGui = new MainGui(null);
	}

}
