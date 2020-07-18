package controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import model.Account;
import model.AccountHolder;
import model.AccountStatus;

public class AccountHolderAPI {

	public static String list() {
		ArrayList<AccountHolder> all = AccountHolder.retrieveAll();
		JSONArray jsonall = new JSONArray();
		for (AccountHolder obj : all) {
			jsonall.add(obj.asJSONObject());
		}
		return jsonall.toJSONString();
	}

	public static String detail(int ID) {
		AccountHolder obj = AccountHolder.search(ID);
		if (obj!=null) {
			return obj.toJSON();
		}
		return null;
	}

}
