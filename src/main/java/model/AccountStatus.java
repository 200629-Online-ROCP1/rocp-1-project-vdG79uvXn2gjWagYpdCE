package model;

import field.*;

/* Status possibilities are Pending, Open, or Closed, or Denied
  public class AccountStatus {
    private int statusId; // primary key
    private String status; // not null, unique
  }
*/

public class AccountStatus extends Model {
    StringField status = new StringField("status");

    public AccountStatus() {
        super("AccountStatus");
        this.status.Option("null_allowed", 0);
        this.status.Option("is_unique", 1);
        this.addField(status);
    }
}