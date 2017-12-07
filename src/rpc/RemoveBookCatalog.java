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
			String message = "";
			
			//communicate with db
			BookCatalog bc = db.queryById(title);
			boolean canRemove = true;
			for (BookCopy b : bc.getCopies()) {
				if (!b.getStatus().equals("available")) {
					canRemove = false;
				}
			}
			if (canRemove) {
				db.remove(title);
				msg.put("status", "OK");
			} else {
				msg.put("status", "error");
				message += "the bookcatalog has some copies that are not available.";
			}
			
			//response
			msg.put("msg", message);
			RpcHelper.writeJsonObject(response, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
