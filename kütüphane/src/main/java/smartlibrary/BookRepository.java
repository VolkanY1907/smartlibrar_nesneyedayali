package smartlibrary;

import java.sql.*;
import java.util.ArrayList;

public class BookRepository {

    // Kitap ekleme metodu
    public void add(Book book) {
        String sql = "INSERT INTO books(title, author, year) VALUES(?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Soru işaretlerinin yerine değerleri atıyoruz
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYear());
            
            pstmt.executeUpdate(); // Sorguyu çalıştır
            System.out.println("Kitap başarıyla eklendi: " + book.getTitle());
            
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    // Tüm kitapları getirme metodu
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Sonuçları döngüyle geziyoruz
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return books;
    }

    // ID'ye göre kitap getirme
    public Book getById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("year")
                );
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return book;
    }
    
    // Kitap silme (Opsiyonel ama istenmiş)
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Kitap silindi, ID: " + id);

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
    
    // Kitap güncelleme (Opsiyonel ama istenmiş)
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYear());
            pstmt.setInt(4, book.getId());
            
            pstmt.executeUpdate();
            System.out.println("Kitap güncellendi, ID: " + book.getId());

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}
