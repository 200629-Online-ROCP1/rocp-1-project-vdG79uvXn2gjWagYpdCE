package database;

import logger.Logger;

public class DBConnector {
	/* This will control connections to the database for the various models.
	 * The username and password must exist as environment variables.
	 */

	private String username = System.getenv("BANKDB_USERNAME");
	private String password = System.getenv("BANKDB_PASSWORD");

	private int port = 5432; //Default port for postgres
	private String hostname;
	private String database;

	public DBConnector() {
		super();
	}
	public DBConnector(String hostname, int port, String database) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.database = database;
	}
	public DBConnector(String hostname, String database) {
		super();
		this.hostname = hostname;
		this.database = database;
	}
	public boolean connect() {
		/* Checks if there is currently a connection. If not,
		 * tries to make connection. If it fails, logs the error
		 * and returns false.
		 */
		// TODO implement connection
		// TODO make it log success or failure
		// TODO catch and throw exceptions
		if (hostname == null) {
			Logger.makeEntry("ERROR", "No database server hostname provided.");
			return false;
		} else if (database == null) {
			Logger.makeEntry("ERROR", "No database name provided.");
			return false;
		} else {
			System.out.println("Pretend I am connecting to the database.");
			Logger.makeEntry("INFO", "Connecting to the database.");
			return true;
		}
	}
	public void disconnect() {
		/* Disconnects from the database. 
		 */
		// TODO implement disconnecting
		// TODO make it log 
		// TODO catch and throw exceptions
		System.out.println("Pretend I am disconnecting from the database.");
		Logger.makeEntry("INFO", "Disconnecting from the database.");
	}

	public void showCredentials() {
		/* This is only for testing/debugging */
		System.out.println(username + "/" + password);
	}
}