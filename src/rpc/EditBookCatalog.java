package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import entity.BookCatalog;
import entity.BookCatalog.BookCatalogBuilder;
import entity.BookCopy;
import service.BookCatalogService;
import service.BookCopyService;
import service.UserService;

/**
 * Servlet handles book catalog editing 
 */
@WebServlet("/EditBookCataLog")
public class EditBookCatalog extends HttpServlet {
	private final UserService db;
	private final BookCatalogService db2;
	private final BookCopyService db3;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBookCatalog() {
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
			String author = (String) input.get("author");
			String title = (String) input.get("title");
			String callNumber = (String) input.get("callNumber");
			String publisher = (String) input.get("publisher");
			String yearOfPub = (String) input.get("yearOfPub");
			String location = (String) input.get("location");
			String keywords = (String) input.get("keywords");
			String librarianCreatedUpdated = (String) input.get("librarianCreatedUpdated");
			String email = (String) input.get("email");

			String message = "";
			

			//communicate to db
			BookCatalog bc = db2.queryById(title);
			if (bc.getWaitlist().size() != 0) {
				message += "this book catalog has books that have been checked out.";
				msg.put("status", "error");
			} else if (!email.equals(bc.getLibrarianCreatedUpdated())) {
				message += email + " you are not the librarian who created this catalog";
				msg.put("status", "error");
			} else {
				bc.setAuthor(author);
				bc.setTitle(title);
				bc.setCallNumber(Integer.parseInt(callNumber));
				bc.setPublisher(publisher);
				bc.setYearOfPublication(Integer.parseInt(yearOfPub));
				bc.setLocationInLibrary(location);
				bc.setKeywords(keywords);
				bc.setLibrarianCreatedUpdated(librarianCreatedUpdated);
				db2.update(bc);
				message += "update is complete.";
				msg.put("status", "OK");
			}
			
			//response
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
