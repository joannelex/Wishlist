package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectOptionTest {

    private SubjectOption testSubjectOption;

    @BeforeEach
    public void setup() {
        testSubjectOption = new SubjectOption("test", "category", 0);
    }

    @Test
    public void testSetName() {
        assertEquals(testSubjectOption.getName(), "test");
        testSubjectOption.setName("test1");
        assertEquals(testSubjectOption.getName(), "test1");
    }

    @Test
    public void testSetCategory() {
        assertEquals(testSubjectOption.getCategory(), "category");
        testSubjectOption.setCategory("category1");
        assertEquals(testSubjectOption.getCategory(), "category1");
    }

    @Test
    public void testSetRating() {
        assertEquals(testSubjectOption.getRating(), 0);
        testSubjectOption.setRating(5);
        assertEquals(testSubjectOption.getRating(), 5);
    }




}
