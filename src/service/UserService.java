package service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import db.MongoDBUtil;
import entity.BookCatalog;
import entity.BookCopy;
import entity.User;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishi on 12/5/17.
 */
public class UserService {

    private final static String COLLECTION_NAME = "users";
    private MongoDBUtil dbUtil = null;

    public List<User> queryAll(){
        List<User> Users = new ArrayList<User>();
        DBCollection collection = getDBCollection();
        DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            DBObject dbObject = cursor.next();
            Users.add(new User(dbObject));
        }
        destory();
        return Users;
    }

    public User queryById(String email){
        User result = null;
        DBCollection collection = getDBCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("email",email);
        DBObject userObj = collection.findOne(query);
        result = new User(userObj);
        destory();
        return result;
    }

    public boolean verifySignIn(String email,String password){
        boolean result = false;
        DBCollection collection = getDBCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("email",email);
        query.put("password",password);
        DBObject userObj = collection.findOne(query);
        if(userObj!=null)   result = true;
        destory();
        return result;
    }

    public List<String> queryByEmail(String email){
        List<String> results = new ArrayList<String>();
        DBCollection collection = getDBCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("email",email);
        DBCursor cursor = collection.find(query);
        while(cursor.hasNext()){
            DBObject dbObject = cursor.next();
            results.add(String.valueOf(dbObject.get("email")));
        }
        destory();
        return results;
    }
    
    public void add(User s){

        DBObject o = s.toDBObject();
        DBCollection collection = getDBCollection();
        //insert 与 save的区别，如果_id已存在，使用insert会报错，使用save，则新的替换旧的
        //	collection.insert(o);
        collection.save(o);
        destory();
    }

    public void update(User s){
        DBObject q = new BasicDBObject();
        q.put("email",s.getEmail());

        DBObject o = s.toDBObject();
        DBCollection collection = getDBCollection();
        collection.update(q, o);
        destory();
    }

    public void remove(String email){
        DBCollection collection = getDBCollection();
        DBObject o = new BasicDBObject();
        o.put("email",email);
        collection.remove(o);
        destory();
    }

    private DBCollection getDBCollection(){
        if(dbUtil == null){
            dbUtil = MongoDBUtil.getInstance();
        }
        return dbUtil.getDBConllection(COLLECTION_NAME);
    }

    private User convert(DBObject dbObject){
        if(dbObject==null){
            return null;
        }
        User s = new User();
        s.setUniversity_id(dbObject.get("university_id").toString());
        s.setEmail(dbObject.get("email").toString());
        s.setPassword(dbObject.get("password").toString());
        //s.setBooks(dbObject.get(FIELDS[3]));
        return s;
    }

//    private boolean isUser(DBObject dbObject){
//        for(String field : FIELDS){
//            if(! dbObject.containsField(field)){
//                return false;
//            }
//        }
//        return true;
//    }
    private void destory(){
        if(dbUtil != null)
            dbUtil.destory();
    }

    public static void main(String[] args){
        UserService us = new UserService();
        BookCatalog catalog = new BookCatalog();
        catalog.setTitle("Title1");
        catalog.setAuthor("Shihan");

        BookCopy bc = new BookCopy();
        bc.setDueDate(new java.util.Date());
        bc.setStatus("Waiting List");
        bc.setBookCatalog(catalog);

        User user = new User();
        user.setUniversity_id("006916370");
        user.setEmail("shihan.wang12@sjsu.edu");
        user.setPassword("123456");
        List<BookCopy> bcList = new ArrayList<BookCopy>();
        bcList.add(bc);
        user.setBooks(bcList);

        us.add(user);
        User uss = us.queryById("shihan.wang11@sjsu.edu");
        //System.out.println(us.verifySignIn("shihan.wang6@sjsu.edu","123456"));
        System.out.println(uss.getBooks().get(0).getBookCatalog().getTitle());
    }
}
