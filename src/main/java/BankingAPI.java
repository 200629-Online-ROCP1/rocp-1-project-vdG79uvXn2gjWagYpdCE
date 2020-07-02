import database.*;

public class BankingAPI {
	
	static public void main(String... args) {
		DBConnector db = new DBConnector();
		db.showCredentials();
	} 
}
