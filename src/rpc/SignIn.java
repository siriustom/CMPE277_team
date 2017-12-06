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
 * Servlet handles sign in
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final MongoClient mongoClient = new MongoClient("54.200.143.121", 27017);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			JSONObject msg = new JSONObject();
			// get request parameters for email and password
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
			String message = "signed in successfully";
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
