package DataBase;

import go.game.Database.DataHandler;
import go.game.Database.Move;

import java.util.ArrayList;
import java.util.List;

public class MockSaveGame implements DataHandler {
    private final List<Move> hypotheticalMoves;

    public MockSaveGame() {
        this.hypotheticalMoves = new ArrayList<>();
    }

    @Override
    public void handle(Move move) {
        addHypotheticalMove(move);
    }

    public void addHypotheticalMove(Move move) {
        hypotheticalMoves.add(move);
        System.out.println("Hypothetical move added: " + move);
    }

    public List<Move> getHypotheticalMoves() {
        return hypotheticalMoves;
    }
}
