package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

public class ConfirmationCommand extends Command{

    public static final String COMMAND_WORD = "y";

    public static final String MESSAGE_SUCCESS = "confirmation";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
