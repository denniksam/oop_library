package step.java.factory;

import org.json.JSONObject;
import org.json.JSONException;
import step.java.library.Journal;
import step.java.library.Literature;

public class JournalFactory implements ConcreteFactory {
    @Override
    public Literature create(JSONObject obj) {
        // obj.get...("field") throws JSONException if no field found
        try {
            if( ! "Journal".equals( obj.getString( "type" ) ) )
                return null ;

            return new Journal(
                    obj.getString("title"),
                    obj.getString("number")
            );
        } catch( JSONException ignored ) {
            return null ;
        }
    }

    @Override
    public String getLiteratureType() {
        return "Journal";
    }
}
