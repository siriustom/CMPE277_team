package entity;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String university_id;
    private String email;
    private String password;
    private List<BookCopy> books;

    public String getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(String university_id) {
        this.university_id = university_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BookCopy> getBooks() {
        return books;
    }

    public void setBooks(List<BookCopy> books) {
        this.books = books;
    }
    
    public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("university_id", university_id);
			obj.put("email", email);
			obj.put("password", password);
			obj.put("books", books);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

    public DBObject toDBObject() {
        DBObject obj = new BasicDBObject();
        try {
            obj.put("university_id", university_id);
            obj.put("email", email);
            obj.put("password", password);
            if(books!=null){
                List<DBObject> objList = new ArrayList<DBObject>();
                for(BookCopy bc:books){
                    objList.add(bc.toDBObject());
                }
                obj.put("books",objList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


}
