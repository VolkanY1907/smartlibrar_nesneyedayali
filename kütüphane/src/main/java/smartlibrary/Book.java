package smartlibrary;

// Kitap sınıfı - Bir kitabı temsil eder
public class Book {
    private int id;
    private String title;
    private String author;
    private int year;

    // Parametresiz constructor (Yapıcı metod)
    public Book() {
    }

    // Parametreli constructor
    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }
    
    // Yeni kayıt eklerken ID olmadan kullanmak için constructor
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    // Getter ve Setter metodları (Verilere erişmek ve değiştirmek için)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Başlık: " + title + " | Yazar: " + author + " | Yıl: " + year;
    }
}
