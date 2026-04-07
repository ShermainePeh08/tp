package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s tags match any of the given keywords (case-insensitive).
 */
public class PersonTagContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    /**
     * Creates a predicate that matches any tag keyword.
     *
     * @param keywords cannot be null
     */
    public PersonTagContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);

        return person.getTags().stream().map(tag -> tag.tagName.toLowerCase(Locale.ROOT))
                .anyMatch(tagName -> keywords.stream().anyMatch(keyword ->
                        tagName.equals(keyword.toLowerCase(Locale.ROOT))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonTagContainsKeywordsPredicate)) {
            return false;
        }

        PersonTagContainsKeywordsPredicate otherPredicate = (PersonTagContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
