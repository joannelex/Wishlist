package ui;

import model.SubjectOption;
import model.WishlistSubject;
import model.exception.DuplicateInputException;
import model.exception.InvalidRatingException;
import model.exception.InvalidStringInputException;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

// represents application's main window frame
// this class references code from Youtube, CPSC210/SimpleDrawingPLayer-Complete
// https://youtu.be/BuW7y21FcYI
// https://youtu.be/Kmgo00avvEw
public class SubjectOptionUI extends JFrame implements ActionListener {

    public static final int WIDTH_FRAME = 400;
    public static final int HEIGHT_FRAME = 700;

    private WishlistSubject currentWls;
    private WishlistUI wishlistUI;

    private Font font;

    private ImageIcon icon;
    private ImageIcon warning;

    private JPanel windowPanel;
    private JPanel labelPanel;
    private JPanel buttonsPanel;
    private JPanel displayPanel;
    private JPanel subjectOptionPanel;

    private JLabel mainLabel;
    private JLabel soDisplayLabel;

    private JButton buttonAddOption;
    private JButton buttonMinusOption;
    private JButton buttonChoose;
    private JButton buttonBack;

    // EFFECT: constructor sets up a visual window of given WishlistSubject
    public SubjectOptionUI(WishlistSubject currentWls, WishlistUI wishlistUI) {
        super("Adventure");
        this.currentWls = currentWls;
        this.wishlistUI = wishlistUI;

        initializeSoFields();
        initializeSoGraphics();
    }

    // this method uses image icon from: Wikimedia Commons
    // https://commons.wikimedia.org/wiki/File:OOjs_UI_icon_alert-destructive.svg
    // MODIFIES: this
    // EFFECT: sets fields for SubjectOptionUI class
    //         this method is called by the SubjectOptionUI constructor
    public void initializeSoFields() {
        this.font = new Font("Arial", Font.PLAIN, 11);
        this.icon = new ImageIcon("./img/icon.png");
        this.warning = new ImageIcon("./img/warning.png");

        this.soDisplayLabel = new JLabel();

        this.displayPanel = new JPanel();
        this.labelPanel = new JPanel();
        this.windowPanel = new JPanel();
        this.buttonsPanel = new JPanel();
    }

    // MODIFIES: this
    // EFFECT: draws the JFrame window where SubjectOptionUI will operate, populates JPanels, JLabels and JButtons
    public void initializeSoGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        getContentPane().setBackground(Color.BLACK);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(currentWls.getWishlistSubjectName());

        initializePanels();
        initializeLabels();
        initializeButtons();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the display panel for the middle window of application, sets colour of background
    public void initializeDisplayPanel() {
        JPanel wsPaddingPanelNorth = new JPanel();
        JPanel wsPaddingPanelEast = new JPanel();
        JPanel wsPaddingPanelSouth = new JPanel();
        JPanel wsPaddingPanelWest = new JPanel();
        wsPaddingPanelNorth.setBackground(new Color(88, 169, 252));
        wsPaddingPanelEast.setBackground(new Color(88, 169, 252));
        wsPaddingPanelSouth.setBackground(new Color(88, 169, 252));
        wsPaddingPanelWest.setBackground(new Color(88, 169, 252));
        wsPaddingPanelNorth.setPreferredSize(new Dimension(80, 80));
        wsPaddingPanelEast.setPreferredSize(new Dimension(80, 80));
        wsPaddingPanelSouth.setPreferredSize(new Dimension(80, 80));
        wsPaddingPanelWest.setPreferredSize(new Dimension(80, 80));

        displayPanel.setBackground(Color.WHITE);

        windowPanel.add(wsPaddingPanelNorth, BorderLayout.NORTH);
        windowPanel.add(wsPaddingPanelEast, BorderLayout.EAST);
        windowPanel.add(wsPaddingPanelSouth, BorderLayout.SOUTH);
        windowPanel.add(wsPaddingPanelWest, BorderLayout.WEST);
        windowPanel.add(displayPanel, BorderLayout.CENTER);
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
        initializeDisplayPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons panel
    public void initializeButtonsPanel() {
        buttonsPanel.setLayout(new GridLayout(4,1));
        buttonsPanel.setBackground(new Color(88, 169, 252));
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes the labels
    public void initializeLabels() {
        mainLabel = new JLabel(currentWls.getWishlistSubjectName());
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setForeground(new Color(88, 169, 252));
        mainLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        mainLabel.setBounds(0,60,0,0);
        labelPanel.add(mainLabel, BorderLayout.AFTER_LAST_LINE);

        initializeDisplayLabel();
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/10367722/clearing-my-jframe-jpanel-in-a-new-game
    // MODIFIES: this
    // EFFECTS: if list of subject option is empty, display default message. Else, display all current subjects
    public void initializeDisplayLabel() {
        if (currentWls.getSubjectOptions().size() == 0) {
            soDisplayLabel.setText("No options yet!");
            soDisplayLabel.setBounds(10,10,220, 520);
            soDisplayLabel.setFont(new Font("Arial", Font.PLAIN, 10));

            displayPanel.removeAll();
            displayPanel.add(soDisplayLabel);

        } else {
            JList<String> currentSoNames = listToJlist();
            currentSoNames.setFont(new Font("Arial", Font.PLAIN, 10));

            displayPanel.removeAll();
            displayPanel.add(currentSoNames);
        }
        revalidate();
        repaint();
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/3269516/java-arraylists-into-jlist
    // MODIFIES: this
    // EFFECTS: converts a list of String to a JList, and returns it
    public JList<String> listToJlist() {
        List<String> reformatedOptions = currentWls.displayAllOptions();
        String[] str = new String[reformatedOptions.size()];
        JList<String> currentSoNames = new JList<>(reformatedOptions.toArray(str));
        return currentSoNames;
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons for the button panel; sets font and colour
    public void initializeButtons() {
        buttonAddOption = setUpButton("Add option");
        buttonMinusOption = setUpButton("Remove option");
        buttonChoose = setUpButton("CHOOSE");
        buttonBack = setUpButton("Back");

        addSoButtons();
        registerSoButtonListenerToButton();
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

    // MODIFIES: this
    // EFFECTS: adds initialized buttons to button panel
    public void addSoButtons() {
        buttonsPanel.add(buttonAddOption);
        buttonsPanel.add(buttonMinusOption);
        buttonsPanel.add(buttonChoose);
        buttonsPanel.add(buttonBack);
    }

    // This methods references code from Oracle
    // https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
    // MODIFIES: this
    // EFFECTS: registers each button to ActionListener
    public void registerSoButtonListenerToButton() {
        buttonAddOption.addActionListener(this);
        buttonMinusOption.addActionListener(this);
        buttonChoose.addActionListener(this);
        buttonBack.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: assign each button to an action to be perfromed when clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAddOption) {
            addSubjectOption();
            initializeDisplayLabel();
        } else if (e.getSource() == buttonMinusOption) {
            if (currentWls.length() != 0) {
                removeSubjectOption();
            }
            initializeDisplayLabel();
        } else if (e.getSource() == buttonChoose) {
            if (currentWls.length() != 0) {
                chooseSubjectOption();
            }
        } else if (e.getSource() == buttonBack) {
            this.dispose();
            wishlistUI.setVisible(true);
        }
    }

    // this method references code from: StackOverflow
    // https://stackoverflow.com/questions/13334198/java-custom-buttons-in-showinputdialog
    // REQUIRES: list of subject options is not empty
    // MODIFIES: this
    // EFFECTS: displays JOptionPane with options to generate random choice
    public void chooseSubjectOption() {
        subjectOptionPanel = new JPanel();
        String[] options = {"By rating", "By category", "All options"};

        int reply = JOptionPane.showOptionDialog(displayPanel, "How would you like to generate your choice?",
                "Generate choice", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                icon, options, 0);

        if (reply == JOptionPane.YES_OPTION) {
            // By rating
            generateByRating();
        } else if (reply == JOptionPane.NO_OPTION) {
            // By category
            generateByCategory();
        } else if (reply == JOptionPane.CANCEL_OPTION) {
            // all options
            generateFromAll();
        }
    }

    // REQUIRES: list of subject options is not empty
    // MODIFIES: this
    // EFFECT: generates and displays a random decision from all the options
    //          If exception is caught, display according error message
    public void generateFromAll() {
        Random random = new Random();
        int randomPositionNumber = random.nextInt(currentWls.length());
        SubjectOption randomSubjectOption = currentWls.getOptionGivenIndex(randomPositionNumber);

        displayMsg("We choose: " + randomSubjectOption.getName(), "CHOOSE");
    }

    // REQUIRES: list of subject options is not empty
    // MODIFIES: this
    // EFFECT: generates and displays a random decision from options with given category
    //          If exception is caught, display according error message
    public void generateByCategory() {
        subjectOptionPanel = new JPanel();
        JTextField givenCategory = new JTextField(10);
        givenCategory.setFont(font);

        JLabel message = new JLabel("What category would you like to draw from?");
        message.setFont(font);
        subjectOptionPanel.add(message);
        subjectOptionPanel.add(givenCategory);

        int reply = JOptionPane.showConfirmDialog(displayPanel, subjectOptionPanel,
                "New Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            List<SubjectOption> listForRandomChoiceByCategory;
            try {
                listForRandomChoiceByCategory = currentWls.getOptionGivenCategory(givenCategory.getText());
                Random random = new Random();
                int randomPositionNumber = random.nextInt(listForRandomChoiceByCategory.size());
                SubjectOption randomSubjectOption = listForRandomChoiceByCategory.get(randomPositionNumber);

                displayMsg("We choose: " + randomSubjectOption.getName(), "CHOOSE");
            } catch (NullPointerException e) {
                warningMsg("Options with given category does not exist.", "CHOOSE");
            } catch (IllegalArgumentException e) {
                warningMsg("Invalid input", "CHOOSE");
            }
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

    // REQUIRES: list of subject options is not empty
    // MODIFIES: this
    // EFFECT: generates and displays a random decision from options with given rating
    //         If exception is caught, display according error message
    public void generateByRating() {
        subjectOptionPanel = new JPanel();
        JTextField givenRating = new JTextField(10);
        givenRating.setFont(font);
        JLabel message = new JLabel("What rating would you like to draw from?");
        message.setFont(font);
        subjectOptionPanel.add(message);
        subjectOptionPanel.add(givenRating);

        int reply = JOptionPane.showConfirmDialog(displayPanel, subjectOptionPanel,
                "New Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            List<SubjectOption> listForRandomChoiceByRating;
            try {
                listForRandomChoiceByRating = currentWls.getOptionGivenRating(Integer.parseInt(givenRating.getText()));
                Random random = new Random();
                int randomPositionNumber = random.nextInt(listForRandomChoiceByRating.size());
                SubjectOption randomSubjectOption = listForRandomChoiceByRating.get(randomPositionNumber);

                displayMsg("We choose: " + randomSubjectOption.getName(), "CHOOSE");
            } catch (NullPointerException e) {
                warningMsg("Options with given rating does not exist.", "CHOOSE");
            } catch (IllegalArgumentException e) {
                warningMsg("Invalid input-please enter a number for rating.", "CHOOSE");
            }
        }
    }

    // REQUIRES: list of subject options is not empty
    // MODIFIES: this
    // EFFECTS: if option with given name exists, removes the option from current lis of options.
    //          If exception is caught, display according error message
    public void removeSubjectOption() {
        subjectOptionPanel = new JPanel();
        JTextField optionName = new JTextField(10);
        optionName.setFont(font);
        JLabel message = new JLabel("Enter the name of option to delete: ");
        message.setFont(font);
        subjectOptionPanel.add(message);
        subjectOptionPanel.add(optionName);

        int reply = JOptionPane.showConfirmDialog(displayPanel, subjectOptionPanel,
                "Remove Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            try {
                currentWls.removeOption(optionName.getText());
            } catch (NullPointerException e) {
                warningMsg("Option with given name does not exist.", "Remove Option");
            } catch (InvalidStringInputException e) {
                warningMsg("Invalid input", "Remove Option");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if option with given name is unique, add the option to current list of options.
    //          If exception is caught, display according error message
    public void addSubjectOption() {
        subjectOptionPanel = new JPanel();
        JTextField name = new JTextField(10);
        JTextField category = new JTextField(10);
        JTextField rating = new JTextField(10);
        name.setFont(font);
        category.setFont(font);
        rating.setFont(font);

        JLabel nameLabel = new JLabel("Enter option name: ");
        nameLabel.setFont(font);
        JLabel categoryLabel = new JLabel("Enter category: ");
        categoryLabel.setFont(font);
        JLabel ratingLabel = new JLabel("Enter rating: ");
        ratingLabel.setFont(font);

        addSubjectOptionToPanel(name, category, rating, nameLabel, categoryLabel, ratingLabel);

        int reply = JOptionPane.showConfirmDialog(displayPanel, subjectOptionPanel,
                "New option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

        if (reply == JOptionPane.OK_OPTION) {
            tryCatchForAddSubjectOption(name, category, rating);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds components required for collecting information about new SubjectOption, to subjectOption panel
    private void addSubjectOptionToPanel(JTextField name, JTextField category, JTextField rating,
                                         JLabel nameLabel, JLabel categoryLabel, JLabel ratingLabel) {
        subjectOptionPanel.add(nameLabel);
        subjectOptionPanel.add(name);
        subjectOptionPanel.add(categoryLabel);
        subjectOptionPanel.add(category);
        subjectOptionPanel.add(ratingLabel);
        subjectOptionPanel.add(rating);
    }

    private void tryCatchForAddSubjectOption(JTextField name, JTextField category, JTextField rating) {
        try {
            currentWls.addOption(new SubjectOption(name.getText(), category.getText(),
                    Integer.parseInt(rating.getText())));
        } catch (IllegalArgumentException e) {
            warningMsg("Invalid input", "Add Option");
        } catch (DuplicateInputException e) {
            warningMsg("Option with same name already exists.", "Add Option");
        } catch (InvalidStringInputException e) {
            warningMsg("Invalid name or category input.", "Add Option");
        } catch (InvalidRatingException e) {
            warningMsg("Rating must be in between 0 to 5.", "Add Option");
        }
    }

}
