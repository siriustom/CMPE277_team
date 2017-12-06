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

/**
 * Servlet handles sign up process after sign up request
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	
	private static final MongoClient mongoClient = new MongoClient("54.200.143.121", 27017);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject msg = new JSONObject();
			// get request parameters for userID and password
			String universityId = request.getParameter("university_id");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String verifyCode = request.getParameter("verification_code");
			MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
			String message = "user has been signed up";
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
