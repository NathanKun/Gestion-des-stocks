package util;

import dao.UserDAO;

/**
 * For some reason, first time the application connects to the data base, it
 * will stuck for a few second. This class will connect to the data base in the
 * backstage, so the GUI won't stuck anymore.
 * 
 * @author HE Junyang
 *
 */
public class FirstConnectDataBaseThread extends Thread {
	public void run() {
		new UserDAO().getUser("a");
	}
}