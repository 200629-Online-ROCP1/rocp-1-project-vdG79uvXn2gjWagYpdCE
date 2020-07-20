package model;

import dao.AccountDAO;
import dao.AccountHolderDAO;
import model.*;

import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Account {
	private ArrayList<String> fields = new ArrayList<String>(
			Arrays.asList("balance", "deleted", "accountstatus", "accounttype", "accountholder"));
	private Map<String, String> fieldValues = new HashMap<String, String>();

	private AccountType accounttype_fk;
	private AccountStatus accountstatus_fk;
	private AccountHolder accountholder_fk;
	private int primaryKey = 0;
	private boolean saved = false;
	private String uuid;

	// Constructors
	public Account() {
		super();
	}

	public Account(Map<String, String> data) throws IllegalArgumentException {
		super();
		for (String field : fields) {
			if (data.containsKey(field)) {
				this.fieldValues.put(field, data.get(field));
			} else {
				if (field == "accountholder") {
					if (data.containsKey("accountholder_id")) {
						int id = Integer.parseInt(data.get("accountholder_id"));
						this.accountholder_fk = AccountHolder.search(id);
					} else {
						System.out.println("ERROR: No value was provided for field " + field);
					}
				}
				if (field == "accountstatus") {
					if (data.containsKey("accountstatus_id")) {
						int id = Integer.parseInt(data.get("accountstatus_id"));
						this.accountstatus_fk = AccountStatus.search(id);
					} else {
						System.out.println("ERROR: No value was provided for field " + field);
					}
				}
				if (field == "accounttype") {
					if (data.containsKey("accounttype_id")) {
						int id = Integer.parseInt(data.get("accounttype_id"));
						this.accounttype_fk = AccountType.search(id);
					} else {
						System.out.println("ERROR: No value was provided for field " + field);
					}
				}
			}
		}
		this.uuid = UUID.randomUUID().toString();
		if (this.accounttype_fk == null) {
			this.accounttype_fk = AccountType.search(data.get("accounttype"));
		}
		if (this.accountstatus_fk == null) {
			this.accountstatus_fk = AccountStatus.search(data.get("accountstatus"));
		}
		if (this.accountholder_fk == null) {
			this.accountholder_fk = AccountHolder.search(data.get("accountholder"));
		}
		Role role_fk = Role.search(this.accountholder_fk.getRoleID()); 
		if (!role_fk.getField("role").equals("Standard")) {
			throw new IllegalArgumentException("The Account owner must have a role of Standard");
		}
	}

	public Account(int pk, Map<String, String> data) {
		this(data);
		this.primaryKey = pk;
		this.saved = true;
	}

	public String toString() {
		String retString = new String("PK => " + primaryKey + "\n");
		for (String field : fields) {
			if (field == "accounttype") {
				retString += "    AccountType PK => " + accounttype_fk.getID() + " (" + accounttype_fk.getField("type")
						+ ")\n";
			} else if (field == "accountstatus") {
				retString += "    AccountStatus PK => " + accountstatus_fk.getID() + " ("
						+ accountstatus_fk.getField("status") + ")\n";
			} else if (field == "accountholder") {
				retString += "    AccountHolder PK => " + accountholder_fk.getID() + " ("
						+ accountholder_fk.getField("username") + ")\n";
			} else {
				retString += "    " + getField(field) + "\n";
			}
		}
		if (!saved) {
			retString += "    (NOT SAVED)";
		}
		return retString;
	}

	public String toJSON() {
		return asJSONObject().toString();
	}

	public JSONObject asJSONObject() {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("account_id", primaryKey);
		for (String field : fields) {
			if (field.equals("accountstatus")) {
				jsonobj.put("accountstatus", accountstatus_fk.asJSONObject());
			} else if (field.equals("accounttype")) {
				jsonobj.put("accounttype", accounttype_fk.asJSONObject());
			} else if (field.equals("accountholder")) {
				jsonobj.put("accountholder", accountholder_fk.asJSONObject());
			} else {
				jsonobj.put(field, getField(field));
			}
		}
		return jsonobj;
	}

	public String getField(String fieldName) {
		if (fieldName == "uuid") {
			return this.uuid;
		}
		return fieldValues.get(fieldName);
	}

	public void setField(Map<String, String> data) {
		for (String field : data.keySet()) {
			this.fieldValues.put(field, data.get(field));
		}
		saved = false;
	}

	public int getID() {
		return primaryKey;
	}

	public int getFKID(String fieldName) {
		if (fieldName == "accountstatus") {
			return accountstatus_fk.getID();
		} else if (fieldName == "accounttype") {
			return accounttype_fk.getID();
		} else if (fieldName == "accountholder") {
			return accountholder_fk.getID();
		} else {
			System.out.println("ERROR: foreign key " + fieldName + " is unknown.");
			return 0;
		}

	}

	// Database operations - save(insert or update), search, refresh
	public void save() throws IllegalArgumentException {
		AccountDAO dao = AccountDAO.getInstance();
		if (primaryKey > 0) { // FIX
			if (dao.update(this)) {
				saved = true;
			}
		} else {
			if (dao.insert(this)) {
				Account tmp = Account.search(getField("uuid"));
				this.primaryKey = tmp.primaryKey;
				saved = true;
			}
		}
	}

	public static Account search(String uuid) {
		AccountDAO dao = AccountDAO.getInstance();
		return dao.search(uuid);
	}

	public static Account search(int ID) {
		AccountDAO dao = AccountDAO.getInstance();
		return dao.search(ID);
	}

	public static void deleteAll() {
		AccountDAO dao = AccountDAO.getInstance();
		dao.deleteAll();
	}
	
	/** Does not actually delete the account, sets the deleted to true */
	public static void delete(int ID) {
		Map<String, String> data = new HashMap<String, String>();
		Account account = Account.search(ID);
		data.put("deleted", "true");
		account.setField(data);
		account.save();
	}

	public static ArrayList<Account> retrieveAll() {
		AccountDAO dao = AccountDAO.getInstance();
		return dao.retrieveAll();
	}
	
	public static ArrayList<Account> filter(String fieldName, int ID) {
		AccountDAO dao = AccountDAO.getInstance();
		return dao.filter(fieldName, ID);
	}
}
