package entity;

import java.util.Date;

public class BookCopy {

    private String status;
    private User user;
    private Date due_date;
    private Date check_out_date;
    private int renew_count;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        this.check_out_date = check_out_date;
    }

    public int getRenew_count() {
        return renew_count;
    }

    public void setRenew_count(int renew_count) {
        this.renew_count = renew_count;
    }
}
