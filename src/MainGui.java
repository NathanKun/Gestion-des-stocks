package src;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGui extends JFrame implements ActionListener{

	JPanel jp_main = new JPanel();
	
	public MainGui()  {
		this.setTitle("Gestion de stocks");
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		
		// permet de quitter l'application si on ferme la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setContentPane(jp_main);

		// affichage de la fenetre
		this.setVisible(true);
	}




	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		MainGui mainGui = new MainGui();
	}
	
}
