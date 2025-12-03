package smartlibrary;

import java.sql.*;
import java.util.ArrayList;

public class LoanRepository {

    // Ödünç verme işlemi
    public void add(Loan loan) {
        String sql = "INSERT INTO loans(bookId, studentId, dateBorrowed, dateReturned) VALUES(?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loan.getBookId());
            pstmt.setInt(2, loan.getStudentId());
            pstmt.setString(3, loan.getDateBorrowed());
            pstmt.setString(4, loan.getDateReturned()); // Başlangıçta null olabilir

            pstmt.executeUpdate();
            System.out.println("Kitap ödünç verildi.");

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    // Bir kitabın şu an ödünçte olup olmadığını kontrol eder (Basit kontrol)
    public boolean isBookBorrowed(int bookId) {
        // Kitap ID'si eşleşen ve geri dönüş tarihi NULL olan kayıt var mı?
        String sql = "SELECT count(*) FROM loans WHERE bookId = ? AND dateReturned IS NULL";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Eğer sayım 0'dan büyükse kitap ödünçtedir
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Loan> getAll() {
        ArrayList<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setBookId(rs.getInt("bookId"));
                loan.setStudentId(rs.getInt("studentId"));
                loan.setDateBorrowed(rs.getString("dateBorrowed"));
                loan.setDateReturned(rs.getString("dateReturned"));
                
                loans.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return loans;
    }

    // Kitap iade alma işlemi (Loan kaydını günceller)
    public void returnBook(int bookId, String returnDate) {
        // Kitabı bulup dateReturned alanını güncelliyoruz
        // Sadece iade edilmemiş (dateReturned IS NULL) kaydı güncelliyoruz
        String sql = "UPDATE loans SET dateReturned = ? WHERE bookId = ? AND dateReturned IS NULL";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, returnDate);
            pstmt.setInt(2, bookId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Kitap iadesi başarıyla alındı.");
            } else {
                System.out.println("Bu kitap şu an ödünçte değil veya bulunamadı.");
            }

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
    
    // Genel update metodu (İstenen arayüz için, genelde returnBook kullanılır)
    public void update(Loan loan) {
        String sql = "UPDATE loans SET bookId = ?, studentId = ?, dateBorrowed = ?, dateReturned = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loan.getBookId());
            pstmt.setInt(2, loan.getStudentId());
            pstmt.setString(3, loan.getDateBorrowed());
            pstmt.setString(4, loan.getDateReturned());
            pstmt.setInt(5, loan.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
             System.out.println("Hata: " + e.getMessage());
        }
    }
    
    public void delete(int id) {
         String sql = "DELETE FROM loans WHERE id = ?";
         try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setInt(1, id);
             pstmt.executeUpdate();
         } catch (SQLException e) {
             System.out.println("Hata: " + e.getMessage());
         }
    }
    
    public Loan getById(int id) {
        String sql = "SELECT * FROM loans WHERE id = ?";
        Loan loan = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                loan = new Loan(
                    rs.getInt("id"),
                    rs.getInt("bookId"),
                    rs.getInt("studentId"),
                    rs.getString("dateBorrowed"),
                    rs.getString("dateReturned")
                );
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return loan;
    }
}
