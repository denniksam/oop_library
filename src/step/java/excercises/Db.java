package step.java.excercises;

import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Db {

    private final String PREFIX = "KH181_0_" ;

    public void auth_maria() {
        // User authorization demo
        // 1. Algo: No passwords in DB
        // 1.1. Hash
        // 1.2. Salt
        String str = hash( "123" ) ;
        System.out.println( str ) ;
        System.out.println( str.length() ) ;
    }

    public String hash( String str ) {
        if( str == null ) str = "" ;
        try {
            MessageDigest messageDigest =
                MessageDigest.getInstance( "SHA-1" ) ;
            // MD5 - 128b, SHA-1 - 160b, SHA-256
            byte[] src = str.getBytes() ;
            byte[] res = messageDigest.digest( src ) ;
            // return DatatypeConverter.printHexBinary( res ) ;
            char[] sym = new char[] {
                    '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' } ;
            StringBuilder sb = new StringBuilder() ;
            for( byte b : res ) {
                sb.append( sym[ ( b & 0xF0 ) >> 4 ] ) ;
                sb.append( sym[ b & 0xF ] ) ;
            }
            return sb.toString() ;
        } catch( NoSuchAlgorithmException ex ) {
            System.err.println( ex.getMessage() ) ;
            return null ;
        }
    }


    /**
     * Oracle DB eXpress Edition
     */
    public void demo_xe() {
        // Loading config: ../config/db2.json
        File file = new File( "./src/step/java/config/db2.json") ;
        if( ! file.exists() ) {
            System.err.println( "Config location error" ) ;
            return ;
        }
        JSONObject conf ;
        String connectionString ;
        try {
            byte[] buf = new byte[ (int) file.length() ] ;
            new FileInputStream( file ).read( buf ) ;
            conf = new JSONObject( new String( buf ) ) ;
            connectionString = String.format (
                    "jdbc:oracle:thin:%s/%s@%s:%d:XE",
                    conf.getString( "user" ),
                    conf.getString( "pass" ),
                    conf.getString( "host" ),
                    conf.getInt( "port" )
            ) ;
        } catch( IOException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        // System.out.println( connectionString ) ;
        Connection connection ;  // ~SqlConnection
        try {
            // Registering driver
            DriverManager.registerDriver(
                    // Don't forget add OJDBC8.JAR library
                    new oracle.jdbc.driver.OracleDriver()
            );
            // Connecting...
            connection = DriverManager.getConnection(
                    connectionString
            ) ;
        } catch( SQLException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }

        // Queries
        // ! Single DB - use prefixes
        // Update-Queries: DDL + DML (except SELECT)
        String query ;
        // Read-queries
        query = "SELECT * FROM " + PREFIX + "exercise" ;
        try(    Statement statement = connection.createStatement();
                ResultSet res = statement.executeQuery( query )
        ) {
            while( res.next() ) {
                System.out.printf(
                        "%s\t%s\t%s%n",
                        res.getString( 1 ),
                        res.getString( "name" ),
                        res.getString( 3 )
                ) ;
            }
        } catch( SQLException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        /*
        query = "CREATE TABLE " + PREFIX + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(64) NOT NULL,"
                + "id_parent RAW(16) )" ;
        query = "INSERT INTO " + PREFIX + "exercise( name ) " +
                "VALUES( 'Petrovich' ) " ;
        query = "INSERT INTO " + PREFIX + "exercise( name, id_parent ) " +
                "VALUES( 'Lukich', (SELECT id FROM "
                + PREFIX + "exercise WHERE name='Petrovich' ) ) " ;


        try( Statement statement =
                     connection.createStatement() ) {
            // statement ~ SqlCommand
            statement.executeUpdate( query ) ;
        }
        catch( SQLException ex ) {
            System.err.println( ex.getMessage() + " " + query ) ;
            return ;
        }*/
        /*
        query = String.format(
            "INSERT INTO %sexercise( name, id_parent ) " +
            "VALUES( '%s', (SELECT id FROM %sexercise WHERE name='%s') )",
            PREFIX, "Pavlovna", PREFIX, "Petrovich"
        ) ;


        query = "INSERT INTO " + PREFIX + "exercise( name ) VALUES( ? ) " ;
        try( PreparedStatement statement =
                    connection.prepareStatement( query ) ) {
            statement.setString( 1, "Kuzmich" ) ;
            statement.executeUpdate() ;
        }
        catch( SQLException ex ) {
            System.err.println( ex.getMessage() + " " + query ) ;
            return ;
        }*/
        System.out.println( "Oracle - OK" ) ;
    }

    /**
     * Oracle MariaDB (ex MySQL)
     */
    public void demo_maria() {
        /*
            Preface: 1. Install MySQL/MariaDB
            2. Create Database (CREATE DATABASE J181;)
            3. GRANT ALL PRIVILEGES ON J181.* TO 'user181'@'localhost' IDENTIFIED BY 'pass181' ;
         */
        // Loading config: ../config/db3.json
        File file = new File( "./src/step/java/config/db3.json") ;
        if( ! file.exists() ) {
            System.err.println( "Config location error" ) ;
            return ;
        }
        JSONObject conf ;
        String connectionString ;
        String user, pass ;
        try {
            byte[] buf = new byte[ (int) file.length() ] ;
            new FileInputStream( file ).read( buf ) ;
            conf = new JSONObject( new String( buf ) ) ;
            connectionString = String.format (
                    "jdbc:%s://%s:%d/%s"   // Размещение БД
                    + "?useUnicode=true&characterEncoding=UTF-8"             // Кодировка канала (подключения)
                    + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",   // При проблемах согласования времени
                    conf.getString( "dbms" ),
                    conf.getString( "host" ),
                    conf.getInt( "port" ),
                    conf.getString( "schema" )
            ) ;
            user = conf.getString( "user" ) ;
            pass = conf.getString( "pass" ) ;
        } catch( IOException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        // System.out.println( connectionString ) ;

        // Alternative for driver registering
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" ) ;
        } catch( ClassNotFoundException ex ) {
            System.err.println( "Driver class not found" ) ;
            return ;
        }
        Connection connection ;
        try {
            connection = DriverManager.getConnection(
                    connectionString,
                    user, pass ) ;
        } catch( SQLException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        String query ;
        /*
        query = "CREATE TABLE " + PREFIX + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(64) NOT NULL,"
                + "id_parent RAW(16) )" ;

        query = "CREATE TABLE IF NOT EXISTS exercise( "
                + "id BIGINT PRIMARY KEY , "  // defaults values, not functions
                + "name VARCHAR(64) NOT NULL,"
                + "id_parent BIGINT )";
        query = "INSERT INTO exercise( id, name ) " +
                "VALUES( uuid_short(), 'Petrovich' ) " ;

        // ! The same table for INSERT and SELECT - error
        query = "INSERT INTO exercise VALUES( uuid_short(), 'Lukich', " +
                "(SELECT id FROM exercise WHERE name='Petrovich' ) ) " ;


        // 1. Subquery
        String subquery = "SELECT id FROM exercise WHERE name='Petrovich'" ;
        // 2. Prepared - query with placeholder
        query = "INSERT INTO exercise VALUES( uuid_short(), 'Lukich', ? )" ;

        try( Statement statement = connection.createStatement() ;
             PreparedStatement prep = connection.prepareStatement( query )
        ) {
            ResultSet res = statement.executeQuery( subquery ) ;
            if( res.next() ) {  // next ~ Read - fetch a row
                prep.setString(
                    1,
                    res.getString( 1 )  // ! indexing origin - 1 !
                ) ;
                prep.executeUpdate() ;
            }
        }
        catch( SQLException ex ) {
            System.err.println( ex.getMessage() + " " + query ) ;
            return ;
        }
         */
        // Read-queries
        query = "SELECT * FROM exercise" ;
        try(    Statement statement = connection.createStatement();
                ResultSet res = statement.executeQuery( query )
        ) {
            while( res.next() ) {
                System.out.printf(
                    "%s\t%s\t%s%n",
                    res.getString( 1 ),
                    res.getString( "name" ),
                    res.getString( 3 )
                ) ;
            }
        } catch( SQLException ex ) {
            System.err.println( ex.getMessage() ) ;
            return ;
        }
        System.out.println( "Maria - OK" ) ;
    }
}

/*
    Работа с базами данных
    База Данных - способ организации данных, при котором
        кроме данных создаются связи между ними.
    По видам связей БД классифицируются отдельно.
        Табличные БД (обычные, SQL-БД)
        Сетевые БД (Grid)
        Графовые (GraphQL)
        ООП
    Стандарт SQL довольно старый (74-86). Все СУБД его
        реализуют, а также добавляют свои особенности.
        Это формирует диалекты SQL
        Кроме диалектов разные СУБД вводят свои типы данных
        VARCHAR2(size)  4k ANSI
        NVARCHAR2(size) 4k Localized
        NUMBER(p,s) p - цифр всего, s (scale) - точность (после .)
        RAW(size)  Бинарный (байтовый)
        CLOB  Char LOB (~TEXT)
        NCLOB
    СУБД (DBMS) играют роль сервера. Соответственно,
        требуют подключения. При подключении из "языка
        программирования" требуются специальные драйверы
        (коннекторы).
    После установки подключения ЯП предлагают универсальный
        интерфейс для работы с БД (ADO, PDO, JDBC, ...)

 */