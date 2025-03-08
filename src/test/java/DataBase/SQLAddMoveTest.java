package DataBase;
import go.game.Database.Move;
import go.game.Database.SQLSaveGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import go.game.Database.JDBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SQLAddMoveTest {
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
    public void testGetIdMove() {
        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        int gameIDMove = sqlSaveGame.getIDMove(sqlSaveGame.getIDGame());
        assertTrue(gameIDMove >= 0);
    }

    @Test
    public void testAddMove() {
        Move move = new Move.Builder(1, 1)
                .x(2)
                .y(2)
                .color("WHITE")
                .build();
        MockSaveGame mockSaveGame = new MockSaveGame();
        mockSaveGame.handle(move);
        List<Move> hypotheticalMoves = mockSaveGame.getHypotheticalMoves();
        assertFalse(hypotheticalMoves.isEmpty());
    }
}
