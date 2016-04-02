package src.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * My Regex Tester
 * Test is String a long or double
 * @author HE Junyang
 *
 */
public class Regex {
	/**
	 * test is a String a double
	 * @param str String for test
	 * @return	true if is a double, false if not
	 */
	public static boolean isDouble(String str){
		String regEx = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        }
        else {
            return false;
        }
	}
	
	/**
	 * test is a String a long
	 * @param str String for test
	 * @return	true if is a long, false if not
	 */
	public static boolean isLong(String str){
		String regEx = "^-?[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        }
        else {
            return false;
        }
	}
	
	/**
	 * main method, for testing
	 * @param args for main
	 */
	public static void main(String[] args) {
		System.out.println(isDouble("3.1231"));
		System.out.println(isDouble("3"));
		System.out.println(isDouble(".1231"));
		System.out.println(isDouble("-3.1231"));
		System.out.println(isDouble("434531234537513"));
		

		System.out.println(isLong("3.1231"));
		System.out.println(isLong("3"));
		System.out.println(isLong(".1231"));
		System.out.println(isLong("-3.1231"));
		System.out.println(isLong("434531234537513"));

	}

}