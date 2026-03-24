package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    public static final int WORD_MATCH_SCORE_NO_MATCH = 0;
    public static final int WORD_MATCH_SCORE_SUBSTRING = 1;
    public static final int WORD_MATCH_SCORE_PREFIX = 2;
    public static final int WORD_MATCH_SCORE_EXACT = 3;

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a token-level partial match score between {@code sentenceWord} and {@code word}, ignoring case.
     * Score ordering is: exact (3) &gt; prefix (2) &gt; substring (1) &gt; no match (0).
     *
     * @param sentenceWord cannot be null, cannot be empty, must be a single word
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static int getWordPartialMatchScoreIgnoreCase(String sentenceWord, String word) {
        requireNonNull(sentenceWord);
        requireNonNull(word);

        String preppedSentenceWord = sentenceWord.trim();
        String preppedWord = word.trim();
        checkArgument(!preppedSentenceWord.isEmpty(), "Sentence word parameter cannot be empty");
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedSentenceWord.split("\\s+").length == 1,
                "Sentence word parameter should be a single word");
        checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        String sentenceWordLower = preppedSentenceWord.toLowerCase(Locale.ROOT);
        String wordLower = preppedWord.toLowerCase(Locale.ROOT);

        if (sentenceWordLower.equals(wordLower)) {
            return WORD_MATCH_SCORE_EXACT;
        }
        if (sentenceWordLower.startsWith(wordLower)) {
            return WORD_MATCH_SCORE_PREFIX;
        }
        if (sentenceWordLower.contains(wordLower)) {
            return WORD_MATCH_SCORE_SUBSTRING;
        }
        return WORD_MATCH_SCORE_NO_MATCH;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} can be parsed as an integer.
     *
     */
    public static boolean isValidInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
