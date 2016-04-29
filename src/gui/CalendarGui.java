package src.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

public class CalendarGui extends JFrame {

	private JPanel jpMain;
	private JPanel jpProductList;
	private JPanel jpOrderPane;
	private JPanel jpCalendarPicker;
	private JRadioButton jrbSingleDay;
	private JRadioButton jrbPeriode;
	private JXDatePicker datePickerA;
	private JXDatePicker datePickerB;

	/**
	 * Calendar of order.
	 */
	public CalendarGui() {

		jpMain = new JPanel();
		jpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		jpMain.setLayout(null);
		
		jpProductList = new JPanel();
		jpProductList.setBounds(10, 10, 150, 550);
		jpMain.add(jpProductList);
		jpProductList.setLayout(null);
		
		jpCalendarPicker = new JPanel();
		jpCalendarPicker.setBounds(170, 10, 614, 33);
		jpMain.add(jpCalendarPicker);
		jpCalendarPicker.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		jrbSingleDay = new JRadioButton("Single Day");
		jrbPeriode = new JRadioButton("Periode");
		datePickerA = new JXDatePicker();
		datePickerB = new JXDatePicker();
		
		
		jpCalendarPicker.add(jrbSingleDay);
		jpCalendarPicker.add(jrbPeriode);
		jpCalendarPicker.add(datePickerA);
		jpCalendarPicker.add(datePickerB);
		
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
