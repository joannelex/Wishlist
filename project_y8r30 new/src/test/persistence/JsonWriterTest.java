package persistence;

// This class references code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

import model.ListOfWishlistSubject;
import model.SubjectOption;
import model.WishlistSubject;
import model.exception.DuplicateInputException;
import model.exception.InvalidRatingException;
import model.exception.InvalidStringInputException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfWishlistSubject lowls = new ListOfWishlistSubject();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWishlist() {
        try {
            ListOfWishlistSubject lowls = new ListOfWishlistSubject();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfWishlistSubject.json");
            writer.open();
            writer.write(lowls);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfWishlistSubject.json");
            lowls = reader.read();
            assertEquals(0, lowls.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWishlist() {
        try {
            ListOfWishlistSubject lowls = new ListOfWishlistSubject();
            try {
                lowls.addWishlistSubject("shopping");
                lowls.addWishlistSubject("food");
            } catch (DuplicateInputException e) {
                fail("Exception should not have been thrown");
            } catch (InvalidStringInputException e) {
                fail("Exception should not have been thrown");
            }

            try {
                lowls.getWishlistSubjectGivenIndex(0)
                        .addOption(new SubjectOption("adidas", "athletics", 3));
                lowls.getWishlistSubjectGivenIndex(0)
                        .addOption(new SubjectOption("aritzia", "casual", 4));
                lowls.getWishlistSubjectGivenIndex(1)
                        .addOption(new SubjectOption("pho", "vietnamese", 5));
            } catch (DuplicateInputException e) {
                fail("duplicate input");
            } catch (InvalidStringInputException e) {
                fail("invalid string input");
            } catch (InvalidRatingException e) {
                fail("invalid rating input");
            }

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralListOfWishlistSubject.json");
            writer.open();
            writer.write(lowls);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralListOfWishlistSubject.json");
            lowls = reader.read();
            assertEquals(2, lowls.length());

            WishlistSubject firstWishlistSubject = lowls.getWishlistSubjectGivenIndex(0);
            WishlistSubject secondWishlistSubject = lowls.getWishlistSubjectGivenIndex(1);

            checkWishlistSubjectName("shopping", firstWishlistSubject);
            checkWishlistSubjectName("food", secondWishlistSubject);

            checkSubjectOption("adidas", "athletics", 3,
                    firstWishlistSubject.getSubjectOptions().get(0));
            checkSubjectOption("aritzia", "casual", 4,
                    firstWishlistSubject.getSubjectOptions().get(1));
            checkSubjectOption("pho", "vietnamese", 5,
                    secondWishlistSubject.getSubjectOptions().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}