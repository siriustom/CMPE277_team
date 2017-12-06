package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import db.MongoDBUtil;

/**
 * A Servlet that handles a request for sign up
 */
@WebServlet("/RequestSignUp")
public class RequestSignUp extends HttpServlet {
	private static final MongoClient mongoClient = new MongoClient("54.200.143.121", 27017);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestSignUp() {
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
			// get request parameters for userID and password
			String universityId = request.getParameter("university_id");
			String email = request.getParameter("email");
			MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
			int code = codeGenerate();
			String message = "verification code sent";
			msg.put("status", "OK");
			msg.put("verification_code", code);
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private int codeGenerate() {
		return (int) (Math.random() * 1000000 + 1);
	}

}
