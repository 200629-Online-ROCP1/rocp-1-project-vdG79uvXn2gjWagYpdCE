package controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import model.Account;
import model.AccountStatus;

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
		Account obj = Account.search(ID);
		if (obj!=null) {
			return obj.toJSON();
		}
		return null;
	}
}
