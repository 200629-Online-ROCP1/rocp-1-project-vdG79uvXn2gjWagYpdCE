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
		 * TODO: catch any errors
		 * 
		 */
		java.time.LocalDateTime currentDateTime = java.time.LocalDateTime.now();
		final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ISO_DATE_TIME;
		String timestamp = currentDateTime.format(formatter);
		String logstring = new String(timestamp + " " + level + ": " + message);
		System.out.println(logstring);
	}
	
}