package rpc;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import db.MongoDBUtil;
import service.UserService;

/**
 * A Servlet that handles a request for sign up
 */
@WebServlet("/RequestSignUp")
public class RequestSignUp extends HttpServlet {
	private final UserService db;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestSignUp() {
        super();
        	db = new UserService();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			JSONObject msg = new JSONObject();
			
			// get request parameters for universityID and password
			String universityId = request.getParameter("university_id");
			String email = request.getParameter("email");
			
			//generate a random number for verification code
			int code = codeGenerate();
			
			//no need to verify duplicate email, just send back verification code
			String message = "verification code sent";
			msg.put("status", "OK");
			msg.put("verification_code", code);
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
			
			//send email 
			String from = "linxiaoran0210@gmail.com";
			String host = "Server";
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", host);
			Session session = Session.getDefaultInstance(properties);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private int codeGenerate() {
		return (int) (Math.random() * 1000000 + 1);
	}

}
