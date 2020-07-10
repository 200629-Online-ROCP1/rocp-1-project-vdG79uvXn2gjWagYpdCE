package model;

import dao.AccountStatusDAO;
import database.DBConnector;
import field.*;



public class AccountStatus {
    StringField statusField = new StringField("status");

    private String status = "";
    private boolean saved = false;
    private int primaryKey = 0;

    // Constructors
    public AccountStatus() {
      super();
    }
    public AccountStatus(String status) {
      super();
      this.status = status;
    }
    public AccountStatus(int pk, String status) {
      this(status);
      this.primaryKey = pk;
      this.saved = true;
    }
    public String toString() {
      String retString = new String("PK => " + primaryKey);
      retString += ", " + Field("status");
      if (!saved) {
        retString += " (NOT SAVED)";
      }
      return retString;

    }

    public String Field(String fieldName) {
      return status;
    }

    // Database operations - save(insert or update), search, refresh
    public void save() {
      AccountStatusDAO dao = AccountStatusDAO.getInstance(); 
      if (dao.insert(this)) {
        AccountStatus tmp = AccountStatus.search(status);
        this.primaryKey = tmp.primaryKey;
        saved = true;
      }
    }

    public static AccountStatus search(String status) {
      AccountStatusDAO dao = AccountStatusDAO.getInstance(); 
      return dao.search(status);
    }
}