package rpc;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		try {
			JSONObject msg = new JSONObject();

			// get request parameters for universityID and password
			JSONObject input = RpcHelper.readJsonObject(request);
			String universityId = (String) input.get("university_id");
			String email = (String) input.get("email");
			int atSign = email.indexOf('@');

			// communicate to db
			List<String> listOfEmail = db.queryByEmail(email);
			if (listOfEmail.size() >= 2) {
				String moreThanTwo = "you cannot have more than two accounts.";
				msg.put("status", "error");
				msg.put("msg", moreThanTwo);
				RpcHelper.writeJsonObject(response, msg);
			} else if (listOfEmail.size() == 0) {
				// generate a random number for verification code
				String code = codeGenerate();

				// no need to verify duplicate email, just send back verification code
				String verifyCode = "verification code sent";
				msg.put("status", "OK");
				msg.put("verification_code", code);
				msg.put("msg", verifyCode);
				RpcHelper.writeJsonObject(response, msg);
				
				//send email
				sendEmail(code, email);
			} else {
				int atSignStore = listOfEmail.get(0).indexOf("@");
				if (email.substring(atSign + 1) == "sjsu.edu" && listOfEmail.get(0).substring(atSignStore + 1) == "sjsu.edu"
						|| email.substring(atSign + 1) != "sjsu.edu"
								&& listOfEmail.get(0).substring(atSignStore + 1) != "sjsu.edu") {
					String moreThanOne = "you cannot have more than one account in certain email domain.";
					msg.put("status", "error");
					msg.put("msg", moreThanOne);
					RpcHelper.writeJsonObject(response, msg);
				} else {
					
					// generate a random number for verification code
					String code = codeGenerate();
					
					// no need to verify duplicate email, just send back verification code
					String verifyCode = "verification code sent";
					msg.put("status", "OK");
					msg.put("verification_code", code);
					msg.put("msg", verifyCode);
					RpcHelper.writeJsonObject(response, msg);
					
					//send email
					sendEmail(code, email);
				}
			} 
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String codeGenerate() {
		return String.valueOf((int) (Math.random() * 1000000 + 1));
	}
	
	private void sendEmail(String code, String to) {
		// send email
		String from = "zeningdeng2@gmail.com";
		String host = "aspmx.l.google.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", 25);
		
		//properties.setProperty("mail.user", "zeningdeng2@gmail.com");
		//properties.setProperty("mail.password", "zdpassword");
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage e = new MimeMessage(session);
			e.setFrom(new InternetAddress(from));
			e.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			e.setSubject("This is validation code!");
			e.setContent("Your code is " + code);
			Transport.send(e);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
