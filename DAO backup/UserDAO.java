package src.dao;

import java.sql.*;
import java.util.*;

import src.gds.User;

//import projetDeveloppementLogiciel.User;
/**
 * class userDAO
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 * @version 1.0
 */
public class UserDAO extends DAO {

	/**
	 * Add a user into the db
	 * 
	 * @param user
	 *            user for add
	 * @return number of line added
	 */
	public int addUser(User user) {
		/*Connection con = null;
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
		return retour;*/
		return this.addLine("User", user);
	}

	/**
	 * give the user who has a specific identifiant
	 * 
	 * @param id
	 *            identifiant whish we give to have his user owner
	 * @return the specific user
	 */
	public User getUser(String id) {
		/*Connection con = null;
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
		return retour;*/
		String sql = ("SELECT * FROM user_usr WHERE usr_id = ?");
		return (User)this.getOne("User", sql, id);
	}

	/**
	 * allow to have the full list of users presents in the data base
	 * 
	 * @return the list of all the users in the data base
	 */
	public List<User> getUserList() {
		/*Connection con = null;
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
		return retour;*/
		final String sql = ("SELECT * FROM user_usr");
		return (List<User>) this.getList("User", sql, 0, 0l);
	}

	/**
	 * delete a user in the data base product list
	 * 
	 * @param id
	 *            contain the id of the user we want to delete
	 * @return the number of line delete
	 */
	public int deleteUser(String id) {
		/*Connection con = null;
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
		return retour;*/
		return this.deleteLine("User", id);
	}

	/**
	 * update the informations of the user in parameter
	 * 
	 * @param user
	 *            the user to update
	 * @return the number of update made
	 */
	public int updateUser(User user) {
		/*Connection con = null;
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
		return retour;*/
		return this.updateLine("User", user);
	}
	
	public static void main (String[] args){
		UserDAO dao = new UserDAO();
//		System.out.println("Get List :");
//		System.out.println(dao.getUserList().toString());
//		System.out.println("Get User \"b\" :");
//		System.out.println(dao.getUser("b").toString());
//		System.out.println("Add User :");
//		System.out.println(dao.addUser(new User("b", "b", "bb")));
//		System.out.println("Delete User :");
//		System.out.println(dao.deleteUser("b"));
		System.out.println("Update User :");
		System.out.println(dao.updateUser(new User("b", "bbb", "bbbbbbb")));
		
	}
}
