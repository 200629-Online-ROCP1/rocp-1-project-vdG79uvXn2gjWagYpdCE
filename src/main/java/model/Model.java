package model;

import java.util.*; 
import field.*;

public class Model {
    private String tableName;
    private ArrayList<field.Field> Fields = new ArrayList<field.Field>(12); 
    private boolean is_saved = false;
    
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

    public String getPrimaryField() {
        return Fields.get(0).FieldName();
    }

    public String createSQL() {
        String retString = "DROP TABLE IF EXISTS " + tableName + ";\n";
        retString += "CREATE TABLE " + tableName + "\n(\n";
        for (int i=0; i<Fields.size(); i++) {
            retString += Fields.get(i);
            if (i==Fields.size()-1) {
                retString += "\n";
            } else {
                retString += ",\n";
            }
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