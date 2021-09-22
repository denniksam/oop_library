package step.java.library;

public class Book
        extends Literature      // extension - inheritance
        implements Printable {  // interface implementation

    // Book( "Martin Eden", "Jack London" )
    private String author ;

    public Book( String title, String author ) {
        // this.title - error, no access
        // this.setTitle( title ) ;  // warning
        super.setTitle( title ) ;  // OK
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void print() {
        System.out.printf(
                "Book: %s (by %s)",
                super.getTitle(),
                author
        );
    }
}
