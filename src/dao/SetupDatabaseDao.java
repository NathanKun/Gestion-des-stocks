package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class SetupDatabaseDao {

	static final String fileDrop = "src/resources/drop.sql";
	static final String fileCreate = "src/resources/create.sql";
	static final String fileInsert = "src/resources/insert.sql";

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
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
			DAO.LOGIN = "c##nathankun";
			DAO.PASS = "83783548jun";
			break;
		case "Junior":
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "system";
			DAO.PASS = "bdd";
			break;
		case "BDD3":
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "BDD3";
			DAO.PASS = "BDD3";
			break;
		case "BDD5":
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "BDD5";
			DAO.PASS = "BDD5";
			break;
		case "BDD6":
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "BDD6";
			DAO.PASS = "BDD6";
			break;
		case "BDD7":
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "BDD7";
			DAO.PASS = "BDD7";
			break;
		case "BDD8":
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "BDD8";
			DAO.PASS = "BDD8";
			break;

		default:
			DAO.URL = "jdbc:oracle:thin:@localhost:1521:xe";
			DAO.LOGIN = "system";
			DAO.PASS = "bdd";
			break;
		}
		System.out.println("URL = " + DAO.URL);
		System.out.println("LOGIN = " + DAO.LOGIN);
		System.out.println("PASS = " + DAO.PASS);
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
		File myFile = new File(fileName);
		if (!myFile.exists()) {
			System.err.println("Can't Find " + fileName);
		}
		StringBuffer temp = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(myFile));
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
	 */
	public static int updateOne(String sql) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;
		try {
			con = DriverManager.getConnection(DAO.URL, DAO.LOGIN, DAO.PASS);
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
			con = DriverManager.getConnection(DAO.URL, DAO.LOGIN, DAO.PASS);
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
		SetupDatabaseDao.dropAll();
		SetupDatabaseDao.createTable();
		SetupDatabaseDao.insertData();
	}

}