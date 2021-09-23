package step.java.library;

import step.java.factory.LiteratureFactory;

import java.io.File;
import java.util.ArrayList;

public class Library {
    // Aggregation - collection of ...
    private ArrayList<Literature> funds ;   // Funds
    // = new ArrayList<>() ;  // Not recommend: code/declaration mixing

    private int n ;  // threads counter
    private final Object mutex ;

    public Library() {
        funds = new ArrayList<>() ;  // OK
        mutex = new Object() ;
    }

    public void add( Literature lit ) {
        funds.add( lit ) ;
    }

    /**
     * Scan directory for json files and try to add  them to funds
     * @param dirname directory path
     */
    public void addDirectory( String dirname ) {
        String tag = "Library.addDirectory " ;
        if( dirname == null ) {
            System.err.println( tag + "no directory" ) ;
            return ;
        }
        File dir = new File( dirname ) ;
        if( ! dir.isDirectory() ) {
            System.err.println( tag + "path is not directory" ) ;
            return ;
        }
        LiteratureFactory literatureFactory = new LiteratureFactory() ;

        for( File file : dir.listFiles() ) {
            Literature lit = literatureFactory.createFrom( file ) ;
            System.out.print( file.getName() + " " ) ;
            if( lit == null ) {
                System.out.println( "ignored" ) ;
            } else {
                this.add( lit ) ;
                System.out.println( "added" ) ;
            }
        }

    }

    /**
     * addDirectoryAsync - Async version
     * @param dirname directory path
     * @param then callback after finish
     */
    public void addDirectoryAsync( String dirname, Runnable then ) {
        String tag = "Library.addDirectory " ;
        if( dirname == null ) {
            System.err.println( tag + "no directory" ) ;
            return ;
        }
        File dir = new File( dirname ) ;
        if( ! dir.isDirectory() ) {
            System.err.println( tag + "path is not directory" ) ;
            return ;
        }
        LiteratureFactory literatureFactory = new LiteratureFactory() ;

        // Задача: реализовать работу с файлами асинхронно
        n = 0 ;  // clear counter
        for( File file : dir.listFiles() ) {
            n++ ;  // inc threads counter
            new Thread( () -> {
                try {
                    Literature lit = literatureFactory.createFrom(file);

                    System.out.print(file.getName() + " ");
                    if (lit == null) {
                        System.out.println("ignored");
                    } else {
                        this.add(lit);
                        System.out.println("added");
                    }
                } finally {
                    synchronized( mutex ) {
                        n-- ;
                        if( n == 0 ) {
                            new Thread( then ).start() ;
                        }
                    }
                }
            } ) .start() ;
        }
    }

    public void print() {
        /* for( Literature lit : funds ) {
            lit.print() ;
            System.out.println() ;  // new line
        } */
        // print() seems to be unnecessary in Literature class
        /*
        for( Literature lit : funds ) {
            if( lit instanceof Book ) {
                ( (Book) lit ).print() ;
            }
        }  // reduces scalability - we should add code for each new class
         */
        // The problem: some funds are printable, some aren't
        //  we want to add Holograms
        for( Literature lit : funds ) {
            if( lit instanceof Printable ) {
                ( (Printable) lit).print();
                System.out.println() ;  // new line
            }
        }
    }
}
