package logger;

public class Logger {
	/* This implements a logging function for the application
	 */

	private static String filename;

	public static void setFilename(String _filename) {
		filename = _filename;
	}
	public static void makeEntry(String level, String message) {
		/* Logs the given message to filename with a timestamp and level.
		 * 
		 * TODO: Implement writing to the file
		 * TODO: Get timestamp
		 * TODO: catch any errors
		 * 
		 */
		String logstring = new String("<TIMESTAMP> " + level + ": " + message);
		System.out.println(logstring);
	}
	
}