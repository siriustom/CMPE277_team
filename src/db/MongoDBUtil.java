package db;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBUtil {
	public static final String DB_NAME = "library";

	private final static String DB_HOST = "54.200.143.121";
	private final static int DB_PORT = 27017;

	private MongoClient mg = null;
	private DB db = null;
	private DBCollection collection= null;

	private MongoDBUtil(){};

	public static MongoDBUtil getInstance(){
		return new MongoDBUtil();
	}


	public DBCollection getDBConllection(String collectionName){
		init();
		collection = db.getCollection(collectionName);
		return collection;
	}
	private  void init(){
		try {
			mg = new MongoClient(DB_HOST,DB_PORT);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("mongo连接失败！");
		}
		db = mg.getDB(DB_NAME);

	}
	public  void destory(){
		if(mg != null)
			mg.close();
		db = null;
		collection = null;
	}
}
