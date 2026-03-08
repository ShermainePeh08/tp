package seedu.address.model.person.warnings;

/**
 * Represents a warning that is generated when a duplicate person is detected.
 */
public class DuplicatePersonWarning {

    public static final String MESSAGE_SIMILAR_NAME = "⚠ Warning: There's a contact with a similar name (name: %s), "
            + "is this intentional?";
    public static final String MESSAGE_SIMILAR_ADDRESS = "⚠ Warning: There's a contact with a similar address "
            + "(name: %s, address: %s), is this intentional?";

    private final boolean value;
    private final String warning;
    private String fieldMatchedWith;

    /**
     * Constructs a {@code DuplicatePersonWarning} with the specified value and warning message.
     *
     * @param value   The boolean value indicating whether potential duplicate is detected.
     * @param warning The string warning message associated with the duplicate person detection.
     */
    public DuplicatePersonWarning(boolean value, String warning) {
        this.value = value;
        this.warning = warning;
    }

    public boolean getValue() {
        return value;
    }

    public String getWarning() {
        return warning;
    }

}
