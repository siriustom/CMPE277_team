package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;

import entity.BookCatalog;
import service.BookCatalogService;
import service.UserService;

/**
 * Servlet handles book catalog search
 */
@WebServlet("/search")
public class SearchBookCatalog extends HttpServlet {
	private final BookCatalogService db;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBookCatalog() {
        super();
        db = new BookCatalogService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject msg = new JSONObject();

			// get request parameters for book title
			
			String title = request.getParameter("title");
			String message = "";
			//communicate to db
			if (db.isBookExisted(title)) {
				BookCatalog bookList = db.queryById(title);
				JSONObject bookListJSON = bookList.toJSONObject();
				message += "the bookcatalog has been returned";
				msg.put("status", "OK");
				msg.put("bookcatalog", bookListJSON);
			} else {
				message += "no such bookcatalog for given title.";
				msg.put("status", "error");
			}
			
			//response
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
		
	}

}
