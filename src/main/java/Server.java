import database.*;

public class Server {
	
	static public void main(String... args) {
		DBConnector db = new DBConnector();
		db.showCredentials();
	} 
}
