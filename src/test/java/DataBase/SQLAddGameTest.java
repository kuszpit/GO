package DataBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import go.game.Database.JDBConnector;
import go.game.Database.SQLAddGame;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SQLAddGameTest {
    private Connection connection;

    @BeforeEach
    public void setUp() {
        try {
            connection = JDBConnector.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetIdGame() {
        SQLAddGame sqlAddGame = new SQLAddGame();
        int gameId = sqlAddGame.getIDGame();
        assertTrue(gameId >= 0);
    }

    @Test
    public void testAddGame() {
        MockGameAdder mockGameAdder = new MockGameAdder();
        mockGameAdder.handle();
        mockGameAdder.getHypotheticalGames();

        List<String> hypotheticalGames = mockGameAdder.getHypotheticalGames();
        assertNotNull(hypotheticalGames);
        assertTrue(hypotheticalGames.size() > 0);
    }
}
