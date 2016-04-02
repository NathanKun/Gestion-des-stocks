package src.util;

import java.sql.*;
import java.util.*;

import src.GDS.User;

//import projetDeveloppementLogiciel.User;
/**
 * class userDAO
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 * @version 1.0
 */
public class UserDAO {
	/**
	 * connection parameter between oracle URL and the DGB, LOGIN and PASS are
	 * constants
	 */
	/*
	 * final static String URL="jdbc:oracle:thin:@localhost:1521:xe"; final
	 * static String LOGIN = "BDD6"; 
	 * final static String PASS = "BDD6";
	 */
	/*
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String LOGIN = "system";
	final static String PASS = "bdd";
	*/
	final static String URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
	final static String LOGIN = "c##nathankun";
	final static String PASS = "83783548jun";

	/**
	 * class userDAO constructor
	 */
	public UserDAO() {
		// loading the pilot of DGB
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println(
					"impossible to load the BDD pilot, please make sure you have import the .jar folder in the project");
		}
	}
	/**
	 * Add a user into the db
	 * @param user	user for add
	 * @return	number of line added
	 */
	public int addUser(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("INSERT INTO user_usr (usr_id, usr_pw, usr_name) VALUES (?,?,?)");
			ps.setString(1, user.getId());
			ps.setString(2, user.getPw());
			ps.setString(3, user.getName());

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}

	/**
	 * give the user who has a specific identifiant
	 * 
	 * @param id
	 *            identifiant whish we give to have his user owner
	 * @return the specific user
	 */
	public User getUser(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User retour = null;

		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM user_usr WHERE usr_id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				retour = new User(rs.getString("usr_id"), rs.getString("usr_pw"), rs.getString("usr_name"));
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing of ResultSet, PreparedStatement and connection
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
		return retour;
	}

	/**
	 * allow to have the full list of users presents in the data base
	 * 
	 * @return the list of all the users in the data base
	 */
	public List<User> getUserList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> retour = new ArrayList<User>();
		// connection to the data base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM product_pdt");

			// requet execution
			rs = ps.executeQuery();

			// we crosse all the line of the results
			while (rs.next())
				retour.add(new User(rs.getString("usr_id"), rs.getString("usr_pw"), rs.getString("pdt_name")));
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// closing rs, PreparedStatement and connection
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
		return retour;
	}

	/**
	 * delete a user in the data base product list
	 * 
	 * @param id
	 *            contain the id of the user we want to delete
	 * @return the number of line delete
	 */
	public int deleteUser(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("DELETE FROM user_usr WHERE usr_id=?");
			ps.setString(1, id);

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}

	/**
	 * update the informations of the user in parameter
	 * 
	 * @param user
	 *            the user to update
	 * @return the number of update made
	 */
	public int updateUser(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		// connection to date base
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("UPDATE user_usr SET usr_pw=?, usr_name=? WHERE usr_id=?");
			ps.setString(1, user.getPw());
			ps.setString(2, user.getName());
			ps.setString(3, user.getId());

			// excecution of the requiere
			retour = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close preparedStatement and connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}
}
