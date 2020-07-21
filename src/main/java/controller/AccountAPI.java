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
	
	/** Used when a servlet does a withdrawal, deposit, or transfer */
	public static boolean transaction(int ID, double amount) {
		Account obj = Account.search(ID);
//		System.out.println("deleted >> " + obj.getField("deleted"));
//		System.out.println("status >> " + obj.getField("accountstatus"));
		if (!obj.getField("accountstatus").equals("Open")) { return false; }
		if (obj.getField("deleted").equals("true")) { return false; }
		double balance = Double.parseDouble(obj.getField("balance"));
		Double new_balance = balance + amount;
		if (new_balance < 0) { return false; }
		Map<String, String> data = new HashMap<String, String>();
		data.put("balance", new_balance.toString());
		obj.setField(data);
		obj.save();
		return true;
	}
}
