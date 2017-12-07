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
import entity.User;
import service.UserService;

/**
 * Servlet handles sign up process after sign up request
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	
	private final UserService db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        db = new UserService();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		try {
			JSONObject msg = new JSONObject();
			// get request parameters for universityID, email and password
			JSONObject input = RpcHelper.readJsonObject(request);
			String universityId = (String) input.get("university_id");
			String email = (String) input.get("email");
			String password = (String) input.get("password");
			
			//communicate to database
			User user = new User();
			user.setUniversity_id(universityId);
			user.setEmail(email);
			user.setPassword(password);
			db.add(user);
			//write response
			String message = "user has been signed up";
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
