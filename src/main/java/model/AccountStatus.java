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
        this.status.Option("null_allowed", false);
        this.status.Option("is_unique", true);
        this.addField(status);
    }
}