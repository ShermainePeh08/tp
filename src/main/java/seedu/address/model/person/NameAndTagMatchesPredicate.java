package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} matches both name and tag search predicates.
 */
public class NameAndTagMatchesPredicate implements RankedPersonPredicate {

    private final NameContainsKeywordsScoredPredicate namePredicate;
    private final PersonTagContainsKeywordsPredicate tagPredicate;

    /**
     * Creates a predicate that requires both a name match and a tag match.
     */
    public NameAndTagMatchesPredicate(NameContainsKeywordsScoredPredicate namePredicate,
                                      PersonTagContainsKeywordsPredicate tagPredicate) {
        requireNonNull(namePredicate);
        requireNonNull(tagPredicate);
        this.namePredicate = namePredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return namePredicate.test(person) && tagPredicate.test(person);
    }

    @Override
    public Comparator<Person> createPersonComparator() {
        return namePredicate.createPersonComparator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameAndTagMatchesPredicate)) {
            return false;
        }

        NameAndTagMatchesPredicate otherPredicate = (NameAndTagMatchesPredicate) other;
        return namePredicate.equals(otherPredicate.namePredicate)
                && tagPredicate.equals(otherPredicate.tagPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("tagPredicate", tagPredicate)
                .toString();
    }
}
