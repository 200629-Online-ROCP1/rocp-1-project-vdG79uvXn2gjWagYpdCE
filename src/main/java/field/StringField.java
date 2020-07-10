package field;

import java.util.ArrayList;
import java.util.List;

public class StringField extends Field {
    private String value;

    public StringField(String fieldName) {
        super(fieldName, "TEXT");
    }
    public void setValue(String val) {
        this.value = val;
    }
    public String getValue() {
        return this.value;
    }
}