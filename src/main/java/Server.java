import database.DBConnector;
import logger.Logger;

public class Server {
	
	static public void main(String... args) {
		DBConnector db = new DBConnector("localhost", "dewey-cheatem-and-howe");

		Logger.setFilename("/tmp/bank_app.log");
		Logger.makeEntry("INFO", "Starting application");

		db.connect();

	} 
}
