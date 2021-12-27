package model;

import model.exception.DuplicateInputException;
import model.exception.InvalidRatingException;
import model.exception.InvalidStringInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistSubjectTest {

    WishlistSubject testWishlistSubject;
    List<SubjectOption> listOfOptionsForTest;
    SubjectOption subjectOption1;
    SubjectOption subjectOption2;
    SubjectOption subjectOption3;
    SubjectOption subjectOption4;
    SubjectOption subjectOption5;

    @BeforeEach
    public void setup() {
        listOfOptionsForTest = new ArrayList<>();
        testWishlistSubject = new WishlistSubject("test", listOfOptionsForTest);
    }

    @Test
    public void testAddOptionToEmpty() {
        subjectOption1 = new SubjectOption("option1", "test", 0);

        assertEquals(testWishlistSubject.length(), 0);
        try {
            testWishlistSubject.addOption(subjectOption1);
        } catch (DuplicateInputException e) {
            fail("duplicate input");
        } catch (InvalidStringInputException e) {
            fail("invalid string input");
        } catch (InvalidRatingException e) {
            fail("invalid rating");
        }
        assertEquals(testWishlistSubject.length(), 1);
    }

    @Test
    public void testAddOptionToNotEmpty() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);

        listOfOptionsForTest.add(subjectOption1);

        assertEquals(testWishlistSubject.length(), 1);
        try {
            testWishlistSubject.addOption(subjectOption2);
        } catch (DuplicateInputException e) {
            fail("duplicate input");
        } catch (InvalidStringInputException e) {
            fail("invalid string input");
        } catch (InvalidRatingException e) {
            fail("invalid rating");
        }
        assertEquals(testWishlistSubject.length(), 2);
    }

    @Test
    public void testAddOptionDuplicateInputException() {
        subjectOption2 = new SubjectOption("option2", "test", 3);
        try {
            testWishlistSubject.addOption(subjectOption2);
            testWishlistSubject.addOption(subjectOption2);
            fail("should have caught DuplicateInputException");
        } catch (DuplicateInputException e) {
            // expected
        } catch (InvalidStringInputException e) {
            fail("should have caught DuplicateInputException");
        } catch (InvalidRatingException e){
            fail("should have caught DuplicateInputException");

        }
    }

    @Test
    public void testAddOptionInvalidStringExceptionInvalidName() {
        try {
            testWishlistSubject.addOption(new SubjectOption(" ", "test", 1));
            fail("should have caught InvalidStringInput");
        } catch (DuplicateInputException e) {
            fail("should have caught InvalidStringInput");
        } catch (InvalidStringInputException e) {
            // expected
        } catch (InvalidRatingException e){
            fail("should have caught InvalidStringInput");
        }
    }

    @Test
    public void testAddOptionInvalidStringExceptionInvalidCategory() {
        try {
            testWishlistSubject.addOption(new SubjectOption("name", " ", 1));
            fail("should have caught InvalidStringInput");
        } catch (DuplicateInputException e) {
            fail("should have caught InvalidStringInput");
        } catch (InvalidStringInputException e) {
            // expected
        } catch (InvalidRatingException e){
            fail("should have caught InvalidStringInput");
        }
    }

    @Test
    public void testAddOptionInvalidStringExceptionRatingTooLow () {
        try {
            testWishlistSubject.addOption(new SubjectOption("name", "test", -1));
            fail("should have caught InvalidRatingInput");
        } catch (DuplicateInputException e) {
            fail("should have caught InvalidRatingInput");
        } catch (InvalidStringInputException e) {
            fail("should have caught InvalidRatingInput");
        } catch (InvalidRatingException e){
            // expected
        }
    }

    @Test
    public void testAddOptionInvalidStringExceptionRatingTooHigh () {
        try {
            testWishlistSubject.addOption(new SubjectOption("name", "test", 6));
            fail("should have caught InvalidRatingInput");
        } catch (DuplicateInputException e) {
            fail("should have caught InvalidRatingInput");
        } catch (InvalidStringInputException e) {
            fail("should have caught InvalidRatingInput");
        } catch (InvalidRatingException e){
            // expected
        }
    }

    @Test
    public void testRemoveOptionFromListWithMultiple() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);

        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);

        assertEquals(testWishlistSubject.length(), 3);
        try {
            testWishlistSubject.removeOption("option2");
            assertEquals(testWishlistSubject.length(), 2);
            assertFalse(testWishlistSubject.listContains(subjectOption2));
        } catch (InvalidStringInputException e) {
            fail("Was not expecting InvalidStringInputException");
        }
    }

    @Test
    public void testRemoveOptionFromListWithSingle() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        listOfOptionsForTest.add(subjectOption1);
        assertEquals(testWishlistSubject.length(), 1);
        try {
            testWishlistSubject.removeOption("option1");
            assertEquals(testWishlistSubject.length(), 0);
            assertFalse(testWishlistSubject.listContains(subjectOption1));
        } catch (InvalidStringInputException e) {
            fail("Was not expecting InvalidStringInputException");
        }
    }

    @Test
    public void testRemoveOptionNullPointerException() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 0);
        listOfOptionsForTest.add(subjectOption1);
        assertEquals(testWishlistSubject.length(), 1);
        try {
            testWishlistSubject.removeOption("option2");
            fail("should have caught NullPointerException");
        } catch (InvalidStringInputException e) {
            fail("should have caught NullPointerException");
        } catch (NullPointerException e){
            // expected
        }
    }

    @Test
    public void testRemoveOptionInvalidStringInputException() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        listOfOptionsForTest.add(subjectOption1);
        assertEquals(testWishlistSubject.length(), 1);
        try {
            testWishlistSubject.removeOption(" ");
            fail("should have caught InvalidStringInputException");
        } catch (InvalidStringInputException e) {
            // expected
        } catch (NullPointerException e){
            fail("should have caught InvalidStringInputException");
        }
    }

    @Test
    public void testGetOptionGivenCategoryNoMatches() {
        subjectOption1 = new SubjectOption("option1", "no", 0);
        subjectOption2 = new SubjectOption("option2", "no", 3);
        subjectOption3 = new SubjectOption("option3", "no", 5);
        subjectOption4 = new SubjectOption("option4", "no", 5);
        subjectOption5 = new SubjectOption("option5", "no", 1);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);
        listOfOptionsForTest.add(subjectOption4);
        listOfOptionsForTest.add(subjectOption5);

        assertEquals(testWishlistSubject.length(), 5);
        try {
            testWishlistSubject.getOptionGivenCategory("yes");
            fail("Should have caught NullPointerException");
        } catch (NullPointerException e) {
            //expected
        }
        // assertEquals(testWishlistSubject.getOptionGivenCategory("yes"), new ArrayList<SubjectOption>());

    }

    @Test
    public void testGetOptionGivenCategoryYesMatches() {
        subjectOption1 = new SubjectOption("option1", "no", 0);
        subjectOption2 = new SubjectOption("option2", "yes", 3);
        subjectOption3 = new SubjectOption("option3", "no", 5);
        subjectOption4 = new SubjectOption("option4", "yes", 5);
        subjectOption5 = new SubjectOption("option5", "yes", 1);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);
        listOfOptionsForTest.add(subjectOption4);
        listOfOptionsForTest.add(subjectOption5);

        assertEquals(testWishlistSubject.length(), 5);
        assertEquals(testWishlistSubject.getOptionGivenCategory("yes"),
                new ArrayList<>(Arrays.asList(subjectOption2, subjectOption4, subjectOption5)));
    }

    @Test
    public void testGetOptionGivenRatingNoMatch() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        subjectOption4 = new SubjectOption("option4", "test", 5);
        subjectOption5 = new SubjectOption("option5", "test", 1);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);
        listOfOptionsForTest.add(subjectOption4);
        listOfOptionsForTest.add(subjectOption5);

        assertEquals(testWishlistSubject.length(), 5);
        try {
            testWishlistSubject.getOptionGivenRating(2);
            fail("Should have caught NullPointerException");
        } catch (NullPointerException e) {
            //expected
        }
        //assertEquals(testWishlistSubject.getOptionGivenRating(2), new ArrayList<SubjectOption>());
    }

    @Test
    public void testGetOptionGivenRatingYesMatch() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        subjectOption4 = new SubjectOption("option4", "test", 5);
        subjectOption5 = new SubjectOption("option5", "test", 1);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);
        listOfOptionsForTest.add(subjectOption4);
        listOfOptionsForTest.add(subjectOption5);

        assertEquals(testWishlistSubject.length(), 5);
        assertEquals(testWishlistSubject.getOptionGivenRating(5), new ArrayList<>(Arrays.asList(subjectOption3, subjectOption4)));
    }

    @Test
    public void testGetOptionGivenIndex() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        subjectOption4 = new SubjectOption("option4", "test", 5);
        subjectOption5 = new SubjectOption("option5", "test", 1);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);
        listOfOptionsForTest.add(subjectOption4);
        listOfOptionsForTest.add(subjectOption5);

        assertEquals(testWishlistSubject.length(), 5);
        assertEquals(testWishlistSubject.getOptionGivenIndex(0),subjectOption1);
        assertEquals(testWishlistSubject.getOptionGivenIndex(2),subjectOption3);
        assertEquals(testWishlistSubject.getOptionGivenIndex(4),subjectOption5);
    }

    @Test
    public void testListContainsNo() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        subjectOption5 = new SubjectOption("option5", "test", 1);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);

        assertEquals(testWishlistSubject.length(), 3);
        assertFalse(testWishlistSubject.listContains(subjectOption5));
    }

    @Test
    public void testListContainsYes() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);

        assertEquals(testWishlistSubject.length(), 3);
        assertTrue(testWishlistSubject.listContains(subjectOption3));
        assertTrue(testWishlistSubject.listContains(subjectOption2));
        assertTrue(testWishlistSubject.listContains(subjectOption1));
    }

    @Test
    public void testGetSubjectOptionsEmpty() {
        List<SubjectOption> listOfSubjectOptionsTest = new ArrayList<>();
        assertEquals(testWishlistSubject.getSubjectOptions(),listOfSubjectOptionsTest);
    }

    @Test
    public void testGetSubjectOptionsSingle() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        listOfOptionsForTest.add(subjectOption1);

        List<SubjectOption> listOfSubjectOptionsTest = new ArrayList<>();
        listOfSubjectOptionsTest.add(subjectOption1);

        assertEquals(testWishlistSubject.getSubjectOptions(),listOfSubjectOptionsTest);
    }

    @Test
    public void testGetSubjectOptionsMultiple() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);

        List<SubjectOption> listOfSubjectOptionsTest = new ArrayList<>();
        listOfSubjectOptionsTest.add(subjectOption1);
        listOfSubjectOptionsTest.add(subjectOption2);
        listOfSubjectOptionsTest.add(subjectOption3);

        assertEquals(testWishlistSubject.getSubjectOptions(),listOfSubjectOptionsTest);
    }

    @Test
    public void testDisplayAllOptions() {
        subjectOption1 = new SubjectOption("option1", "test", 0);
        subjectOption2 = new SubjectOption("option2", "test", 3);
        subjectOption3 = new SubjectOption("option3", "test", 5);
        listOfOptionsForTest.add(subjectOption1);
        listOfOptionsForTest.add(subjectOption2);
        listOfOptionsForTest.add(subjectOption3);

        assertEquals(listOfOptionsForTest.size(), 3);
        List<String> reformatedOptions = new ArrayList<>();
        reformatedOptions.add("option1   |   test   |   0");
        reformatedOptions.add("option2   |   test   |   3");
        reformatedOptions.add("option3   |   test   |   5");
        assertEquals(testWishlistSubject.displayAllOptions(), reformatedOptions);
    }
}
