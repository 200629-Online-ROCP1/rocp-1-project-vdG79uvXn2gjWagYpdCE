package controller;

import java.util.ArrayList;
import model.Account;

public class AccountAPI {
	
	public static String list() {
		ArrayList<Account> all = Account.retrieveAll();
		String retString = "[\n";
		for (Account obj : all) { retString += obj.toJSON() + "\n\t"; }
		return retString + "\n]";
	}
	
	public static String detail(int ID) {
		return "[\n"+ Account.search(ID).toJSON() + "\n]";
	}
}
