package go.game.Database;

import java.awt.*;

public class LoadMoveHandle{
    SQLLoadGame sqlLoadGame;

    public LoadMoveHandle(SQLLoadGame sqlLoadGame) { this.sqlLoadGame = sqlLoadGame; }
    public Move getMove(int idGame, int idMove) {
        return sqlLoadGame.getMove(idGame, idMove);
    }
}
