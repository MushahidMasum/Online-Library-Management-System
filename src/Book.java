public class Book {

    private int bookId;
    private String title;
    private String author;
    private String category;
    private int quantity;
    private int available;

    public Book(int bookId, String title, String author,
                String category, int quantity, int available) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
        this.available = available;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailable() {
        return available;
    }
}