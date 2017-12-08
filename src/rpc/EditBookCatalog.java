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
import service.UserService;

/**
 * Servlet handles book catalog editing 
 */
@WebServlet("/EditBookCataLog")
public class EditBookCatalog extends HttpServlet {
    private final BookCatalogService db;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBookCatalog() {
        super();
        	db = new BookCatalogService();
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
			String coverImage = (String) input.get("coverImage");
			String librarianCreatedUpdated = (String) input.get("librarianCreatedUpdated");

			String message = "update complete: ";
			
			
			//communicate to db
			BookCatalog bc = db.queryById(title);
			bc.setAuthor(author);
			bc.setTitle(title);
			bc.setCallNumber(Integer.parseInt(callNumber));
			bc.setPublisher(publisher);
			bc.setYearOfPublication(Integer.parseInt(yearOfPub));
			bc.setLocationInLibrary(location);
			bc.setKeywords(keywords);
			bc.setCoverImage(coverImage);
			bc.setLibrarianCreatedUpdated(librarianCreatedUpdated);
			db.update(bc);
			
			//response
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
