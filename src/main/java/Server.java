import database.DBConnector;
import logger.Logger;
import model.AccountStatus;

import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.SQLException;

public class Server {
	
	static public void main(String... args) {

		// Logger.setFilename("/tmp/bank_app.log");
		// Logger.makeEntry("INFO", "Starting application");

		/* Status possibilities are Pending, Open, or Closed, or Denied */
		AccountStatus accountStatus = AccountStatus.search("Pending");
		// System.out.println(accountStatus);

		accountStatus = new AccountStatus("Iniaxxaataial11");
		System.out.println(accountStatus);
		accountStatus.save();
		System.out.println(accountStatus);
		accountStatus.setField("status", "renamexxd");
		System.out.println(accountStatus);
		accountStatus.save();
		System.out.println(accountStatus);

		// accountStatus = new AccountStatus("Open");
		// accountStatus.save();
		// accountStatus = new AccountStatus("Closed");
		// accountStatus.save();
		// accountStatus = new AccountStatus("Denied");
		// accountStatus.save();
	}
}
