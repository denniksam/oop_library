package step.java.library;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper
        extends Literature
        implements Printable {

    private Date date ;
    private SimpleDateFormat printDateFormat, parseDateFormat ;

    private void createFormatters() {
        printDateFormat = new SimpleDateFormat( "dd.MM.yyyy" ) ;
        parseDateFormat = new SimpleDateFormat( "yyyy-MM-dd" ) ;
    }

    public Newspaper( String title, Date date ) {
        createFormatters() ;
        super.setTitle( title ) ;
        this.date = date ;
    }

    public Newspaper( String title, String date ) {
        createFormatters() ;
        super.setTitle( title ) ;
        try {
            this.date = parseDateFormat.parse( date ) ;
        } catch( Exception ignored ) {
            this.date = null ;
        }
    }

    public Date getDate() {
        return date;
    }

    public String getDateRaw() {
        return parseDateFormat.format( this.date ) ;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void print() {
        System.out.printf(
                "Newspaper: %s (%s)",
                super.getTitle(),
                printDateFormat.format( this.date )
        );
    }
}
