package src.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComponent;

import org.jdesktop.swingx.JXDatePicker;

import src.dao.ProductDAO;
import src.gds.Product;

public class CalendarGui extends JFrame {

	private JPanel jpMain;

	private JPanel jpProductList;
	private JLabel jlProductList;
	private JTextField jtfPdtFilter;
	private JList<String> jlistProductList;
	private ListModel<String> modelProductList;
	private JScrollPane jspProductList;

	private JPanel jpOrderPane;
	private JLabel jlOrderList;
	private JTable jtbOrderList;
	private DefaultTableModel modelOrderList;
	private JScrollPane jspOrderList;

	private JPanel jpDatePicker;
	private JRadioButton jrbSingleDay;
	private JRadioButton jrbPeriode;
	private JXDatePicker datePickerA;
	private JXDatePicker datePickerB;
	
	private ArrayList<Product> listProduct;
	private String selectedProductName;

	/**
	 * Calendar of order.
	 */
	public CalendarGui() {

		jpMain = new JPanel();
		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		jpMain.setLayout(null);

		initProductListPanel();
		
		initDatePickerPanel();

		// order list panel
		jpOrderPane = new JPanel();
		jpOrderPane.setBounds(170, 54, 614, 506);
		jpMain.add(jpOrderPane);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setContentPane(jpMain);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Order Calendar");
	}

	private void initProductListPanel (){
		// init components
		jpProductList = new JPanel();
		jpProductList.setBounds(10, 0, 150, 550);
		jpProductList.setLayout(null);

		jlProductList = new JLabel("Product list : ");
		jlProductList.setBounds(10, 10, 100, 30);
		jtfPdtFilter = new JTextField();
		jtfPdtFilter.setBounds(10, 40, 130, 20);
		
		// add products' name in the list
		listProduct = new ProductDAO().getProductList();
		modelProductList = new DefaultListModel<String>();
		for(Product pdt : listProduct){
			((DefaultListModel<String>) modelProductList).addElement(pdt.getName());
		}
		jlistProductList = new JList<String>(modelProductList);
		jlistProductList.setBounds(0, 0, 130, 500);
		
		// scroll pane for list
		jspProductList = new JScrollPane();
		jspProductList.setBounds(10, 70, 130, 500);
		jspProductList.setViewportView(jlistProductList);
		
		// add components
		jpProductList.add(jlProductList);
		jpProductList.add(jtfPdtFilter);
		jpProductList.add(jspProductList);
		jpMain.add(jpProductList);
		
		// add list listner
		jlistProductList.addListSelectionListener(new ListSelectionListener () {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				selectedProductName = jlistProductList.getSelectedValue();
			}
		});
	}
	
	private void initDatePickerPanel() {
		// date picker panel
		jpDatePicker = new JPanel();
		jpDatePicker.setBounds(170, 10, 614, 33);
		jpMain.add(jpDatePicker);
		jpDatePicker.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		datePickerA = new JXDatePicker();
		datePickerB = new JXDatePicker();

		initRadioButtons();
		jpDatePicker.add(jrbSingleDay);
		jpDatePicker.add(jrbPeriode);
		jpDatePicker.add(datePickerA);
		jpDatePicker.add(datePickerB);

	}

	private void initRadioButtons() {
		jrbSingleDay = new JRadioButton("Single Day");
		jrbPeriode = new JRadioButton("Periode");

		ButtonGroup editableGroup = new ButtonGroup();
		editableGroup.add(jrbSingleDay);
		editableGroup.add(jrbPeriode);

		// add allow listener
		jrbPeriode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if (ev.getActionCommand() == "Periode") {
					datePickerB.setVisible(true);
				}
			}
		});
		jrbSingleDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if (ev.getActionCommand() == "Single Day") {
					datePickerB.setVisible(false);
				}
			}
		});

		// set default selection
		editableGroup.setSelected(jrbSingleDay.getModel(), true);
		datePickerB.setVisible(false);
	}

	private boolean isDateBAfterDateA() {
		if (datePickerA.getDate().before(datePickerB.getDate())) {
			return true;
		}
		return false;
	}

	/**
	 * Main method for testing
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarGui frame = new CalendarGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
