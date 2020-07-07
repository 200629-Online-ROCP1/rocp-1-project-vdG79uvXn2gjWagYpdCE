package field;

public class Field {
    private String fieldName;
    private String fieldType;
    private boolean null_allowed = false;
    
    public Field() {
        super();
    }
    public Field(String fieldName, String fieldType, boolean null_allowed) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.null_allowed = null_allowed;
    }
    public String toString() {
        if (null_allowed==true) {
            return "    " + fieldName + " " + this.fieldType + " NOT NULL ";
        } else {
            return "    " + fieldName + " " + this.fieldType;
        }
    }
}