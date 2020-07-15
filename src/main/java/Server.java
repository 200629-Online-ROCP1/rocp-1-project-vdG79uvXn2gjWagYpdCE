import model.*;
import java.util.*;

public class Server {
	
	static public void main(String... args) {

		// Logger.setFilename("/tmp/bank_app.log");
		// Logger.makeEntry("INFO", "Starting application");
		initialDataLoad();

		Account account;
		Map<String, String> data = new HashMap<String, String>();
		data.put("balance", "100.00");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Savings");
		data.put("accountholder", "hsimpson");
		account = new Account(data);
		System.out.println(account); 
		// accountHolder.save();
	}

	static public void initialDataLoad() {
		Account.truncate();
		AccountHolder.deleteAll();
		AccountStatus.deleteAll();
		AccountType.deleteAll();
		Role.deleteAll();

		initialDataLoadAccountStatus();
		initialDataLoadAccountType();
		initialDataLoadRole();
		initialDataLoadAccountHolder();
	}
	static public void initialDataLoadAccountHolder() {
		AccountHolder accountHolder;
		Map<String, String> data = new HashMap<String, String>();
		
		
		data.put("role", "Standard");
		data.put("username", "hsimpson");
		data.put("firstname", "Homer");
		data.put("lastname", "Simpson");
		data.put("password", "password789");
		data.put("email", "hsimpson2112@hotmail.com");
		accountHolder = new AccountHolder(data); 
		accountHolder.save();

		data.put("role", "Standard");
		data.put("username", "nflanders");
		data.put("firstname", "Ned");
		data.put("lastname", "Flanders");
		data.put("password", "password456");
		data.put("email", "nflanders@gmail.com");
		accountHolder = new AccountHolder(data); 
		accountHolder.save();

		data.put("role", "Employee");
		data.put("username", "kbrockman");
		data.put("firstname", "Kent");
		data.put("lastname", "Brockman");
		data.put("password", "password123");
		data.put("email", "kbrockman@gmail.com");
		accountHolder = new AccountHolder(data); 
		accountHolder.save();
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
