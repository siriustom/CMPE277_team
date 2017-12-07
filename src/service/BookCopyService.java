package service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import db.MongoDBUtil;
import entity.BookCatalog;
import entity.BookCopy;
import entity.BookCopy;
import entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishi on 12/5/17.
 */
public class BookCopyService {

    private final static String COLLECTION_NAME = "bookcopies";
    private MongoDBUtil dbUtil = null;

    public List<BookCopy> queryAll(){
        List<BookCopy> bookCopies = new ArrayList<BookCopy>();
        DBCollection collection = getDBCollection();
        DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            DBObject dbObject = cursor.next();
            bookCopies.add(new BookCopy(dbObject));
        }
        destory();
        return bookCopies;
    }

    public BookCopy queryById(String copyId){
        BookCopy result = null;
        DBCollection collection = getDBCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("copyId",copyId);
        DBObject bookCopyObj = collection.findOne(query);
        result = new BookCopy(bookCopyObj);
        destory();
        return result;
    }


    public void addCopies(List<BookCopy> copyList){
        if(copyList!=null && copyList.size()>0){
            DBCollection collection = getDBCollection();
            for(BookCopy copy:copyList){
                collection.save(copy.toDBObject());
            }
            destory();
        }

    }

    public void add(BookCopy s){

        DBObject o = s.toDBObject();
        DBCollection collection = getDBCollection();
        //insert 与 save的区别，如果_id已存在，使用insert会报错，使用save，则新的替换旧的
        //	collection.insert(o);
        collection.save(o);
        destory();
    }

    public void update(BookCopy s){
        DBObject q = new BasicDBObject();
        q.put("copyId",s.getCopyId());

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
        BookCopyService bcs = new BookCopyService();
        BookCatalog catalog = new BookCatalog();
        catalog.setTitle("Title11");
        catalog.setAuthor("Shihan");

        BookCopy bc = new BookCopy(catalog);
        for(int i=0;i<10;i++)
            catalog.getCopies().add(bc);


        bcs.addCopies(catalog.getCopies());

        System.out.println("Number of Copies: "+bcs.queryAll().size());
    }


}
