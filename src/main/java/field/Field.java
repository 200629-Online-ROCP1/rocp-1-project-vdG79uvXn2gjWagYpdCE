package field;

import java.util.Hashtable;

public class Field {
    protected String fieldName;
    private String fieldType;

    Hashtable<String, Boolean> options = new Hashtable<String, Boolean>();
    
    public Field() {
        super();
    }
    public Field(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        options.put("null_allowed", false);
        options.put("is_unique", true);
    }
    public String toString() {
        String retString = "    " + fieldName + " " + this.fieldType;

        if (options.get("is_unique")==true) {
            retString += " UNIQUE";
        }
        if (options.get("null_allowed")==false) {
            retString += " NOT NULL";
        }
        return retString + ",\n"; 
    }
    public boolean Option(String optionName) {
        return options.get(optionName);
    }
    public boolean Option(String optionName, boolean value) {
        options.put(optionName, value);
        return options.get(optionName);
    }
}