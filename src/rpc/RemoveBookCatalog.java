package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import entity.BookCatalog;
import entity.BookCopy;
import service.BookCatalogService;
import service.UserService;

/**
 * Servlet handles book catalog removing request
 */
@WebServlet("/RemoveBookCatalog")
public class RemoveBookCatalog extends HttpServlet {
    private final BookCatalogService db;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveBookCatalog() {
        super();
        db = new BookCatalogService();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDelete(request, response);
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject msg = new JSONObject();
			JSONObject input = RpcHelper.readJsonObject(request);
			
			// get request parameters for book title
			String title = (String) input.get("title");
			String email = (String) input.get("email");
			String message = "";
			
			//communicate with db
			BookCatalog bc = db.queryById(title);
			String librarian = bc.getLibrarianCreatedUpdated();
			if (librarian.equals(email)) {
				db.remove(title);
				if (!db.isBookExisted(title)) {
					msg.put("status", "OK");
					message += "the bookcatalog " + title + " does not exist any more.";
				} else {
					msg.put("status", "error");
					message += "the remove process did not succeed for unknown reason.";
				}
			} else {
				message += email + " you are not the librarian who created the bookcatalog.";
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
