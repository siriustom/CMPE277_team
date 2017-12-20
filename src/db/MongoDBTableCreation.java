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
	    MongoClient mongoClient = new MongoClient("13.56.249.240", 27017);
	    MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);

	    // Step 1: remove old collections.
	    db.getCollection("users").drop();
	    db.getCollection("books").drop();
	    db.getCollection("bookcopies").drop();


	    // Step 2: create new collections
//	    db.getCollection("users")
//	        .insertOne(new Document().append("email", "linxiaoran0210@gmail.com").
//	        		append("universityID", "011824657").append("password", "366462"));

	    // make sure email is unique.
	    IndexOptions indexOptions = new IndexOptions().unique(true);
	    // use 1 for ascending index , -1 for descending index
	    // createIndex let search by "email"
	    db.getCollection("users").createIndex(new Document("email", 1), indexOptions);

	    // make sure book title is unique.
	    // createIndex let search by "title"
	    db.getCollection("books").createIndex(new Document("title", 1), indexOptions);

	    db.getCollection("bookcopies").createIndex(new Document("copyId", 1), indexOptions);

	    mongoClient.close();
	    System.out.println("Collection creation is done successfully.");
	  }
}
