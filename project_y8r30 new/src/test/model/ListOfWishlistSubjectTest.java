package model;

import model.exception.DuplicateInputException;
import model.exception.InvalidStringInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfWishlistSubjectTest {

    ListOfWishlistSubject testListOfWishlistSubject;
    ListOfWishlistSubject testListOfWishlistSubjectNotEmpty;


    @BeforeEach
    public void setup() {
        testListOfWishlistSubject = new ListOfWishlistSubject();
        testListOfWishlistSubjectNotEmpty= new ListOfWishlistSubject();
        try {
            testListOfWishlistSubjectNotEmpty.addWishlistSubject("wls1");
            testListOfWishlistSubjectNotEmpty.addWishlistSubject("wls2");
            testListOfWishlistSubjectNotEmpty.addWishlistSubject("wls3");
        } catch (DuplicateInputException e) {
            fail("Did not expect DuplicateException");
        } catch (InvalidStringInputException e) {
            fail("Did not expect InvalidStringException");
        }
    }

    @Test
    public void testGetListOfWishlistSubjectNamesFromEmptyList() {
        assertEquals(testListOfWishlistSubject.length(), 0);
        assertEquals(testListOfWishlistSubject.getListOfWishlistSubjectNames(), new ArrayList<String>());
    }

    @Test
    public void testGetListOfWishlistSubjectNamesFromNonEmptyList() {
        List<String> listOfWishlistSubjectNameTest = new ArrayList<>();
        listOfWishlistSubjectNameTest.add("wls1");
        listOfWishlistSubjectNameTest.add("wls2");
        listOfWishlistSubjectNameTest.add("wls3");

        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        assertEquals(testListOfWishlistSubjectNotEmpty.getListOfWishlistSubjectNames(), listOfWishlistSubjectNameTest);
    }

    @Test
    public void testGetWishlistSubjectGivenIndexFirst() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        assertEquals(testListOfWishlistSubjectNotEmpty.getWishlistSubjectGivenIndex(0).getWishlistSubjectName(), "wls1");
    }

    @Test
    public void testGetWishlistSubjectGivenIndexLast() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        assertEquals(testListOfWishlistSubjectNotEmpty.getWishlistSubjectGivenIndex(2)
                .getWishlistSubjectName(), "wls3");
    }

    @Test
    public void testGetWishlistSubjectGivenIndexMiddle() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        assertEquals(testListOfWishlistSubjectNotEmpty.getWishlistSubjectGivenIndex(1)
                .getWishlistSubjectName(), "wls2");
    }

    @Test
    public void testGetWishlistSubjectGivenNameFirst() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        try {
            assertEquals(testListOfWishlistSubjectNotEmpty.getWishlistSubjectGivenName("wls1")
                    .getWishlistSubjectName(), "wls1");
        } catch (InvalidStringInputException e) {
            fail("was not expecting InvalidStringInputException");
        } catch (NullPointerException e) {
            fail("was not expecting NullPointerException");
        }
    }

    @Test
    public void testGetWishlistSubjectGivenNameLast() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        try {
            assertEquals(testListOfWishlistSubjectNotEmpty.getWishlistSubjectGivenName("wls3")
                    .getWishlistSubjectName(), "wls3");
        } catch (InvalidStringInputException e) {
            fail("was not expecting InvalidStringInputException");
        } catch (NullPointerException e) {
            fail("was not expecting NullPointerException");
        }
    }

    @Test
    public void testGetWishlistSubjectGivenNameMiddle() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        try {
            assertEquals(testListOfWishlistSubjectNotEmpty.getWishlistSubjectGivenName("wls2")
                    .getWishlistSubjectName(), "wls2");
        } catch (InvalidStringInputException e) {
            fail("was not expecting InvalidStringInputException");
        } catch (NullPointerException e) {
            fail("was not expecting NullPointerException");
        }
    }

    @Test
    public void testWishlistSubjectContainsGivenNameYes() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        assertTrue(testListOfWishlistSubjectNotEmpty.wishlistSubjectContainsGivenName("wls1"));
        assertTrue(testListOfWishlistSubjectNotEmpty.wishlistSubjectContainsGivenName("wls2"));
        assertTrue(testListOfWishlistSubjectNotEmpty.wishlistSubjectContainsGivenName("wls3"));
    }

    @Test
    public void testWishlistSubjectContainsGivenNameEmpty() {
        assertEquals(testListOfWishlistSubject.length(), 0);
        assertFalse(testListOfWishlistSubject.wishlistSubjectContainsGivenName("wls1"));
    }

    @Test
    public void testWishlistSubjectContainsGivenNameNo() {
        assertEquals(testListOfWishlistSubjectNotEmpty.length(), 3);
        assertFalse(testListOfWishlistSubjectNotEmpty.wishlistSubjectContainsGivenName("wls4"));
    }

    @Test
    public void testRemoveWishlistSubjectWithOneItem() {
        assertEquals(testListOfWishlistSubject.length(), 0);
        try {
            testListOfWishlistSubject.addWishlistSubject("wls1");
            testListOfWishlistSubject.addWishlistSubject("wls2");
        } catch (DuplicateInputException e) {
            fail("was not expecting DuplicateInputException");
        } catch (InvalidStringInputException e) {
            fail("was not expecting InvalidStringException");
        }

        assertEquals(testListOfWishlistSubject.length(), 2);
        try {
            testListOfWishlistSubject.removeWishlistSubject("wls1");
            assertEquals(testListOfWishlistSubject.length(), 1);
            assertTrue(testListOfWishlistSubject.wishlistSubjectContainsGivenName("wls2"));
        } catch (InvalidStringInputException e) {
            fail("was not expecting InvalidStringInput");
        }
    }

    @Test
    public void testGetWishlistSubjectGivenNameNullPointerException() {
        try {
            testListOfWishlistSubject.addWishlistSubject("wls1");
            testListOfWishlistSubject.getWishlistSubjectGivenName("test");
            fail("should have caught EmptyListException");
        } catch (NullPointerException e) {
            // expected
        } catch (InvalidStringInputException e) {
            fail("should have caught EmptyListException");
        } catch (DuplicateInputException e) {
            fail("should have caught EmptyListException");
        }
    }

    @Test
    public void testGetWishlistSubjectGivenNameInvalidStringInputException() {
        try {
            testListOfWishlistSubject.addWishlistSubject("wls1");
            testListOfWishlistSubject.getWishlistSubjectGivenName(" ");
            fail("should have caught InvalidStringInputException");
        } catch (NullPointerException e) {
            fail("should have caught InvalidStringInputException");
        } catch (InvalidStringInputException e) {
            // expected
        } catch (DuplicateInputException e) {
            fail("should have caught InvalidStringInputException");
        }
    }

    @Test
    public void testAddWishlistSubjectInvalidStringInputException() {
        try {
            testListOfWishlistSubject.addWishlistSubject(" ");
            fail("should have caught InvalidStringInputException");
        } catch (InvalidStringInputException e) {
            // expected
        } catch (DuplicateInputException e) {
            fail("should have caught InvalidStringInputException");
        }
    }

    @Test
    public void testAddWishlistSubjectDuplicateInputException() {
        try {
            testListOfWishlistSubject.addWishlistSubject("wls1");
            testListOfWishlistSubject.addWishlistSubject("wls1");
            fail("should have caught DuplicateInputException");
        } catch (InvalidStringInputException e) {
            fail("should have caught DuplicateInputException");
        } catch (DuplicateInputException e) {
            // expected
        }
    }

    @Test
    public void testRemoveWishlistSubjectNullPointerException() {
        try {
            testListOfWishlistSubjectNotEmpty.removeWishlistSubject("wls4");
            fail("should have caught NullPointerException");
        } catch (InvalidStringInputException e) {
            fail("should have caught NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testRemoveWishlistSubjectInvalidStringInputException() {
        try {
            testListOfWishlistSubjectNotEmpty.removeWishlistSubject(" ");
            fail("should have caught InvalidStringInputException");
        } catch (InvalidStringInputException e) {
            // expected
        } catch (NullPointerException e) {
            fail("should have caught InvalidStringInputException");
        }
    }
}

