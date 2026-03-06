package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String ERROR_STYLE_CLASS = "error";
    private static final String WARN_STYLE_CLASS = "warn";
    private static final String SUCCESS_STYLE_CLASS = "success";
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a {@code ResultDisplay} with the given {@code CommandResult}.
     */
    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Sets the feedback to the user and applies the appropriate style based on the feedback type.
     *
     * @param feedbackToUser the feedback message to be displayed to the user.
     * @param feedbackType the type of feedback, which determines the style to be applied.
     */
    public void setFeedbackToUser(String feedbackToUser, String feedbackType) {
        requireNonNull(feedbackToUser);
        requireNonNull(feedbackType);

        String styleClass = mapFeedbackTypeToStyleClass(feedbackType);
        setFeedbackWithStyle(feedbackToUser, styleClass);
    }

    private String mapFeedbackTypeToStyleClass(String feedbackType) {
        if (CommandResult.FEEDBACK_TYPE_ERROR.equals(feedbackType)) {
            return ERROR_STYLE_CLASS;
        }

        if (CommandResult.FEEDBACK_TYPE_WARN.equals(feedbackType)) {
            return WARN_STYLE_CLASS;
        }

        return SUCCESS_STYLE_CLASS;
    }

    private void setFeedbackWithStyle(String feedbackToUser, String styleClass) {
        resultDisplay.setText(feedbackToUser);
        resultDisplay.getStyleClass().removeAll(ERROR_STYLE_CLASS, WARN_STYLE_CLASS, SUCCESS_STYLE_CLASS);
        resultDisplay.getStyleClass().add(styleClass);
    }

}
