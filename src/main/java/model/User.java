package model;

import field.*;

/* public class User {
  private int userId; // primary key
  private String username; // not null, unique
  private String password; // not null
  private String firstName; // not null
  private String lastName; // not null
  private String email; // not null
  private Role role;
}
*/

public class User extends Model {
    StringField username = new StringField("username");
    StringField password = new StringField("password");
    StringField firstName = new StringField("firstName");
    StringField lastName = new StringField("lastName");
    StringField email = new StringField("email");
    BooleanField deleted = new BooleanField("deleted");
    ForeignKeyField role = new ForeignKeyField("Role");

    public User() {
        super("User");
        this.username.Option("null_allowed", 0);
        this.username.Option("is_unique", 1);
        this.addField(username);
        this.password.Option("null_allowed", 0);
        this.addField(password);
        this.firstName.Option("null_allowed", 0);
        this.addField(firstName);
        this.lastName.Option("null_allowed", 0);
        this.addField(lastName);
        this.email.Option("null_allowed", 0);
        this.addField(email);
        this.addField(role);
    }
}
