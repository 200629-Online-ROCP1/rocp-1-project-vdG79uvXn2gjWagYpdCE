package model;

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
        PrimaryKeyField pk = new PrimaryKeyField(tableName + "_id");
        Fields.add(pk);
    }
    protected void addField(field.Field newField) {
        Fields.add(newField);
    }

    public String createSQL() {
        String retString = "CREATE TABLE " + tableName + "\n(\n";
        for (int i=0; i<Fields.size(); i++) {
            retString += Fields.get(i);
        }
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