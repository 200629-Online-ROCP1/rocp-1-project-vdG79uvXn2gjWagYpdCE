package model;

import dao.AccountHolderDAO;
import database.DBConnector;
import field.*;

public class AccountHolder {
    private String status = "";
    private boolean saved = false;
    private int primaryKey = 0;

    // Constructors
    public AccountHolder() {
      super();
    }
    public AccountHolder(String status) {
      super();
      this.status = status;
    }
    public AccountHolder(int pk, String status) {
      this(status);
      this.primaryKey = pk;
      this.saved = true;
    }
    public String toString() {
      String retString = new String("PK => " + primaryKey);
      retString += ", " + getField("status");
      if (!saved) {
        retString += " (NOT SAVED)";
      }
      return retString;

    }

    public String getField(String fieldName) {
      return status;
    }
    public String setField(String fieldName, String status) {
      this.status = status;
      this.saved = false;
      return status;
    }
    public int getID() {
      return primaryKey;
    }

    // Database operations - save(insert or update), search, refresh
    public void save() {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      if (primaryKey > 0) {
        if (dao.update(this)) {
          saved = true;
        }
      } else {
        if (dao.insert(this)) { //Only runs if the insert is successful
          AccountHolder tmp = AccountHolder.search(status);
          this.primaryKey = tmp.primaryKey;
          saved = true;
        }
      }
    }

    public static AccountHolder search(String status) {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      return dao.search(status);
    }

    public static void deleteAll() {
      AccountHolderDAO dao = AccountHolderDAO.getInstance(); 
      dao.delete();
    }
}
