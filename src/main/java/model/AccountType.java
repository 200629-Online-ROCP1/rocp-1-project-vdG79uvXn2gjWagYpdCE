package model;

import field.*;

/* Type possibilities are Checking or Savings
  public class AccountType {
    private int typeId; // primary key
    private String type; // not null, unique
  }
*/

public class AccountType extends Model {
    StringField type = new StringField("type");

    public AccountType() {
        super("AccountType");
        this.type.Option("null_allowed", false);
        this.type.Option("is_unique", true);
        this.addField(type);
    }
}
