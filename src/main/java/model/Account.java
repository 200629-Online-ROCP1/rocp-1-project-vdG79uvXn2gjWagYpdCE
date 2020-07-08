package model;

import field.*;

/* public class Account {
    private int accountId; // primary key
    private double balance;  // not null
    private AccountStatus status;
    private AccountType type;
    private boolean deleted;
    private User owner;
}
*/

public class Account extends Model {
    DoubleField balance = new DoubleField("balance");
    BooleanField deleted = new BooleanField("deleted");
    ForeignKeyField account_status = new ForeignKeyField("AccountStatus");
    ForeignKeyField account_type = new ForeignKeyField("AccountType");
    ForeignKeyField owner = new ForeignKeyField("User");

    public Account() {
        super("Account");
        this.balance.Option("null_allowed", 0);
        this.addField(balance);
        this.addField(deleted);
        this.addField(account_status);
        this.addField(account_type);
        this.addField(owner);
    }
}
