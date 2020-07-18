package controller;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import model.Role;

public class RoleAPI {
	public static String list() {
		ArrayList<Role> all = Role.retrieveAll();
		JSONArray jsonall = new JSONArray();
		for (Role obj : all) {
			jsonall.add(obj.asJSONObject());
		}
		return jsonall.toJSONString();
	}

	public static String detail(int ID) {
		return "[\n" + Role.search(ID).toJSON() + "\n]";
	}
}
