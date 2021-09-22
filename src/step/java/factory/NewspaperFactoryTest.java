package step.java.factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import step.java.library.Newspaper;

import static org.junit.Assert.*;

public class NewspaperFactoryTest {

    @Test
    public void create() {
        NewspaperFactory factory = new NewspaperFactory() ;
        Assert.assertNull( factory.create( TestSamples.getJsonBook() ) ) ;
        Assert.assertNull( factory.create( TestSamples.getJsonJournal() ) ) ;
        Assert.assertNull( factory.create( TestSamples.getJsonInvalidType() ) ) ;

        JSONObject json = TestSamples.getJsonNewspaper() ;
        Newspaper obj = (Newspaper) factory.create( json ) ;
        Assert.assertNotNull( obj ) ;
        Assert.assertEquals(
                json.getString( "date" ),
                obj.getDateRaw()
        ) ;
        Assert.assertEquals(
                json.getString( "title" ),
                obj.getTitle()
        ) ;
    }

    @Test
    public void getLiteratureType() {
        Assert.assertEquals(
                "Newspaper",
                new NewspaperFactory().getLiteratureType()
        );
    }
}