package entity;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONException;
import org.json.JSONObject;

public class BookCopy{

    private String status;
    private String user;
    private BookCatalog bookCatalog;
    private Date dueDate;
    private Date checkOutDate;
    private int renewCount;

    public BookCopy() {
    	
    }
    
    public BookCopy(BookCatalog bookCatalog) {
    		this.status = "available";
    		this.user = "";
    		this.bookCatalog = bookCatalog;
    		this.renewCount = 3;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }

    public BookCatalog getBookCatalog() {
        return bookCatalog;
    }

    public void setBookCatalog(BookCatalog bookCatalog) {
        this.bookCatalog = bookCatalog;
    }

    public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("status", status);
			obj.put("user", user);
			obj.put("bookCatalog", bookCatalog);
			obj.put("dueDate", dueDate);
			obj.put("checkOutDate", checkOutDate);
			obj.put("renewCount", renewCount);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

    public DBObject toDBObject() {
        DBObject obj = new BasicDBObject();
        try {
            obj.put("status", status);
            obj.put("user", user);
            obj.put("bookCatalog", bookCatalog.toDBObject());
            obj.put("dueDate", dueDate);
            obj.put("checkOutDate", checkOutDate);
            obj.put("renewCount", renewCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public BookCopy(DBObject dbObject) {
        //super();
        this.status = String.valueOf(dbObject.get("status"));
        this.user = String.valueOf(dbObject.get("user"));
        this.renewCount = Integer.parseInt(String.valueOf(dbObject.get("renewCount")));

        //this.dueDate = Integer.parseInt(dbObject.get("renewCount").toString());
        //this.checkOutDate = Integer.parseInt(dbObject.get("renewCount").toString());
        DBObject catalogObj = (DBObject)dbObject.get("bookCatalog");
        if(catalogObj!=null){

            this.setBookCatalog(new BookCatalog(catalogObj));
        }
    }
}
