package step.java.library;

public class Journal
        extends Literature      // extension - inheritance
        implements Printable {  // interface implementation

    private String number ;  // Year, No (2021,1)

    public Journal( String title, String number ) {
        super.setTitle( title ) ;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void print() {
        System.out.printf(
                "Journal: %s (%s)",
                super.getTitle(),
                number
        );
    }
}
