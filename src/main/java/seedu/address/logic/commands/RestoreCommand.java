package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Restores an archived vendor in the address book.
 * The vendor is identified using the index from the currently displayed vendor list.
 *
 * Usage: restore vendor INDEX
 * Example: restore vendor 1
 *
 * Note: Internally vendors are represented as {@code Person}.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " INDEX\n"
            + "Restores the archived vendor identified by the index number.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESTORE_SUCCESS =
            "Restored Vendor: %1$s";

    private final Index targetIndex;

    /**
     * Creates a RestoreCommand to restore the vendor at the specified {@code Index}.
     *
     * @param targetIndex Index of the vendor in the filtered vendor list.
     */
    public RestoreCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the restore command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult indicating the result of the command execution.
     * @throws CommandException if the index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // get archived persons WITHOUT changing UI
        List<Person> archivedList = model.getAddressBook().getPersonList()
                .stream()
                .filter(Person::isArchived)
                .toList();

        if (targetIndex.getZeroBased() >= archivedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRestore = archivedList.get(targetIndex.getZeroBased());

        model.restorePerson(personToRestore);

        // Only update UI after success
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ACTIVE_PERSONS);

        return new CommandResult(String.format(MESSAGE_RESTORE_SUCCESS, personToRestore));
    }

    @Override
    public PendingConfirmation getPendingConfirmation() {
        return new PendingConfirmation();
    }
}
