package servlets;

import servlets.ServletUtils;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import controller.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import model.*;
import utils.JWT;
import io.jsonwebtoken.Claims;

public class APIServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		
		String auth = req.getHeader("authorization");
		String requestOwner;
		String requestOwnerRole;
		int requestOwnerID;
		if (auth==null) {
			res = ServletUtils.sendMessage(res, 401, "Authorization required"); 
			return;
		} else {
			String token = auth.replace("Bearer ", "");
			try {
				Claims claims = JWT.decode(token);
				requestOwner = claims.get("username").toString();
				AccountHolder obj = AccountHolder.search(requestOwner);
				requestOwnerRole = obj.getField("role");
				requestOwnerID = obj.getID(); 
			} catch (MalformedJwtException e) {
				res = ServletUtils.sendMessage(res, 401, "Malformed Token");
				return;
			}
		}
		
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		String[] portions = URI.split("/");
		int ID = 0;

		if (portions.length == 2) {
			try {
				ID = Integer.parseInt(portions[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				res = ServletUtils.sendMessage(res, 400, "The id you provided is not an integer");
				return;
			}
		} else if (portions.length == 3) {  //TODO refactor into switch
			try {
				ID = Integer.parseInt(portions[2]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				res = ServletUtils.sendMessage(res, 400, "The id you provided is not an integer");
				return;
			}
		}

		String results = new String("");
		if (portions[0].equals("users")) {
			if (portions.length == 2) {
				if ((requestOwnerID==ID) ||
					(requestOwnerRole.equals("Admin")) ||
					(requestOwnerRole.equals("Employee"))) 
				{
					results = AccountHolderAPI.detail(ID);
				} else {
					res = ServletUtils.sendMessage(res, 403, "Forbidden");
					return;
				}
			} else {
				if ((requestOwnerRole.equals("Admin"))||(requestOwnerRole.equals("Employee"))) {
					results = AccountHolderAPI.list();
				} else {
					res = ServletUtils.sendMessage(res, 403, "Forbidden");
					return;
				}
			}
		} else if (portions[0].equals("accounts")) {
			Account accountholder = Account.search(ID);
			if (portions.length == 2) {
				if ((requestOwnerID==accountholder.getFKID("accountholder")) ||
					(requestOwnerRole.equals("Admin")) ||
					(requestOwnerRole.equals("Employee"))) {
					results = AccountAPI.detail(ID);
				} else {
					res = ServletUtils.sendMessage(res, 403, "Forbidden");
					return;
				}
			} else if (portions.length == 3) {
				if (portions[1].equals("status")) {
					if ((requestOwnerRole.equals("Admin"))||(requestOwnerRole.equals("Employee"))) {
						results = AccountAPI.filter("accountstatus", ID);
					} else {
						res = ServletUtils.sendMessage(res, 403, "Forbidden");
						return;
					}
				} else if (portions[1].equals("owner")) {
					if ((requestOwnerID==ID) ||
						(requestOwnerRole.equals("Admin")) ||
						(requestOwnerRole.equals("Employee"))) 
					{
						results = AccountAPI.filter("accountholder", ID);
					} else {
						res = ServletUtils.sendMessage(res, 403, "Forbidden");
						return;
					}
				}
				if (results==null) { 
					res = ServletUtils.sendMessage(res, 404, "The id provided was not found");
					return; 
				}
			} else {
				if ((requestOwnerRole.equals("Admin"))||(requestOwnerRole.equals("Employee"))) {
					results = AccountAPI.list();
				} else {
					res = ServletUtils.sendMessage(res, 403, "Forbidden");
					return;
				}
			}
		} else {
			res = ServletUtils.sendMessage(res, 404, "The id provided was not found");
			return;
		}
		res.setStatus(200);
		PrintWriter out = res.getWriter();
		out.print(results);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		
		String auth = req.getHeader("authorization");
		String requestOwner;
		String requestOwnerRole;
		int requestOwnerID;
		if (auth==null) {
			res = ServletUtils.sendMessage(res, 401, "Authorization required"); 
			return;
		} else {
			String token = auth.replace("Bearer ", "");
			try {
				Claims claims = JWT.decode(token);
				requestOwner = claims.get("username").toString();
				AccountHolder obj = AccountHolder.search(requestOwner);
				requestOwnerRole = obj.getField("role");
				requestOwnerID = obj.getID(); 
			} catch (MalformedJwtException e) {
				res = ServletUtils.sendMessage(res, 401, "Malformed Token");
				return;
			}
		}
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = new String(s);
		
		try {
			Object jsonObj = parser.parse(body);
			jsonObject = (JSONObject) jsonObj;
			
		} catch (ParseException e){
			System.out.println("position: " + e.getPosition());
			System.out.println(e);
			res = ServletUtils.sendMessage(res, 400, "The json provided is not parsable");
			return;
		}
		
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		String[] portions = URI.split("/");
		String results = new String("");
		
		if (portions[0].equals("accounts")) {
			AccountHolder new_owner = AccountHolder.search(jsonObject.get("accountholder").toString());
			if ((requestOwnerID==new_owner.getID()) ||
				(requestOwnerRole.equals("Admin")) || 
				(requestOwnerRole.equals("Employee"))
				) {
				ArrayList<String> fields = new ArrayList<String>(
	                    Arrays.asList("balance", "deleted", "accountstatus", "accounttype", "accountholder"));
				Map<String, String> data = new HashMap<String, String>();
				for (String field : fields) {
	                if (jsonObject.containsKey(field)) {
	                	data.put(field, jsonObject.get(field).toString());
	                } else {
	                	res = ServletUtils.sendMessage(res, 400, "The field " + field + " was not provided");
	    				return;
	                }
				}
				
	            try {
	            	Account entry = new Account(data);
	            	entry.save();
	            	results = AccountAPI.detail(entry.getID());
	            } catch (IllegalArgumentException e) {
	            	res = ServletUtils.sendMessage(res, 400, e.toString());
	            	return;
	            }
            
	            if (results==null) {
	            	res = ServletUtils.sendMessage(res, 400, "There was a problem creating your object.");
	            	return; 
	            }
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		} else if (portions[0].equals("users") || portions[0].equals("register")) {
			if (requestOwnerRole.equals("Admin")) {
				ArrayList<String> fields = new ArrayList<String>(
	                    Arrays.asList("username", "password", "firstname", "lastname", "email", "role"));
				Map<String, String> data = new HashMap<String, String>();
				for (String field : fields) {
	                if (jsonObject.containsKey(field)) {
	                	data.put(field, jsonObject.get(field).toString());
	                } else {
	                	res = ServletUtils.sendMessage(res, 400, "The field " + field + " was not provided");
	    				return;
	                }
				}
	            AccountHolder entry = new AccountHolder(data);
	            entry.save();
	            results = AccountHolderAPI.detail(entry.getID());
	            if (results==null) {
	            	res = ServletUtils.sendMessage(res, 400, "There was a problem creating your object.");
	            	return; 
	            }
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		} else if (portions[0].equals("deposit")) {
			Account accountholder = Account.search(Integer.parseInt(jsonObject.get("accountId").toString()));
			if ((requestOwnerID==accountholder.getFKID("accountholder")) || (requestOwnerRole.equals("Admin"))) {
				AccountAPI.transaction(
						Integer.parseInt(jsonObject.get("accountId").toString()), 
						Double.parseDouble(jsonObject.get("amount").toString()) 
						);
				String message = "$" + jsonObject.get("amount").toString() + " has been deposited to Account #" + jsonObject.get("accountId").toString();
				res = ServletUtils.sendMessage(res, 200, message);
				return;
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		} else if (portions[0].equals("withdraw")) {
			Account accountholder = Account.search(Integer.parseInt(jsonObject.get("accountId").toString()));
			if ((requestOwnerID==accountholder.getFKID("accountholder")) || (requestOwnerRole.equals("Admin"))) {
				AccountAPI.transaction(
						Integer.parseInt(jsonObject.get("accountId").toString()), 
						Double.parseDouble(jsonObject.get("amount").toString()) * -1
						);
				String message = "$" + jsonObject.get("amount").toString() + " has been withdrawn from Account #" + jsonObject.get("accountId").toString();
				res = ServletUtils.sendMessage(res, 200, message);
				return;
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		} else if (portions[0].equals("transfer")) {
			if ((requestOwnerID==Integer.parseInt(jsonObject.get("sourceAccountId").toString())) || (requestOwnerRole.equals("Admin"))) {
				String amount = jsonObject.get("amount").toString();
				String source = jsonObject.get("sourceAccountId").toString();
				String target = jsonObject.get("targetAccountId").toString();
				AccountAPI.transaction(Integer.parseInt(source), Double.parseDouble(amount) * -1);
				AccountAPI.transaction(Integer.parseInt(target), Double.parseDouble(amount));
				String message = "$" + amount + " has been transferred from Account #" + source + " to Account #" + target;
				res = ServletUtils.sendMessage(res, 200, message);
				return;
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		}
		
		res.setStatus(201);
		PrintWriter out = res.getWriter();
		out.print(results);
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		
		String auth = req.getHeader("authorization");
		String requestOwner;
		String requestOwnerRole;
		int requestOwnerID;
		if (auth==null) { 
			res = ServletUtils.sendMessage(res, 401, "Authorization required"); 
			return; 
		} else {
			String token = auth.replace("Bearer ", "");
			try {
				Claims claims = JWT.decode(token);
				requestOwner = claims.get("username").toString();
				AccountHolder obj = AccountHolder.search(requestOwner);
				requestOwnerRole = obj.getField("role");
				requestOwnerID = obj.getID(); 
			} catch (MalformedJwtException e) {
				res = ServletUtils.sendMessage(res, 401, "Malformed Token");
				return;
			}
		}
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = new String(s);
		
		try {
			Object jsonObj = parser.parse(body);
			jsonObject = (JSONObject) jsonObj;
			
		} catch (ParseException e){
			res = ServletUtils.sendMessage(res, 400, "The json provided is not parsable");
			return;
		}
		
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		String[] portions = URI.split("/");
		String results = new String("");
		
		if (portions[0].equals("accounts")) {
			if (requestOwnerRole.equals("Admin")) {
				ArrayList<String> fields = new ArrayList<String>(
	                    Arrays.asList("balance", "deleted", "accountstatus", "accounttype", "accountholder"));
				if (!jsonObject.containsKey("account_id")) {
					res = ServletUtils.sendMessage(res, 400, "The field account_id was not provided");
					return;
				}
				int ID = Integer.parseInt(jsonObject.get("account_id").toString());
				Map<String, String> data = new HashMap<String, String>();
				for (String field : fields) {
	                if (jsonObject.containsKey(field)) {
	                	data.put(field, jsonObject.get(field).toString());
	                } else {
	                	res = ServletUtils.sendMessage(res, 400, "The field " + field + " was not provided");
	    				return;
	                }
				}
				
	            try {
	            	Account entry = new Account(ID, data);
	            	entry.save();
	            	results = AccountAPI.detail(entry.getID());
	            } catch (IllegalArgumentException e) {
	            	res = ServletUtils.sendMessage(res, 400, e.toString());
	            	return;
	            }
	            
	            if (results==null) {
	            	res = ServletUtils.sendMessage(res, 400, "There was a problem creating your object.");
	            	return; 
	            }
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		} else if (portions[0].equals("users")) {
			if (
				(requestOwnerRole.equals("Admin")) || 
				(requestOwner.equals(jsonObject.get("username").toString()))
			   ) {
				if (!jsonObject.containsKey("accountholder_id")) {
					res = ServletUtils.sendMessage(res, 400, "The field accountholder_id was not provided");
					return;
				}
				int ID = Integer.parseInt(jsonObject.get("accountholder_id").toString());
				ArrayList<String> fields = new ArrayList<String>(
	                    Arrays.asList("username", "password", "firstname", "lastname", "email", "role"));
				Map<String, String> data = new HashMap<String, String>();
				for (String field : fields) {
	                if (jsonObject.containsKey(field)) {
	                	data.put(field, jsonObject.get(field).toString());
	                } else {
	                	res = ServletUtils.sendMessage(res, 400, "The field " + field + " was not provided");
	    				return;
	                }
				}
				try {
					AccountHolder entry = new AccountHolder(ID, data);
	            	entry.save();
	            	results = AccountHolderAPI.detail(entry.getID());
	            } catch (IllegalArgumentException e) {
	            	res = ServletUtils.sendMessage(res, 400, e.toString());
	            	return;
	            }
	            
				if (results==null) {
					res = ServletUtils.sendMessage(res, 400, "There was a problem creating your object.");
	            	return; 
	            }
			} else {
				res = ServletUtils.sendMessage(res, 403, "Forbidden");
				return;
			}
		}
		
		System.out.println(jsonObject.get("status"));
		res.setStatus(202);
		PrintWriter out = res.getWriter();
		out.print(results);
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		JSONObject jsonObject;
		res = ServletUtils.APISetup(res);
		if (!Authorization.processJWT(req, res)) { return; }
		
//		try {
//			jsonObject = ServletUtils.bodyAsJSON(req, res);
//		} catch (ParseException e){
//			res = ServletUtils.sendMessage(res, 400, "The json provided is not parsable");
//			return;
//		} 
		
		System.out.println(jsonObject.toJSONString());
		res = ServletUtils.sendMessage(res, 200, "You are inside the doDelete");
		return;
	}
}