package seedu.address.model.util;

import java.util.Arrays;

/**
 * Contains utility methods for similarity scoring for duplicate warnings.
 */
public class SimilarityScoringUtil {
    /**
     * Returns the length of the longest contiguous substring common to both strings a and b.
     *
     * @param a the first string to compare.
     * @param b the second string to compare.
     * @return the length of the longest contiguous substring common to both strings.
     */
    public static int longestContiguousMatch(String a, String b) {
        String aLower = a.toLowerCase();
        String bLower = b.toLowerCase();

        int[] prev = new int[bLower.length() + 1];
        int[] curr = new int[bLower.length() + 1];
        int best = 0;

        for (int i = 1; i <= aLower.length(); i++) {
            for (int j = 1; j <= bLower.length(); j++) {
                if (aLower.charAt(i - 1) == bLower.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + 1;
                    best = Math.max(best, curr[j]);
                } else {
                    curr[j] = 0;
                }
            }
            // Swap and clear for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
            Arrays.fill(curr, 0);
        }

        return best;
    }
}
