package service;

import entity.BookCatalog;
import entity.BookCopy;

import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shishi on 12/20/17.
 */
public class NotificationManager {

    private static final long PERIOD = 10 * 1000;// 10 Seconds

    public  Map<String,ReturnBookTimer> timerMap = new ConcurrentHashMap<String,ReturnBookTimer>();

    private static final String taskName = "MainTask";

    private static NotificationManager manager = null;

    Thread taskThread;

    private boolean isRunning = true;

    private NotificationManager(){
        taskThread = new Thread(new ThreadByRunnable(),taskName);
        taskThread.start();

    }

    public static NotificationManager getInstance(){
        if(manager==null){
            manager = new NotificationManager();
        }
        return manager;
    }


    private ReturnBookTimer getTimer(String copyId){
        return timerMap.get(copyId);
    }


    public void registerTask(BookCopy bc){
        ReturnBookTimer timer = new ReturnBookTimer(bc);
        timerMap.put(bc.getCopyId(),timer);
    }

    public void removeTask(String copyId){
        ReturnBookTimer timer = getTimer(copyId);
        if(timer!=null){
            timer.cancel();
            timerMap.remove(timer);
        }

    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public class ThreadByRunnable implements Runnable {

        @Override
        public void run() {

            while(isRunning) {
                System.out.println("Running: " + Thread.currentThread().getName() );

                try {
                    for(Iterator<String> iter = timerMap.keySet().iterator();iter.hasNext();){
                        String key = iter.next();
                        ReturnBookTimer timer = timerMap.get(key);
                        timer.run();
                    }
                    Thread.sleep(PERIOD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
        }


    public static void main(String[] args){
        BookCatalog catalog = new BookCatalog();
        NotificationManager nm = NotificationManager.getInstance();
        catalog.setTitle("Title14");
        catalog.setAuthor("Shihan");
        //for(int i=0;i<3;i++) {
            BookCopy bc = new BookCopy(catalog);
            bc.setDueDate(new java.util.Date());
            bc.setUser("shaozhuo.jia@sjsu.edu");
            nm.registerTask(bc);
        //}
    }

}

