package step.java.factory;

import org.json.JSONObject;
import step.java.library.Literature;

/**
 * Interface for concrete factories: BookF, JournalF, etc
 */
public interface ConcreteFactory {
    /**
     * Factory method
     * @param obj JSON object
     * @return concrete object of Literature type
     */
    Literature create(JSONObject obj);

    /**
     * Supported type of Literature
     * @return string type representation
     */
    String getLiteratureType();
}
