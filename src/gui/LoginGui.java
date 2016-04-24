package src.gui;
import src.dao.UserDAO;
import src.gds.User;
import src.util.MouseTracker;

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
 * graphical user interface of login window
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class LoginGui extends JFrame implements ActionListener {
	/**
	 * main container
	 */
	private JPanel jp = new JPanel();
	/**
	 * main background
	 */
	private JLabel jl_bgMain = new JLabel();
	/**
	 * label : id
	 */
	private JLabel jl_id = new JLabel("ID : ");
	/**
	 * label : password
	 */
	private JLabel jl_pw = new JLabel("Password : ");
	/**
	 * text field for id
	 */
	private JTextField jtf_id = new JTextField();
	/**
	 * password field for password
	 */
	private JPasswordField jtf_pw = new JPasswordField();
	/**
	 * button : login
	 */
	private JButton jb_login = new JButton("Login");
	
	/**
	 * constructor 
	 */
	public LoginGui() {
		init();
		initComponents();
		
		//Find mouse's position
		MouseTracker mt = new MouseTracker();
		mt.setBounds(0, 0, 1024, 768);
		mt.setOpaque(false);
		jp.add(mt);
		
	}
	
	/**
	 * Initialization principal
	 * init JFrame
	 * init jp main container
	 * setup background
	 */
	public void init() {
		this.setTitle("Login");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		//X onclick, reopen MainGui, then, dispose LoginGui
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new MainGui(null);
				dispose();
			}
		});
		
		// not resizable, components will use the absolute position
		this.setResizable(false);
		this.setVisible(true);

		jp.setLayout(null);
		jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//TODO put an image at the right side of the window, like IHM
		jl_bgMain.setIcon(new ImageIcon("data\\bg_login.png"));
		jl_bgMain.setBounds(0, 0, 800, 400);
		this.getLayeredPane().add(jl_bgMain, new Integer(Integer.MIN_VALUE));
		
		jp = (JPanel) this.getContentPane();
		jp.setOpaque(false);
	}
	
	/**
	 * initialization of components
	 * init labels, text fields, buttons.
	 */
	public void initComponents(){
		jl_id.setBounds(50, 100, 100, 30);
		jl_pw.setBounds(50, 200, 100, 30);
		jtf_id.setBounds(150, 100, 250, 30);
		jtf_pw.setBounds(150, 200, 250, 30);
		jb_login.setBounds(350, 280, 100, 50);
		
		jp.add(jl_id);
		jp.add(jl_pw);
		jp.add(jtf_id);
		jp.add(jtf_pw);
		jp.add(jb_login);
		
		jb_login.addActionListener(this);
	}
	
	/**
	 * Login algorithm
	 * @return	Objet User who logged in, or null if login field
	 */
	public User login(){
		User user;
		String id = jtf_id.getText();
		char[] pw = jtf_pw.getPassword();
		
		//TODO DAO and Login Algorithm, then dispose()
		
		
		if(id.isEmpty() == true){
			JOptionPane.showConfirmDialog(null, "ID can't be void!", "Opps",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		} else if(pw.length == 0){
			JOptionPane.showConfirmDialog(null, "Password can't be void!", "Opps",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		} else {
			UserDAO userDAO = new UserDAO();
			user = userDAO.getUser(id);
			System.out.println("id = " + id);
			if(user == null){
				System.out.println("id wrong");
				JOptionPane.showConfirmDialog(null, "ID or Password incorrect.", "Opps",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
			else {
				if(user.getPw().equals(new String(pw))){
					return user;
				}
				else{
					System.out.println("pw wrong");
					JOptionPane.showConfirmDialog(null, "ID or Password incorrect.", "Opps",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		return null;
	}
	/**
	 * action perform if button on click
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		//Login button on click
		if(ae.getSource() == jb_login){
			User user = login();
			if(user != null){
				new MainGui(user);
				dispose();
			}
		}
	}
}
