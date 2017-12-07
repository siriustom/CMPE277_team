package entity;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONException;
import org.json.JSONObject;

public class BookCopy{

    private String copyId;
    private String status;
    private String user;
    private String bookCatalog;
    private Date dueDate;
    private Date checkOutDate;
    private int renewCount;

    public BookCopy() {
    	
    }
    
    public BookCopy(BookCatalog bookCatalog) {
    		this.status = "available";
    		this.user = "";
    		this.bookCatalog = bookCatalog.getTitle();
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

    public String getBookCatalog() {
        return bookCatalog;
    }

    public void setBookCatalog(String bookCatalog) {
        this.bookCatalog = bookCatalog;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
            obj.put("copyId", copyId);
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
            obj.put("copyId", copyId);
            obj.put("status", status);
            obj.put("user", user);
            obj.put("bookCatalog", bookCatalog);
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
        this.copyId = String.valueOf(dbObject.get("_id"));
        this.status = String.valueOf(dbObject.get("copyId"));
        this.status = String.valueOf(dbObject.get("status"));
        this.user = String.valueOf(dbObject.get("user"));
        this.renewCount = Integer.parseInt(String.valueOf(dbObject.get("renewCount")));
        if(dbObject.get("dueDate")!=null)
            this.dueDate = new Date(String.valueOf(dbObject.get("dueDate")));
        if(dbObject.get("checkOutDate")!=null)
            this.checkOutDate = new Date(String.valueOf(dbObject.get("checkOutDate")));
        this.bookCatalog = String.valueOf(dbObject.get("bookCatalog"));
//        DBObject catalogObj = (DBObject)dbObject.get("bookCatalog");
//        if(catalogObj!=null){
//
//            this.setBookCatalog(new BookCatalog(catalogObj));
//        }
    }
}
