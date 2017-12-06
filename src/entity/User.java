package entity;

public class User {

    private String universityid;
    private String email;
    private String password;
    private BookCopy[] copies;

    public String getUniversityid() {
        return universityid;
    }

    public void setUniversityid(String universityid) {
        this.universityid = universityid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BookCopy[] getCopies() {
        return copies;
    }

    public void setCopies(BookCopy[] copies) {
        this.copies = copies;
    }
}
