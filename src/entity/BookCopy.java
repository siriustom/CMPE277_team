package entity;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class BookCopy {

    private String status;
    private String user;
    private BookCatalog bookCatalog;
    private Date dueDate;
    private Date checkOutDate;
    private int renewCount;

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
}
