package rpc;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.BookCatalog;
import entity.BookCopy;
import entity.User;


/**
 * A helper class to handle rpc related parsing logics.
 */
public class RpcHelper {
	// Parses a JSONObject from http request.
	public static JSONObject readJsonObject(HttpServletRequest request) {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			reader.close();
			return new JSONObject(jb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Writes a JSONObject to http response.
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Writes a JSONArray to http response.
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) {
		try {
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.print(array);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Converts a list of BookCatalog objects to JSONArray.
	public static JSONArray getBookCatalogJSONArray(List<BookCatalog> bookList) {
		JSONArray result = new JSONArray();
		try {
			for (BookCatalog bc : bookList) {
				result.put(bc.toJSONObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Converts a list of BookCopy objects to JSONArray.
	public static JSONArray getBookCopyJSONArray(List<BookCopy> copyList) {
		JSONArray result = new JSONArray();
		try {
			for (BookCopy copy : copyList) {
				result.put(copy.toJSONObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Converts a list of User objects to JSONArray.
	public static JSONArray getUserJSONArray(List<User> userList) {
		JSONArray result = new JSONArray();
		try {
			for (User user : userList) {
				result.put(user.toJSONObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
