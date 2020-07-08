package model;

import field.*;

/* public class Role {
  private int roleId; // primary key (added by default)
  private String role; // not null, unique
*/

public class Role extends Model {
    StringField role = new StringField("role");

    public Role() {
        super("Role");
        this.role.Option("null_allowed", 0);
        this.role.Option("is_unique", 1);
        this.addField(role);
    }
}