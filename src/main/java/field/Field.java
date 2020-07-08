package field;

import java.util.Hashtable;

public class Field {
    protected String fieldName;
    protected String fieldType;

    protected Hashtable<String, Integer> options = new Hashtable<String, Integer>();
    
    public Field() {
        super();
    }
    public Field(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        options.put("null_allowed", 0);
        options.put("is_unique", 1);
    }
    public String toString() {
        String retString = "    " + fieldName + " " + this.fieldType;

        if (options.get("is_unique")==1) {
            retString += " UNIQUE";
        }
        if (options.get("null_allowed")==0) {
            retString += " NOT NULL";
        }
        return retString + ",\n"; 
    }
    public int Option(String optionName) {
        return options.get(optionName);
    }
    public int Option(String optionName, int value) {
        options.put(optionName, value);
        return options.get(optionName);
    }
}