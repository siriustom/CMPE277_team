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
import service.BookCopyService;

/**
 * Servlet handles book catalog creation
 */
@WebServlet("/CreatBookCatalog")
public class CreatBookCatalog extends HttpServlet {
	private final BookCatalogService db;
	private final BookCopyService db2;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatBookCatalog() {
        super();
        db = new BookCatalogService();
        db2 = new BookCopyService();
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
			JSONObject[] copies = (JSONObject[]) input.get("copies");
			String message = "";
			
			if (!db.isBookExisted(title)) {
				//communicate to db
				BookCatalogBuilder builder = new BookCatalogBuilder();
				BookCatalog bc = builder.setAuthor(author).setTitle(title).setCallNumber(Integer.parseInt(callNumber)).
				setPublisher(publisher).setYearOfPublication(Integer.parseInt(yearOfPub)).setLocationInLibrary(location).
				setKeywords(keywords).setLibrarianCreatedUpdated(librarianCreatedUpdated).build();
				List<BookCopy> copylist = new ArrayList<>();
				for (int i = 0; i < copies.length; i++) {
					BookCopy book = new BookCopy(bc);
					//call book copy service API
					db2.add(book);
					copylist.add(book);
				}
				db.add(bc);
				message += "book catalog has been created.";
				msg.put("status", "OK");
			} else {
				message += "book catalog has been already existed.";
				msg.put("status", "error");
			}

			//response
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
