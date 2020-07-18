package controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.AccountStatus;

public class AccountStatusAPI {
	public static String list() {
		ArrayList<AccountStatus> all = AccountStatus.retrieveAll();
		JSONArray jsonall = new JSONArray();
		for (AccountStatus obj : all) {
			jsonall.add(obj.asJSONObject());
		}
		return jsonall.toJSONString();
	}

	public static String detail(int ID) {
		return AccountStatus.search(ID).toJSON();
	}
}
