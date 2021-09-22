package step.java.factory;

import org.json.JSONObject;
import step.java.library.Literature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class LiteratureFactory {
    /**
     * Collection of factories for concrete types
     */
    private ArrayList<ConcreteFactory> factories ;

    public LiteratureFactory() {
        factories = new ArrayList<>() ;
        registerFactory( new BookFactory() ) ;
        registerFactory( new JournalFactory() ) ;
        registerFactory( new NewspaperFactory() ) ;
    }

    /**
     * Registerer for concrete factories
     * @param factory the factory
     * @return true if register OK, false if errors or factory already registered
     */
    public boolean registerFactory( ConcreteFactory factory ) {
        if( factories.contains( factory ) ) {
            return false ;
        }
        factories.add( factory ) ;
        return true ;
    }

    /**
     * Creates concrete Literature (Book, Journal,...) from JSON Object
     * @param obj JSON Object with concrete fields
     * @return concrete Literature
     */
    Literature createFrom( JSONObject obj ) {
        for( ConcreteFactory factory : factories ) {
            Literature lit = factory.create( obj ) ;
            if( lit != null )
                return lit ;
        }
        return null ;
    }

    public Literature createFrom( File file ) {
        if( file == null ) return null ;
        if( ! file.exists() ) {
            return null ;
        }
        try( Scanner scanner = new Scanner( file ) ) {
            StringBuilder sb = new StringBuilder() ;
            while( scanner.hasNext() ) {
                sb.append( scanner.nextLine() ) ;
            }
            return this.createFrom( new JSONObject( sb.toString() ) ) ;
        } catch( Exception ex ) {
            System.err.println( ex.getMessage() ) ;
            return null ;
        }
        /* // Low-level reading
        try( InputStream reader = new FileInputStream( file ) ) {
            int sym ;
            StringBuilder sb = new StringBuilder() ;
            while( ( sym = reader.read() ) != -1 ) {
                sb.append( (char) sym ) ;
            }
            return this.createFrom( new JSONObject( sb.toString() ) ) ;
        } catch( Exception ex ) {
            System.err.println( ex.getMessage() ) ;
            return null ;
        }
         */
    }
}
