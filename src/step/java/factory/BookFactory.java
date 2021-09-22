package step.java.factory;

import org.json.JSONException;
import org.json.JSONObject;
import step.java.library.Book;
import step.java.library.Literature;

public class BookFactory implements ConcreteFactory {
    @Override
    public Literature create(JSONObject obj) {
        try {
            if( ! "Book".equals( obj.getString( "type" ) ) )
                return null ;

            return new Book(
                    obj.getString("title"),
                    obj.getString("author")
            );
        } catch( JSONException ignored ) {
            return null;
        }
    }

    @Override
    public String getLiteratureType() {
        return "Book";
    }
}
