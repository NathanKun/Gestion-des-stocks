package src.prototype;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
	
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String LOGIN = "BDD6";  //exemple BDD1
	final static String PASS = "BDD6";   //exemple BDD1
	
	public ProductDAO() {
		// chargement du pilote de bases de donnees
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}

	}

	public List<Product> getListeProducts(String nom) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Product> retour = new ArrayList<Product>();
		ArrayList<Product>  list = new ArrayList<Product>();
		// connexion a� la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM product_pdt");

			// on execute la requete
			rs = ps.executeQuery();
			// on parcourt les lignes du resultat
			while (rs.next())
				list.add(new Product(rs.getInt("pdt_id"),
						rs.getString("pdt_name"),
						rs.getInt("pdt_supplier"),
						rs.getDouble("pdt_price")));

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du rs, du preparedStatement et de la connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		

		if(!nom.equals("")){
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).getPdt_name().contains(nom))
					retour.add(list.get(i));
			}
			return retour;
		}
		
		return list;

	}

	public int supprimerProduct(int pdt_id) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;

		// connexion a� la base de donnees
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// preparation de l'instruction SQL, chaque ? represente une valeur
			// a� communiquer dans l'insertion
			// les getters permettent de recuperer les valeurs des attributs
			// souhaites
			ps = con.prepareStatement("DELETE FROM product_pdt WHERE pdt_id = ?");
			ps.setInt(1, pdt_id);


			// Execution de la requete
			retour = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return retour;

	}
	
	public int ajouterProduct(Product product) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;

		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO product_pdt (pdt_id, pdt_name, pdt_supplier, pdt_price)"
					+ " VALUES (?, ?, ?, ?)");
			ps.setInt(1, product.getPdt_id());
			ps.setString(2, product.getPdt_name());
			ps.setInt(3, product.getPdt_supplier());
			ps.setDouble(4, product.getPdt_price());

			// Execution de la requete
			retour = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return retour;

	}

}
