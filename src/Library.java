import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Library {

    public static void viewBooks() {

        String sql = "SELECT * FROM books";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== ALL BOOKS =====");

            while (rs.next()) {

                System.out.println(
                    "ID: " + rs.getInt("book_id") +
                    " | Title: " + rs.getString("title") +
                    " | Author: " + rs.getString("author") +
                    " | Category: " + rs.getString("category") +
                    " | Quantity: " + rs.getInt("quantity") +
                    " | Available: " + rs.getInt("available")
                );
            }

        } catch (Exception e) {
            System.out.println("Error while viewing books!");
            e.printStackTrace();
        }
    }


    public static void searchBook(String keyword) {

        String sql = "SELECT * FROM books " +
                     "WHERE title LIKE ? " +
                     "OR author LIKE ? " +
                     "OR category LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\n===== SEARCH RESULTS =====");

            while (rs.next()) {

                found = true;

                System.out.println(
                    "ID: " + rs.getInt("book_id") +
                    " | Title: " + rs.getString("title") +
                    " | Author: " + rs.getString("author") +
                    " | Category: " + rs.getString("category") +
                    " | Available: " + rs.getInt("available")
                );
            }

            if (!found) {
                System.out.println("No book found!");
            }

        } catch (Exception e) {
            System.out.println("Error while searching book!");
            e.printStackTrace();
        }
    }


    public static void addBook(String title, String author,
                               String category, int quantity) {

        String sql = "INSERT INTO books " +
                     "(title, author, category, quantity, available) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, quantity);
            ps.setInt(5, quantity);

            ps.executeUpdate();

            System.out.println("Book added successfully!");

        } catch (Exception e) {
            System.out.println("Error while adding book!");
            e.printStackTrace();
        }
    }


    public static void issueBook(int studentId, int bookId) {

        String sql = "CALL issue_book(?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("message"));
            }

        } catch (Exception e) {
            System.out.println("Error while issuing book!");
            e.printStackTrace();
        }
    }


    public static void returnBook(int studentId, int bookId) {

        String sql = "CALL return_book(?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("message"));
            }

        } catch (Exception e) {
            System.out.println("Error while returning book!");
            e.printStackTrace();
        }
    }


    public static void viewIssuedBooks() {

        String sql = "SELECT " +
                     "students.name AS student_name, " +
                     "books.title AS book_name, " +
                     "issued_books.issue_date, " +
                     "issued_books.return_date " +
                     "FROM issued_books " +
                     "JOIN students ON issued_books.student_id = students.student_id " +
                     "JOIN books ON issued_books.book_id = books.book_id " +
                     "ORDER BY issued_books.issue_date DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== ISSUED BOOKS =====");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                    "Student: " + rs.getString("student_name") +
                    " | Book: " + rs.getString("book_name") +
                    " | Issue Date: " + rs.getDate("issue_date") +
                    " | Return Date: " + rs.getDate("return_date")
                );
            }

            if (!found) {
                System.out.println("No issued books found!");
            }

        } catch (Exception e) {
            System.out.println("Error while viewing issued books!");
            e.printStackTrace();
        }
    }


    // View only currently issued books
    public static void viewCurrentlyIssuedBooks() {

        String sql = "SELECT " +
                     "students.name AS student_name, " +
                     "books.title AS book_name, " +
                     "issued_books.issue_date " +
                     "FROM issued_books " +
                     "JOIN students ON issued_books.student_id = students.student_id " +
                     "JOIN books ON issued_books.book_id = books.book_id " +
                     "WHERE issued_books.return_date IS NULL " +
                     "ORDER BY issued_books.issue_date";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== CURRENTLY ISSUED BOOKS =====");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                    "Student: " + rs.getString("student_name") +
                    " | Book: " + rs.getString("book_name") +
                    " | Issue Date: " + rs.getDate("issue_date")
                );
            }

            if (!found) {
                System.out.println("No books are currently issued!");
            }

        } catch (Exception e) {
            System.out.println("Error while viewing currently issued books!");
            e.printStackTrace();
        }
    }


    public static void addStudent(String name, String email, String phone) {

        String sql = "INSERT INTO students (name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            System.out.println("Student added successfully!");

        } catch (Exception e) {
            System.out.println("Error while adding student!");
            e.printStackTrace();
        }
    }


    public static void viewStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== ALL STUDENTS =====");

            while (rs.next()) {

                System.out.println(
                    "ID: " + rs.getInt("student_id") +
                    " | Name: " + rs.getString("name") +
                    " | Email: " + rs.getString("email") +
                    " | Phone: " + rs.getString("phone")
                );
            }

        } catch (Exception e) {
            System.out.println("Error while viewing students!");
            e.printStackTrace();
        }
    }
}