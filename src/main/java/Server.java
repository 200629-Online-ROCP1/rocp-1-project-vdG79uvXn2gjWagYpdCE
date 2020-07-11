import database.DBConnector;
import logger.Logger;
import model.AccountStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.SQLException;

public class Server {
	
	static public void main(String... args) {

		// Logger.setFilename("/tmp/bank_app.log");
		// Logger.makeEntry("INFO", "Starting application");

		initialDataLoad();

	}

	static public void initialDataLoad() {
		initialDataLoadAccountStatus();
	}
	static public void initialDataLoadAccountStatus() {
		AccountStatus.deleteAll();
		AccountStatus accountStatus;
		for (String status: Arrays.asList("Pending", "Open", "Closed", "Denied")) {
			accountStatus = new AccountStatus(status);
			accountStatus.save();
        }
	}
}
