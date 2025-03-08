package go.game.Database;

import java.io.IOException;
import java.sql.*;

public class SQLAddGame {
    Connection connection = null;

    public int getIDGame() {
        int ID = 0;
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();

            String query = "SELECT COALESCE(MAX(id), 0) FROM games;";
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
        return ID + 1;
    }

    public void addGame() {
        try {
            // Uzyskaj połączenie z bazą danych
            connection = JDBConnector.getConnection();

            String query = "INSERT INTO games (id) VALUES (?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, getIDGame());

                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBConnector.release(null, null, connection);
        }
    }
}
