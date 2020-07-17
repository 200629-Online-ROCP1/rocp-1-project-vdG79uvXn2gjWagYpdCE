package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {

	static private Map<String, String> ops = new HashMap<String, String>();

	static private void loadOperators() {
		ops.put("eq", "=");
		ops.put("gt", ">");
		ops.put("gte", ">=");
		ops.put("lt", "<");
		ops.put("lte", "<=");
	}

	static public String Where(ArrayList<String> fields, ArrayList<String> operators) {
		loadOperators();
		String retString = "WHERE ";
		for (int i = 0; i < fields.size() - 1; i++) {
			retString += fields.get(i) + ops.get(operators.get(i)) + "? AND ";
		}
		retString += fields.get(fields.size() - 1) + ops.get(operators.get(fields.size() - 1)) + "?";
		return retString;
	}

}