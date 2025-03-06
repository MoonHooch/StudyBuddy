package managers;

import models.FlashCard;
import models.Loader;
import models.Subject;
import models.SubjectList;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class PanelManager {

    // Common Panel Sizes
    public static final Dimension TITLE_PANEL_SIZE = new Dimension(800, 80);
    public static final Dimension CONTENT_PANEL_SIZE = new Dimension(800, 500);
    public static final Dimension LABEL_PANEL_SIZE = new Dimension(800,40);
    public static final Dimension CONTENT_AREA_PANEL_SIZE = new Dimension(700,360);
    public static final Dimension DESCRIPTION_AREA_SIZE = new Dimension(700, 360);

    // Panel Colors
    public static final Color TITLE_PANEL_COLOR = new Color(0x4e1f77);
    public static final Color CONTENT_PANEL_COLOR = new Color(0x4e1f77);
    public static final Color LABEL_PANEL_COLOR = new Color(0x7e64a7);
    public static final Color CONTENT_AREA_PANEL_COLOR = new Color(0x8a79b4);
    public static final Color DESCRIPTION_AREA_COLOR = new Color(0x9a86c1);
    public static final Color SPECIAL_BUTTON_COLOR = new Color(0x4fc453);

    // Types of content loaded into the contentPanel
    public enum ContentType {
        SUBJECTS, FLASHCARDS, FLASHCARD_DESCRIPTION;
    }

    // Declared globally to help with back navigation
    public static Subject selectedSubject;


    /* Creates the titlePanel and titleLabel, then adds titlePanel to the frame.
    * This is effectively the top banner, and should persist regardless of what content is loaded to the contentPanel. */
    public static void createTitlePanel(JFrame frame) {
        JPanel titlePanel = new JPanel();
        configureTitlePanel(titlePanel);
        JLabel titleLabel = new JLabel("StudyBuddy");
        configureTitleLabel(titleLabel);

        // Assembly
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);
    }

    /* Creates the contentPanel, which consists of a labelPanel and a contentAreaPanel.
    * The labelPanel contains a label that describes the type of content is displayed in the content area panel.
    * The contentAreaPanel contains the relevant content.
    * This method is responsible for the general structure of the screen. The layout of the contentAreaPanel changes
    * depending on the contentType passed as an argument.
    * subjectName/flashcardDescription are only used for populateFlashcardList/populateFlashcardDescription */
    public static JPanel createContentPanel(
            String labelText,
            String subjectName,
            String flashcardDescription,
            JFrame frame,
            ContentType contentType)
    {
        //--CONTENT PANEL--
        JPanel contentPanel = new JPanel();
        configureContentPanel(contentPanel);

        //--LABEL PANEL--
        JPanel labelPanel = new JPanel();
        configureLabelPanel(labelPanel);
        JLabel contentLabel = new JLabel(labelText);
        configureContentLabel(contentLabel);
        // WORK IN PROGRESS CODE!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Creates a back button on all screens besides the home (subjects) screen.
        if (contentType != ContentType.SUBJECTS) {
            JButton backButton = new JButton("Back");
            configureBackButton(backButton);
            labelPanel.add(backButton, BorderLayout.EAST);
            if (contentType == ContentType.FLASHCARDS) {
                backButton.addActionListener(e ->{
                    JPanel newContentPanel = PanelManager.createContentPanel("Subjects", null, null, frame, PanelManager.ContentType.SUBJECTS);
                    frame.getContentPane().remove(frame.getContentPane().getComponent(1));
                    frame.add(newContentPanel, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                });
            }
            else if (contentType == ContentType.FLASHCARD_DESCRIPTION) {
                backButton.addActionListener(e ->{
                    JPanel newContentPanel = PanelManager.createContentPanel(labelText, labelText, null, frame, ContentType.FLASHCARDS);
                    frame.getContentPane().remove(frame.getContentPane().getComponent(1));
                    frame.add(newContentPanel, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                });

            }
        }


        //--CONTENT AREA PANEL--
        JPanel contentAreaPanel = new JPanel();
        configureContentAreaPanel(contentAreaPanel);
        // Creates scrollPane to wrap the contentAreaPanel
        JScrollPane scrollPane = new JScrollPane(
                contentAreaPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        configureScrollPane(scrollPane);

        // Decide which content to load into the contentPanel based on ContentType
        switch (contentType) {
            case SUBJECTS:
                populateSubjectList(contentAreaPanel, frame);
                break;
            case FLASHCARDS:
                populateFlashCardList(subjectName, contentAreaPanel, frame);
                break;
            case FLASHCARD_DESCRIPTION:
                populateFlashCardDescription(flashcardDescription, contentAreaPanel);
                break;
        }

        // Assembly
        labelPanel.add(contentLabel, BorderLayout.WEST);
        contentPanel.add(labelPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(contentPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    /* Populates the contentPanel with buttons representing each subject in the data file.
    * Clicking a button replaces the contents of the contentPanel with flashcards pertaining to the given subject. */
    private static void populateSubjectList(JPanel buttonPanel, JFrame frame) {
        // Load the data from the csv into a SubjectList
        SubjectList subjectList = Loader.loadFromCSV("data.csv");

        // Populates buttonPanel with subjectsButtons
        for (Subject subject : subjectList.getSubjects()) {
            JButton subjectButton = new JButton(subject.getSubjectName());
            configureButton(subjectButton, false);

            // Add action listener to the subjectButton
            subjectButton.addActionListener(e -> {
                JPanel newContentPanel = PanelManager.createContentPanel(subject.getSubjectName(), subject.getSubjectName(), null, frame, ContentType.FLASHCARDS);
                frame.getContentPane().remove(frame.getContentPane().getComponent(1));
                frame.add(newContentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });
            buttonPanel.add(subjectButton);
        }
        // Adds an "Add New Subject" button to the end of the list
        JButton addNewSubjectButton = new JButton("Add Subject");
        configureButton(addNewSubjectButton, true);

        // Assembly
        buttonPanel.add(addNewSubjectButton);
        adjustContentPanel(buttonPanel);
    }
    /* Populates the contentPanel with buttons representing each flashCard in the given subject
     * Clicking a button replaces the contents of the contentPanel with flashcards pertaining to the given subject. */
    private static void populateFlashCardList(String subjectName, JPanel buttonPanel, JFrame frame) {
        // Load the data from the csv into a SubjectList
        SubjectList subjectList = Loader.loadFromCSV("data.csv");

        // Get the specific subject by name
        for (Subject subject : subjectList.getSubjects()) {
            if (subject.getSubjectName().equals(subjectName)) {
                selectedSubject = subject;
                break;
            }
        }

        // Populates buttonPanel with flashcardButtons
        for (FlashCard flashCard : selectedSubject.getFlashCards()) {
            JButton flashCardButton = new JButton(flashCard.getItemName());
            configureButton(flashCardButton, false);

            // Add action listener to flashCardButton
            flashCardButton.addActionListener(e ->{
                JPanel newContentPanel = PanelManager.createContentPanel(flashCard.getItemName(), null, flashCard.getItemDescription(), frame, ContentType.FLASHCARD_DESCRIPTION);
                frame.getContentPane().remove(frame.getContentPane().getComponent(1));
                frame.add(newContentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            });
            buttonPanel.add(flashCardButton);
        }
        // Adds an "Add New Flashcard" button to the end of the list
        JButton addNewFlashCardButton = new JButton("Add Flash Card");
        configureButton(addNewFlashCardButton, true);

        // Assembly
        buttonPanel.add(addNewFlashCardButton);
        adjustContentPanel(buttonPanel);
    }

    /* Populates the contentPanel with a descriptionArea that acts as the "back" of a flash card. */
    private static void populateFlashCardDescription(String flashcardDescription, JPanel contentPanel) {
        JTextArea descriptionArea = new JTextArea(flashcardDescription);
        configureDescriptionArea(descriptionArea);
        // Assembly
        contentPanel.add(descriptionArea);
        adjustContentPanel(contentPanel);
    }

    /* Adjusts the height of the scrollPane to accommodate the amount of content loaded into it.
    * Method counts the number of buttons, then calculates the amount of height required based on the height of the
    * buttons and the vertical gap between the buttons. */
    // This method uses a lot of hardcoding, fix in future
    private static void adjustContentPanel(JPanel subjectButtonPanel) {
        // Define button dimensions and gaps
        int buttonHeight = ButtonSizeManager.LARGE_BUTTON_SIZE.height;
        int verticalGap = 10;

        // Count the number of buttons
        int numButtons = subjectButtonPanel.getComponentCount();
        // Prevents out of bounds exception for less than 3 subjects
        if (numButtons < 3) {
            numButtons = 3;
        }

        // Rounds buttons up to nearest multiple of 3
        if (numButtons % 3 == 1) {
            numButtons += 2;
        } else if (numButtons % 3 == 2) {
            numButtons += 1;
        }
        int numRows = numButtons / 3;

        // Calculate the total height needed for all rows, including vertical gaps
        int totalHeight = (numRows * buttonHeight) + ((numRows) * verticalGap + 10);

        if (totalHeight < 360) {
            totalHeight = 360;
        }

        // Adjust the height of the scroll pane to fit the buttons exactly
        subjectButtonPanel.setPreferredSize(new Dimension(subjectButtonPanel.getWidth(), totalHeight));

        // Force the panel to revalidate and update its layout
        subjectButtonPanel.revalidate();
    }

    public static void configureButton(JButton button, boolean isSpecial) {
        button.setFont(FontManager.BUTTON_FONT);
        button.setForeground(FontManager.BUTTON_TEXT_COLOR);
        button.setPreferredSize(ButtonSizeManager.LARGE_BUTTON_SIZE);
        if (isSpecial) {
            button.setBackground(SPECIAL_BUTTON_COLOR);
        }
    }

    public static void configureBackButton(JButton button) {
        button.setFont(FontManager.BUTTON_FONT);
        button.setPreferredSize(ButtonSizeManager.BACK_BUTTON_SIZE);
    }

    public static void configureScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Cleans up an unwanted border
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }

    public static void configureTitlePanel(JPanel titlePanel) {
        titlePanel.setBorder(BorderManager.EMPTY_BORDER);
        titlePanel.setPreferredSize(TITLE_PANEL_SIZE);
        titlePanel.setBackground(TITLE_PANEL_COLOR);
    }

    public static void configureContentPanel(JPanel contentPanel) {
        contentPanel.setBorder(BorderManager.EMPTY_BORDER);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setPreferredSize(CONTENT_PANEL_SIZE);
        contentPanel.setBackground(CONTENT_PANEL_COLOR);
    }

    public static void configureLabelPanel(JPanel labelPanel) {
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setBackground(LABEL_PANEL_COLOR);
        labelPanel.setPreferredSize(LABEL_PANEL_SIZE);

    }

    public static void configureContentAreaPanel(JPanel contentAreaPanel) {
        contentAreaPanel.setBackground(CONTENT_AREA_PANEL_COLOR);
        contentAreaPanel.setPreferredSize(CONTENT_AREA_PANEL_SIZE);
        contentAreaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    public static void configureDescriptionArea(JTextArea descriptionArea) {
        descriptionArea.setFont(FontManager.BODY_FONT);
        descriptionArea.setBackground(DESCRIPTION_AREA_COLOR);
        descriptionArea.setForeground(FontManager.BODY_TEXT_COLOR);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false); // Make it read-only
        descriptionArea.setPreferredSize(DESCRIPTION_AREA_SIZE);
    }

    public static void configureTitleLabel(JLabel titleLabel) {
        titleLabel.setFont(FontManager.TITLE_FONT);
        titleLabel.setForeground(FontManager.TITLE_COLOR);
    }

    public static void configureContentLabel(JLabel contentLabel) {
        contentLabel.setFont(FontManager.SUBTITLE_FONT);
        contentLabel.setForeground(FontManager.SUBTITLE_COLOR);
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

}

