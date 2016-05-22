package dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class SetupDatabaseDao {

	static final String fileDrop = "/resources/drop.sql";
	static final String fileCreate = "/resources/create.sql";
	static final String fileInsert = "/resources/insert.sql";

	/**
	 * Constructor, load the JDBC driver.
	 */
	public SetupDatabaseDao() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException exception) {
			System.err.println("impossible to load the database driver, please make sur you "
					+ "have import the .jar file in the project");
		}
	}

	/**
	 * Switch DAO.URL, DAO.LOGIN and DAO.PASS by user.
	 * 
	 * @param user
	 *            user
	 */
	public static void switchStaticFields(String user) {
		switch (user) {
		case "Junyang":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
			Dao.LOGIN = "c##nathankun";
			Dao.PASS = "83783548jun";
			break;
		case "Junior":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "system";
			Dao.PASS = "bdd";
			break;
		case "BDD1":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "BDD1";
			Dao.PASS = "BDD1";
			break;
		case "BDD3":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "BDD3";
			Dao.PASS = "BDD3";
			break;
		case "BDD5":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "BDD5";
			Dao.PASS = "BDD5";
			break;
		case "BDD6":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "BDD6";
			Dao.PASS = "BDD6";
			break;
		case "BDD7":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "BDD7";
			Dao.PASS = "BDD7";
			break;
		case "BDD8":
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "BDD8";
			Dao.PASS = "BDD8";
			break;

		default:
			Dao.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			Dao.LOGIN = "system";
			Dao.PASS = "bdd";
			break;
		}
		System.out.println("URL = " + Dao.URL);
		System.out.println("LOGIN = " + Dao.LOGIN);
		System.out.println("PASS = " + Dao.PASS);
	}

	/**
	 * Read the .sql file and separate the request one by one in a list.
	 * 
	 * @param fileName
	 *            the name of the .sql file
	 * @return the list with requests
	 */
	public static List<String> readSqlFile(String fileName) {
		List<String> sqlList = new ArrayList<String>();
		InputStream is = SetupDatabaseDao.class.getResourceAsStream(fileName);
		StringBuffer temp = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String str;
			while ((str = in.readLine()) != null) {
				temp.append(str);
			}
			in.close();
		} catch (Exception ex) {
			ex.getStackTrace();
		}
		String[] sqls = temp.toString().split(";");
		for (String sql : sqls) {
			sqlList.add(sql);
		}
		return sqlList;
	}

	/**
	 * Process one line of SQL code.
	 * 
	 * @param sql
	 *            SQL code
	 * @return number of rows updated
	 */
	public static int updateOne(String sql) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		try {
			con = DriverManager.getConnection(Dao.URL, Dao.LOGIN, Dao.PASS);
			ps = con.prepareStatement(sql);
			retour = ps.executeUpdate();
		} catch (Exception ex) {
			System.out.println("Failed : " + sql);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
				System.out.println("closing problem");
			}
		}
		return retour;
	}

	/**
	 * Drop all tables and sequences of G.D.S.
	 */
	public static void dropAll() {
		for (String sql : readSqlFile(fileDrop)) {
			updateOne(sql);
		}
		System.out.println("Drop Finished.");
	}

	private static void batch(String fileName) {
		Connection con = null;
		PreparedStatement ps = null;
		List<String> sqlList = readSqlFile(fileName);
		try {
			con = DriverManager.getConnection(Dao.URL, Dao.LOGIN, Dao.PASS);
			Statement smt = con.createStatement();

			for (String sql : sqlList) {
				smt.addBatch(sql);
			}
			smt.executeBatch();

			if (fileName.equals(fileCreate)) {
				System.out.println("Create successfully");
			} else if (fileName.equals(fileInsert)) {
				System.out.println("Insert successfully");
			} else {
				System.out.println("Success, but what file is it?");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (fileName.equals(fileCreate)) {
				System.out.println("Create failed");
			} else if (fileName.equals(fileInsert)) {
				System.out.println("Insert failed");
			} else {
				System.out.println("Failed, what file is it?");
			}
		} finally {
			// scanner.close();
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ex) {
				System.out.println("closing problem");
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("closing problem");
			}
		}
	}

	/**
	 * Create the tables in database.
	 */
	public static void createTable() {
		batch(fileCreate);
	}

	/**
	 * Insert data for testing in database.
	 */
	public static void insertData() {
		batch(fileInsert);
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 *            for main.
	 */
	public static void main(String[] args) {
		SetupDatabaseDao.switchStaticFields("Junyang");
		// SetupDatabaseDao.dropAll();
		// SetupDatabaseDao.createTable();
		// SetupDatabaseDao.insertData();
	}

}
