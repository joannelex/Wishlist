package persistence;

// This class references code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

import model.ListOfWishlistSubject;
import model.SubjectOption;
import model.WishlistSubject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// represents a reader that reads wishlist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: - reads wishlist from file and returns it
    //          - throws IOException if an error occurs reading data from file
    public ListOfWishlistSubject read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfWishlistSubject(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECT: parses listOfWishlistSubject from JSON object and returns it
    private ListOfWishlistSubject parseListOfWishlistSubject(JSONObject jsonObject) {
        ListOfWishlistSubject listOfWishlistSubject = new ListOfWishlistSubject();
        addToListOfWishlistSubjects(listOfWishlistSubject,jsonObject);
        return listOfWishlistSubject;
    }

    // MODIFIES: lowls
    // EFFECT: parses wishlistSubject from JSON object and adds them to listOfWishlistSubjects
    private void addToListOfWishlistSubjects(ListOfWishlistSubject lowls, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfWishlistSubject");
        for (Object json : jsonArray) {
            JSONObject nextWishlistSubject = (JSONObject) json;
            addWishlistSubject(lowls, nextWishlistSubject);

        }
    }

    // MODIFIES: lowls
    // EFFECT: parses wishlistSubject from JSON object and adds it to ListOfWishlistSubject(lowls)
    //          If exception is caught, prints according error message
    private void addWishlistSubject(ListOfWishlistSubject lowls, JSONObject jsonObject) {
        String subjectName = jsonObject.getString("name");
        try {
            lowls.addWishlistSubject(subjectName);
            WishlistSubject currentWishlistSubject = null;
            currentWishlistSubject = lowls.getWishlistSubjectGivenName(subjectName);
            JSONArray listOfSubjectOptions = jsonObject.getJSONArray("listOfSubjectOptions");

            for (Object json : listOfSubjectOptions) {
                JSONObject nextSubjectOption = (JSONObject) json;
                addSubjectOptions(currentWishlistSubject, nextSubjectOption);
            }
        } catch (Exception e) {
            System.out.println("Invalid-- exception caught");
        }
    }

    // MODIFIES: wls
    // EFFECT: parses subjectOption from JSON object and adds it to wishlistSubject(wls)
    //          If exception is caught, prints according error message
    private void addSubjectOptions(WishlistSubject wls, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String category = jsonObject.getString("category");
        int rating = jsonObject.getInt("rating");

        try {
            SubjectOption currentSubjectOption = new SubjectOption(name, category, rating);
            wls.addOption(currentSubjectOption);
        } catch (Exception e) {
            System.out.println("Invalid-- exception caught");
        }
    }
}
