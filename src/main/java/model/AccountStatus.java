package model;

import field.*;

/* Status possibilities are Pending, Open, or Closed, or Denied */

public class AccountStatus extends Model {
    StringField statusField = new StringField("status");

    public AccountStatus() {
        super("AccountStatus");
        this.statusField.Option("null_allowed", 0);
        this.statusField.Option("is_unique", 1);
        this.addField(statusField);
    }

    // public void changeData(String status) {
    //   is_saved = false;
    //   this.status = status
    // }
}