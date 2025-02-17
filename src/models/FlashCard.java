package models;

public class FlashCard {

    private String itemName;
    private String itemDescription;

    // Constructors
    public FlashCard() {

    }

    public FlashCard(String name, String description) {
        // Uses setters to enforce validation
        setItemName(name);
        setItemDescription(description);
    }

    // Setters
    public void setItemName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Item name field cannot be empty");
        }
        itemName = name;
    }

    public void setItemDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Item description cannot be null or empty");
        }
        this.itemDescription = description;
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
