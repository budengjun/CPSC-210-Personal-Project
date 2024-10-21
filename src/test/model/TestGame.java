package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGame {
    private Game gameA;



    @BeforeEach
    void runBefore() {
        gameA = new Game("Baldur's Gate 3", 60, 54);
    }

    @Test
    void testConstructor() {
        assertEquals("Baldur's Gate 3", gameA.getName());
        assertEquals(60, gameA.getPrice());
        assertEquals(54, gameA.getNumAchievements());
        assertEquals(0, gameA.getNumUnlockedAchievements());
        assertFalse(gameA.getPlayingStatus());
    }

    @Test
    void testMarkedAsNotPlayed() {
        gameA.markAsNotPlayed();
        assertFalse(gameA.getPlayingStatus());
    }

    @Test
    void testMarkedAsPlayed() {
        gameA.markAsPlayed();
        assertTrue(gameA.getPlayingStatus());
    }

    @Test
    void testPlayGame() {
        gameA.playGame(5);
        assertEquals(5, gameA.getNumUnlockedAchievements());
        gameA.playGame(49);
        assertEquals(54, gameA.getNumUnlockedAchievements());
        gameA.playGame(1);
        assertEquals(54, gameA.getNumUnlockedAchievements());

    }
}
