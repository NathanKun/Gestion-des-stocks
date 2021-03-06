package gds;

/**
 * User class, represents an user
 * 
 * @author FOTSING KENGNE Junior - HE Junyang
 * @version 1.0
 */
public class User {
	/**
	 * identifiant of the user.
	 */
	private String id;
	/**
	 * password of the user.
	 */
	private String pw;
	/**
	 * name of the user.
	 */
	private String name;

	/**
	 * constructor of User's class.
	 * 
	 * @param id
	 *            ID of the user
	 * @param pw
	 *            contain the password of the user
	 * @param name
	 *            contain the name of the user
	 */
	public User(String id, String pw, String name) {
		this.id = id;
		this.pw = pw;
		this.name = name;
	}

	/**
	 * get the password of the user.
	 * 
	 * @return the passwor of the user
	 */
	public String getPw() {
		return pw;
	}

	/**
	 * set the user's password.
	 * 
	 * @param pw
	 *            the new password of the user
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}

	/**
	 * get the name of the user.
	 * 
	 * @return the name of the current user
	 */
	public String getName() {
		return name;
	}

	/**
	 * set the name of the user.
	 * 
	 * @param name
	 *            the new name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the user's id.
	 * 
	 * @return the id of the user
	 */
	public String getId() {
		return id;
	}

	/**
	 * toString.
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", name=" + name + "]";
	}

}
