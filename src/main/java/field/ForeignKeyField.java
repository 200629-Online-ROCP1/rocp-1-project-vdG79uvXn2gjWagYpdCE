package field;

public class ForeignKeyField extends IntegerField {
    private String parentTable;

    public ForeignKeyField(String fieldName) {
        super(fieldName);
        this.parentTable = fieldName;
    }
    
    public String toString() {
        return "    " + fieldName + " INTEGER REFERENCES " + 
            parentTable + "(" + parentTable + "_id) ON DELETE RESTRICT";
    }
}