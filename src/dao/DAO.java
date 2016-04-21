package src.dao;

/**
 * abstract class for DAO
 * 
 * @author HE Junyang - FOTSING KENGNE Junior
 *
 */
abstract class DAO {
	/**
	 * connection parameter between oracle URL and the DGB, LOGIN and PASS are
	 * constants
	 */
	
	/*
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
	final static String LOGIN = "system";
	final static String PASS = "bdd";
	*/
	
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String LOGIN = "BDD5"; 
	final static String PASS = "BDD5";
	 
	/*
	final static String URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
	final static String LOGIN = "c##nathankun";
	final static String PASS = "83783548jun";
	*/
	/**
	 * Constructor
	 */
	public DAO() {
		// loading the pilot of DGB
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println(
					"impossible to load the BDD pilot, please make sur you have import thr .jar folder in the project");
		}
	}
}