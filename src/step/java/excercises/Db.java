package step.java.excercises;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class Db {

    private final String PREFIX = "KH181_0_" ;

    public void demo() {
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
        System.out.println( connectionString ) ;
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
        /*
        query = "CREATE TABLE " + PREFIX + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(64) NOT NULL,"
                + "id_parent RAW(16) )" ;
         */ /*
        query = "INSERT INTO " + PREFIX + "exercise( name ) " +
                "VALUES( 'Petrovich' ) " ;
        */
        /*
        query = "INSERT INTO " + PREFIX + "exercise( name, id_parent ) " +
                "VALUES( 'Lukich', (SELECT id FROM "
                + PREFIX + "exercise WHERE name='Petrovich' ) ) " ;
         */
        /*
        query = String.format(
            "INSERT INTO %sexercise( name, id_parent ) " +
            "VALUES( '%s', (SELECT id FROM %sexercise WHERE name='%s') )",
            PREFIX, "Pavlovna", PREFIX, "Petrovich"
        ) ;
        try( Statement statement = connection.createStatement() ){
            // statement ~ SqlCommand
            statement.executeUpdate( query ) ;
        }
        catch( SQLException ex ) {
            System.err.println( ex.getMessage() + " " + query ) ;
            return ;
        }
         */
        query = "INSERT INTO " + PREFIX + "exercise( name ) VALUES( ? ) " ;
        try( PreparedStatement statement =
                    connection.prepareStatement( query ) ) {
            statement.setString( 1, "Kuzmich" ) ;
            statement.executeUpdate() ;
        }
        catch( SQLException ex ) {
            System.err.println( ex.getMessage() + " " + query ) ;
            return ;
        }
        System.out.println( "OK" ) ;
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