package launcher;

import gui.MainGui;
import gui.SetupDatabase;
import util.FirstConnectDataBaseThread;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Main entry of the Application.
 * Link to Setup Data Base Helper or the Main Page of the App.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public class Launcher extends JFrame implements ActionListener {
	
	/**
	 * Button : link to the database helper.
	 */
	private JButton jbDb;
	/**
	 * Button : Link to the main page of the app.
	 */
	private JButton jbApp;

	/**
	 * Constructor.
	 */
	public Launcher(){
		this.setTitle("Launcher");
		this.setSize(400, 300);
		this.setLayout(null);
		JLabel jl = new JLabel("Welcome to G.D.S Launcher");
		jl.setBounds(100, 20, 200, 20);
		this.add(jl);
		
		jbDb = new JButton("Launch DataBase Helper");
		jbDb.setBounds(50, 75, 300, 60);
		jbDb.addActionListener(this);
		this.add(jbDb);
		
		jbApp = new JButton("Launch App G.D.S");
		jbApp.setBounds(50, 175, 300, 60);
		jbApp.addActionListener(this);
		this.add(jbApp);
		
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Action Perform after a button on clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbDb) {
			this.dispose();
			new SetupDatabase();
		} else if (e.getSource() == jbApp) {
			this.dispose();
			new MainGui(null);
		}
	}

	/**
	 * Main entry of application.
	 * @param args
	 * 			For main.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Launcher();
					new FirstConnectDataBaseThread().start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

}
