package go.game.Database;

public class AddingMoveHandle implements DataHandler{
    private SQLSaveGame sqlSaveGame;

    public AddingMoveHandle(SQLSaveGame sqlSaveGame) { this.sqlSaveGame = sqlSaveGame; }
    @Override
    public void handle(Move move) {
        sqlSaveGame.addMove(move);
    }
}
