package controller;

import java.util.ArrayList;
import model.AccountHolder;

public class AccountHolderAPI {

	public static String list() {
		ArrayList<AccountHolder> all = AccountHolder.retrieveAll();
		String retString = "[\n";
		for (AccountHolder obj : all) {
			retString += obj.toJSON() + "\n\t";
		}
		return retString + "\n]";
	}

	public static String detail(int ID) {
		return "[\n" + AccountHolder.search(ID).toJSON() + "\n]";
	}

}
