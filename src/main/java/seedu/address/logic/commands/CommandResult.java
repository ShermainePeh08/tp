package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public static final String FEEDBACK_TYPE_SUCCESS = "success";
    public static final String FEEDBACK_TYPE_WARN = "warn";
    public static final String FEEDBACK_TYPE_ERROR = "error";

    private final String feedbackToUser;
    private final String feedbackType;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, FEEDBACK_TYPE_SUCCESS);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields and feedback type.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String feedbackType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.feedbackType = requireNonNull(feedbackType);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, FEEDBACK_TYPE_SUCCESS);
    }

    /**
     * Constructs a {@code CommandResult} with feedback text and feedback type.
     */
    public CommandResult(String feedbackToUser, String feedbackType) {
        this(feedbackToUser, false, false, feedbackType);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && feedbackType.equals(otherCommandResult.feedbackType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, feedbackType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("feedbackType", feedbackType)
                .toString();
    }

}
