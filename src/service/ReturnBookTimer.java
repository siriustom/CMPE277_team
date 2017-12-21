package service;

import entity.BookCopy;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.List;
import java.util.Date;
/**
 * Created by shishi on 12/20/17.
 */
public class ReturnBookTimer extends TimerTask{

    private String copyId;

    private List<Integer> returnDates;

    private int status = 0;

    private Date dueDate;

    private String email;

    public ReturnBookTimer(BookCopy bc){
        this.dueDate = bc.getDueDate();
        this.copyId = bc.getCopyId();
        this.email = bc.getUser();
        initReturnTimes();
    }

    /*
     * Initialization
     * */
    private void initReturnTimes(){
        returnDates = new ArrayList<Integer>();
        Calendar lastDay = Calendar.getInstance();
        lastDay.setTime(dueDate);
        returnDates.add(lastDay.get(Calendar.DAY_OF_MONTH));
        for(int i=0;i<4;i++){

            Calendar cl = getDateBefore(lastDay,i);
            returnDates.add(lastDay.get(Calendar.DAY_OF_MONTH));

        }

    }

    /*
     * run
     * */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if(returnDates.contains(day))
        {

            System.out.println("Sending Notification...");
            sendNotification();
            boolean b = returnDates.remove(new Integer(day));

        }
    }

    public void sendNotification(){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            HttpUtil hu = new HttpUtil();

            JSONObject jsonObj = hu.convertToJson(email,sdf.format(dueDate));
            String result = hu.sendPost( jsonObj);
            System.out.println("Notification has been Sent:"+result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * getdatebefore
     *
     * @param cl
     */
    private Calendar getDateBefore(Calendar cl,int num) {

        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        return cl;
    }


    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
