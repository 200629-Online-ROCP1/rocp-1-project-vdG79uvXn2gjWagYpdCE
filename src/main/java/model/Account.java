package model;

import dao.AccountDAO;
import database.DBConnector;
import database.QueryBuilder;
import field.*;

import model.*;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Account {
    private ArrayList<String> fields = 
      new ArrayList<String>(Arrays.asList("balance", "deleted", "accountstatus", "accountype", "accountholder"));
    private Map<String, String> fieldValues = new HashMap<String, String>();

    private AccountType accounttype_fk;
    private AccountStatus accountstatus_fk;
    private AccountHolder accountholder_fk;
    private int primaryKey = 0;
    private boolean saved = false;

    // Constructors
    public Account() {
      super();
    }

    public Account(Map<String, String> data) {
      super();
      for (String field: fields) {
        if (data.containsKey(field)) {
          this.fieldValues.put(field, data.get(field));
        } else {
          if (field=="accountholder") { 
            if (data.containsKey("accountholder_id")) {
              int id = Integer.parseInt(data.get("accountholder_id"));
              this.accountholder_fk = AccountHolder.search(id);
            }
          } else {
            System.out.println("ERROR: No value was provided for field " + field);
          }
          if (field=="accountstatus") { 
            if (data.containsKey("accountstatus_id")) {
              int id = Integer.parseInt(data.get("accountstatus_id"));
              this.accountstatus_fk = AccountStatus.search(id);
            }
          } else {
            System.out.println("ERROR: No value was provided for field " + field);
          }
          if (field=="accounttype") { 
            if (data.containsKey("accounttype_id")) {
              int id = Integer.parseInt(data.get("accounttype_id"));
              this.accounttype_fk = AccountType.search(id);
            }
          } else {
            System.out.println("ERROR: No value was provided for field " + field);
          }
        }
      }
      if (this.accounttype_fk==null) { AccountType.search(data.get("accounttype")); }
      if (this.accountstatus_fk==null) { AccountStatus.search(data.get("accountstatus")); }
      if (this.accountholder_fk==null) { AccountType.search(data.get("accountholder")); }
    }
    public Account(int pk, Map<String, String> data) { 
      this(data);
      this.primaryKey = pk;
      this.saved = true;
    }
    /*
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
    */

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
    /*
    public int getRoleID() {
      return role_fk.getID();
    }
    */

    // Database operations - save(insert or update), search, refresh
    public void save() {
      AccountDAO dao = AccountDAO.getInstance(); 
      if (primaryKey > 0) { //FIX
        if (dao.update(this)) {
          saved = true;
        }
      } else {
        if (dao.insert(this)) {
          Account tmp = Account.searchUsername(getField("username"));
          this.primaryKey = tmp.primaryKey;
          saved = true;
        }
      }
    }

    public static Account searchUsername(String username) {
      AccountDAO dao = AccountDAO.getInstance(); 
      return dao.searchUsername(username);
    }

    public static void truncate() {
      AccountDAO dao = AccountDAO.getInstance(); 
      dao.truncate();
    }
}
