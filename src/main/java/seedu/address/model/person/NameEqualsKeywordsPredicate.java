package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Test that a {@code Person} matches exactly with another {@code Person}
 */
public class NameEqualsKeywordsPredicate implements Predicate<Person> {
    private final Person person;

    /**
     * Creates a predicate that matches a person's name exactly.
     *
     * @param person cannot be null
     */
    public NameEqualsKeywordsPredicate(Person person) {
        this.person = requireNonNull(person);
    }

    @Override
    public boolean test(Person other) {
        return this.person.equals(other);
    }
}
