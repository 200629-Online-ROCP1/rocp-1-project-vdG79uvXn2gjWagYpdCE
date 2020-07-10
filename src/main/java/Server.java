import database.DBConnector;
import logger.Logger;
import model.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Server {
	
	static public void main(String... args) {
		try {
			Connection dbconn = DBConnector.getConnection("172.18.0.2", "bank_database");
		} catch (SQLException e) {
			System.out.println(e);
		}

		// Logger.setFilename("/tmp/bank_app.log");
		// Logger.makeEntry("INFO", "Starting application");

		/* Status possibilities are Pending, Open, or Closed, or Denied */
		// AccountStatus accountStatus = new AccountStatus();
		// accountStatus.create("Pending");
		// accountStatus.save(db);
	}
}
