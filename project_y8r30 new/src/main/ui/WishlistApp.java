package ui;

// This class references code from CPSC210/TellerApp
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

import model.ListOfWishlistSubject;
import model.SubjectOption;
import model.WishlistSubject;
import model.exception.DuplicateInputException;
import model.exception.InvalidRatingException;
import model.exception.InvalidStringInputException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wishlist application for: - storing choice options
//                           - random generation of an option
public class WishlistApp {
    private static final String JSON_STORE = "./data/wishlist.json";
    private Scanner input;
    private ListOfWishlistSubject listOfWishlistSubjects;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECT: constructs ListOfWishlistSubjects and runs the wishlist application
    public WishlistApp() throws FileNotFoundException {
        System.out.println("\nWelcome! Ready for another Adventure?");
        listOfWishlistSubjects = new ListOfWishlistSubject();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWishlistApp();
    }

    // EFFECT: returns current listOfWishlistSubject
    public ListOfWishlistSubject getListOfWishlistSubjects() {
        return listOfWishlistSubjects;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWishlistApp() {
        boolean keepGoing = true;
        String command;

        init();
        displayWishlistSubjectMenu();

        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processWishlistSubjectCommand(command);
            }
        }

        System.out.println("GoodBye! Can't wait for our next adventure!");
    }

    // EFFECT: initializes WishlistApp
    private void init() {
        listOfWishlistSubjects = new ListOfWishlistSubject();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECT: display menu of subject to the user
    private void displayWishlistSubjectMenu() {
        System.out.println("\nHOME");
        if (listOfWishlistSubjects.length() != 0) {
            for (int i = 0; i < listOfWishlistSubjects.length(); i++) {
                int position = i + 1;
                WishlistSubject currentWishlistSubject = listOfWishlistSubjects.getWishlistSubjectGivenIndex(i);
                String currentWishlistSubjectName = currentWishlistSubject.getWishlistSubjectName();
                System.out.println(position + ": " + currentWishlistSubjectName);
            }
        }
        System.out.println("\n+ -> Add new subject");
        System.out.println("- -> Remove subject");
        System.out.println("o -> See options");
        System.out.println("s -> save wishlist to file");
        System.out.println("l -> load wishlist from file");
        System.out.println("q -> quit");
    }

    // EFFECT: display menu of options to the user
    private void displaySubjectOptionMenu(WishlistSubject selectedWishlistSubject) {
        System.out.println("\n" + selectedWishlistSubject.getWishlistSubjectName());
        if (selectedWishlistSubject.length() != 0) {
            for (int i = 0; i < selectedWishlistSubject.length(); i++) {
                int position = i + 1;
                SubjectOption currentSubjectOption = selectedWishlistSubject.getOptionGivenIndex(i);
                System.out.println(position + ": " + currentSubjectOption.getName() + " | "
                        + currentSubjectOption.getCategory() + " | " + currentSubjectOption.getRating());
            }
        }
        System.out.println("\n+ -> Add new option");
        System.out.println("- -> Remove option");
        System.out.println("c -> CHOOSE");
        System.out.println("< -> back");
    }

    // EFFECT: display menu of the type of random choice to the user
    private void displayRandomChoiceMenu() {
        System.out.println("\nCHOSE BY:");
        System.out.println("1 -> select all");
        System.out.println("2 -> category");
        System.out.println("3 -> rating");
        System.out.println("< -> back");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from subject menu
    private void processWishlistSubjectCommand(String command) {
        if (command.equals("+")) {
            processPlus();
        } else if (command.equals("-")) {
            processMinus();
        } else if (command.equals("o")) {
            processO();
        } else if (command.equals("s")) {
            saveWishlist();
            displayWishlistSubjectMenu();
        } else if (command.equals("l")) {
            loadWishlist();
            displayWishlistSubjectMenu();
        } else {
            System.out.println("Selection is not valid. Please retry.");
            displayWishlistSubjectMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: process the command to add a new WishlistSubject with given name
    private void processPlus() {
        System.out.println("Enter new subject name: ");
        addNewWishlistSubject(input.next());
        displayWishlistSubjectMenu();
    }

    // MODIFIES: this
    // EFFECTS: - if ListOfWishlistSubject is not empty, process the command to remove the WishlistSubject
    //          - otherwise prints error message and returns to subject menu
    private void processMinus() {
        if (listOfWishlistSubjects.length() == 0) {
            System.out.println("No subject exists to be removed.");
        } else {
            System.out.println("Which subject would you like to remove?");
            System.out.println("Enter subject name: ");
            removeGivenWishlistSubject(input.next());
        }
        displayWishlistSubjectMenu();
    }

    // EFFECTS: - if ListOfWishlistSubject is not empty, accesses SubjectOptions of WishlistSubject with given name
    //          - otherwise prints error message and returns to subject menu
    private void processO() {
        if (listOfWishlistSubjects.length() == 0) {
            System.out.println("No subject exists to display options.");
        } else {
            System.out.println("Which subject options would you like to see?");
            System.out.println("Enter subject name: ");
            accessSubjectOptions(input.next());
        }
    }

    // MODIFIES: this
    // EFFECT: access the option menu for given subject
    private void accessSubjectOptions(String wishlistOptionName) {
        WishlistSubject currentWishlistOption = null;
        try {
            currentWishlistOption = listOfWishlistSubjects.getWishlistSubjectGivenName(wishlistOptionName);
        } catch (NullPointerException e) {
            System.out.println("given option does not exist.");
        } catch (InvalidStringInputException e) {
            System.out.println("invalid input");
        }
        if (listOfWishlistSubjects.wishlistSubjectContainsGivenName(currentWishlistOption.getWishlistSubjectName())) {
            displaySubjectOptionMenu(currentWishlistOption);
            processSubjectOptionCommand(currentWishlistOption);
        }
    }

    // MODIFIES: this
    // EFFECT: process user command from options menu
    private void processSubjectOptionCommand(WishlistSubject wishlistSubject) {
        String command = input.next();
        if (command.equals("+")) {
            addNewSubjectOption(wishlistSubject);
            displayAndProcessSubjectOption(wishlistSubject);
        } else if (command.equals(("-"))) {
            removeSubjectOption(wishlistSubject);
            displayAndProcessSubjectOption(wishlistSubject);
        } else if (command.equals("c")) {
            displayRandomChoiceMenu();
            processRandomChoiceCommand(wishlistSubject);
            displayAndProcessSubjectOption(wishlistSubject);
        } else if (command.equals("<")) {
            displayWishlistSubjectMenu();
        } else {
            System.out.println("Selection is not valid. Please retry.");
            displayAndProcessSubjectOption(wishlistSubject);
        }
    }

    // MODIFIES: this
    // EFFECT: display and processes SubjectOption menu and command
    private void displayAndProcessSubjectOption(WishlistSubject wishlistSubject) {
        displaySubjectOptionMenu(wishlistSubject);
        processSubjectOptionCommand(wishlistSubject);
    }

    // MODIFIES: this
    // EFFECT: process choice command from choice menu
    private void processRandomChoiceCommand(WishlistSubject currentWishlistSubject) {
        if (currentWishlistSubject.length() == 0) {
            System.out.println("No option available to draw from--press + to add options.");
        } else {
            String command = input.next();
            if (command.equals("1")) {
                drawRandomFromAll(currentWishlistSubject);
            } else if (command.equals("2")) {
                drawRandomFromCategory(currentWishlistSubject);
            } else if (command.equals("3")) {
                drawRandomFromRating(currentWishlistSubject);
            } else if (command.equals("<")) {
                displaySubjectOptionMenu(currentWishlistSubject);
                processSubjectOptionCommand(currentWishlistSubject);
            } else {
                System.out.println("Choice selection is not valid. Please retry.");
            }
        }
    }

    // MODIFIES: this
    // EFFECT: adds a new subject with given name, into the list of subjects
    private void addNewWishlistSubject(String newWishlistSubjectName) {
        try {
            listOfWishlistSubjects.addWishlistSubject(newWishlistSubjectName);
        } catch (DuplicateInputException e) {
            System.out.println("This subject already exists! Please retry.");
        } catch (InvalidStringInputException e) {
            System.out.println("invalid input. Please retry.");
        }
    }

    // MODIFIES: this
    // EFFECT: removes the wishlist subject with given name from HOME
    private void removeGivenWishlistSubject(String removeSubjectName) {
        try {
            listOfWishlistSubjects.removeWishlistSubject(removeSubjectName);
        } catch (InvalidStringInputException e) {
            System.out.println("invalid input");
        } catch (NullPointerException e) {
            System.out.println("Subject with given name does not exist");
        }
    }

    // MODIFIES: this, givenWishlistSubject
    // EFFECT: adds a new option with given (valid) name, category, rating to the wishlist subject
    private void addNewSubjectOption(WishlistSubject givenWishlistSubject) {
        List<String> listOfOptionNames = new ArrayList<>();

        for (SubjectOption next : givenWishlistSubject.getSubjectOptions()) {
            listOfOptionNames.add(next.getName());
        }

        System.out.println("Enter new option name: ");
        String givenName = input.next();
        System.out.println("Enter new option category: ");
        String givenCategory = input.next();
        System.out.println("Enter new option rating from 0-5: ");
        int givenRating = input.nextInt();

        try {
            givenWishlistSubject.addOption(new SubjectOption(givenName, givenCategory, givenRating));
        } catch (DuplicateInputException e) {
            System.out.println("This name already exists in this subject! Please retry.");
        } catch (InvalidStringInputException e) {
            System.out.println("Invalid entry. Please retry.");
        } catch (InvalidRatingException e) {
            System.out.println("Rating is from 0-5 stars! Please retry.");
        }
    }


    // MODIFIES: this, givenWishlistSubject
    // EFFECT: removes the wishlist option that has the given name
    private void removeSubjectOption(WishlistSubject givenWishlistSubject) {
        try {
            System.out.println("Which option would you like to remove?");
            System.out.println("Enter option name: ");
            String optionNameToRemove = input.next();
            givenWishlistSubject.removeOption(optionNameToRemove);
        } catch (NullPointerException e) {
            System.out.println("Option with given name does not exist.");
        } catch (InvalidStringInputException e) {
            System.out.println("invalid input");
        }
    }

    // EFFECT: randomly draws an option from the given wishlist subject
    private void drawRandomFromAll(WishlistSubject givenWishlistSubject) {
        Random random = new Random();
        int randomPositionNumber = random.nextInt(givenWishlistSubject.length());
        SubjectOption randomSubjectOption = givenWishlistSubject.getOptionGivenIndex(randomPositionNumber);
        System.out.println("We choose: " + randomSubjectOption.getName());
    }

    // EFFECT: randomly draws an option with given rating, from the given wishlist subject
    private void drawRandomFromRating(WishlistSubject givenWishlistSubject) {
        System.out.println("What rating would you like to draw from?");
        int chosenRating = input.nextInt();

        List<SubjectOption> listForRandomChoiceByRating = givenWishlistSubject.getOptionGivenRating(chosenRating);

        if (listForRandomChoiceByRating.size() == 0) {
            System.out.println("given rating doesn't exist! Please retry.");
        } else {
            Random random = new Random();
            int randomPositionNumber = random.nextInt(listForRandomChoiceByRating.size());
            SubjectOption randomSubjectOption = listForRandomChoiceByRating.get(randomPositionNumber);
            System.out.println("We choose: " + randomSubjectOption.getName());
        }
    }

    // EFFECT: randomly draws an option with given category, from the given wishlist subject
    private void drawRandomFromCategory(WishlistSubject givenWishlistSubject) {
        System.out.println("What category would you like to draw from?");
        String chosenCategory = input.next();

        List<SubjectOption> listForRandomChoiceByCategory = givenWishlistSubject.getOptionGivenCategory(chosenCategory);

        if (listForRandomChoiceByCategory.size() == 0) {
            System.out.println("given category doesn't exist! Please retry.");
        } else {
            Random random = new Random();
            int randomPositionNumber = random.nextInt(listForRandomChoiceByCategory.size());
            SubjectOption randomSubjectOption = listForRandomChoiceByCategory.get(randomPositionNumber);
            System.out.println("We choose: " + randomSubjectOption.getName());
        }
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECT: saves the wishlist to file
    private void saveWishlist() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfWishlistSubjects);
            jsonWriter.close();
            System.out.println("Saved wishlist to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECT: load wishlist from file
    private void loadWishlist() {
        try {
            listOfWishlistSubjects = jsonReader.read();
            System.out.println("Loaded wishlist from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}