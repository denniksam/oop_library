package step.java.factory;

import org.json.JSONObject;
import org.junit.Assert;
import step.java.library.Journal;


public class JournalFactoryTest {

    @org.junit.Test
    public void create() {
        JSONObject book = TestSamples.getJsonBook() ;
        JSONObject jrnl = TestSamples.getJsonJournal() ;

        JournalFactory factory = new JournalFactory() ;
        Assert.assertNull( factory.create( book ) ) ;
        Journal journal = (Journal) factory.create( jrnl ) ;
        Assert.assertNotNull( journal ) ;
        Assert.assertEquals(
                journal.getNumber(),
                jrnl.getString( "number" )
        ) ;
        Assert.assertEquals(
                journal.getTitle(),
                jrnl.getString( "title" )
        ) ;
    }

    @org.junit.Test
    public void getLiteratureType() {
        Assert.assertEquals(
                new JournalFactory().getLiteratureType(),
                "Journal"
        );
    }
}