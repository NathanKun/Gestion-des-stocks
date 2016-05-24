package gui;

import dao.SetupDatabaseDao;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * Setup DataBase helper.
 * @author HE Junyang
 *
 */
public final class SetupDatabase extends JFrame implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6207107139661053454L;
	private JPanel contentPane;
	private JButton btnDrop;
	private JButton btnCreate;
	private JButton btnInsert;
	private JRadioButton rdbtnJunyang;
	private JRadioButton rdbtnJunior;
	private JRadioButton rdbtnBdd1;
	private JRadioButton rdbtnBdd3;
	private JRadioButton rdbtnBdd5;
	private JRadioButton rdbtnBdd6;
	private JRadioButton rdbtnBdd7;
	private JRadioButton rdbtnBdd8;
	private JButton btnLaunchApp;
	private ButtonGroup buttonGroup;

	/**
	 * Create the frame.
	 */
	public SetupDatabase() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 486, 235);
		setTitle("Setup DataBase Helper");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		rdbtnJunyang = new JRadioButton("Junyang");
		rdbtnJunyang.setBounds(6, 6, 121, 23);
		contentPane.add(rdbtnJunyang);

		rdbtnJunior = new JRadioButton("Junior");
		rdbtnJunior.setBounds(6, 31, 121, 23);
		contentPane.add(rdbtnJunior);

		rdbtnBdd1 = new JRadioButton("BDD1");
		rdbtnBdd1.setBounds(6, 56, 121, 23);
		contentPane.add(rdbtnBdd1);

		rdbtnBdd3 = new JRadioButton("BDD3");
		rdbtnBdd3.setBounds(6, 83, 121, 23);
		contentPane.add(rdbtnBdd3);

		rdbtnBdd5 = new JRadioButton("BDD5");
		rdbtnBdd5.setBounds(6, 106, 70, 23);
		contentPane.add(rdbtnBdd5);

		rdbtnBdd6 = new JRadioButton("BDD6");
		rdbtnBdd6.setBounds(6, 131, 70, 23);
		contentPane.add(rdbtnBdd6);

		rdbtnBdd7 = new JRadioButton("BDD7");
		rdbtnBdd7.setBounds(6, 156, 70, 23);
		contentPane.add(rdbtnBdd7);

		rdbtnBdd8 = new JRadioButton("BDD8");
		rdbtnBdd8.setBounds(6, 183, 70, 23);
		contentPane.add(rdbtnBdd8);

		btnDrop = new JButton("Drop");
		btnDrop.setBounds(270, 6, 93, 23);
		contentPane.add(btnDrop);

		btnCreate = new JButton("Create");
		btnCreate.setBounds(270, 56, 93, 23);
		contentPane.add(btnCreate);

		btnInsert = new JButton("Insert");
		btnInsert.setBounds(270, 106, 93, 23);
		contentPane.add(btnInsert);

		btnLaunchApp = new JButton("Launch App");
		btnLaunchApp.setBounds(255, 153, 121, 29);
		contentPane.add(btnLaunchApp);

		btnDrop.addActionListener(this);
		btnCreate.addActionListener(this);
		btnInsert.addActionListener(this);
		btnLaunchApp.addActionListener(this);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnJunyang);
		buttonGroup.add(rdbtnJunior);
		buttonGroup.add(rdbtnBdd1);
		buttonGroup.add(rdbtnBdd3);
		buttonGroup.add(rdbtnBdd5);
		buttonGroup.add(rdbtnBdd6);
		buttonGroup.add(rdbtnBdd7);
		buttonGroup.add(rdbtnBdd8);

		JLabel jlCreateAGds = new JLabel("Create a blank G.D.S database with an Admin a, a.");
		jlCreateAGds.setBounds(110, 87, 370, 15);
		contentPane.add(jlCreateAGds);

		JLabel jlDropTheGds = new JLabel("Drop the G.D.S database.");
		jlDropTheGds.setBounds(230, 35, 194, 15);
		contentPane.add(jlDropTheGds);

		JLabel jlInsertSomeData = new JLabel("Insert some data for testing.");
		jlInsertSomeData.setBounds(220, 135, 300, 15);
		contentPane.add(jlInsertSomeData);

		rdbtnJunyang.addActionListener(this);
		rdbtnJunior.addActionListener(this);
		rdbtnBdd1.addActionListener(this);
		rdbtnBdd3.addActionListener(this);
		rdbtnBdd5.addActionListener(this);
		rdbtnBdd6.addActionListener(this);
		rdbtnBdd7.addActionListener(this);
		rdbtnBdd8.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == btnDrop) {
			SetupDatabaseDao.dropAll();
		} else if (ev.getSource() == btnCreate) {
			SetupDatabaseDao.createTable();
		} else if (ev.getSource() == btnInsert) {
			SetupDatabaseDao.insertData();
		} else if (ev.getSource() == rdbtnJunyang) {
			SetupDatabaseDao.switchStaticFields("Junyang");
		} else if (ev.getSource() == rdbtnJunior) {
			SetupDatabaseDao.switchStaticFields("Junior");
		} else if (ev.getSource() == rdbtnBdd1) {
			SetupDatabaseDao.switchStaticFields("BDD1");
		} else if (ev.getSource() == rdbtnBdd3) {
			SetupDatabaseDao.switchStaticFields("BDD3");
		} else if (ev.getSource() == rdbtnBdd5) {
			SetupDatabaseDao.switchStaticFields("BDD5");
		} else if (ev.getSource() == rdbtnBdd6) {
			SetupDatabaseDao.switchStaticFields("BDD6");
		} else if (ev.getSource() == rdbtnBdd7) {
			SetupDatabaseDao.switchStaticFields("BDD7");
		} else if (ev.getSource() == rdbtnBdd8) {
			SetupDatabaseDao.switchStaticFields("BDD8");
		} else if (ev.getSource() == btnLaunchApp) {
			new MainGui(null);
			dispose();
		}
	}

	/**
	 * Entry of setup database helper.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new SetupDatabase();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}