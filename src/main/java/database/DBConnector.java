package database;

public class DBConnector {
	private String username = System.getenv("BANKDB_USERNAME");
	private String password = System.getenv("BANKDB_PASSWORD");

	public DBConnector() {
		super();
	}

	public void showCredentials() {
		System.out.println(username + "/" + password);
	}
}