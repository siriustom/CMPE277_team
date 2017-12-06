package service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import db.MongoDBUtil;
import entity.BookCatalog;
import entity.BookCopy;
import entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishi on 12/5/17.
 */
public class UserService {

    private final static String COLLECTION_NAME = "users";
    private final static String[] FIELDS = new String[]{"university_id","email","password","books"};
    private MongoDBUtil dbUtil = null;

    public List<User> queryAll(){
        List<User> Users = new ArrayList<User>();
        DBCollection collection = getDBCollection();
        DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            DBObject dbObject = cursor.next();
            Users.add(convert(dbObject));
        }
        destory();
        return Users;
    }

    public void add(User s){
        DBObject o = new BasicDBObject();
        o.put(FIELDS[0],s.getUniversity_id());
        o.put(FIELDS[1], s.getEmail());
        o.put(FIELDS[2], s.getPassword());


        List<DBObject> objList = new ArrayList<DBObject>();
        if(s.getBooks()!=null) {
            for (BookCopy bc : s.getBooks()) {
                DBObject obj = new BasicDBObject();
                obj.put("status", bc.getStatus());
                obj.put("user", bc.getUser());
                obj.put("status", bc.getStatus());
                obj.put("dueDate", bc.getDueDate());
                obj.put("checkOutDate", bc.getCheckOutDate());
                objList.add(obj);
            }

            o.put(FIELDS[3], objList);
        }
        DBCollection collection = getDBCollection();
        //insert 与 save的区别，如果_id已存在，使用insert会报错，使用save，则新的替换旧的
        //	collection.insert(o);
        collection.save(o);
        destory();
    }

    public void update(User s){
        DBObject q = new BasicDBObject();
        q.put(FIELDS[0],s.getUniversity_id());

        DBObject o = new BasicDBObject();
        o.put(FIELDS[1], s.getEmail());
        o.put(FIELDS[2], s.getPassword());
        o.put(FIELDS[3], s.getBooks());

        DBCollection collection = getDBCollection();
        collection.update(q, o);
        destory();
    }

    public void remove(int id){
        DBCollection collection = getDBCollection();
        DBObject o = new BasicDBObject();
        o.put(FIELDS[0], id);
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
        if(! isUser(dbObject)){
            return null;
        }
        User s = new User();
        s.setUniversity_id(dbObject.get(FIELDS[0]).toString());
        s.setEmail(dbObject.get(FIELDS[1]).toString());
        s.setPassword(dbObject.get(FIELDS[2]).toString());
        //s.setBooks(dbObject.get(FIELDS[3]));
        return s;
    }

    private boolean isUser(DBObject dbObject){
        for(String field : FIELDS){
            if(! dbObject.containsField(field)){
                return false;
            }
        }
        return true;
    }
    private void destory(){
        if(dbUtil != null)
            dbUtil.destory();
    }

    public static void main(String[] args){
        UserService us = new UserService();
        BookCatalog catalog = new BookCatalog();
        catalog.setAuthor("shihan");

        BookCopy bc = new BookCopy();
        bc.setDueDate(new java.util.Date());
        bc.setStatus("Waiting List");
        User user = new User();
        user.setUniversity_id("006916367");
        user.setEmail("shihan.wang2@sjsu.edu");
        user.setPassword("123456");



//        List<BookCopy> bcList = new ArrayList<BookCopy>();
//        bcList.add(bc);
//        user.setBooks(bcList);
        us.add(user);


        System.out.println("Number of Users:"+us.queryAll().size());
    }
}
