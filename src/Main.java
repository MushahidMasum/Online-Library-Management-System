import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== ONLINE LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book");
            System.out.println("3. Add New Book");
            System.out.println("4. Add Student");
            System.out.println("5. View Students");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
            System.out.println("8. View Issued Books");
            System.out.println("9. View Currently Issued Books");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    Library.viewBooks();
                    break;

                case 2:
                    System.out.print("Enter book title, author or category: ");
                    String keyword = sc.nextLine();

                    Library.searchBook(keyword);
                    break;

                case 3:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter category: ");
                    String category = sc.nextLine();

                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();

                    Library.addBook(title, author, category, quantity);
                    break;

                case 4:
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    Library.addStudent(name, email, phone);
                    break;

                case 5:
                    Library.viewStudents();
                    break;

                case 6:
                    System.out.print("Enter student ID: ");
                    int issueStudentId = sc.nextInt();

                    System.out.print("Enter book ID: ");
                    int issueBookId = sc.nextInt();

                    Library.issueBook(issueStudentId, issueBookId);
                    break;

                case 7:
                    System.out.print("Enter student ID: ");
                    int returnStudentId = sc.nextInt();

                    System.out.print("Enter book ID: ");
                    int returnBookId = sc.nextInt();

                    Library.returnBook(returnStudentId, returnBookId);
                    break;

                case 8:
                    Library.viewIssuedBooks();
                    break;

                case 9:
                    Library.viewCurrentlyIssuedBooks();
                    break;

                case 10:
                    System.out.println(
                        "Thank you for using Library Management System!"
                    );
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}