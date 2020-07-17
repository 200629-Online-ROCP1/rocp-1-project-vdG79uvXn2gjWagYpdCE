package controller;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import dao.AccountTypeDAO;
import model.AccountType;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountTypeAPI {
	private static final ObjectMapper wrapper = new ObjectMapper();
	
	public static String list() {
		ArrayList<AccountType> all = AccountType.retrieveAll();
		System.out.println(all);
		try {
			return wrapper.writeValueAsString(all);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
