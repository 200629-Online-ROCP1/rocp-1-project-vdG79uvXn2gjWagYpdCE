package controller;

import java.util.ArrayList;
import model.AccountType;

public class AccountTypeAPI {

	public static String list() {
		ArrayList<AccountType> all = AccountType.retrieveAll();
		String retString = "[\n";
		for (AccountType type : all) {
			retString += type.toJSON() + "\n\t";
		}
		return retString + "\n]";
	}

	public static String detail(int ID) {
		AccountType type = AccountType.search(ID);
		return "[\n" + type.toJSON() + "\n]";
	}
}
