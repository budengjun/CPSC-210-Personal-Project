package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class TestLibrary {
    private Game gameA;
    private Game gameB;
    private Library testLibrary;
    private List<Game> gameList;



    @BeforeEach
    void runBefore() {
    gameA = new Game("Baldur's Gate 3", 60, 54);
    gameB = new Game("Grand Theft Auto V", 39.9, 77);
    testLibrary = new Library();
    gameList = new ArrayList<>();
}

@Test
void testAddGame() {
    assertEquals(0, gameList.size());
    testLibrary.addGame(gameA);
    assertEquals(1, gameList.size());
    assertEquals(gameA, gameList.get(0));
    testLibrary.addGame(gameB);
    assertEquals(2, gameList.size());
    assertEquals(gameA, gameList.get(0));
    assertEquals(gameB, gameList.get(1));
}


@Test
void testRemoveGame() {
    testLibrary.addGame(gameA);
    testLibrary.addGame(gameB);
    assertEquals(2, gameList.size());
    testLibrary.removeGame(gameA);
    assertEquals(1, gameList.size());
    assertEquals(gameB, gameList.get(0));
    testLibrary.removeGame(gameB);
    assertEquals(0, gameList.size());
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
