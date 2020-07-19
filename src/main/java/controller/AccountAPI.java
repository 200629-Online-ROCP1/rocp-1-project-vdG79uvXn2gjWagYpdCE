package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		Account obj = Account.search(ID);
		if (obj!=null) {
			return obj.toJSON();
		}
		return null;
	}
	
	public static String filter(String fieldName, int ID) {
		ArrayList<Account> all = Account.filter(fieldName, ID);
		JSONArray jsonall = new JSONArray();
		for (Account obj : all) {
			jsonall.add(obj.asJSONObject());
		}
		return jsonall.toJSONString();
	}
	
	public static void transaction(int ID, double amount) {
		Account obj = Account.search(ID);
		double balance = Double.parseDouble(obj.getField("balance"));
		Double new_balance = balance + amount;
		Map<String, String> data = new HashMap<String, String>();
		data.put("balance", new_balance.toString());
		obj.setField(data);
		obj.save();
	}
}
