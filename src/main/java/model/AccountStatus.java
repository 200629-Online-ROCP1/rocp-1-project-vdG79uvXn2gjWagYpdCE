package model;

import database.DBConnector;
import field.*;



public class AccountStatus extends Model {
    StringField statusField = new StringField("status");

    public AccountStatus() {
        super("AccountStatus");
        this.statusField.Option("null_allowed", 0);
        this.statusField.Option("is_unique", 1);
        this.addField(statusField);
    }

    public void create(String status) {
      is_saved = false;
      pk = 0;
      this.statusField.setValue(status);
    }

    public int save(DBConnector db) {
      String sql = new String();
      if (this.pk==0) { // There is no database record associated with this object. (INSERT)
        sql = "INSERT INTO accountstatus (status) VALUES (?);";
      } else { // (UPDATE)
        sql = "UPDATE accountstatus SET status=\'" + statusField.getValue() + "\' WHERE accountstatus_id=" + this.pk + ";";
      }
      this.pk = db.execute(sql);
      is_saved = true;
      return this.pk;
    }
}