package dao;

import gds.User;

import java.util.List;

/**
 * class userDAO
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 * @version 1.0
 */
public abstract class UserDao extends Dao {

	/**
	 * Add a user into the db.
	 * 
	 * @param user
	 *            user for add
	 * @return number of line added
	 */
	public static int addUser(User user) {
		return Dao.addLine("User", user);
	}

	/**
	 * get the user with id.
	 * 
	 * @param id
	 *            id which we give to have his user owner
	 * @return the specific user
	 */
	public static User getUser(String id) {
		String sql = ("SELECT * FROM user_usr WHERE usr_id = ?");
		return (User) Dao.getOne("User", sql, id);
	}

	/**
	 * allow to have the full list of users presents in the data base.
	 * 
	 * @return the list of all the users in the data base
	 */
	@SuppressWarnings("unchecked")
	public static List<User> getUserList() {
		final String sql = ("SELECT * FROM user_usr");
		return (List<User>) Dao.getList("User", sql, 0, 0L);
	}

	/**
	 * delete a user in the data base product list.
	 * 
	 * @param id
	 *            contain the id of the user we want to delete
	 * @return the number of line delete
	 */
	public static int deleteUser(String id) {
		return Dao.deleteLine("User", id);
	}

	/**
	 * update the informations of the user in parameter.
	 * 
	 * @param user
	 *            the user to update
	 * @return the number of update made
	 */
	public static int updateUser(User user) {
		return Dao.updateLine("User", user);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            for main
	 */
	public static void main(String[] args) {
		System.out.println("Get List :");
		System.out.println(UserDao.getUserList().toString());
		// System.out.println("Get User \"b\" :");
		// System.out.println(UserDAO.getUser("b").toString());
		// System.out.println("Add User :");
		// System.out.println(UserDAO.addUser(new User("b", "b", "bb")));
		// System.out.println("Delete User :");
		// System.out.println(UserDAO.deleteUser("b"));
		// System.out.println("Update User :");
		// System.out.println(UserDAO.updateUser(new User("b", "bbb",
		// "bbbbbbb")));

	}
}
