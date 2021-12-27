package model;

import model.exception.DuplicateInputException;
import model.exception.InvalidRatingException;
import model.exception.InvalidStringInputException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of options within a wishlist's subject
public class WishlistSubject implements Writable {
    private String name;                              // Name of the subjects in the wishlist
    private List<SubjectOption> listOfSubjectOptions; // list of options of a subject

    // REQUIRES: list of options is empty
    // EFFECT: constructs a subject with a given name and list of options
    public WishlistSubject(String name, List<SubjectOption> listOfSubjectOptions) {
        this.name = name;
        this.listOfSubjectOptions = listOfSubjectOptions;
    }

    // EFFECT: returns the name of given subject
    public String getWishlistSubjectName() {
        return this.name;
    }

    // EFFECT: returns the list of options of given subject
    public List<SubjectOption> getSubjectOptions() {
        return this.listOfSubjectOptions;
    }

    public List<String> getSubjectOptionNames() {
        List<String> subjectOptionNames = new ArrayList<>();
        for (SubjectOption next : listOfSubjectOptions) {
            subjectOptionNames.add(next.getName());
        }
        return subjectOptionNames;
    }

    // REQUIRES: subject's option list is not empty
    // EFFECT: returns a list of option with given category
    public List<SubjectOption> getOptionGivenCategory(String category) {
        List<SubjectOption> result = new ArrayList<>();

        for (SubjectOption next: listOfSubjectOptions) {
            if (next.getCategory().equals(category)) {
                result.add(next);
            }
        }

        if (result.size() == 0) {
            throw new NullPointerException();
        }

        EventLog.getInstance().logEvent(new Event("Got SubjectOptions with category: " + category));
        return result;
    }

    // REQUIRES: subject's option list is not empty
    // EFFECT: returns a list of option with given rating
    public List<SubjectOption> getOptionGivenRating(int rating) {
        List<SubjectOption> result = new ArrayList<>();
        for (SubjectOption next: listOfSubjectOptions) {
            if (next.getRating() == rating) {
                result.add(next);
            }
        }

        if (result.size() == 0) {
            throw new NullPointerException();
        }

        EventLog.getInstance().logEvent(new Event("Got SubjectOptions with rating: " + rating));
        return result;
    }

    public List<String> displayAllOptions() {
        List<String> reformatedOptions = new ArrayList<>();
        for (SubjectOption next : listOfSubjectOptions) {
            String reformatedOption = next.getName() + "   |   " + next.getCategory() + "   |   " + next.getRating();
            reformatedOptions.add(reformatedOption);
        }

        return reformatedOptions;
    }

    // REQUIRES: option exists at given index
    // EFFECT: returns the Option that is present at given index
    public SubjectOption getOptionGivenIndex(int i) {
        return listOfSubjectOptions.get(i);
    }

    // REQUIRES: option is unique
    // MODIFIES: this
    // EFFECT: adds an option to the list of options
    public void addOption(SubjectOption subjectOption) throws
            DuplicateInputException, InvalidStringInputException, InvalidRatingException {
        if (subjectOption.getName().trim().isEmpty() || subjectOption.getCategory().trim().isEmpty()) {
            throw new InvalidStringInputException();
        } else if (getSubjectOptionNames().contains(subjectOption.getName())) {
            throw new DuplicateInputException();
        }  else if (subjectOption.getRating() < 0 || subjectOption.getRating() > 5) {
            throw new InvalidRatingException();
        }
        listOfSubjectOptions.add(subjectOption);
        EventLog.getInstance().logEvent(new Event("Added Option: " + subjectOption.getName() + "  |  "
                + subjectOption.getCategory() + "  |  " + subjectOption.getRating()));
    }

    // REQUIRES: list is not empty, an option with the given name exists
    // MODIFIES: this
    // EFFECT: removes the option with the given name
    public void removeOption(String name) throws InvalidStringInputException {
        if (name.trim().isEmpty()) {
            throw new InvalidStringInputException();
        } else if (!getSubjectOptionNames().contains(name)) {
            throw new NullPointerException();
        }
        listOfSubjectOptions.removeIf(next -> (next.getName()).equals(name));
        EventLog.getInstance().logEvent(new Event("Removed Option: " + name));
    }

    // EFFECT: returns true if the list of options contains given option, false otherwise (for Test)
    public boolean listContains(SubjectOption subjectOption) {
        return listOfSubjectOptions.contains(subjectOption);
    }

    // EFFECT: returns the number of options in the list of SubjectOptions
    public int length() {
        return listOfSubjectOptions.size();
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("listOfSubjectOptions", losoToJson());
        return json;
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns things in listOfSubjectOption as a JSON array
    private JSONArray losoToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SubjectOption so : listOfSubjectOptions) {
            jsonArray.put(so.toJson());
        }

        return jsonArray;
    }
}
