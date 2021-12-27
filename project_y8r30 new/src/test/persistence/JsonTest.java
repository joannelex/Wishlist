package persistence;

// This class references code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

import model.SubjectOption;
import model.WishlistSubject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWishlistSubjectName(String name,
                                            WishlistSubject wls) {
        assertEquals(name, wls.getWishlistSubjectName());
    }

    protected void checkSubjectOption(String name, String category, int rating,SubjectOption so) {
        assertEquals(name, so.getName());
        assertEquals(category, so.getCategory());
        assertEquals(rating, so.getRating());
    }
}
