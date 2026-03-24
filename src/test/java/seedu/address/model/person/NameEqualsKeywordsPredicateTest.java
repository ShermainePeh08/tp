package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameEqualsKeywordsPredicateTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NameEqualsKeywordsPredicate(null));
    }

    @Test
    public void test_samePerson_returnsTrue() {
        Person person = new PersonBuilder().withName("Alice").build();
        NameEqualsKeywordsPredicate predicate = new NameEqualsKeywordsPredicate(person);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_differentPerson_returnsFalse() {
        Person expected = new PersonBuilder().withName("Alice").build();
        Person other = new PersonBuilder().withName("Bob").build();
        NameEqualsKeywordsPredicate predicate = new NameEqualsKeywordsPredicate(expected);

        assertFalse(predicate.test(other));
    }
}
