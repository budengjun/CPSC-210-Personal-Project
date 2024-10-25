package persistence;

import model.Game;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import javax.swing.text.html.ListView;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Library li = new Library();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            Library li = new Library();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.write(li);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            li = reader.read();
            assertEquals(0, li.getGameList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {
            Library li = new Library();
            li.addGame(new Game("Baldur's Gate 3", 60, 54, 80.1));
            li.addGame(new Game("Grand Theft Auto V", 39.9, 77, 61.2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(li);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            li = reader.read();
            List<Game> games = li.getGameList();
            assertEquals(2, games.size());
            checkGame("Baldur's Gate 3", 60, 54, 80.1, games.get(0));
            checkGame("Grand Theft Auto V", 39.9, 77, 61.2, games.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}