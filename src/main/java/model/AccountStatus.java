package model;

import dao.AccountStatusDAO;
import database.DBConnector;
import field.*;



public class AccountStatus {
    StringField statusField = new StringField("status");

    private String status = "";

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
      dao.insert(this);
    }
}