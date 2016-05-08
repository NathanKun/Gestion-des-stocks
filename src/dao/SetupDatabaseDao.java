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

public final class SetupDatabaseDao {

	static String URL;
	static String LOGIN;
	static String PASS;

	public SetupDatabaseDao() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException exception) {
			System.err.println("impossible to load the database driver, please make sur you "
					+ "have import the .jar file in the project");
		}
	}

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
		} catch (Exception e) {
			e.getStackTrace();
		}
		String sqls[] = temp.toString().split(";");
		for (String sql : sqls) {
			sqlList.add(sql);
		}
		return sqlList;
	}

	public static void createTable() {

		String content = null;
		Scanner scanner = null;
		int[] retour = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			// scanner = new Scanner(new
			// File("src/resources/create_table.sql"));
			// content = scanner.useDelimiter("\\Z").next();
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// ps = con.prepareStatement(content);
			// retour = ps.executeUpdate();
			List<String> sqlList = readSqlFile("src/resources/create_table.sql");
			Statement smt = con.createStatement();

			for (String sql : sqlList) {
				smt.addBatch(sql);
			}
			retour = smt.executeBatch();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
//			scanner.close();
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
		// System.out.println(content);
		for (int i = 0; i < retour.length; i++){
			System.out.println(retour[i]);
		}
	}

	public static void main(String[] args) {
		 SetupDatabaseDao.switchStaticFields("Junyang");
		 SetupDatabaseDao.createTable();
		// List<String> list = readSqlFile("src/resources/create_table.sql");
		// for (String str : list) {
		// System.out.println(str);
		// }
	}

}
