package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class TestLibrary {
    private Game gameA;
    private Game gameB;
    private Library testLibrary;



    @BeforeEach
    void runBefore() {
    gameA = new Game("Baldur's Gate 3", 60, 54, 80.1);
    gameB = new Game("Grand Theft Auto V", 39.9, 77, 61.2);
    testLibrary = new Library();
}

@Test
void testAddGame() {
    assertEquals(0, testLibrary.getGameList().size());
    testLibrary.addGame(gameA);
    assertEquals(1, testLibrary.getGameList().size());
    assertEquals(gameA, testLibrary.getGameList().get(0));
    testLibrary.addGame(gameB);
    assertEquals(2, testLibrary.getGameList().size());
    assertEquals(gameA, testLibrary.getGameList().get(0));
    assertEquals(gameB, testLibrary.getGameList().get(1));
}


@Test
void testRemoveGame() {
    testLibrary.addGame(gameA);
    testLibrary.addGame(gameB);
    assertEquals(2, testLibrary.getGameList().size());
    testLibrary.removeGame(gameA);
    assertEquals(1, testLibrary.getGameList().size());
    assertEquals(gameB, testLibrary.getGameList().get(0));
    testLibrary.removeGame(gameB);
    assertEquals(0, testLibrary.getGameList().size());
}

@Test
void testPlayGames() {
    testLibrary.addGame(gameA);
    testLibrary.addGame(gameB);
    testLibrary.playGames(gameA, 20);
    assertTrue(gameA.getPlayingStatus());
    assertFalse(gameB.getPlayingStatus());
    assertEquals(20, gameA.getNumUnlockedAchievements());
    assertEquals(0, gameB.getNumUnlockedAchievements());
    testLibrary.playGames(gameB, 50);
    assertTrue(gameB.getPlayingStatus());
    assertEquals(50, gameB.getNumUnlockedAchievements());
}
}
