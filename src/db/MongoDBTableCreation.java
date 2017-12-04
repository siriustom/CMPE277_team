package db;

import java.text.ParseException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

import db.MongoDBUtil;

//Create tables for MongoDB
public class MongoDBTableCreation {
	// Run as Java application to create MongoDB tables with index.
	  public static void main(String[] args) throws ParseException {
	    MongoClient mongoClient = new MongoClient();
	    MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);

	    // Step 1: remove old tables.
	    db.getCollection("users").drop();
	    db.getCollection("books").drop();

	    // Step 2: create new collections, populate data and create index.
	    db.getCollection("users")
	        .insertOne(new Document().append("email", "linxiaoran0210@gmail.com").append("universityID", "011824657")
	            .append("password", "366462"));


	    mongoClient.close();
	    System.out.println("Collection creation is done successfully.");
	  }
}
