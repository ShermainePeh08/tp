package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ParseResult class.
 */
public class ParseResultTest {

    @Test
    public void constructor_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ParseResult<>(null, Optional.empty()));
    }

    @Test
    public void constructor_nullWarning_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ParseResult<>("value", null));
    }

    @Test
    public void getValue() {
        // EP: no warning -> returns value
        ParseResult<String> parseResult = new ParseResult<>("value", Optional.empty());
        assertEquals("value", parseResult.getValue());

        // EP: with warning -> returns value unaffected by warning
        ParseResult<String> withWarning = new ParseResult<>("value", Optional.of("warn"));
        assertEquals("value", withWarning.getValue());
    }

    @Test
    public void getWarning() {
        // EP: no warning -> returns empty optional
        ParseResult<String> parseResult = new ParseResult<>("value", Optional.empty());
        assertEquals(Optional.empty(), parseResult.getWarning());

        // EP: with warning -> returns warning
        ParseResult<String> withWarning = new ParseResult<>("value", Optional.of("warn"));
        assertEquals(Optional.of("warn"), withWarning.getWarning());
    }

    @Test
    public void equals() {
        ParseResult<String> parseResult = new ParseResult<>("value", Optional.empty());

        // EP: same values -> returns true
        assertTrue(parseResult.equals(new ParseResult<>("value", Optional.empty())));

        // EP: same object -> returns true
        assertTrue(parseResult.equals(parseResult));

        // EP: null -> returns false
        assertFalse(parseResult.equals(null));

        // EP: different types -> returns false
        assertFalse(parseResult.equals(0.5f));

        // EP: different value, same warning -> returns false
        assertFalse(parseResult.equals(new ParseResult<>("different", Optional.empty())));

        // EP: same value, different warning -> returns false
        assertFalse(parseResult.equals(new ParseResult<>("value", Optional.of("warning"))));

        // EP: same value and warning -> returns true
        ParseResult<String> withWarning = new ParseResult<>("value", Optional.of("warn"));
        assertTrue(withWarning.equals(new ParseResult<>("value", Optional.of("warn"))));

        // EP: different value and different warning -> returns false
        assertFalse(parseResult.equals(new ParseResult<>("different", Optional.of("warning"))));
    }

    @Test
    public void hashcode() {
        ParseResult<String> parseResult = new ParseResult<>("value", Optional.empty());

        // EP: same values -> returns same hashcode
        assertEquals(parseResult.hashCode(), new ParseResult<>("value", Optional.empty()).hashCode());

        // EP: different value -> returns different hashcode
        assertNotEquals(parseResult.hashCode(), new ParseResult<>("different", Optional.empty()).hashCode());

        // EP: different warning -> returns different hashcode
        assertNotEquals(parseResult.hashCode(), new ParseResult<>("value", Optional.of("warning")).hashCode());

        // EP: same value and warning -> returns same hashcode
        ParseResult<String> withWarning = new ParseResult<>("value", Optional.of("warn"));
        assertEquals(withWarning.hashCode(), new ParseResult<>("value", Optional.of("warn")).hashCode());

        // EP: different value and different warning -> returns different hashcode
        assertNotEquals(parseResult.hashCode(), new ParseResult<>("different", Optional.of("warning")).hashCode());
    }

    @Test
    public void toStringMethod() {
        ParseResult<String> parseResult = new ParseResult<>("testValue", Optional.empty());
        assertEquals("ParseResult[value=testValue, warning=Optional.empty]", parseResult.toString());

        ParseResult<String> parseResultWithWarning = new ParseResult<>("testValue", Optional.of("testWarning"));
        assertEquals("ParseResult[value=testValue, warning=Optional[testWarning]]", parseResultWithWarning.toString());
    }
}

