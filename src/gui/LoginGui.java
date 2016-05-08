package gui;

import dao.UserDAO;
import gds.User;
import util.MouseTracker;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * graphical user interface of login window.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class LoginGui extends JFrame implements ActionListener {
	/**
	 * main container.
	 */
	private JPanel jp = new JPanel();
	/**
	 * main background.
	 */
	private JLabel jlBgMain = new JLabel();
	/**
	 * label : id.
	 */
	private JLabel jlId = new JLabel("ID : ");
	/**
	 * label : password.
	 */
	private JLabel jlPw = new JLabel("Password : ");
	/**
	 * text field for id.
	 */
	private JTextField jtfId = new JTextField();
	/**
	 * password field for password.
	 */
	private JPasswordField jtfPw = new JPasswordField();
	/**
	 * button : login.
	 */
	private JButton jbLogin = new JButton("Login");

	/**
	 * constructor.
	 */
	public LoginGui() {
		init();
		initComponents();

		// Find mouse's position
		// MouseTracker mt = new MouseTracker();
		// mt.setBounds(0, 0, 1024, 768);
		// mt.setOpaque(false);
		// jp.add(mt);

	}

	/**
	 * Initialization principal init JFrame init jp main container setup.
	 * background
	 */
	public void init() {
		this.setTitle("Login");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// X onclick, reopen MainGui, then, dispose LoginGui
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				new MainGui(null);
				dispose();
			}
		});

		// not resizable, components will use the absolute position
		this.setResizable(false);
		this.setVisible(true);

	}

	/**
	 * initialization of components init labels, text fields, buttons.
	 */
	public void initComponents() {
		jp = (JPanel) this.getContentPane();
		jp.setOpaque(false);
		jp.setLayout(null);

		jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		jlBgMain.setIcon(new ImageIcon(getClass().getResource("/resources/bg_login.jpg")));
		;
		jlBgMain.setBounds(461, 23, 323, 295);
		jp.add(jlBgMain, new Integer(Integer.MIN_VALUE));

		jlId.setBounds(50, 150, 100, 30);
		jlPw.setBounds(50, 200, 100, 30);
		jtfId.setBounds(150, 150, 250, 30);
		jtfPw.setBounds(150, 200, 250, 30);
		jbLogin.setBounds(300, 268, 100, 50);

		jp.add(jlId);
		jp.add(jlPw);
		jp.add(jtfId);
		jp.add(jtfPw);
		jp.add(jbLogin);

		JLabel jlbIdentificationAdmin = new JLabel("Identification Admin");
		jlbIdentificationAdmin.setBounds(50, 100, 200, 15);
		getContentPane().add(jlbIdentificationAdmin);

		jbLogin.addActionListener(this);
	}

	/**
	 * Login algorithm
	 * 
	 * @return Objet User who logged in, or null if login field
	 */
	public User login() {
		User user;
		String id = jtfId.getText();
		char[] pw = jtfPw.getPassword();

		if (id.isEmpty() == true) {
			JOptionPane.showConfirmDialog(null, "ID can't be void!", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		} else if (pw.length == 0) {
			JOptionPane.showConfirmDialog(null, "Password can't be void!", "Opps", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		} else {
			UserDAO userDao = new UserDAO();
			user = userDao.getUser(id);
			System.out.println("id = " + id);
			if (user == null) {
				System.out.println("id wrong");
				JOptionPane.showConfirmDialog(null, "ID or Password incorrect.", "Opps", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			} else {
				if (user.getPw().equals(new String(pw))) {
					return user;
				} else {
					System.out.println("pw wrong");
					JOptionPane.showConfirmDialog(null, "ID or Password incorrect.", "Opps", JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		return null;
	}

	/**
	 * action perform if button on click.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		// Login button on click
		if (ae.getSource() == jbLogin) {
			User user = login();
			if (user != null) {
				new MainGui(user);
				dispose();
			}
		}
	}
}
