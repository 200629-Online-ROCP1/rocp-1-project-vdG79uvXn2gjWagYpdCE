package controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import model.Account;
import model.AccountStatus;
import model.AccountType;

public class AccountTypeAPI {

	public static String list() {
		ArrayList<AccountType> all = AccountType.retrieveAll();
		JSONArray jsonall = new JSONArray();
		for (AccountType obj : all) {
			jsonall.add(obj.asJSONObject());
		}
		return jsonall.toJSONString();
	}

	public static String detail(int ID) {
		AccountType obj = AccountType.search(ID);
		if (obj!=null) {
			return obj.toJSON();
		}
		return "No item found with that ID.";
	}
}
