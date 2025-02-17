package models;

import java.io.*;
import java.util.*;

public class Loader {

    // Method to load data from a CSV file
    public static SubjectList loadFromCSV(String filePath) {
        SubjectList subjectList = new SubjectList();
        Map<String, Subject> subjectMap = new HashMap<>();  // Temporary map to avoid duplicate subjects

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line

            // Assigns line to the next line in the BufferedReader, then performs the comparison
            while ((line = br.readLine()) != null) {
                // String array is populated by csv
                String[] data = line.split(",");

                // Each datum is trimmed of whitespace and named for clarity
                String subjectName = data[0].trim();
                String flashCardName = data[1].trim();
                String flashCardDescription = data[2].trim();

                // Check if the subject already exists in the map
                Subject subject = subjectMap.get(subjectName);
                if (subject == null) {
                    subject = new Subject(subjectName);
                    subjectMap.put(subjectName, subject);
                    subjectList.addSubject(subject);
                }

                // Creates a flashcard and add it to the subject
                FlashCard flashCard = new FlashCard(flashCardName, flashCardDescription);
                subject.addFlashCard(flashCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subjectList;
    }
}