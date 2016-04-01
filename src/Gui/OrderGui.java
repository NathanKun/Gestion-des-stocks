package src.Gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.security.Init;

import src.GDS.Order;
import src.GDS.User;
//import src.prototype.Product;
import src.util.MouseTracker;


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
	
	private User user;
	
	/**
	 * constructor
	 */
	public OrderGui(User user) {
		this.user = user;
		init();
		setupButtons();
		
		//Find mouse's position
		MouseTracker mt = new MouseTracker();
		mt.setBounds(0, 0, 1024, 768);
		mt.setOpaque(false);
		jp_main.add(mt);
		
		
	}

	/**
	 * initial
	 */
	public void init() {
		this.setTitle("Gestion de stocks - Order");
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		//confirmation when X on click
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0)//y for 0, n for 1
					dispose();
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
	 * setup buttons
	 */
	public void setupButtons(){
		jb_new.setBounds(500, 200, 200, 50);
		jb_edit.setBounds(500, 270, 200, 50);
		jb_search.setBounds(500, 340, 200, 50);
		jb_calendar.setBounds(800, 100, 200, 50);
		jb_replenishment.setBounds(500, 500, 200, 50);
		jb_return.setBounds(50, 599, 100, 100);

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
		//jp_main.add(jb_edit);
		jp_main.add(jb_search);
		jp_main.add(jb_calendar);
		jp_main.add(jb_replenishment);
		jp_main.add(jb_return);
	}
	
	
	public static void main(String[] args) {
		OrderGui orderGui = new OrderGui(null);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO 
		if(ae.getSource() == jb_return){
			new MainGui(user);
			dispose();
		} else if(ae.getSource() == jb_new){
			new  OrderDialog(this, true, null);
		}

	}

}

class OrderDialog extends JDialog implements ActionListener{

	//TODO labels, textFields and buttons for a order.
	

	private JTable jt_pdtList = null;

	private DefaultTableModel model = null;

	private JButton jb_addPdt = new JButton("Add");
	
	private JButton jb_createOdr = new JButton("Create");
	
	private JLabel jl_id = new JLabel ("Order ID : ");
	
	private JLabel jl_state = new JLabel ("State : ");
	
	private JLabel jl_CltName = new JLabel ("Client name : ");
	
	private JLabel jl_date = new JLabel ("Date : ");
	
	private JLabel jl_AddPdt = new JLabel ("Add product By ID : ");
	
	private JLabel jl_searchPdt = new JLabel ("Search product by name : ");

	private JLabel jl_pdtList = new JLabel ("Product list : ");
	
	private JLabel jl_price = new JLabel ("Totol price : ");
	
	//TODO add id generator here
	private JTextField jt_idField = new JTextField(/**/);
	
	private JTextField jt_state = new JTextField("Creating");
	
	//TODO other components
	
	
	public OrderDialog(Frame owner, boolean modal, Order order) {
		super(owner, modal);
		this.setTitle("Order");
		this.setSize(500, 600);
		
		init(order);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void init(Order order){
		//TODO init
		String[][][] datas = {};
		String[] titles = {"Product", "Price", "Quantity"};
		model = new DefaultTableModel(datas, titles);
		jt_pdtList = new JTable(model);
		
		this.add(jt_pdtList);
		this.add(new JScrollPane(jt_pdtList));
		
		if(order != null){
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

