package persistence;

import model.Game;
import model.Library;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Library li = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        try {
            Library li = reader.read();
            assertEquals(0, li.getGameList());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Library li = reader.read();
            List<Game> games = li.getGameList();
            assertEquals(2, games.size());
            checkGame("Baldur's Gate 3", 60, 54, 80.1, games.get(0));
            checkGame("Grand Theft Auto V", 39.9, 77, 61.2, games.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}