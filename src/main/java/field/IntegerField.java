package field;

import field.IntegerField;

public class IntegerField extends Field {
    public IntegerField(String fieldName) {
        super(fieldName, "INTEGER", false);
    }
    public IntegerField(String fieldName, boolean null_allowed) {
        super(fieldName, "INTEGER", null_allowed);
    }
    
}