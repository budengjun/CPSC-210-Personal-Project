package persistence;

import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGame(String name, double price, int numAchievements, double popularIndex, Game game) {
        assertEquals(name, game.getName());
        assertEquals(price, game.getPrice());
        assertEquals(numAchievements, game.getNumAchievements());
        assertEquals(popularIndex, game.getPopularIndex());
    }
}