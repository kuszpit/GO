package Logic;

import go.game.logic.DefaultLogicStrategy;
import go.game.logic.Logic;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DefaultLogicStrategyTest {

    @Test
    void countBreath() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 1,Color.WHITE);
        logic.updateBoard(2, 2, Color.WHITE);
        logic.updateBoard( 1, 2, Color.BLACK);

        int breath = logic.countBreath(1, 2);
        assertEquals(2, breath);
    }

    @Test
    void countBreathHypothetical() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 1,Color.WHITE);
        logic.updateBoard(2, 2, Color.WHITE);

        int breath = logic.countBreathHypothetical(1, 2, Color.BLACK);
        assertEquals(2, breath);
    }

    @Test
    void countBreathChain() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 1,Color.WHITE);
        logic.updateBoard(2, 2, Color.WHITE);
        logic.updateBoard( 1, 2, Color.WHITE);

        int breath = logic.countBreath(1, 2);
        assertEquals(7, breath);
    }

    @Test
    void getUpdateTest() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 1,Color.WHITE);
        logic.updateBoard(2, 2, Color.WHITE);
        logic.updateBoard( 1, 2, Color.WHITE);

        assertEquals('W', logic.getElement(1, 1));
    }

    @Test
    void ifAlreadyOccupiedTest() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 1,Color.WHITE);
        logic.updateBoard(2, 2, Color.WHITE);
        logic.updateBoard( 1, 2, Color.WHITE);

        assertFalse(logic.ifAlreadyOccupied(1, 2));
        assertTrue(logic.ifAlreadyOccupied(2, 3));
    }

    @Test
    void getElementTest() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 1,Color.WHITE);
        logic.updateBoard(2, 2, Color.BLACK);

        assertEquals('W', logic.getElement(1, 1));
        assertEquals('B', logic.getElement(2,2));
        assertEquals(' ', logic.getElement(3,3));
    }

    @Test
    void checkRemoveStonesTest() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 2,Color.WHITE);
        logic.updateBoard(1, 1, Color.BLACK);
        logic.updateBoard(2, 1, Color.WHITE);
        logic.updateBoard( 1, 2, Color.WHITE);

        assertFalse(logic.checkRemoveStones(Color.WHITE, 0, 1));
    }

    @Test
    void moveBotTest() {
        Logic logic = new Logic(new DefaultLogicStrategy());
        logic.updateBoard(1, 2,Color.WHITE);
        logic.updateBoard(1, 1, Color.BLACK);
        logic.updateBoard(2, 1, Color.WHITE);
        logic.updateBoard( 1, 2, Color.WHITE);

        int[] move = logic.moveBot();
        assertEquals(0, move[0]);
        assertEquals(1, move[1]);
    }
}

