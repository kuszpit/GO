package Server;

import go.game.ClientServer.GO;
import go.game.frames.SecondFrame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InicializeGame {
    private GO server;

    @BeforeEach
    public void setUp() {
        server = new GO();
    }

    @AfterEach
    public void tearDown() {
        if (server != null) {
            assertTrue(server.isServerRunning());
            server.setServerRunning(false);
        }
    }

    @Test
    public void testInitializeGame() {
        SecondFrame secondFrame = new SecondFrame();
        secondFrame.setGameMode(0);

        assertTrue(server.currentGame != null);
        assertTrue(server.player2Joined);
        assertTrue(server.startGame);
    }
}
