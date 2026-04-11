package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyVendorVault;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Retrieves the original command associated with the given alias text.
     * If the provided command text is a recognized alias, the corresponding original command is returned.
     * Otherwise, the input text is return as it is.
     */
    String getOriginalCommand(String commandText);

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the Inventory. */
    ReadOnlyInventory getInventory();

    /**
     * Returns the VendorVault.
     */
    ReadOnlyVendorVault getVendorVault();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Product> getFilteredProductList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Adds a command text into history for navigation.
     */
    void addCommandHistory(String commandText);

    /**
     * Returns the previous command text from history.
     */
    String getPrevCommandHistory(String currentInput);

    /**
     * Returns the next command text from history.
     */
    String getNextCommandHistory(String currentInput);
}
