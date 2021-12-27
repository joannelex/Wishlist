package model;

import model.exception.DuplicateInputException;
import model.exception.InvalidStringInputException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of WishlistSubjects
public class ListOfWishlistSubject implements Writable {
    private List<WishlistSubject> listOfWishlistSubjects;  // a list of wishlist subject

    // constructs an empty list of Wishlist Subjects
    public ListOfWishlistSubject() {
        this.listOfWishlistSubjects = new ArrayList<>();
    }

    // EFFECT: returns the number of wishlist subjects in the list
    public int length() {
        return listOfWishlistSubjects.size();
    }

    // EFFECT: returns a list of the names of WishlistSubject in the list of WishlistSubjects, in same order
    public List<String> getListOfWishlistSubjectNames() {
        List<String> listOfWishlistSubjectNames = new ArrayList<>();
        for (WishlistSubject next : listOfWishlistSubjects) {
            listOfWishlistSubjectNames.add(next.getWishlistSubjectName());
        }

        return listOfWishlistSubjectNames;
    }

    // REQUIRES: - index < length of the list of wishlist subject
    //           - list of wishlist subject is not empty
    // EFFECT: returns the wishlist subject at the given index
    public WishlistSubject getWishlistSubjectGivenIndex(int index) {
        return listOfWishlistSubjects.get(index);
    }

    // REQUIRES: - given wishlist subject name exists in the list
    //           - list of wishlist subject is not empty
    // EFFECT: finds and returns the wishlistSubject with given name
    public WishlistSubject getWishlistSubjectGivenName(String givenWishlistSubjectName)
            throws InvalidStringInputException, NullPointerException {
        WishlistSubject foundWishlistSubject = null;
        if (givenWishlistSubjectName.trim().isEmpty()) {
            throw new InvalidStringInputException();
        } else if (!getListOfWishlistSubjectNames().contains(givenWishlistSubjectName)) {
            throw new NullPointerException();
        }

        for (WishlistSubject next: listOfWishlistSubjects) {
            if (givenWishlistSubjectName.equals(next.getWishlistSubjectName())) {
                EventLog.getInstance().logEvent(new Event("Accessing WishlistSubject with name: "
                        + givenWishlistSubjectName));
                foundWishlistSubject = next;
            }
        }
        return foundWishlistSubject;
    }

    // EFFECT: return true if list of WishlistSubjects contains a WishlistSubject with given name,
    //         otherwise return false
    public boolean wishlistSubjectContainsGivenName(String givenWishlistSubjectName) {
        boolean isPresent = false;
        for (WishlistSubject next: listOfWishlistSubjects) {
            if (givenWishlistSubjectName.equals(next.getWishlistSubjectName())) {
                isPresent = true;
                break;
            }
        }

        return isPresent;
    }

    // REQUIRES: name of wishlist subject to be added is unique
    // MODIFIES: this
    // EFFECT: adds a new wishList with given name to the list ****
    public void addWishlistSubject(String newWishlistSubjectName)
            throws DuplicateInputException, InvalidStringInputException {
        if (getListOfWishlistSubjectNames().contains(newWishlistSubjectName)) {
            throw new DuplicateInputException();
        } else if (newWishlistSubjectName.trim().isEmpty()) {
            throw new InvalidStringInputException();
        }

        List<SubjectOption> emptySubjectOptionList = new ArrayList<>();
        listOfWishlistSubjects.add(new WishlistSubject(newWishlistSubjectName, emptySubjectOptionList));
        EventLog.getInstance().logEvent(new Event("Added WishlistSubject: " + newWishlistSubjectName));
    }

    // REQUIRES: a wishlistSubject with given name exists in the list
    // MODIFIES: this
    // EFFECT: removes the wishlistSubject with given name from the list
    public void removeWishlistSubject(String wishlistSubjectNameRemove)
            throws NullPointerException, InvalidStringInputException {
        if (wishlistSubjectNameRemove.trim().isEmpty()) {
            throw new InvalidStringInputException();
        } else if (!getListOfWishlistSubjectNames().contains(wishlistSubjectNameRemove)) {
            throw new NullPointerException();
        }
        listOfWishlistSubjects.removeIf(next -> wishlistSubjectNameRemove.equals(next.getWishlistSubjectName()));
        EventLog.getInstance().logEvent(new Event("Removed WishlistSubject: " + wishlistSubjectNameRemove));
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfWishlistSubject", wishlistSubjectToJson());

        return json;
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns wishlistSubject in this list as a JSON array
    private JSONArray wishlistSubjectToJson() {
        JSONArray jsonArray = new JSONArray();

        for (WishlistSubject wls : listOfWishlistSubjects) {
            jsonArray.put(wls.toJson());
        }

        EventLog.getInstance().logEvent(new Event("Saved current wishlist"));
        return jsonArray;
    }
}
