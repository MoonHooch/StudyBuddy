package models;

import java.util.List;
import java.util.ArrayList;

public class Subject {

    private String subjectName;
    private List<FlashCard> flashCards;

    // Constructor
    public Subject(String subjectName) {
        setSubjectName(subjectName);
        this.flashCards = new ArrayList<>();
    }

    // Setters
    public void setSubjectName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("models.Subject name field cannot be empty");
        }
        this.subjectName = name;
    }

    // Getters
    public String getSubjectName() {
        return subjectName;
    }

    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    // Adds flashcard to models.Subject
    public void addFlashCard(FlashCard flashCard) {
        if (flashCard == null) {
            throw new IllegalArgumentException("Flashcard cannot be null");
        }
        flashCards.add(flashCard);
    }

    // Removes flashcard from models.Subject
    public void removeFlashCard(FlashCard flashCard) {
        flashCards.remove(flashCard);
    }

}