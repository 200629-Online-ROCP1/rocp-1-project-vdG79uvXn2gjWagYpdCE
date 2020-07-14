package model;

import dao.RoleDAO;
import database.DBConnector;
import field.*;

public class Role {
    private String role = "";
    private boolean saved = false;
    private int primaryKey = 0;

    // Constructors
    public Role() {
      super();
    }
    public Role(String role) {
      super();
      this.role = role;
    }
    public Role(int pk, String role) {
      this(role);
      this.primaryKey = pk;
      this.saved = true;
    }
    public String toString() {
      String retString = new String("PK => " + primaryKey);
      retString += ", " + getField("role");
      if (!saved) {
        retString += " (NOT SAVED)";
      }
      return retString;

    }

    public String getField(String fieldName) {
      return role;
    }
    public String setField(String fieldName, String role) {
      this.role = role;
      this.saved = false;
      return role;
    }
    public int getID() {
      return primaryKey;
    }

    // Database operations - save(insert or update), search, refresh
    public void save() {
      RoleDAO dao = RoleDAO.getInstance(); 
      if (primaryKey > 0) {
        if (dao.update(this)) {
          saved = true;
        }
      } else {
        if (dao.insert(this)) { //Only runs if the insert is successful
          Role tmp = Role.search(role);
          this.primaryKey = tmp.primaryKey;
          saved = true;
        }
      }
    }

    public static Role search(String role) {
      RoleDAO dao = RoleDAO.getInstance(); 
      return dao.search(role);
    }

    public static Role searchID(String ID) {
      RoleDAO dao = RoleDAO.getInstance(); 
      return dao.searchID(ID);
    }

    public static void deleteAll() {
      RoleDAO dao = RoleDAO.getInstance(); 
      dao.delete();
    }
}
