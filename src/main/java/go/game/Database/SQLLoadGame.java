package go.game.Database;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLLoadGame {
    Connection connection = null;

    public Move getMove(int idGame, int idMove) {
        String typeOfMove = null;
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();


            String query = "SELECT type_of_move, x_c, y_c, color FROM moves WHERE id_game = ? AND id_move = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idGame);
                preparedStatement.setInt(2, idMove);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Move move = new Move.Builder(idGame, idMove)
                            .typeOfMove(resultSet.getString(1))
                            .x(Integer.parseInt(resultSet.getString(2)))
                            .y(Integer.parseInt(resultSet.getString(3)))
                            .color(resultSet.getString(4))
                            .build();
                    return move;
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
        return null;
    }

    public String getWinner(int idGame) {
        String winner;
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();


            String query = "SELECT winner FROM games WHERE id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idGame);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    winner = resultSet.getString(1);
                    return winner;
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
        return null;
    }
}
