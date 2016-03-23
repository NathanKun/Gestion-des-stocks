package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGui extends JFrame implements ActionListener {

	private JPanel jp = new JPanel();
	private JLabel jl_bgMain = new JLabel();

	private JLabel jl_id = new JLabel("ID : ");
	private JLabel jl_pw = new JLabel("Pqssword : ");
	private JTextField jtf_id = new JTextField();
	private JPasswordField jtf_pw = new JPasswordField();
	
	private JButton jb_login = new JButton("Login");
	
	public LoginGui() {
		init();
		initComponents();
	}

	public void init() {
		this.setTitle("Gestion de stocks");
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
		// setup background
		jl_bgMain.setIcon(new ImageIcon("data\\bg_login.png"));
		jl_bgMain.setBounds(0, 0, 800, 400);
		this.getLayeredPane().add(jl_bgMain, new Integer(Integer.MIN_VALUE));
		jp = (JPanel) this.getContentPane();
		jp.setOpaque(false);

	}

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
		
		//TODO put an image at the right side of the window
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Login button

	}

}
