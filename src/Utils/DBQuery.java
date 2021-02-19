package Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the DBQuery class
 */
public class DBQuery {

    private static Statement statement;

    /**
     * Creating the statement object
     * @param connection the connection
     * @throws SQLException sql exception
     */
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * Returning the statement object
     * @return statement
     */
    public static Statement getStatement() {
        return statement;
    }

}
