package model;

import dao.AccountHolderDAO;
import database.DBConnector;
import field.*;

import model.Role;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AccountHolder {
    private ArrayList<String> fields = 
      new ArrayList<String>(Arrays.asList("username", "password", "firstname", "lastname", "email", "role"));
    private Map<String, String> fieldValues = new HashMap<String, String>();
    private Role role_fk;
    private int primaryKey = 0;
    private boolean saved = false;

    // Constructors
    public AccountHolder() {
      super();
    }

    public AccountHolder(Map<String, String> data) {
      super();
      for (String field: fields) {
        if (data.containsKey(field)) {
          this.fieldValues.put(field, data.get(field));
        } else {
          System.out.println("ERROR: No value was provided for field " + field);
        }
      }
      this.role_fk = Role.search(data.get("role")); 
    }
    public AccountHolder(int pk, Map<String, String> data) { 
      this(data);
      this.primaryKey = pk;
      this.saved = true;
    }
    public String toString() {
      String retString = new String("PK => " + primaryKey + "\n");
      for (String field: fields) {
        if (field=="role") {
          retString += "    Role PK => " + getRoleID() + " ("+ role_fk.getField("role") + ")\n";
        } else {
          retString += "    " + getField(field) + "\n";
        }
      }
      if (!saved) {
        retString += "    (NOT SAVED)";
      }
      return retString;

    }

    public String getField(String fieldName) {
      return fieldValues.get(fieldName);
    }
    public void setField(Map<String, String> data) {
      for (String field: data.keySet()) {
        this.fieldValues.put(field, data.get(field));
      }
    }
    public int getID() {
      return primaryKey;
    }
    public int getRoleID() {
      return role_fk.getID();
    }

    // Database operations - save(insert or update), search, refresh
    public void save() {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      if (primaryKey > 0) { //FIX
        if (dao.update(this)) {
          saved = true;
        }
      } else {
        if (dao.insert(this)) { //FIX
          Map<String, String> data = new HashMap<String, String>();
		      data.put("username", getField("username"));
          AccountHolder tmp = AccountHolder.search(data);  // FIX
          this.primaryKey = tmp.primaryKey;
          saved = true;
        }
      }
    }

    public static AccountHolder search(Map<String, String> data) {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      return dao.search(data);
    }

    public static void deleteAll() {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      dao.delete();
    }
}
