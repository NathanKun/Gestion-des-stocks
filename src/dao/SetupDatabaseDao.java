package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.security.auth.login.FailedLoginException;

public final class SetupDatabaseDao {

	static String URL;
	static String LOGIN;
	static String PASS;

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
	 * Switch url, login and pass by user.
	 * 
	 * @param user
	 *            user
	 */
	public static void switchStaticFields(String user) {
		switch (user) {
		case "Junyang":
			URL = "jdbc:oracle:thin:@localhost:1521:dbkun";
			LOGIN = "c##nathankun";
			PASS = "83783548jun";
			break;
		case "Junior":
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "system";
			PASS = "bdd";
			break;
		case "BDD3":
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "BDD3";
			PASS = "BDD3";
			break;
		case "BDD5":
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "BDD5";
			PASS = "BDD5";
			break;
		case "BDD6":
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "BDD6";
			PASS = "BDD6";
			break;
		case "BDD7":
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "BDD7";
			PASS = "BDD7";
			break;
		case "BDD8":
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "BDD8";
			PASS = "BDD8";
			break;

		default:
			URL = "jdbc:oracle:thin:@localhost:1521:xe";
			LOGIN = "system";
			PASS = "bdd";
			break;
		}
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
			con = DriverManager.getConnection(URL, LOGIN, PASS);
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
			con = DriverManager.getConnection(URL, LOGIN, PASS);
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
