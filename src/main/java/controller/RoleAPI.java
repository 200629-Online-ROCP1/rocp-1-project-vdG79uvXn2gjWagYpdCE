package controller;

import java.util.ArrayList;

import model.AccountStatus;
import model.Role;

public class RoleAPI {
	public static String list() {
		ArrayList<Role> all = Role.retrieveAll();
		String retString = "[\n";
		for (Role obj : all) {
			retString += obj.toJSON() + "\n\t";
		}
		return retString + "\n]";
	}

	public static String detail(int ID) {
		return "[\n" + Role.search(ID).toJSON() + "\n]";
	}
}
