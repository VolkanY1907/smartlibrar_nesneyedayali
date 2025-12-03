package smartlibrary;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    // Repository nesneleri (Veritabanı işlemleri için)
    private static BookRepository bookRepo = new BookRepository();
    private static StudentRepository studentRepo = new StudentRepository();
    private static LoanRepository loanRepo = new LoanRepository();
    
    // Kullanıcı girişi için Scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Uygulama başladığında tabloları oluştur
        Database.createNewTable();
        
        System.out.println("=== SmartLibrary Kütüphane Sistemine Hoş Geldiniz ===");

        while (true) {
            System.out.println("\nLütfen bir işlem seçiniz:");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitapları Listele");
            System.out.println("3. Öğrenci Ekle");
            System.out.println("4. Öğrencileri Listele");
            System.out.println("5. Kitap Ödünç Ver");
            System.out.println("6. Ödünç Listesini Görüntüle");
            System.out.println("7. Kitap Geri Teslim Al");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz.");
                continue;
            }

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    listStudents();
                    break;
                case 5:
                    lendBook();
                    break;
                case 6:
                    listLoans();
                    break;
                case 7:
                    returnBook();
                    break;
                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    // 1. Kitap Ekleme
    private static void addBook() {
        System.out.print("Kitap Başlığı: ");
        String title = scanner.nextLine();
        
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        
        System.out.print("Basım Yılı: ");
        int year = 0;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Hatalı yıl girişi.");
            return;
        }

        Book newBook = new Book(title, author, year);
        bookRepo.add(newBook);
    }

    // 2. Kitapları Listeleme
    private static void listBooks() {
        ArrayList<Book> books = bookRepo.getAll();
        if (books.isEmpty()) {
            System.out.println("Kayıtlı kitap yok.");
        } else {
            System.out.println("--- Kitap Listesi ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    // 3. Öğrenci Ekleme
    private static void addStudent() {
        System.out.print("Öğrenci Adı: ");
        String name = scanner.nextLine();
        
        System.out.print("Bölümü: ");
        String department = scanner.nextLine();

        Student newStudent = new Student(name, department);
        studentRepo.add(newStudent);
    }

    // 4. Öğrencileri Listeleme
    private static void listStudents() {
        ArrayList<Student> students = studentRepo.getAll();
        if (students.isEmpty()) {
            System.out.println("Kayıtlı öğrenci yok.");
        } else {
            System.out.println("--- Öğrenci Listesi ---");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    // 5. Kitap Ödünç Verme
    private static void lendBook() {
        // Önce listeleri gösterelim ki kullanıcı ID seçebilsin
        listBooks();
        System.out.print("Ödünç verilecek Kitap ID: ");
        int bookId = 0;
        try {
            bookId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID.");
            return;
        }

        // Kitap zaten ödünçte mi?
        if (loanRepo.isBookBorrowed(bookId)) {
            System.out.println("HATA: Bu kitap şu an başkasında ödünçte! Önce iade edilmeli.");
            return;
        }
        
        // Kitap var mı kontrolü (Basitçe getById ile)
        Book b = bookRepo.getById(bookId);
        if (b == null) {
            System.out.println("HATA: Bu ID ile bir kitap bulunamadı.");
            return;
        }

        listStudents();
        System.out.print("Ödünç alacak Öğrenci ID: ");
        int studentId = 0;
        try {
            studentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID.");
            return;
        }
        
        Student s = studentRepo.getById(studentId);
        if (s == null) {
            System.out.println("HATA: Bu ID ile bir öğrenci bulunamadı.");
            return;
        }

        System.out.print("Ödünç Tarihi (Örn: 2023-10-27): ");
        String date = scanner.nextLine();

        Loan newLoan = new Loan(bookId, studentId, date);
        loanRepo.add(newLoan);
    }

    // 6. Ödünç Listesi
    private static void listLoans() {
        ArrayList<Loan> loans = loanRepo.getAll();
        if (loans.isEmpty()) {
            System.out.println("Ödünç kaydı bulunamadı.");
        } else {
            System.out.println("--- Ödünç Listesi ---");
            for (Loan l : loans) {
                // Detaylı bilgi göstermek için kitap ve öğrenci isimlerini çekebiliriz
                // Ama şimdilik basitçe Loan nesnesini yazdırıyoruz
                Book b = bookRepo.getById(l.getBookId());
                Student s = studentRepo.getById(l.getStudentId());
                
                String bookTitle = (b != null) ? b.getTitle() : "Bilinmeyen Kitap";
                String studentName = (s != null) ? s.getName() : "Bilinmeyen Öğrenci";
                
                System.out.println(l + " -> [" + bookTitle + " - " + studentName + "]");
            }
        }
    }

    // 7. Kitap İade Alma
    private static void returnBook() {
        System.out.print("İade edilen Kitap ID: ");
        int bookId = 0;
        try {
            bookId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID.");
            return;
        }

        System.out.print("İade Tarihi (Örn: 2023-11-01): ");
        String date = scanner.nextLine();

        loanRepo.returnBook(bookId, date);
    }
}
