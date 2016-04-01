package src.Gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.security.Init;

import src.GDS.Order;
import src.GDS.Product;
import src.GDS.User;
//import src.prototype.Product;
import src.util.*;

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

		// Find mouse's position
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

		// confirmation when X on click
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) // y
																						// for
																						// 0,
																						// n
																						// for
																						// 1
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
	public void setupButtons() {
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
		// jp_main.add(jb_edit);
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
		if (ae.getSource() == jb_return) {
			new MainGui(user);
			dispose();
		} else if (ae.getSource() == jb_new) {
			new OrderDialog(this, true, null);
		}

	}

}

class OrderDialog extends JDialog implements ActionListener {

	private JTable jtb_pdtList = null;

	private JTable jtb_pdtSearch = null;

	private DefaultTableModel model_pdtList = null;

	private DefaultTableModel model_pdtSearch = null;

	private JButton jb_addPdt = new JButton("Add");

	private JButton jb_createOdr = new JButton("Create");

	private JButton jb_searchPdt = new JButton("Search");

	private JButton jb_removePdt = new JButton("Remove");

	private JLabel jl_id = new JLabel("Order ID : ");

	private JLabel jl_state = new JLabel("State : ");

	private JLabel jl_CltName = new JLabel("Client name : ");

	private JLabel jl_date = new JLabel("Date : ");

	private JLabel jl_addPdt = new JLabel("Add product By ID : ");

	private JLabel jl_searchPdt = new JLabel("Search product by name : ");

	private JLabel jl_pdtList = new JLabel("Product list : ");

	private JLabel jl_price = new JLabel("Totol price : ");

	// TODO add id generator here
	private JTextField jt_id = new JTextField(/**/);

	private JTextField jt_state = new JTextField();

	private JTextField jt_CltName = new JTextField();

	private JTextField jt_date = new JTextField();

	private JTextField jt_pdtId = new JTextField();

	private JTextField jt_searchPdt = new JTextField();

	private JTextField jt_price = new JTextField();

	private JPanel jp_up = new JPanel();

	private JPanel jp_middle = new JPanel();

	private JPanel jp_down = new JPanel();

	private long seletedPdtId = 0;

	// TODO other components

	public OrderDialog(Frame owner, boolean modal, Order order) {
		super(owner, modal);
		this.setTitle("Order");
		this.setSize(500, 750);

		init(order);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void init(Order order) {
		this.setLayout(new FlowLayout());
		jp_up.setLayout(new GridLayout(6, 3));
		jp_up.add(jl_id);
		jp_up.add(jt_id);
		jp_up.add(new JPanel());
		jp_up.add(jl_state);
		jp_up.add(jt_state);
		jp_up.add(new JPanel());
		jp_up.add(jl_CltName);
		jp_up.add(jt_CltName);
		jp_up.add(new JPanel());
		jp_up.add(jl_date);
		jp_up.add(jt_date);
		jp_up.add(new JPanel());
		jp_up.add(jl_addPdt);
		jp_up.add(jt_pdtId);
		jp_up.add(jb_addPdt);
		jp_up.add(jl_searchPdt);
		jp_up.add(jt_searchPdt);
		jp_up.add(jb_searchPdt);
		this.add(jp_up);

		String[][][] datas = {};
		String[] titlesList = { "ID", "Name", "Price", "Quantity" };
		String[] titlesSearch = { "ID", "Name", "Price", "Stock" };
		model_pdtSearch = new DefaultTableModel(datas, titlesSearch);
		model_pdtList = new DefaultTableModel(datas, titlesList);

		jtb_pdtSearch = new JTable(model_pdtSearch);
		jp_middle.setLayout(new BoxLayout(jp_middle, BoxLayout.Y_AXIS));
		jtb_pdtSearch.setPreferredScrollableViewportSize(new Dimension(450, 180));
		jp_middle.add(new JLabel("Search result : "));
		jp_middle.add(jtb_pdtSearch);
		jp_middle.add(new JScrollPane(jtb_pdtSearch));
		this.add(jp_middle);

		jtb_pdtList = new JTable(model_pdtList);
		jtb_pdtList.setPreferredScrollableViewportSize(new Dimension(450, 180));
		// jp_down.setLayout(new BoxLayout(jp_down, BoxLayout.Y_AXIS));
		// jtb_pdtList.setSize(jp_down.getWidth(), jp_down.getHeight());
		jp_down.setPreferredSize(new Dimension(490, 350));
		jp_down.add(new JLabel("Product list :"));
		jp_down.add(jtb_pdtList);
		jp_down.add(new JScrollPane(jtb_pdtList));
		jp_down.add(jb_removePdt);
		jp_down.add(jl_price);
		jt_price.setPreferredSize(new Dimension(250, 25));
		jp_down.add(jt_price);
		jp_down.add(jb_createOdr);
		jp_down.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.add(jp_down);

		jb_addPdt.addActionListener(this);
		jb_createOdr.addActionListener(this);
		jb_removePdt.addActionListener(this);
		jb_searchPdt.addActionListener(this);

		jtb_pdtList.setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModel_pdtList = jtb_pdtList.getSelectionModel();
		cellSelectionModel_pdtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel_pdtList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				long selectedData = 0;

				int[] selectedRow = jtb_pdtList.getSelectedRows();
				int[] selectedColumns = jtb_pdtList.getSelectedColumns();
				if (selectedRow.length != 0)
					selectedData = (long) jtb_pdtList.getValueAt(selectedRow[0], 0);
				seletedPdtId = selectedData;
			}
		});

		jtb_pdtSearch.setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModel_pdtSearch = jtb_pdtSearch.getSelectionModel();
		cellSelectionModel_pdtSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel_pdtSearch.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				long selectedData = 0;

				int[] selectedRow = jtb_pdtSearch.getSelectedRows();
				int[] selectedColumns = jtb_pdtSearch.getSelectedColumns();

				selectedData = (long) jtb_pdtSearch.getValueAt(selectedRow[0], 0);
				jt_pdtId.setText(String.valueOf(selectedData));
			}
		});

		if (order != null) {

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jb_addPdt) {
			if (Regex.isLong(jt_pdtId.getText())) {
				Product pdt = new ProductDAO().getProduct(Long.parseLong(jt_pdtId.getText()));
				if (pdt != null) {
					boolean b = false;
					for (int i = 0; i < model_pdtList.getRowCount(); i++) {
						if ((long) model_pdtList.getValueAt(i, 0) == pdt.getId())
							b = true;
					}
					if (b) {
						for (int i = 0; i < model_pdtList.getRowCount(); i++) {
							if ((long) model_pdtList.getValueAt(i, 0) == pdt.getId())
								model_pdtList.setValueAt((int) model_pdtList.getValueAt(i, 3) + 1, i, 3);
						}
					} else
						model_pdtList.addRow(new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), 1 });
				} else {
					System.out.println("pdt not found");
				}
			} else {
				System.out.println("pdt id wrong type");
				JOptionPane.showConfirmDialog(null, "Invalid input", "Opps", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == jb_createOdr) {

		} else if (e.getSource() == jb_removePdt) {
			for (int i = 0; i < model_pdtList.getRowCount(); i++) {
				if ((long) model_pdtList.getValueAt(i, 0) == seletedPdtId) {
					model_pdtList.removeRow(i);
				}
			}
		} else if (e.getSource() == jb_searchPdt) {
			ArrayList<Product> pdtList = new ProductDAO().getProductList();
			for (int i = model_pdtSearch.getRowCount() - 1; i >= 0; i--) {
				model_pdtSearch.removeRow(i);
			}
			if (!pdtList.isEmpty()) {
				for (Product pdt : pdtList) {
					if (pdt.getName().contains(jt_searchPdt.getText())) {
						model_pdtSearch
								.addRow(new Object[] { pdt.getId(), pdt.getName(), pdt.getPrice(), pdt.getStock() });
					}
				}

			} else {
				System.out.println("pdt not found");
			}
		}
	}
}
