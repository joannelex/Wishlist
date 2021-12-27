package persistence;

// This class references code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

import model.ListOfWishlistSubject;
import model.WishlistSubject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfWishlistSubject lowls = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWishlist() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfWishlistSubject.json");
        try {
            ListOfWishlistSubject lowls = reader.read();
            assertEquals(0, lowls.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWishlist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfWishlistSubject.json");
        try {
            ListOfWishlistSubject lowls = reader.read();
            assertEquals(2, lowls.length());

            WishlistSubject firstWishlistSubject = lowls.getWishlistSubjectGivenIndex(0);
            WishlistSubject secondWishlistSubject = lowls.getWishlistSubjectGivenIndex(1);

            checkWishlistSubjectName("food", firstWishlistSubject);
            checkWishlistSubjectName("shopping", secondWishlistSubject);

            checkSubjectOption("sushi", "japanese", 4,
                    firstWishlistSubject.getSubjectOptions().get(0));
            checkSubjectOption("kbbq", "korean", 4,
                    firstWishlistSubject.getSubjectOptions().get(1));
            checkSubjectOption("nike", "athletics", 5,
                    secondWishlistSubject.getSubjectOptions().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderInvalidWishlistSubject() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidWishlistSubject.json");
        try {
            ListOfWishlistSubject lowls = reader.read();
            assertEquals(1, lowls.length());

            WishlistSubject firstWishlistSubject = lowls.getWishlistSubjectGivenIndex(0);
            checkWishlistSubjectName("food", firstWishlistSubject);
            // failed to add second (invalid) subject

            checkSubjectOption("sushi", "japanese", 4,
                    firstWishlistSubject.getSubjectOptions().get(0));
            checkSubjectOption("kbbq", "korean", 4,
                    firstWishlistSubject.getSubjectOptions().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderInvalidSubjectOption() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidSubjectOption.json");
        try {
            ListOfWishlistSubject lowls = reader.read();
            assertEquals(2, lowls.length());

            WishlistSubject firstWishlistSubject = lowls.getWishlistSubjectGivenIndex(0);
            WishlistSubject secondWishlistSubject = lowls.getWishlistSubjectGivenIndex(1);

            checkWishlistSubjectName("food", firstWishlistSubject);
            checkWishlistSubjectName("shopping", secondWishlistSubject);

            checkSubjectOption("sushi", "japanese", 4,
                    firstWishlistSubject.getSubjectOptions().get(0));
            // failed to add second (invalid) option for first subject
            checkSubjectOption("nike", "athletics", 5,
                    secondWishlistSubject.getSubjectOptions().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
