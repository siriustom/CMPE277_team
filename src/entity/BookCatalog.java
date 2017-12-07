package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookCatalog {
	private String author;
	private String title;
	private int callNumber;
	private String publisher;
	private int yearOfPublication;
	private String locationInLibrary;
	private String keywords;
	private String coverImage;
	private List<String> waitlist = new ArrayList<String>();
	private String librarianCreatedUpdated;
	private List<BookCopy> copies = new ArrayList<BookCopy>();
	
	//Getters
	public String getAuthor() {
		return author;
	}
	public String getTitle() {
		return title;
	}
	public int getCallNumber() {
		return callNumber;
	}
	public String getPublisher() {
		return publisher;
	}
	public int getYearOfPublication() {
		return yearOfPublication;
	}
	public String getLocationInLibrary() {
		return locationInLibrary;
	}
	public String getKeywords() {
		return keywords;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public List<String> getWaitlist() {
		return waitlist;
	}
	public String getLibrarianCreatedUpdated() {
		return librarianCreatedUpdated;
	}
	public List<BookCopy> getCopies() {
		return copies;
	}

	public BookCatalog(){}
	
	
	//Setters
	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public void setLocationInLibrary(String locationInLibrary) {
		this.locationInLibrary = locationInLibrary;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public void setWaitlist(List<String> waitlist) {
		this.waitlist = waitlist;
	}

	public void setLibrarianCreatedUpdated(String librarianCreatedUpdated) {
		this.librarianCreatedUpdated = librarianCreatedUpdated;
	}

	public void setCopies(List<BookCopy> copies) {
		this.copies = copies;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("author", author);
			obj.put("title", title);
			obj.put("callNumber", callNumber);
			obj.put("publisher", publisher);
			obj.put("yearOfPublication", yearOfPublication);
			obj.put("locationInLibrary", locationInLibrary);
			obj.put("keywords", keywords);
			obj.put("coverImage", coverImage);
			obj.put("waitlist", waitlist);
			obj.put("librarianCreatedUpdated", librarianCreatedUpdated);
			obj.put("copies", copies);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public DBObject toDBObject() {
		DBObject obj = new BasicDBObject();
		try {
			obj.put("author", author);
			obj.put("title", title);
			obj.put("callNumber", callNumber);
			obj.put("publisher", publisher);
			obj.put("yearOfPublication", yearOfPublication);
			obj.put("locationInLibrary", locationInLibrary);
			obj.put("keywords", keywords);
			obj.put("coverImage", coverImage);
			obj.put("librarianCreatedUpdated", librarianCreatedUpdated);
			if(copies!=null){
				List<DBObject> objList = new ArrayList<DBObject>();
				for(BookCopy bc:copies){
					objList.add(bc.toDBObject());
				}
				obj.put("copies",objList);
			}
			if(waitlist!=null){

				obj.put("waitlist",waitlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public BookCatalog(DBObject dbObject) {
		//super();
		this.author = String.valueOf(dbObject.get("author"));
		this.title = String.valueOf(dbObject.get("title"));
		this.callNumber = Integer.parseInt(String.valueOf(dbObject.get("callNumber")));
		this.publisher = String.valueOf(dbObject.get("publisher"));
		this.yearOfPublication = Integer.parseInt(String.valueOf(dbObject.get("yearOfPublication")));
		this.locationInLibrary = String.valueOf(dbObject.get("locationInLibrary"));
		this.keywords = String.valueOf(dbObject.get("keywords"));
		this.coverImage = String.valueOf(dbObject.get("coverImage"));
		this.librarianCreatedUpdated = String.valueOf(dbObject.get("librarianCreatedUpdated"));

		List<String> waitList = (List<String>)dbObject.get("waitlist");
		if(waitList!=null){

			for(String str:waitList){
				waitlist.add(str);
			}
		}

		List<DBObject> bookObjList = (List<DBObject>)dbObject.get("books");
		if(bookObjList!=null){

			for(DBObject obj:bookObjList){
				BookCopy copy = new BookCopy(obj);
				copies.add(copy);
			}
		}
	}
}
