package gui;

import dao.SupplierDao;
import gds.Supplier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * The dialog for create or edit a supplier.
 * 
 * @author HE Junyang
 *
 */
public class SupplierDialog extends JDialog implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7356808531399005538L;
	/**
	 * main content panel.
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * text field for the supplier id.
	 */
	private JTextField jtfId;
	/**
	 * text field for the supplier name.
	 */
	private JTextField jtfName;
	/**
	 * label : name.
	 */
	private JLabel jlName;
	/**
	 * label : id.
	 */
	private JLabel jlId;
	/**
	 * label : title.
	 */
	private JLabel jlTitle;
	/**
	 * panel of the buttons.
	 */
	private JPanel jpButtonPane;
	/**
	 * OK button.
	 */
	private JButton jbOk;
	/**
	 * cancel button.
	 */
	private JButton jbCancel;
	/**
	 * the supplier selected.
	 */
	private Supplier spr;

	/**
	 * Create the dialog.
	 */
	public SupplierDialog(Frame owner, boolean modal, Supplier spr) {
		super(owner, modal);
		this.spr = spr;

		this.setSize(400, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		jlId = new JLabel("ID : ");
		jlId.setSize(50, 20);
		jlId.setLocation(10, 35);
		jlName = new JLabel("Name : ");
		jlName.setBounds(10, 65, 50, 20);

		if (spr == null) {
			jlTitle = new JLabel("New supplier :");
		} else {
			jlTitle = new JLabel("Edit a supplier :");
		}

		jlTitle.setBounds(10, 5, 100, 20);

		jtfId = new JTextField();
		jtfId.setBounds(70, 35, 246, 20);
		jtfName = new JTextField();
		jtfName.setBounds(70, 65, 246, 20);

		contentPanel.setLayout(null);
		contentPanel.add(jlId);
		contentPanel.add(jtfId);
		contentPanel.add(jlName);
		contentPanel.add(jtfName);
		contentPanel.add(jlTitle);

		jpButtonPane = new JPanel();
		jpButtonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(jpButtonPane, BorderLayout.SOUTH);

		jbOk = new JButton("OK");
		jpButtonPane.add(jbOk);
		getRootPane().setDefaultButton(jbOk);

		jbCancel = new JButton("Cancel");
		jpButtonPane.add(jbCancel);

		jbOk.addActionListener(this);
		jbCancel.addActionListener(this);

		jtfId.setEditable(false);
		// init texts in text fields
		if (spr != null) {
			jtfId.setText(String.valueOf(spr.getId()));
			jtfName.setText(spr.getName());
		} else {
			jtfId.setText(String.valueOf(SupplierDao.nextSprId()));
		}

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * update or create the supplier in db.
	 */
	private void saveSupplier() {
		// if text field is empty
		if (jtfName.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(this, "Supplier name can't be void.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		} else {
			// Detect if the name is already existed
			ArrayList<Supplier> list = SupplierDao.getSupplierList();
			boolean nameExist = false;
			for (Supplier spr : list) {
				if (spr.getName().equals(jtfName.getText())) {
					nameExist = true;
				}
			}
			if (!nameExist) {
				// update or create the supplier
				if (spr != null) {
					spr.setName(jtfName.getText());
					SupplierDao.updateSupplier(spr);
					JOptionPane.showConfirmDialog(this, "Supplier updated.", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					SupplierDao.addSupplier(new Supplier(Long.parseLong(jtfId.getText()), jtfName.getText()));
					JOptionPane.showConfirmDialog(this, "Supplier added.", "Confirm", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
				((ManageSupplierGui) this.getOwner()).refreshList();
				this.dispose();
			} else {
				JOptionPane.showConfirmDialog(this, "Supplier name is already existed.", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * action perform after a button is on clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == jbOk) {
			saveSupplier();
		} else if (ev.getSource() == jbCancel) {
			this.dispose();
		}

	}
}