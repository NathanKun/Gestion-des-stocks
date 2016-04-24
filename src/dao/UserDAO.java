package src.dao;

import src.gds.User;

import java.util.List;

//import projetDeveloppementLogiciel.User;
/**
 * class userDAO
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 * @version 1.0
 */
public class UserDAO extends DAO {

	/**
	 * Add a user into the db.
	 * 
	 * @param user
	 *            user for add
	 * @return number of line added
	 */
	public int addUser(User user) {
		return this.addLine("User", user);
	}

	/**
	 * get the user with id.
	 * 
	 * @param id
	 *            id which we give to have his user owner
	 * @return the specific user
	 */
	public User getUser(String id) {
		String sql = ("SELECT * FROM user_usr WHERE usr_id = ?");
		return (User) this.getOne("User", sql, id);
	}

	/**
	 * allow to have the full list of users presents in the data base.
	 * 
	 * @return the list of all the users in the data base
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
		final String sql = ("SELECT * FROM user_usr");
		return (List<User>) this.getList("User", sql, 0, 0L);
	}

	/**
	 * delete a user in the data base product list.
	 * 
	 * @param id
	 *            contain the id of the user we want to delete
	 * @return the number of line delete
	 */
	public int deleteUser(String id) {
		return this.deleteLine("User", id);
	}

	/**
	 * update the informations of the user in parameter.
	 * 
	 * @param user
	 *            the user to update
	 * @return the number of update made
	 */
	public int updateUser(User user) {
		return this.updateLine("User", user);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		// System.out.println("Get List :");
		// System.out.println(dao.getUserList().toString());
		// System.out.println("Get User \"b\" :");
		// System.out.println(dao.getUser("b").toString());
		// System.out.println("Add User :");
		// System.out.println(dao.addUser(new User("b", "b", "bb")));
		// System.out.println("Delete User :");
		// System.out.println(dao.deleteUser("b"));
		System.out.println("Update User :");
		System.out.println(dao.updateUser(new User("b", "bbb", "bbbbbbb")));

	}
}
