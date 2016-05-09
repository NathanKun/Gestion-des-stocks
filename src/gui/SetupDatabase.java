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

public class SetupDatabase extends JFrame implements ActionListener {

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
	private JRadioButton rdbtnBdd3;
	private JRadioButton rdbtnBdd5;
	private JRadioButton rdbtnBdd6;
	private JRadioButton rdbtnBdd7;
	private JRadioButton rdbtnBdd8;
	private JButton btnLaunchApp;

	/**
	 * Create the frame.
	 */
	public SetupDatabase() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 386, 235);
		setTitle("Setup DataBase");
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

		rdbtnBdd3 = new JRadioButton("BDD3");
		rdbtnBdd3.setBounds(6, 56, 121, 23);
		contentPane.add(rdbtnBdd3);

		rdbtnBdd5 = new JRadioButton("BDD5");
		rdbtnBdd5.setBounds(6, 81, 59, 23);
		contentPane.add(rdbtnBdd5);

		rdbtnBdd6 = new JRadioButton("BDD6");
		rdbtnBdd6.setBounds(6, 106, 70, 23);
		contentPane.add(rdbtnBdd6);

		rdbtnBdd7 = new JRadioButton("BDD7");
		rdbtnBdd7.setBounds(6, 131, 70, 23);
		contentPane.add(rdbtnBdd7);

		rdbtnBdd8 = new JRadioButton("BDD8");
		rdbtnBdd8.setBounds(6, 156, 70, 23);
		contentPane.add(rdbtnBdd8);

		btnDrop = new JButton("Drop");
		btnDrop.setBounds(180, 10, 93, 23);
		contentPane.add(btnDrop);

		btnCreate = new JButton("Create");
		btnCreate.setBounds(180, 56, 93, 23);
		contentPane.add(btnCreate);

		btnInsert = new JButton("Insert");
		btnInsert.setBounds(180, 106, 93, 23);
		contentPane.add(btnInsert);

		btnLaunchApp = new JButton("Launch App");
		btnLaunchApp.setBounds(166, 156, 121, 29);
		contentPane.add(btnLaunchApp);

		btnDrop.addActionListener(this);
		btnCreate.addActionListener(this);
		btnInsert.addActionListener(this);
		btnLaunchApp.addActionListener(this);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnJunyang);
		buttonGroup.add(rdbtnJunior);
		buttonGroup.add(rdbtnBdd3);
		buttonGroup.add(rdbtnBdd5);
		buttonGroup.add(rdbtnBdd6);
		buttonGroup.add(rdbtnBdd7);
		buttonGroup.add(rdbtnBdd8);

		JLabel lblCreateAGds = new JLabel("Create a blank G.D.S database with an Admin a, a.");
		lblCreateAGds.setBounds(71, 85, 300, 15);
		contentPane.add(lblCreateAGds);

		JLabel lblDropTheGds = new JLabel("Drop the G.D.S database.");
		lblDropTheGds.setBounds(165, 35, 168, 15);
		contentPane.add(lblDropTheGds);

		JLabel lblInsertSomeData = new JLabel("Insert some data for testing.");
		lblInsertSomeData.setBounds(138, 135, 195, 15);
		contentPane.add(lblInsertSomeData);

		rdbtnJunyang.addActionListener(this);
		rdbtnJunior.addActionListener(this);
		rdbtnBdd3.addActionListener(this);
		rdbtnBdd5.addActionListener(this);
		rdbtnBdd6.addActionListener(this);
		rdbtnBdd7.addActionListener(this);
		rdbtnBdd8.addActionListener(this);
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetupDatabase frame = new SetupDatabase();
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}