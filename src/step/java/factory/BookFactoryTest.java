package step.java.factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import step.java.library.Book;
import step.java.library.Journal;

public class BookFactoryTest {

    @org.junit.Test
    public void create() {
        JSONObject book = TestSamples.getJsonBook() ;
        JSONObject jrnl = TestSamples.getJsonJournal() ;

        BookFactory factory = new BookFactory() ;
        Assert.assertNull( factory.create( jrnl ) ) ;
        Assert.assertNull( factory.create( TestSamples.getJsonInvalidType() ) ) ;

        Book testBook = (Book) factory.create( book ) ;
        Assert.assertNotNull( testBook ) ;
        Assert.assertEquals(
                book.getString( "author" ),
                testBook.getAuthor()
        ) ;
        Assert.assertEquals(
                book.getString( "title" ),
                testBook.getTitle()
        ) ;
    }

    @org.junit.Test
    public void getLiteratureType() {
        Assert.assertEquals(
                "Book",
                new BookFactory().getLiteratureType()
        );
    }
}