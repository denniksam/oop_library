package step.java.factory;

import org.json.JSONObject;

public class TestSamples {

    private static JSONObject
        book, journal, invalid, newspaper ;

    public static JSONObject getJsonBook() {
        if( book == null ) {
            book = new JSONObject();
            book.put("type", "Book");
            book.put("author", "Jack London");
            book.put("title", "Martin Eden");
        }
        return book;
    }

    public static JSONObject getJsonJournal() {
        if(journal == null ) {
            journal = new JSONObject();
            journal.put("type", "Journal");
            journal.put("number", "2021, 1");
            journal.put("title", "Quantum Mechanics");
        }
        return journal ;
    }

    public static JSONObject getJsonInvalidType() {
        if( invalid == null ) {
            invalid = new JSONObject();
            invalid.put("type", "Invalid");
            invalid.put("number", "2021, 1");
            invalid.put("title", "Quantum Mechanics");
            invalid.put("author", "Jack London");
        }
        return invalid ;
    }

    public static JSONObject getJsonNewspaper() {
        if( newspaper == null ) {
            newspaper = new JSONObject();
            newspaper.put("type", "Newspaper");
            newspaper.put("date", "2021-09-20");
            newspaper.put("title", "Times");
        }
        return newspaper;
    }
}
