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
public class BookCatalogService {

    private final static String COLLECTION_NAME = "books";
    private MongoDBUtil dbUtil = null;

    public List<BookCatalog> queryAll(){
        List<BookCatalog> catalogs = new ArrayList<BookCatalog>();
        DBCollection collection = getDBCollection();
        DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            DBObject dbObject = cursor.next();
            catalogs.add(new BookCatalog(dbObject));
        }
        destory();
        return catalogs;
    }

    public BookCatalog queryById(String title){
        BookCatalog result = null;
        DBCollection collection = getDBCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("title",title);
        DBObject catalogObj = collection.findOne(query);
        result = new BookCatalog(catalogObj);
        destory();
        return result;
    }

    
    public void add(BookCatalog s){

        DBObject o = s.toDBObject();
        DBCollection collection = getDBCollection();
        //insert 与 save的区别，如果_id已存在，使用insert会报错，使用save，则新的替换旧的
        //	collection.insert(o);
        collection.save(o);
        destory();
    }

    public void update(BookCatalog s){
        DBObject q = new BasicDBObject();
        q.put("title",s.getTitle());

        DBObject o = s.toDBObject();
        DBCollection collection = getDBCollection();
        collection.update(q, o);
        destory();
    }

    public void remove(String title){
        DBCollection collection = getDBCollection();
        DBObject o = new BasicDBObject();
        o.put("title",title);
        collection.remove(o);
        destory();
    }

    private DBCollection getDBCollection(){
        if(dbUtil == null){
            dbUtil = MongoDBUtil.getInstance();
        }
        return dbUtil.getDBConllection(COLLECTION_NAME);
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
        BookCatalogService us = new BookCatalogService();
        BookCatalog catalog = new BookCatalog();
        catalog.setTitle("Title1");
        catalog.setAuthor("Shihan");

        BookCopy bc = new BookCopy();
        bc.setDueDate(new java.util.Date());
        bc.setStatus("Waiting List");
        bc.setBookCatalog(catalog);

        User user = new User();
        user.setUniversity_id("006916370");
        user.setEmail("shihan.wang9@sjsu.edu");
        user.setPassword("123456");
        List<BookCopy> bcList = new ArrayList<BookCopy>();
        bcList.add(bc);
        user.setBooks(bcList);

        //us.add(user);
        //User uss = us.queryById("shihan.wang6@sjsu.edu");

        //System.out.println(uss.getBooks().get(0).getBookCatalog().getTitle());
    }
}
