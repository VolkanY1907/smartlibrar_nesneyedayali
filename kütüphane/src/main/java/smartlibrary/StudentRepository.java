package smartlibrary;

import java.sql.*;
import java.util.ArrayList;

public class StudentRepository {

    public void add(Student student) {
        String sql = "INSERT INTO students(name, department) VALUES(?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            
            pstmt.executeUpdate();
            System.out.println("Öğrenci başarıyla eklendi: " + student.getName());

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public ArrayList<Student> getAll() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return students;
    }

    public Student getById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department")
                );
            }
        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return student;
    }

    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Öğrenci silindi, ID: " + id);

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, department = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setInt(3, student.getId());

            pstmt.executeUpdate();
            System.out.println("Öğrenci güncellendi, ID: " + student.getId());

        } catch (SQLException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}
