package ui;

import model.Event;
import model.EventLog;
import model.ListOfWishlistSubject;
import model.WishlistSubject;
import model.exception.DuplicateInputException;
import model.exception.InvalidStringInputException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// represents application's main window frame
// this class references code from YouTube, CPSC210/SimpleDrawingPLayer-Complete
// https://youtu.be/BuW7y21FcYI
// https://youtu.be/Kmgo00avvEw

public class WishlistUI extends JFrame implements ActionListener {

    public static final int WIDTH_FRAME = 400;
    public static final int HEIGHT_FRAME = 700;

    private ListOfWishlistSubject currentLowls;

    private static final String JSON_STORE = "./data/wishlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Font font;

    private ImageIcon icon;
    private ImageIcon warning;

    private JPanel windowPanel;
    private JPanel labelPanel;
    private JPanel buttonsPanel;
    private JPanel displayPanel;
    private JPanel wishlistSubjectPanel;

    private JLabel mainLabel;
    private JLabel wsDisplayLabel;

    private JButton buttonAdd;
    private JButton buttonMinus;
    private JButton buttonOption;
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonQuit;

    // EFFECT: constructor sets up a visual wishlist window of current status
    public WishlistUI() {
        super("Adventure");
        initializeFields();
        initializeGraphics();
        initializeWindow();
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/39845163/is-windowadapter-is-an-adapter-pattern-implementation-in-java-swing
    // MODIFIES: this
    // EFFECT: initializes the window for the JFrame
    private void initializeWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
            }
        });
    }

    // EFFECT: prints out the current instance of log onto the console
    private void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.getDate() + "\n" + next.getDescription() + "\n");
        }
    }


    // this method uses image icon from: Wikimedia Commons
    // https://commons.wikimedia.org/wiki/File:OOjs_UI_icon_alert-destructive.svg
    // MODIFIES: this
    // EFFECT: sets fields for WishlistUI class
    //         this method is called by the WishlistUI constructor
    public void initializeFields() {
        this.currentLowls = new ListOfWishlistSubject();
        this.font = new Font("Arial", Font.PLAIN, 11);
        this.icon = new ImageIcon("./img/icon.png");
        this.warning = new ImageIcon("./img/warning.png");

        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);

        this.wsDisplayLabel = new JLabel();

        this.displayPanel = new JPanel();
        this.labelPanel = new JPanel();
        this.windowPanel = new JPanel();
        this.buttonsPanel = new JPanel();
    }

    // MODIFIES: this
    // EFFECT: draws the JFrame window where WishlistUI will operate, populates JPanels, JLabels and JButtons
    public void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        getContentPane().setBackground(Color.BLACK);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Adventure");

        initializePanels();
        initializeLabels();
        initializeButtons();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the display panel for the middle window of application, sets colour of background
    public void initializeWsDisplayPanel() {
        JPanel wsPaddingPanelNorth = new JPanel();
        JPanel wsPaddingPanelEast = new JPanel();
        JPanel wsPaddingPanelSouth = new JPanel();
        JPanel wsPaddingPanelWest = new JPanel();
        wsPaddingPanelNorth.setBackground(new Color(88, 169, 252));
        wsPaddingPanelEast.setBackground(new Color(88, 169, 252));
        wsPaddingPanelSouth.setBackground(new Color(88, 169, 252));
        wsPaddingPanelWest.setBackground(new Color(88, 169, 252));
        wsPaddingPanelNorth.setPreferredSize(new Dimension(80,80));
        wsPaddingPanelEast.setPreferredSize(new Dimension(80,80));
        wsPaddingPanelSouth.setPreferredSize(new Dimension(80,80));
        wsPaddingPanelWest.setPreferredSize(new Dimension(80,80));

        displayPanel.setBackground(Color.WHITE);

        windowPanel.add(wsPaddingPanelNorth, BorderLayout.NORTH);
        windowPanel.add(wsPaddingPanelEast, BorderLayout.EAST);
        windowPanel.add(wsPaddingPanelSouth, BorderLayout.SOUTH);
        windowPanel.add(wsPaddingPanelWest, BorderLayout.WEST);
        windowPanel.add(displayPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons for the button panel; sets font and colour
    public void initializeButtons() {
        buttonAdd = setUpButton("Add subject");
        buttonMinus = setUpButton("Remove subject");
        buttonOption = setUpButton("Access options");
        buttonSave = setUpButton("Save current wishlist");
        buttonLoad = setUpButton("Load wishlist");
        buttonQuit = setUpButton("Quit");

        addWsButtons();
        registerWsButtonListenerToButton();
    }

    // MODIFIES: this
    // EFFECTS: adds initialized buttons to button panel
    public void addWsButtons() {
        buttonsPanel.add(buttonAdd);
        buttonsPanel.add(buttonMinus);
        buttonsPanel.add(buttonOption);
        buttonsPanel.add(buttonSave);
        buttonsPanel.add(buttonLoad);
        buttonsPanel.add(buttonQuit);
    }

    // MODIFIES: this
    // EFFECTS: sets up and returns each button with set font and position
    public JButton setUpButton(String name) {
        JButton button = new JButton(name);
        button.setBackground(new Color(88, 169, 252));
        button.setFont(font);

        button.setHorizontalTextPosition(JButton.CENTER);
        return button;
    }

    // this methods references code from Oracle
    // https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
    // MODIFIES: this
    // EFFECTS: registers each button to ActionListener
    public void registerWsButtonListenerToButton() {
        buttonAdd.addActionListener(this);
        buttonMinus.addActionListener(this);
        buttonOption.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonLoad.addActionListener(this);
        buttonQuit.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: assign each button to an action to be performed when clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAdd) {
            addWishlistSubject();
            initializeDisplayLabel();
        } else if (e.getSource() == buttonMinus) {
            if (currentLowls.length() != 0) {
                removeWishlistSubject();
                initializeDisplayLabel();
            }
        } else if (e.getSource() == buttonOption) {
            if (currentLowls.length() != 0) {
                accessWishlistSubjectOptions();
            }
        } else if (e.getSource() == buttonSave) {
            saveWishlist();
        } else if (e.getSource() == buttonLoad) {
            loadWishlist();
        } else if (e.getSource() == buttonQuit) {
            printLog();
            System.exit(0);
        }
    }

    // this method references code from: YouTube
    // https://youtu.be/Gy3odNyYzhg
    // MODIFIES: this
    // EFFECTS: creates and displays given warning message with warning image icon
    public void warningMsg(String message, String title) {
        JOptionPane.showMessageDialog(displayPanel, message, title, JOptionPane.PLAIN_MESSAGE, warning);
    }

    // this method references code from: YouTube
    // https://youtu.be/Gy3odNyYzhg
    // MODIFIES: this
    // EFFECTS: creates and displays given message with image icon
    public void displayMsg(String message, String title) {
        JOptionPane.showMessageDialog(displayPanel, message, title, JOptionPane.PLAIN_MESSAGE, icon);
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECT: load wishlist from file
    public void loadWishlist() {
        try {
            currentLowls = jsonReader.read();
            displayLoaded();
            displayMsg("Loaded wishlist from " + JSON_STORE, "Load Wishlist");
        } catch (IOException e) {
            warningMsg("Unable to read from file: " + JSON_STORE, "Load Wishlist");
        }
    }

    // MODIFIES: this
    // EFFECT: display the loaded wishlist on displayPanel
    public void displayLoaded() {
        JList<String> currentLowlsNames = listToJlist();
        currentLowlsNames.setFont(font);

        displayPanel.removeAll();
        displayPanel.add(currentLowlsNames);
        revalidate();
        repaint();
    }

    // This method references code from CPSC210/JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECT: saves the wishlist to JSON_STORE file
    public void saveWishlist() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentLowls);
            jsonWriter.close();

            displayMsg("Saved wishlist to " + JSON_STORE, "Save Wishlist");
        } catch (FileNotFoundException e) {
            warningMsg("Unable to write to file: " + JSON_STORE, "Save Wishlist");
        }
    }

    // this method references code from: StackOVerflow
    // https://stackoverflow.com/questions/23008246/how-to-go-back-to-previous-frame-that-was-invisible
    // https://stackoverflow.com/questions/15513380/how-to-open-a-new-window-by-clicking-a-button
    // REQUIRES: currentLowls is not empty
    // MODIFIES: this
    // EFFECTS: if subject with given name exists, sets visibility of this frame to false and opens new window
    //          for the subject. If exception is caught, display according error message
    public void accessWishlistSubjectOptions() {
        wishlistSubjectPanel = new JPanel();
        JTextField subjectName = new JTextField(10);
        subjectName.setFont(font);
        JLabel message = new JLabel("Enter the name of subject to access: ");
        message.setFont(font);
        wishlistSubjectPanel.add(message);
        wishlistSubjectPanel.add(subjectName);

        int reply = JOptionPane.showConfirmDialog(displayPanel, wishlistSubjectPanel,
                "New Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            WishlistSubject selectedWls;
            try {
                selectedWls = currentLowls.getWishlistSubjectGivenName(subjectName.getText());
                this.setVisible(false);
                new SubjectOptionUI(selectedWls, this);
            } catch (NullPointerException e) {
                warningMsg("Subject with given name does not exist.", "Access Subject");
            } catch (InvalidStringInputException e) {
                warningMsg("Invalid input.", "Access Subject");
            }
        }
    }

    // REQUIRES: currentLowls is not empty
    // MODIFIES: this
    // EFFECTS: if subject with given name exists, removes the subject from current ListofWishlistSubject.
    //          If exception is caught, display according error message
    public void removeWishlistSubject() {
        wishlistSubjectPanel = new JPanel();
        JTextField subjectName = new JTextField(10);
        subjectName.setFont(font);
        JLabel message = new JLabel("Enter the name of subject to delete: ");
        message.setFont(font);
        wishlistSubjectPanel.add(message);
        wishlistSubjectPanel.add(subjectName);

        int reply = JOptionPane.showConfirmDialog(displayPanel, wishlistSubjectPanel,
                "Remove Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            try {
                currentLowls.removeWishlistSubject(subjectName.getText());
            } catch (InvalidStringInputException e) {
                warningMsg("Invalid input.", "Remove Subject");
            } catch (NullPointerException e) {
                warningMsg("Subject with given name does not exist.", "Remove Subject");
            }
        }
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/10716828/joptionpane-showconfirmdialog
    // MODIFIES: this
    // EFFECTS: if subject with given name is unique, add the subject to current ListofWishlistSubject.
    //          If exception is caught, display according error message
    private void addWishlistSubject() {
        wishlistSubjectPanel = new JPanel();
        JTextField subjectName = new JTextField(10);
        subjectName.setFont(font);
        JLabel message = new JLabel("Enter subject name: ");
        message.setFont(font);
        wishlistSubjectPanel.add(message);
        wishlistSubjectPanel.add(subjectName);

        int reply = JOptionPane.showConfirmDialog(displayPanel, wishlistSubjectPanel,
                "New Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            try {
                currentLowls.addWishlistSubject(subjectName.getText());
                // successfully added
            } catch (DuplicateInputException e) {
                warningMsg("Subject with same name already exists.", "Add Subject");
            } catch (InvalidStringInputException e) {
                warningMsg("Invalid input", "Add Subject");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize JPanels for title, window and buttons
    public void initializePanels() {
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setPreferredSize(new Dimension(WIDTH_FRAME,70));

        windowPanel.setLayout(new BorderLayout());
        windowPanel.setBackground(new Color(88, 169, 252));

        add(labelPanel, BorderLayout.NORTH);
        add(windowPanel, BorderLayout.CENTER);

        initializeButtonsPanel();
        initializeWsDisplayPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons panel
    public void initializeButtonsPanel() {
        buttonsPanel.setLayout(new GridLayout(6,1));
        buttonsPanel.setBackground(new Color(88, 169, 252));
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes the labels
    public void initializeLabels() {
        mainLabel = new JLabel("ADVENTURE");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setForeground(new Color(88, 169, 252));
        mainLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        mainLabel.setBounds(0,60,0,0);
        labelPanel.add(mainLabel, BorderLayout.AFTER_LAST_LINE);

        initializeDisplayLabel();
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/10367722/clearing-my-jframe-jpanel-in-a-new-game
    // MODIFIES: this
    // EFFECTS: if listOfWishlistSubject is empty, display default message. Else, display all current subjects
    public void initializeDisplayLabel() {
        if (currentLowls.length() == 0) {
            wsDisplayLabel.setText("No subject yet!");
            wsDisplayLabel.setBounds(10,10,220, 520);
            wsDisplayLabel.setFont(font);

            displayPanel.removeAll();
            displayPanel.add(wsDisplayLabel);
        } else {
            JList<String> currentLowlsNames = listToJlist();
            currentLowlsNames.setFont(font);

            displayPanel.removeAll();
            displayPanel.add(currentLowlsNames);
        }
        revalidate();
        repaint();
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/3269516/java-arraylists-into-jlist
    // MODIFIES: this
    // EFFECTS: converts a list of String to a JList, and returns it
    public JList<String> listToJlist() {
        List<String> names = currentLowls.getListOfWishlistSubjectNames();
        String[] str = new String[names.size()];
        return new JList<>(names.toArray(str));
    }
}
