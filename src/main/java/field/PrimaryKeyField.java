package field;

public class PrimaryKeyField extends IntegerField {
    
    public PrimaryKeyField(String fieldName) {
        super(fieldName);
    }
    
    public String toString() {
        return "    " + fieldName + " serial PRIMARY KEY";
    }
}