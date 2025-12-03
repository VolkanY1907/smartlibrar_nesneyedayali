package smartlibrary;

// Ödünç Alma sınıfı - Bir ödünç işlemini temsil eder
public class Loan {
    private int id;
    private int bookId;
    private int studentId;
    private String dateBorrowed;
    private String dateReturned;

    // Parametresiz constructor
    public Loan() {
    }

    // Parametreli constructor
    public Loan(int id, int bookId, int studentId, String dateBorrowed, String dateReturned) {
        this.id = id;
        this.bookId = bookId;
        this.studentId = studentId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }

    // Yeni kayıt için constructor (henüz geri dönmediği için dateReturned null olabilir)
    public Loan(int bookId, int studentId, String dateBorrowed) {
        this.bookId = bookId;
        this.studentId = studentId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = null; // Başlangıçta null
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(String dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    @Override
    public String toString() {
        String status = (dateReturned == null) ? "Ödünçte" : "Teslim Edildi (" + dateReturned + ")";
        return "ID: " + id + " | Kitap ID: " + bookId + " | Öğrenci ID: " + studentId + 
               " | Veriliş: " + dateBorrowed + " | Durum: " + status;
    }
}
