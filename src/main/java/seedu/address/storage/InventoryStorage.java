package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyInventory;

/**
 * Represents a storage for {@link seedu.address.model.Inventory}
 */
public interface InventoryStorage {

    Path getInventoryFilePath();

    Optional<ReadOnlyInventory> readInventory() throws DataLoadingException;

    Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataLoadingException;

    void saveInventory(ReadOnlyInventory inventory) throws IOException;

    void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException;
}
