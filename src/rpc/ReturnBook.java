package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import entity.BookCatalog;
import entity.BookCopy;
import entity.User;
import service.BookCatalogService;
import service.BookCopyService;
import service.UserService;

/**
 * Servlet handles book returning
 */
@WebServlet("/ReturnBook")
public class ReturnBook extends HttpServlet {
    private final UserService db;
    private final BookCatalogService db2;
    private final BookCopyService db3;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnBook() {
        super();
        db = new UserService();
        db2 = new BookCatalogService();
        db3 = new BookCopyService();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject msg = new JSONObject();
			// get request parameters for book title and update fields
			JSONObject input = RpcHelper.readJsonObject(request);
			String title = (String) input.get("title");
			String number = (String) input.get("number");
			String email = (String) input.get("email");
			String message = "";
			
			//communicate to db
			User user = db.queryById(email);
			BookCatalog bc = db2.queryById(title);
			List<BookCopy> avList = new ArrayList<>();
			List<BookCopy> unavList = new ArrayList<>();
			for (BookCopy b : bc.getCopies()) {
				if (b.getStatus().equals("available")) {
					avList.add(b);
				} else {
					unavList.add(b);
				}
			}
			int num = Integer.parseInt(number);

			List<BookCopy> userlist = user.getBooks();
			for (int i = 0; i < num; i++) {
				BookCopy re = unavList.remove(0);
				re.setStatus("available");
				re.setUser("");
				re.setCheckOutDate(null);
				re.setDueDate(null);
				avList.add(re);
				//update bookcopy
				db3.update(re);
				//update user's book list
				for (int j = 0; j < userlist.size(); j++) {
					if (re.getBookCatalog().equals(userlist.get(j).getBookCatalog())) {
						userlist.remove(j);
						break;
					}
				}				
			}
			
			//update user
			user.setBooks(userlist);
			db.update(user);
			
			//update book catalog
			avList.addAll(unavList);
			bc.setCopies(avList);
			db2.update(bc);

			String text = "Book: " + title + '\n' +
					"Number: " + num + '\n' +
					"returndate: " + "12/07/2017" + '\n' +
					"duedat: " + "1/19/2018" + '\n' +
					"user: " + email + '\n';
			
			sendEmail(text, email);
			//response
			message += "book returned.";
			msg.put("status", "OK");
			msg.put("msg", message);
			msg.put("title", title);
			msg.put("num", num);
			
			//notify waitlist
			if (!bc.getWaitlist().isEmpty()) {
				String notify = bc.getWaitlist().get(0);
				String t = "book " + title + " is available to you.";
				sendEmail(t, notify);
			}
			
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void sendEmail(String text, String to) {
		// send email
		String from = "zeningdeng2@gmail.com";
		String host = "aspmx.l.google.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "25");
		
		//properties.setProperty("mail.user", "zeningdeng2@gmail.com");
		//properties.setProperty("mail.password", "zdpassword");
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage e = new MimeMessage(session);
			e.setFrom(new InternetAddress(from));
			e.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			e.setSubject("return confirmation:");
			e.setText(text);
			Transport.send(e);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} 
	}

}
