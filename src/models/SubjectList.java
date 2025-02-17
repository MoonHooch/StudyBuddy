package models;

import java.util.ArrayList;
import java.util.List;

public class SubjectList {

    private final List<Subject> subjects;

    // Constructor
    public SubjectList() {
        this.subjects = new ArrayList<>();
    }

    // Gets a copy of the subject list
    public List<Subject> getSubjects() {
        return new ArrayList<>(subjects);
    }

    // Add a subject to the models.SubjectList
    public void addSubject(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException("models.Subject cannot be null");
        }
        subjects.add(subject);
    }

    // Removes a subject from models.SubjectList
    public void removeSubject(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException("models.Subject cannot be null");
        }
        subjects.remove(subject);
    }
}
