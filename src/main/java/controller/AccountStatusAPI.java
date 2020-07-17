package controller;

import java.util.ArrayList;
import model.AccountStatus;

public class AccountStatusAPI {
	public static String list() {
		ArrayList<AccountStatus> all = AccountStatus.retrieveAll();
		String retString = "[\n";
		for (AccountStatus obj : all) {
			retString += obj.toJSON() + "\n\t";
		}
		return retString + "\n]";
	}

	public static String detail(int ID) {
		return "[\n" + AccountStatus.search(ID).toJSON() + "\n]";
	}
}
