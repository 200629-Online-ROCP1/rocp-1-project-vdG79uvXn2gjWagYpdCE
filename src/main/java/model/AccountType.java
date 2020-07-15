package model;

import dao.AccountTypeDAO;
import database.DBConnector;
import field.*;

public class AccountType {
    private String type = "";
    private boolean saved = false;
    private int primaryKey = 0;

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
    public String toString() {
      String retString = new String("PK => " + primaryKey);
      retString += ", " + getField("type");
      if (!saved) {
        retString += " (NOT SAVED)";
      }
      return retString;

    }

    public String getField(String fieldName) {
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
        if (dao.insert(this)) { //Only runs if the insert is successful
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
}
