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
			
			//communicate to db
			List<BookCatalog> booklist = db.queryAll();
			
			//response
			String message = "all bookcatalogs has been returned";
			msg.put("status", "OK");
			msg.put("bookcatalog", booklist);
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
