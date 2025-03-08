package DataBase;

import go.game.Database.GameHandler;

import java.util.ArrayList;
import java.util.List;

public class MockGameAdder implements GameHandler {
    private final List<String> hypotheticalGames;

    public MockGameAdder() {
        this.hypotheticalGames = new ArrayList<>();
    }
    @Override
    public void handle() {
        String hypotheticalGame = "HypotheticalGame";
        hypotheticalGames.add(hypotheticalGame);
    }

    public List<String> getHypotheticalGames() {
        return new ArrayList<>(hypotheticalGames); 
    }
}
