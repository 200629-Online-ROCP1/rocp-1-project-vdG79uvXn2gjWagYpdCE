package model;

import java.io.*; 
import java.util.*; 
import field.*;

public class Model {
    private String tableName;
    private ArrayList<field.Field> Fields = new ArrayList<field.Field>(12); 
    
    public Model(String tableName) {
        super();
        this.tableName = tableName;
        this.addPKField();
    }

    private void addPKField() {
        PrimaryKeyField pk = new PrimaryKeyField("PK" + tableName);
        Fields.add(pk);
    }

    public String createSQL() {
        String retString = "CREATE TABLE " + tableName + "\n(\n";
        retString += Fields.get(0);
        retString += ");\n\n";
        return retString;
    }

    public void save() {

    }

    public void getByPK(int pk) {

    }

    public void search(String field, String value) {

    }

    public void delete(int pk) {

    }
    
}