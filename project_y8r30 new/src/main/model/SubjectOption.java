package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an WishlistOption having a name, category that it belongs to, and rating out of 5
public class SubjectOption implements Writable {
    private String name;             // name of the option
    private String category;         // category of the option
    private int rating;              // rating of the option from 0-5

    // REQUIRES: - rating is an integer between 0 - 5 inclusive
    //           - name is unique
    // EFFECT: constructs a new wishlist option with given name, category, rating
    public SubjectOption(String name, String category, int rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
    }

    // EFFECT: returns the name of the option
    public String getName() {
        return this.name;
    }

    // EFFECT: returns the category of the option
    public String getCategory() {
        return this.category;
    }

    // EFFECT: returns the rating of the option
    public int getRating() {
        return this.rating;
    }

    // MODIFIES: this
    // EFFECT: changes the name of the given option
    public void setName(String newName) {
        this.name = newName;
    }

    // MODIFIES: this
    // EFFECT: changes the category of the given option
    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    // MODIFIES: this
    // EFFECT: changes the rating of the given option
    public void setRating(int newRating) {
        this.rating = newRating;
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("rating", rating);
        return json;
    }
}
