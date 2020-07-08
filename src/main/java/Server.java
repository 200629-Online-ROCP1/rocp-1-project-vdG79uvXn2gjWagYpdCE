import database.DBConnector;
import logger.Logger;
import model.*;

public class Server {
	
	static public void main(String... args) {
		// DBConnector db = new DBConnector("172.18.0.2", "bank_database");

		// Logger.setFilename("/tmp/bank_app.log");
		// Logger.makeEntry("INFO", "Starting application");

		// db.connect();
		// System.out.println(db.is_connected);
		// db.disconnect();

		// createSQL();
		AccountStatus record_accountStatus1 = new AccountStatus();
		AccountStatus record_accountStatus2 = new AccountStatus();
		record_accountStatus1.changeData("Pending");
		System.out.println(record_accountStatus1);
		System.out.println(record_accountStatus2);
	} 

	static private void createSQL() {
		Role Role = new Role();
		AccountHolder AccountHolder = new AccountHolder();
		AccountStatus AccountStatus = new AccountStatus();
		AccountType AccountType = new AccountType();
		Account Account = new Account();

		System.out.println(Role.createSQL());
		System.out.println(AccountHolder.createSQL());
		System.out.println(AccountStatus.createSQL());
		System.out.println(AccountType.createSQL());
		System.out.println(Account.createSQL());
	}
}
