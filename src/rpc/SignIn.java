package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import db.MongoDBUtil;
import service.UserService;

/**
 * Servlet handles sign in
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private final UserService db;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        db = new UserService();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject msg = new JSONObject();
			// get request parameters for email and password
			JSONObject input = RpcHelper.readJsonObject(request);
			String email = (String) input.get("email");
			String password = (String) input.get("password");
			
			//communicate to db
			String message = "";
			if (db.verifySignIn(email, password)) {
				message = "signed in successfully";
				msg.put("status", "OK");
				msg.put("msg", message);
			} else {
				message = "either email or password is wrong.";
				msg.put("status", "error");
				msg.put("msg", message);
			}
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
