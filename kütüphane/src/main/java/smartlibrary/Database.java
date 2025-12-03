package smartlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // Veritabanı dosyasının adı
    private static final String DB_URL = "jdbc:sqlite:library.db";

    /**
     * SQLite veritabanına bağlantı kurar.
     * @return Connection nesnesi
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            // Bağlantıyı oluşturuyoruz
            conn = DriverManager.getConnection(DB_URL);
            // System.out.println("Veritabanına bağlantı başarılı.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Gerekli tabloları oluşturur.
     */
    public static void createNewTable() {
        
        // Kitaplar tablosu SQL komutu
        String sqlBooks = "CREATE TABLE IF NOT EXISTS books (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT NOT NULL,\n"
                + " author TEXT NOT NULL,\n"
                + " year INTEGER\n"
                + ");";

        // Öğrenciler tablosu SQL komutu
        String sqlStudents = "CREATE TABLE IF NOT EXISTS students (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL,\n"
                + " department TEXT NOT NULL\n"
                + ");";

        // Ödünç işlemleri tablosu SQL komutu
        String sqlLoans = "CREATE TABLE IF NOT EXISTS loans (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " bookId INTEGER,\n"
                + " studentId INTEGER,\n"
                + " dateBorrowed TEXT,\n"
                + " dateReturned TEXT,\n"
                + " FOREIGN KEY (bookId) REFERENCES books (id),\n"
                + " FOREIGN KEY (studentId) REFERENCES students (id)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            
            // Tabloları sırasıyla oluşturuyoruz
            stmt.execute(sqlBooks);
            stmt.execute(sqlStudents);
            stmt.execute(sqlLoans);
            
            // System.out.println("Tablolar oluşturuldu (veya zaten vardı).");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
