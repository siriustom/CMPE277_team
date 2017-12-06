package entity;

import java.util.List;
import java.util.Set;

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
	private List<String> waitlist;
	private String librarianCreatedUpdated;
	private BookCopy[] copies;
	
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
	public BookCopy[] getCopies() {
		return copies;
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
}
