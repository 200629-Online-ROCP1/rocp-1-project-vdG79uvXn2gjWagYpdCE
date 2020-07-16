package controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import dao.AccountTypeDAO;
import model.AccountType;

import javax.servlet.http.HttpServletResponse;

public class AccountTypeAPI {
	public static String list(HttpServletRequest req, HttpServletResponse res) {
//		Set<AccountType> types = AccountType.retrieveAll();
//		String retString = "";
//		for(AccountType type : types) {
//			retString += type.toJSON();
//		}
//		return retString;
		AccountType type = new AccountType("Testing");
		type.save();
		return type.toJSON();
	}
}
