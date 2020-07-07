import database.DBConnector;
import logger.Logger;

public class Server {
	
	static public void main(String... args) {
		DBConnector db = new DBConnector("172.18.0.2", "bank_database");

		Logger.setFilename("/tmp/bank_app.log");
		Logger.makeEntry("INFO", "Starting application");

		db.connect();
		System.out.println(db.is_connected);
		db.disconnect();

	} 
}
