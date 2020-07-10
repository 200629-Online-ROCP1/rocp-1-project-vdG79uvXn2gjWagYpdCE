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

    public String Field(String fieldName) {
      return status;
    }

    // public void create(String status) {
    //   is_saved = false;
    //   pk = 0;
    //   this.statusField.setValue(status);
    // }

    public void save() {
      AccountStatusDAO dao = AccountStatusDAO.getInstance(); 
      if (dao.insert(this)) {
        saved = true;
      }
    }
}