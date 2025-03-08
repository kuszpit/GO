package DataBase;

import go.game.Database.JDBConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBConnectorTest {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException, IOException, ClassNotFoundException {
        connection = JDBConnector.getConnection();
        statement = connection.createStatement();
    }

    @AfterEach
    public void tearDown() {
        JDBConnector.release(resultSet, statement, connection);
    }

    @Test
    public void testConnection() {
        assertNotNull(connection);
    }

    @Test
    public void testStatement() {
        assertNotNull(statement);
    }

    @Test
    public void testQuery() throws SQLException {
        resultSet = statement.executeQuery("SELECT 1;");
        assertTrue(resultSet.next());
    }
}
