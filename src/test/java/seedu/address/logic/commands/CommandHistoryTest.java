package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private static final String CMD_LIST = "list";
    private static final String CMD_CLEAR = "clear";
    private static final String CMD_ADD = "add n/Alice p/123 e/a@x.com a/Blk 1";
    private static final String CMD_ADD_BOB = "add n/Bob";
    private static final String DRAFT = "draft";
    private static final String TYPING = "typing";
    private static final String EMPTY_INPUT = "";
    private static final String BLANK_INPUT = "   ";
    private static final String IGNORED = "ignored";

    @Test
    public void add_nullCommand_throwsNullPointerException() {
        CommandHistory history = new CommandHistory();
        assertThrows(NullPointerException.class, () -> history.add(null));
    }

    @Test
    public void getPrevious_nullInput_throwsNullPointerException() {
        CommandHistory history = new CommandHistory();
        assertThrows(NullPointerException.class, () -> history.getPrevious(null));
    }

    @Test
    public void getNext_nullInput_throwsNullPointerException() {
        CommandHistory history = new CommandHistory();
        assertThrows(NullPointerException.class, () -> history.getNext(null));
    }

    @Test
    public void add_blankCommand_ignored() {
        // EP: blank/whitespace-only command is not stored
        CommandHistory history = new CommandHistory();
        history.add(BLANK_INPUT);

        assertEquals(DRAFT, history.getNext(DRAFT));
        assertEquals(DRAFT, history.getPrevious(DRAFT));
    }

    @Test
    public void getPrevious_emptyHistory_returnsCurrentInput() {
        // EP: empty history -> current input returned as-is
        CommandHistory history = new CommandHistory();
        assertEquals(TYPING, history.getPrevious(TYPING));
    }

    @Test
    public void getNext_emptyHistory_returnsCurrentInput() {
        // EP: empty history -> current input returned as-is
        CommandHistory history = new CommandHistory();
        assertEquals(TYPING, history.getNext(TYPING));
    }

    @Test
    public void getPrevious_singleEntry_clampAtOldest() {
        // EP: single entry, pressing up twice stays at that entry
        CommandHistory history = new CommandHistory();
        history.add(CMD_LIST);

        assertEquals(CMD_LIST, history.getPrevious(EMPTY_INPUT));
        assertEquals(CMD_LIST, history.getPrevious(EMPTY_INPUT));
    }

    @Test
    public void getNext_beforeAnyPrevious_returnsDraft() {
        // EP: getNext without any getPrevious call returns draft immediately
        CommandHistory history = new CommandHistory();
        history.add(CMD_LIST);

        assertEquals(TYPING, history.getNext(TYPING));
    }

    @Test
    public void getPreviousAndNext_navigatesStoredCommands() {
        // EP: multiple entries, navigating up then down traverses history correctly
        CommandHistory history = new CommandHistory();
        history.add(CMD_LIST);
        history.add(CMD_ADD);

        // EP: first getPrevious -> most recent command
        assertEquals(CMD_ADD, history.getPrevious(EMPTY_INPUT));
        // EP: second getPrevious -> older command
        assertEquals(CMD_LIST, history.getPrevious(EMPTY_INPUT));
        // EP: clamped at oldest, stays at CMD_LIST
        assertEquals(CMD_LIST, history.getPrevious(EMPTY_INPUT));

        // EP: getNext from oldest -> moves forward one step
        assertEquals(CMD_ADD, history.getNext(EMPTY_INPUT));
    }

    @Test
    public void getPreviousAndNext_restoresDraftInputAtLatestPosition() {
        // EP: draft is saved when getPrevious is first called and restored at latest position
        CommandHistory history = new CommandHistory();
        history.add(CMD_LIST);
        history.add(CMD_CLEAR);

        assertEquals(CMD_CLEAR, history.getPrevious(CMD_ADD_BOB));
        assertEquals(CMD_ADD_BOB, history.getNext(IGNORED));
        // EP: already at latest, getNext again returns same draft
        assertEquals(CMD_ADD_BOB, history.getNext(IGNORED));
    }

    @Test
    public void resetNavigation_midNavigation_resetsToLatest() {
        // EP: resetNavigation while mid-history resets cursor and clears draft
        CommandHistory history = new CommandHistory();
        history.add(CMD_LIST);
        history.add(CMD_CLEAR);

        history.getPrevious(DRAFT);
        history.resetNavigation();

        // EP: after reset, getPrevious starts fresh from newest
        assertEquals(CMD_CLEAR, history.getPrevious(EMPTY_INPUT));
    }

    @Test
    public void getHistorySnapshot_returnsImmutableSnapshot() {
        // EP: snapshot reflects added commands in order
        CommandHistory history = new CommandHistory();
        history.add(CMD_LIST);
        history.add(CMD_CLEAR);

        List<String> snapshot = history.getHistorySnapshot();
        assertEquals(List.of(CMD_LIST, CMD_CLEAR), snapshot);

        // EP: snapshot is immutable
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(CMD_ADD));
    }

    @Test
    public void getHistorySnapshot_emptyHistory_returnsEmptyList() {
        // EP: empty history -> empty snapshot
        CommandHistory history = new CommandHistory();
        assertTrue(history.getHistorySnapshot().isEmpty());
    }

}
