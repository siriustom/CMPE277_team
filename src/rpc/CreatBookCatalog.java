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
import entity.BookCopy;
import entity.BookCatalog.BookCatalogBuilder;
import service.BookCatalogService;

/**
 * Servlet handles book catalog creation
 */
@WebServlet("/CreatBookCatalog")
public class CreatBookCatalog extends HttpServlet {
	private final BookCatalogService db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatBookCatalog() {
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
			String copies = (String) input.get("copies");
			String message = "this book catalog has been created";
			
			//communicate to db
			BookCatalogBuilder builder = new BookCatalogBuilder();
			BookCatalog bc = builder.setAuthor(author).setTitle(title).setCallNumber(Integer.parseInt(callNumber)).
			setPublisher(publisher).setYearOfPublication(Integer.parseInt(yearOfPub)).setLocationInLibrary(location).
			setKeywords(keywords).setCoverImage(coverImage).setLibrarianCreatedUpdated(librarianCreatedUpdated).build();
			List<BookCopy> copylist = new ArrayList<>();
			for (int i = 0; i < Integer.parseInt(copies); i++) {
				copylist.add(new BookCopy(bc));
			}
			db.add(bc);
			
			
			
			//response
			msg.put("status", "OK");
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
