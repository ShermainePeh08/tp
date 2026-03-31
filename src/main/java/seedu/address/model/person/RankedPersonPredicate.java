package seedu.address.model.person;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Represents a person predicate that provides comparator.
 */
public interface RankedPersonPredicate extends Predicate<Person> {

    Comparator<Person> createPersonComparator();
}
