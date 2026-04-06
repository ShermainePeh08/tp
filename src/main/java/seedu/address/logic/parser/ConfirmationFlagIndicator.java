package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utility class for detecting and handling confirmation flags in tokenized command inputs.
 */
public class ConfirmationFlagIndicator {

    /**
     * Checks whether the given token array contains the specified confirmation flag.
     */
    public static boolean containsConfirmationFlag(
            String[] tokens, String confirmationFlag, String exceptionMessage) throws ParseException {

        if (tokens.length <= 1) {
            return false;
        }

        boolean hasWronglyFormedFlag = Arrays.stream(tokens)
                .anyMatch(token -> isMalformedConfirmationFlag(token, confirmationFlag));
        if (hasWronglyFormedFlag) {
            throw new ParseException(exceptionMessage);
        }
        return Arrays.asList(tokens).contains(confirmationFlag);
    }

    private static boolean isMalformedConfirmationFlag(String token, String confirmationFlag) {
        return token.startsWith(confirmationFlag)
                && !token.equals(confirmationFlag);
    }

    /**
     * Returns a reconstructed command string with the confirmation flag removed.
     */
    public static String removeConfirmationFlag(String[] tokens, String confirmationFlag) {
        boolean removed = false;
        StringBuilder result = new StringBuilder();
        for (String token : tokens) {
            if (token.equals(confirmationFlag) && !removed) {
                removed = true;
            } else {
                result.append(token)
                        .append(" ");
            }
        }
        return result.toString();
    }
}
