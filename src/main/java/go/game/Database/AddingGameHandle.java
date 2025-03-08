package go.game.Database;

public class AddingGameHandle implements GameHandler{
    private SQLAddGame sqlAddGame;

    public AddingGameHandle(SQLAddGame sqlAddGame) { this.sqlAddGame = sqlAddGame; }
    @Override
    public void handle() {
        sqlAddGame.addGame();
    }
}
