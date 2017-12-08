package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import service.UserService;

/**
 * Servlet handles book check out
 */
@WebServlet("/CheckOutBook")
public class CheckOutBook extends HttpServlet {
	private final UserService db;
	private final BookCatalogService db2;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutBook() {
        super();
        db = new UserService();
        db2 = new BookCatalogService();
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
			
			//communicate to db
			User user = db.queryById(email);
			BookCatalog bc = db2.queryById(title);
			List<BookCopy> avList = new ArrayList<>();
			for (BookCopy b : bc.getCopies()) {
				if (b.getStatus() == "available") {
					avList.add(b);
				} 
			}
			int num = Integer.parseInt(number);
			if (avList.size() >= num && num <= 3) {
				if (user.getBooks().size() + num <= 9) {
					for (int i = 0; i < num; i++) {
						BookCopy checkout = avList.remove(0);
						checkout.setUser(user.getEmail());
						Date c = new Date();
						Date due = new Date(c.getTime() + (30 * 24 * 60 * 60 * 1000));
						checkout.setCheckOutDate(c);
						checkout.setDueDate(due);
						user.getBooks().add(checkout);
					}
				}
			}
			
			//reponse
			String message = "";
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
