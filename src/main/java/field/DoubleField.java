package field;

public class DoubleField extends Field {
    public DoubleField(String fieldName) {
        super(fieldName, "NUMERIC");
        options.put("precision", 12);
        options.put("scale", 2);
    }
    public String toString() {
        String retString = "    " + fieldName + " " + 
            this.fieldType + " (" + options.get("precision") + "," + options.get("scale") + ")";

        if (options.get("is_unique")==1) {
            retString += " UNIQUE";
        }
        if (options.get("null_allowed")==0) {
            retString += " NOT NULL";
        }
        return retString;
    }
}