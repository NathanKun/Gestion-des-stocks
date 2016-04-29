package prototype;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.GridLayout;

import java.util.List;


/**
 *Class for the GUI of product management
 *@version 0.1
 *@author HE Junyang
 **/


public class ProductGui extends JFrame implements ActionListener {
	/**
	 * numero de version pour classe serialisable Permet d'eviter le warning
	 * "The serializable class ArticleFenetre does not declare a static final serialVersionUID field of type long"
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * conteneur : il accueille les differents composants graphiques de
	 * ArticleFenetre
	 */
	private JPanel jp_main;

	/**
	 * zone de texte pour pdt_id
	 */
	private JTextField jtf_pdt_id;

	/**
	 * zone de texte pour pdt_name
	 */
	private JTextField jtf_pdt_name;

	/**
	 * zone de texte pour pdt_supplier
	 * 
	 */
	private JTextField jtf_pdt_supplier;
	/**
	 * zone de texte pour pdt_price
	 */
	private JTextField jtf_pdt_price;

	/**
	 * label pdt_id
	 */
	private JLabel jlb_pdt_id;

	/**
	 * label pdt_name
	 */
	private JLabel jlb_pdt_name;

	/**
	 * label pdt_supplier
	 */
	private JLabel jlb_pdt_supplier;

	/**
	 * label pdt_price
	 */
	private JLabel jlb_pdt_price;

	
	/**
	 * bouton d'ajout de le Product
	 */
	private JButton jbt_ajouterProduct;

	/**
	 * bouton qui permet d'afficher tous les Products
	 */
	private JButton jbt_getListeProduct_all;
	/**
	 * bouton qui permet d'afficher les Product cherchees par nom
	 */
	private JButton jbt_getListeProduct_name;
	/**
	 * bouton qui permet de supprimer un Product par id
	 */
	private JButton jbt_supprimerProduct;

	/**
	 * Zone de texte pour afficher les stations
	 */
	JTextArea jta_Product;

	/**
	 * Zone de defilement pour la zone de texte
	 */
	JScrollPane jsp_jta_Product;
	
	/**
	 * panel pour ranger les boutons
	 */
	JPanel jp_btn;
	
	/**
	 * instance de ProductDAO permettant les acces a  la base de donnees
	 */
	private ProductDAO productDAO;

	/**
	 * Constructeur Definit la fenetre et ses composants - affiche la fenetre
	 */
	public ProductGui() {
		// on instancie la classe Article DAO
		this.productDAO = new ProductDAO();

		// on fixe le titre de la fenetre
		this.setTitle("GUI - Gestion des Products");
		// initialisation de la taille de la fenetre
		this.setSize(800, 600);
		//centrer le fenetre
		this.setLocationRelativeTo(null);

		// creation du conteneur
		jp_main = new JPanel();

		// choix du Layout pour ce conteneur
		// il permet de gerer la position des elements
		// il autorisera un retaillage de la fenetre en conservant la
		// presentation
		// BoxLayout permet par exemple de positionner les elements sur une
		// colonne ( PAGE_AXIS )
		jp_main.setLayout(new BoxLayout(jp_main,
				BoxLayout.PAGE_AXIS));

		// choix de la couleur pour le conteneur
		jp_main.setBackground(Color.CYAN);

		// instantiation des composants graphiques
		jtf_pdt_id = new JTextField();
		jtf_pdt_name = new JTextField();
		jtf_pdt_supplier = new JTextField();
		jtf_pdt_price = new JTextField();

		jbt_ajouterProduct =  new JButton("ajouter");
		jbt_getListeProduct_all = new JButton("afficher tous les Products");
		jbt_getListeProduct_name = new JButton("chercher Product par nom");
		jbt_supprimerProduct= new JButton("Supprimer par ID");
		
		jlb_pdt_id = new JLabel("pdt_id :");
		jlb_pdt_name = new JLabel("pdt_name :");
		jlb_pdt_supplier = new JLabel("pdt_supplier :");
		jlb_pdt_price = new JLabel("pdt_price :");

		jta_Product = new JTextArea(10, 20);
		jsp_jta_Product = new JScrollPane(jta_Product);
		jta_Product.setEditable(false);

		jp_btn = new JPanel();
		
		// ajout des composants sur le container
		jp_main.add(jlb_pdt_id);
		// introduire une espace constant entre le label et le champ texte
		jp_main.add(Box.createRigidArea(new Dimension(0, 5)));
		jp_main.add(jtf_pdt_id);
		// introduire une espace constant entre le champ texte et le composant
		// suivant
		jp_main.add(Box.createRigidArea(new Dimension(0, 10)));

		jp_main.add(jlb_pdt_name);
		jp_main.add(Box.createRigidArea(new Dimension(0, 5)));
		jp_main.add(jtf_pdt_name);
		jp_main.add(Box.createRigidArea(new Dimension(0, 10)));

		jp_main.add(jlb_pdt_supplier);
		jp_main.add(Box.createRigidArea(new Dimension(0, 5)));
		jp_main.add(jtf_pdt_supplier);
		jp_main.add(Box.createRigidArea(new Dimension(0, 10)));

		jp_main.add(jlb_pdt_price);
		jp_main.add(Box.createRigidArea(new Dimension(0, 5)));
		jp_main.add(jtf_pdt_price);
		jp_main.add(Box.createRigidArea(new Dimension(0, 10)));
		
		
		jp_btn.setLayout(new GridLayout(2, 2));
		jp_btn.setPreferredSize(new Dimension(300, 100));
		jp_btn.add(jbt_ajouterProduct);
		jp_btn.add(jbt_getListeProduct_all);
		jp_btn.add(jbt_getListeProduct_name);
		jp_btn.add(jbt_supprimerProduct);
		
		jp_main.add(jp_btn);

		jp_main.add(Box.createRigidArea(new Dimension(0, 10)));

		jp_main.add(jsp_jta_Product);

		// ajouter une bordure vide de taille constante autour de l'ensemble des
		// composants
		jp_main.setBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10));

		// ajout des ecouteurs sur les boutons pour gerer les evenements
		jbt_ajouterProduct.addActionListener(this);
		jbt_getListeProduct_all.addActionListener(this);
		jbt_getListeProduct_name.addActionListener(this);
		jbt_supprimerProduct.addActionListener(this);
		
		// permet de quitter l'application si on ferme la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setContentPane(jp_main);

		// affichage de la fenetre
		this.setVisible(true);
	}

	/**
	 * Gere les actions realisees sur les boutons
	 *
	 */
	public void actionPerformed(ActionEvent ae) {
		int retour; // code de retour de la classe ArticleDAO

		try {
			if (ae.getSource() == jbt_ajouterProduct) {
				// on cree l'objet message
				Product p = new Product(
						Integer.parseInt(this.jtf_pdt_id.getText()),
						this.jtf_pdt_name.getText(),
						Integer.parseInt(this.jtf_pdt_supplier.getText()),
						Double.parseDouble(this.jtf_pdt_price.getText()));
				// on demande a  la classe de communication d'envoyer la station
				// dans la table station_stt
				retour = productDAO.ajouterProduct(p);
				// affichage du nombre de lignes ajoutees
				// dans la bdd pour verification
				jta_Product.setText("" + retour + " ligne ajoutee ");
				if (retour == 1)
					JOptionPane.showMessageDialog(this, "Product ajoutee !");
				else
					JOptionPane.showMessageDialog(this, "erreur ajout Product",
							"Erreur", JOptionPane.ERROR_MESSAGE);
			} else if (ae.getSource() == jbt_getListeProduct_all) {
				// on demande a  la classe ArticleDAO d'ajouter le message
				// dans la base de donnees
				List<Product> liste = productDAO.getListeProducts("");
				// on efface l'ancien contenu de la zone de texte
				jta_Product.setText("");
				// on affiche dans la console du client les articles rea§us
				for (Product p : liste) {
					jta_Product.append(p.toString());
					jta_Product.append("\n");
					// Pour afficher dans la console :
					// System.out.println(a.toString());
				}
			}
			else if (ae.getSource() == jbt_getListeProduct_name){
				List<Product> liste = productDAO.getListeProducts(jtf_pdt_name.getText());
				jta_Product.setText("");
				for(Product p : liste){
					jta_Product.append(p.toString());
					jta_Product.append("\n");
				}
			}
			else if (ae.getSource() == jbt_supprimerProduct){
				retour = productDAO.supprimerProduct(Integer.parseInt(jtf_pdt_id.getText()));
				jta_Product.setText("" + retour + " ligne supprimee ");
				if (retour == 1)
					JOptionPane.showMessageDialog(this, "Product supprimee !");
				else
					JOptionPane.showMessageDialog(this, "erreur suppression Product",
							"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Veuillez controler vos saisies", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			System.err.println("Veuillez controler vos saisies");
		}

	}

	public static void main(String[] args) {
		new ProductGui();
	}

}
