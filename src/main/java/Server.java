import model.*;
import java.util.*;

public class Server {

	static public void main(String... args) {

		initialDataLoad();
		
//		String jwt = JWT.create("hsimpson");
//		System.out.println(jwt);
//		Claims claims = JWT.decode(jwt);
//		System.out.println(claims.get("username"));
//		JWT.isExpired(jwt);
	}

	static public void initialDataLoad() {
		Account.deleteAll();
		AccountHolder.deleteAll();
		AccountStatus.deleteAll();
		AccountType.deleteAll();
		Role.deleteAll();

		initialDataLoadAccountStatus();
		initialDataLoadAccountType();
		initialDataLoadRole();
		initialDataLoadAccountHolder();
		initialDataLoadAccount();
	}

	static public void initialDataLoadAccount() {
		Account account;
		Map<String, String> data = new HashMap<String, String>();
		data.put("balance", "100.00");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Savings");
		data.put("accountholder", "hsimpson");
		account = new Account(data);
		account.save();

		data.put("balance", "1650.28");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Checking");
		data.put("accountholder", "hsimpson");
		account = new Account(data);
		account.save();
		
		data.put("balance", "150.28");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Checking");
		data.put("accountholder", "hsimpson");
		account = new Account(data);
		account.save();
		
		data.put("balance", "150.28");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Checking");
		data.put("accountholder", "nflanders");
		account = new Account(data);
		account.save();
		
		data.put("balance", "1250.28");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Savings");
		data.put("accountholder", "nflanders");
		account = new Account(data);
		account.save();
		
		data.put("balance", "1250.28");
		data.put("deleted", "false");
		data.put("accountstatus", "Open");
		data.put("accounttype", "Savings");
		data.put("accountholder", "wsmithers");
		account = new Account(data);
		account.save();
	}

	static public void initialDataLoadAccountHolder() {
		AccountHolder accountHolder;
		Map<String, String> data = new HashMap<String, String>();

		data.put("role", "Standard");
		data.put("username", "hsimpson");
		data.put("firstname", "Homer");
		data.put("lastname", "Simpson");
		data.put("password", "password");
		data.put("email", "hsimpson2112@hotmail.com");
		accountHolder = new AccountHolder(data);
		accountHolder.save();

		data.put("role", "Standard");
		data.put("username", "nflanders");
		data.put("firstname", "Ned");
		data.put("lastname", "Flanders");
		data.put("password", "password");
		data.put("email", "nflanders@gmail.com");
		accountHolder = new AccountHolder(data);
		accountHolder.save();

		data.put("role", "Standard");
		data.put("username", "wsmithers");
		data.put("firstname", "Waylon");
		data.put("lastname", "Smithers");
		data.put("password", "password");
		data.put("email", "wsmithers@gmail.com");
		accountHolder = new AccountHolder(data);
		accountHolder.save();
		
		data.put("role", "Employee");
		data.put("username", "rwiggum");
		data.put("firstname", "Ralph");
		data.put("lastname", "Wiggum");
		data.put("password", "password");
		data.put("email", "rwiggum@gmail.com");
		accountHolder = new AccountHolder(data);
		accountHolder.save();
		
		data.put("role", "Employee");
		data.put("username", "nmuntz");
		data.put("firstname", "Nelson");
		data.put("lastname", "Muntz");
		data.put("password", "password");
		data.put("email", "nmuntz@gmail.com");
		accountHolder = new AccountHolder(data);
		accountHolder.save();
		
		data.put("role", "Admin");
		data.put("username", "msimpson");
		data.put("firstname", "Marge");
		data.put("lastname", "Simpson");
		data.put("password", "password");
		data.put("email", "msimpson@gmail.com");
		accountHolder = new AccountHolder(data);
		accountHolder.save();
	}

	static public void initialDataLoadAccountStatus() {
		AccountStatus.deleteAll();
		AccountStatus accountStatus;
		for (String status : Arrays.asList("Pending", "Open", "Closed", "Denied")) {
			accountStatus = new AccountStatus(status);
			accountStatus.save();
		}
	}

	static public void initialDataLoadAccountType() {
		AccountType.deleteAll();
		AccountType accountType;
		for (String type : Arrays.asList("Checking", "Savings")) {
			accountType = new AccountType(type);
			accountType.save();
		}
	}

	static public void initialDataLoadRole() {
		Role.deleteAll();
		Role role;
		for (String description : Arrays.asList("Admin", "Employee", "Premium", "Standard")) {
			role = new Role(description);
			role.save();
		}
	}
}
