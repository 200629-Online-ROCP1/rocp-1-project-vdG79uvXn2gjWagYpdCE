package model;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import dao.AccountTypeDAO;

/**
 * @author      Patrick Buller <address @ example.com>
 * @version     0.1
 * @since       0.1
 */

public class AccountType {
	private String type = "";
	private boolean saved = false;
	private int primaryKey = 0;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + primaryKey;
		result = prime * result + (saved ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountType other = (AccountType) obj;
		if (primaryKey != other.primaryKey)
			return false;
		if (saved != other.saved)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	// Constructors
	public AccountType() {
		super();
	}

	public AccountType(String type) {
		super();
		this.type = type;
	}

	public AccountType(int pk, String type) {
		this(type);
		this.primaryKey = pk;
		this.saved = true;
	}

	/**
	 * The String representation of the AccountType object
	 *
	 * @return String string representation of the AccountType object
	 */
	public String toString() {
		String retString = new String("PK => " + primaryKey);
		retString += ", " + getField("type");
		if (!saved) {
			retString += " (NOT SAVED)";
		}
		return retString;
	}

	public String toJSON() {
		return asJSONObject().toString();
	}

	public JSONObject asJSONObject() {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("accounttype_id", primaryKey);
		jsonobj.put("type", getField("type"));
		return jsonobj;
	}

	public String getField(String fieldName) {
		return type;
	}

	public String getType() {
		return type;
	}

	public String setField(String fieldName, String type) {
		this.type = type;
		this.saved = false;
		return type;
	}

	public int getID() {
		return primaryKey;
	}

	// Database operations - save(insert or update), search, refresh
	public void save() {
		AccountTypeDAO dao = AccountTypeDAO.getInstance();
		if (primaryKey > 0) {
			if (dao.update(this)) {
				saved = true;
			}
		} else {
			if (dao.insert(this)) { // Only runs if the insert is successful
				AccountType tmp = AccountType.search(type);
				this.primaryKey = tmp.primaryKey;
				saved = true;
			}
		}
	}

	public static AccountType search(String type) {
		AccountTypeDAO dao = AccountTypeDAO.getInstance();
		return dao.search(type);
	}

	public static AccountType search(int ID) {
		AccountTypeDAO dao = AccountTypeDAO.getInstance();
		return dao.search(ID);
	}

	public static void delete(int ID) {
		AccountTypeDAO dao = AccountTypeDAO.getInstance();
		dao.delete(ID);
	}

	public static void deleteAll() {
		AccountTypeDAO dao = AccountTypeDAO.getInstance();
		dao.deleteAll();
	}

	public static ArrayList<AccountType> retrieveAll() {
		AccountTypeDAO dao = AccountTypeDAO.getInstance();
		return dao.retrieveAll();
	}
}
