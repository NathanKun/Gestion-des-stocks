package gui;

import gds.User;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The GUI for the search menu.
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
public final class SearchGui extends JFrame implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6702257643391803027L;
	/**
	 * Button : search a product.
	 */
	private JButton jbProduct = new JButton("Product");
	/**
	 * Button : search a supplier.
	 */
	private JButton jbSupplier = new JButton("Supplier");
	/**
	 * Button : return to the main menu.
	 */
	private JButton jbReturn = new JButton("Return");
	/**
	 * the user who logged in.
	 */
	private User user = null;

	/**
	 * Create the frame.
	 * 
	 * @param user
	 *            the user who logged in
	 */
	public SearchGui(User user) {
		this.user = user;
		this.setTitle("Search");
		// confirmation when X on click
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Comfirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
					// y for 0, n for 1
					System.exit(0);
				}
			}
		});

		setBounds(100, 100, 400, 600);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		// configure the components
		JPanel jpMain = new JPanel();
		getContentPane().add(jpMain);
		jpMain.setLayout(null);

		jbProduct.setFont(new Font("Dialog", Font.PLAIN, 18));
		jbProduct.addActionListener(this);
		jbProduct.setBounds(100, 100, 200, 80);
		jpMain.add(jbProduct);

		jbSupplier.setFont(new Font("Dialog", Font.PLAIN, 18));
		jbSupplier.setBounds(100, 320, 200, 80);
		jbSupplier.addActionListener(this);
		jpMain.add(jbSupplier);

		jbReturn.setFont(new Font("Dialog", Font.PLAIN, 18));
		jbReturn.addActionListener(this);
		jbReturn.setBounds(10, 500, 100, 40);
		jpMain.add(jbReturn);

		this.setVisible(true);
	}

	@Override
	/**
	 * action perform when a button on click
	 */
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == jbProduct) {
			new SearchProductGui();
		} else if (ev.getSource() == jbSupplier) {
			new SearchSupplierGui();
		} else if (ev.getSource() == jbReturn) {
			new MainGui(user);
			dispose();
		}
	}

	/**
	 * main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		new SearchGui(null);
	}

}
