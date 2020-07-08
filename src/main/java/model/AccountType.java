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
        this.type.Option("null_allowed", 0);
        this.type.Option("is_unique", 1);
        this.addField(type);
    }
}
