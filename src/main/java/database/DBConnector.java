package database;

import java.sql.*;
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

	public Connection dbconn;
	public boolean is_connected = false;

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
	public int execute(String sql) {
		// String query = "insert into emps (name, dept, salary) values (?,?,?)";
		// statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		// statement.setString(1, "John");
		// statement.setString(2, "Acc Dept");
		// statement.setInt(3, 10000);
		// statement.executeUpdate();
            // rs = pstmt.getGeneratedKeys();
            // if(rs != null && rs.next()){
            //     System.out.println("Generated Emp Id: "+rs.getInt(1));
            // }

		System.out.println("EXECUTING SQL => " + sql);
		try {
			Statement statement = dbconn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, "Pending");
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
				System.out.println(resultSet.getString("AccountStatus_id"));
                System.out.println(resultSet.getString("status"));
			}
			resultSet.close();
			statement.close();
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			return 1;
		}
	}

	public void connect() {
		/* Checks if there is currently a connection. If not,
		 * tries to make connection. If it fails, logs the error
		 * and returns false.
		 */
		if (hostname == null) {
			Logger.makeEntry("ERROR", "No database server hostname provided.");
		} else if (database == null) {
			Logger.makeEntry("ERROR", "No database name provided.");
		} else {
			try {
				String dbUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;
				dbconn = DriverManager.getConnection(dbUrl, username, password);
				Logger.makeEntry("INFO", "Connecting to the database.");
				is_connected = true;
			} catch (SQLException e) {
				Logger.makeEntry("ERROR", "Database access failed " + e);
			}

		}
	}
	public void disconnect() {
		/* Disconnects from the database. 
		 */
		try {
			dbconn.close();
			Logger.makeEntry("INFO", "Disconnecting from the database.");
		} catch (SQLException e) {
			Logger.makeEntry("ERROR", "Database disconnection failed " + e);
		}
		is_connected = true;
	}

	public void showCredentials() {
		/* This is only for testing/debugging */
		System.out.println(username + "/" + password);
	}
}