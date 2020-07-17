package model;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import dao.AccountHolderDAO;
import dao.AccountStatusDAO;
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
          if (field=="role") { // Missing the role, need to check if contains role_id
            if (data.containsKey("role_id")) {
              this.role_fk = Role.search(Integer.parseInt(data.get("role_id")));
            }
          } else {
            System.out.println("ERROR: No value was provided for field " + field);
          }
        }
      }
      if (this.role_fk==null) {
    	  this.role_fk = Role.search(data.get("role"));
      }
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
    
    public String toJSON() {
    	JSONObject jsonobj = new JSONObject();
        jsonobj.put("accountholder_id", primaryKey);
        for (String field: fields) {
        	if (field.equals("role")) {
        		jsonobj.put("role", role_fk.toJSON());
        	} else {
        		jsonobj.put(field, getField(field));
        	}
        }
        return jsonobj.toString();
    }

    public String getField(String fieldName) {
      return fieldValues.get(fieldName);
    }
    public void setField(Map<String, String> data) {
      for (String field: data.keySet()) {
        this.fieldValues.put(field, data.get(field));
      }
      saved = false;
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
        if (dao.insert(this)) {
          AccountHolder tmp = AccountHolder.search(getField("username"));
          this.primaryKey = tmp.primaryKey;
          saved = true;
        }
      }
    }

    public static AccountHolder search(String username) {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      return dao.search(username);
    }

    public static AccountHolder search(int ID) {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      return dao.search(ID);
    }

    public static void delete(int ID) {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      dao.delete(ID);
    }
    public static void deleteAll() {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      dao.deleteAll();
    }
    public static ArrayList<AccountHolder> retrieveAll() {
    	AccountHolderDAO dao = AccountHolderDAO.getInstance();
    	return dao.retrieveAll();
    }
}
