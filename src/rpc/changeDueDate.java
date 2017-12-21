package rpc;

import java.io.IOException;
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
 * Servlet implementation class changeDueDate
 */
@WebServlet("/changeDueDate")
public class changeDueDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserService db;
	private final BookCatalogService db2;
	private final BookCopyService db3;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeDueDate() {
        super();
        db = new UserService();
        db2 = new BookCatalogService();
        db3 = new BookCopyService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject msg = new JSONObject();
			// get request parameters for book title and update fields
			JSONObject input = RpcHelper.readJsonObject(request);
			String number = (String) input.get("number");
			String email = (String) input.get("email");
			String message = "";
			
			int num = Integer.parseInt(number);
			User user = db.queryById(email);
			List<BookCopy> booklist = user.getBooks();
			for (BookCopy b : booklist) {
				Date oldDue = b.getDueDate();
				Date newDue = new Date(oldDue.getTime() + ( num * 24 * 60 * 60 * 1000));
				b.setDueDate(newDue);
				db3.update(b);
				String title = b.getBookCatalog();
				BookCatalog bc = db2.queryById(title);
				List<BookCopy> catalogList = bc.getCopies();
				for (BookCopy bInCata : catalogList) {
					if (bInCata.getCopyId().equals(b.getCopyId())) {
						bInCata.setDueDate(newDue);
						break;
					}
				}
				bc.setCopies(catalogList);
				db2.update(bc);
			}
			user.setBooks(booklist);
			db.update(user);
			message += "book checkout";
			msg.put("status", "OK");
			msg.put("msg", message);
			
			//response
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
