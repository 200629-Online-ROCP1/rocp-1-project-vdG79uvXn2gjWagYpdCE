package controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import model.Account;
import model.AccountHolder;

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
		return "[\n" + AccountHolder.search(ID).toJSON() + "\n]";
	}

}
