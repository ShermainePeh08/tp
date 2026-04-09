package seedu.address.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collection of utility methods for finding JSON field occurrences and formatting line references.
 */
final class JsonLineReferenceUtil {

    private static final String EXACT_FIELD_PATTERN_TEMPLATE = "\"%s\"\\s*:\\s*\"%s\"";

    private JsonLineReferenceUtil() {}

    static List<Integer> findFieldLineNumbers(Path filePath, String jsonField, String fieldValue) {
        Pattern pattern = buildExactFieldPattern(jsonField, fieldValue);
        Set<Integer> matchedLineNumbers = new LinkedHashSet<>();

        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                Matcher matcher = pattern.matcher(lines.get(i));
                while (matcher.find()) {
                    matchedLineNumbers.add(i + 1);
                }
            }
        } catch (IOException e) {
            return List.of();
        }

        return new ArrayList<>(matchedLineNumbers);
    }

    static String formatLineReference(List<Integer> lineNumbers) {
        String formattedLineNumbers = lineNumbers.stream()
                .map(String::valueOf)
                .reduce((left, right) -> left + ", " + right)
                .orElse("unknown");

        return (lineNumbers.size() <= 1 ? "line " : "lines ") + formattedLineNumbers;
    }

    private static Pattern buildExactFieldPattern(String jsonField, String fieldValue) {
        String regex = EXACT_FIELD_PATTERN_TEMPLATE.formatted(Pattern.quote(jsonField), Pattern.quote(fieldValue));
        return Pattern.compile(regex);
    }
}
