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
				if (b.getStatus() == "available") {
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
				//update bookcopy
				db3.update(re);
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

			
			//response
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
