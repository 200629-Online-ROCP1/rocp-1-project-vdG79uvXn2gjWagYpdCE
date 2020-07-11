import database.DBConnector;
import logger.Logger;
import model.AccountStatus;
import model.AccountType;
import model.AccountHolder;
import model.Role;

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
		initialDataLoadAccountType();
		initialDataLoadRole();
	}
	static public void initialDataLoadAccountStatus() {
		AccountStatus.deleteAll();
		AccountStatus accountStatus;
		for (String status: Arrays.asList("Pending", "Open", "Closed", "Denied")) {
			accountStatus = new AccountStatus(status);
			accountStatus.save();
        }
	}
	static public void initialDataLoadAccountType() {
		AccountType.deleteAll();
		AccountType accountType;
		for (String type: Arrays.asList("Checking", "Savings")) {
			accountType = new AccountType(type);
			accountType.save();
        }
	}
	static public void initialDataLoadRole() {
		Role.deleteAll();
		Role role;
		for (String description: Arrays.asList("Admin", "Employee", "Premium", "Standard")) {
			role = new Role(description);
			role.save();
        }
	}
}
