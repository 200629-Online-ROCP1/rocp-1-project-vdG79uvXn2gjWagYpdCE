package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import logger.Logger;

public class DBConnector {
	/** Utility class to create a connection to our database.
	 *  The username and password must exist as environment variables.
	 */

	private static String username = System.getenv("BANKDB_USERNAME");
	private static String password = System.getenv("BANKDB_PASSWORD");
	
	public static Connection getConnection(String hostname, String database) throws SQLException {
		String url = "jdbc:postgresql://" + hostname + ":5432/" + database;
		return DriverManager.getConnection(url, username, password);
	}
}