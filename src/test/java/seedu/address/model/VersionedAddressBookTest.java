package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class VersionedAddressBookTest {

    private AddressBook addressBook;
    private VersionedAddressBook versionedAddressBook;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();
        versionedAddressBook = new VersionedAddressBook(addressBook);
    }

    @Test
    public void commit_addNewState_pointerMovesForward() {
        AddressBook newState = new AddressBook();
        versionedAddressBook.commit(newState);

        assertTrue(versionedAddressBook.canUndo()); // should now be able to undo
    }

    @Test
    public void undo_afterCommit_restoresPreviousState() {
        // initial commit
        AddressBook state1 = new AddressBook();
        state1.addPerson(new PersonBuilder().build());
        versionedAddressBook.commit(state1);

        // commit second state
        AddressBook state2 = new AddressBook();
        state2.addPerson(new PersonBuilder().withName("John").build());
        versionedAddressBook.commit(state2);

        // undo to previous state
        versionedAddressBook.undo(state2);

        // state2 should now match state1
        assertEquals(state1, state2);
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void redo_afterUndo_restoresNextState() {
        // initial commit
        AddressBook state1 = new AddressBook();
        state1.addPerson(new PersonBuilder().build());
        versionedAddressBook.commit(state1);

        // commit second state
        AddressBook state2 = new AddressBook();
        state2.addPerson(new PersonBuilder().withName("John").build());
        versionedAddressBook.commit(state2);

        // undo
        versionedAddressBook.undo(state2);

        // redo
        // TODO: implement when redo is implemented
        // versionedAddressBook.redo(state2);

        // state2 should match state2 again
        // assertEquals(state2, state2); // just checking redo doesn't throw
    }

    @Test
    public void undo_atInitialState_throwsException() {
        assertThrows(IllegalStateException.class, () -> versionedAddressBook.undo(addressBook));
    }

    @Test
    public void redo_atLatestState_throwsException() {
        assertThrows(IllegalStateException.class, () -> versionedAddressBook.redo(addressBook));
    }
}
