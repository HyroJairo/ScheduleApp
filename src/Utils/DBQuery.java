package Utils;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the DBQuery class
 */
public class DBQuery {

    private static Statement statement;

    //Creating Statement Object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    //Returning Statement Object
    public static Statement getStatement() {
        return statement;
    }

}
