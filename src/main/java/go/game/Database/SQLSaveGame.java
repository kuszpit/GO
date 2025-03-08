package go.game.Database;

import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class SQLSaveGame {
    Connection connection = null;

    public int getIDGame() {
        int ID = 0;
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();

            String query = "SELECT MAX(id) FROM games;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
        return ID;
    }

    public void addMove(Move move) {
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();

            String query = "INSERT INTO moves (id_game, id_move, type_of_move, x_c, y_c, color) VALUES (?, ?, ?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, move.getIdGame());
                preparedStatement.setInt(2, move.getIdMove());
                preparedStatement.setString(3, move.getTypeOfMove());
                preparedStatement.setInt(4, move.getX());
                preparedStatement.setInt(5, move.getY());
                preparedStatement.setString(6, move.getColor());

                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
    }

    public int getIDMove(int idGame){
        int ID = 0;
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();

            String query = "SELECT COALESCE(MAX(id_move), 0) FROM moves WHERE id_game = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idGame);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
        return ID + 1;
    }

    public void setWinner(Color color, int idGame) {
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();
            String winner = (color == Color.BLACK) ? "BLACK" : "WHITE";
            String query = "UPDATE games SET winner = ? WHERE id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, winner);
                preparedStatement.setInt(2, idGame);

                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
    }
}
