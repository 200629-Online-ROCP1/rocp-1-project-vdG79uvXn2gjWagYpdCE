package controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import model.Account;

public class AccountAPI {

	public static String list() {
		ArrayList<Account> all = Account.retrieveAll();
		JSONArray jsonall = new JSONArray();
		for (Account obj : all) {
			jsonall.add(obj.asJSONObject());
		}
		return jsonall.toJSONString();
	}

	public static String detail(int ID) {
		return "[\n" + Account.search(ID).toJSON() + "\n]";
	}
}
