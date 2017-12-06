package entity;

public class User {

    private String university_id;
    private String email;
    private String password;
    private BookCopy[] books;

    public String getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(String university_id) {
        this.university_id = university_id;
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

    public BookCopy[] getBooks() {
        return books;
    }

    public void setBooks(BookCopy[] books) {
        this.books = books;
    }
}
