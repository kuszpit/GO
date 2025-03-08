package DataBase;

import go.game.Database.Move;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
public class MoveTest {

    @Test
    public void moveBuilderTest() {
        int gameId = 1;
        int moveId = 1;
        int x = 2;
        int y =2;
        String color = "BLACK";

        Move move = new Move.Builder(gameId, moveId)
                .x(x)
                .y(y)
                .color(color)
                .build();

        assertNotNull(move);
        assertEquals(gameId, move.getIdGame());
        assertEquals(moveId, move.getIdMove());
        assertEquals(x, move.getX());
        assertEquals(y, move.getY());
        assertEquals(color, move.getColor());
    }
}
