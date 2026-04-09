package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.storage.JsonLineReferenceUtil.findFieldLineNumbers;
import static seedu.address.storage.JsonLineReferenceUtil.formatLineReference;
import static seedu.address.storage.JsonSerializableInventory.MESSAGE_DUPLICATE_PRODUCT;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyInventory;

/**
 * A class to access Inventory data stored as a json file on the hard disk.
 */
public class JsonInventoryStorage implements InventoryStorage {

    public static final String MESSAGE_DUPLICATE_IDENTIFIER = "Duplicate product identifier '";
    private static final String IDENTIFIER_FIELD = "identifier";
    private final Path filePath;

    public JsonInventoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory() throws DataLoadingException {
        return readInventory(filePath);
    }

    /**
     * Similar to {@link #readInventory()}
     *
     * @param filePath location of the data. Cannot be null.
     */
    public Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableInventory> jsonInventory = JsonUtil.readJsonFile(
                filePath, JsonSerializableInventory.class);
        if (!jsonInventory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInventory.get().toModelType());
        } catch (IllegalValueException ive) {
            if (MESSAGE_DUPLICATE_PRODUCT.equals(ive.getMessage())) {
                List<String> duplicateIdentifiers = jsonInventory.get().findDuplicateIdentifiers();
                String detailedMessage = buildDuplicateIdentifierErrorMessage(filePath, duplicateIdentifiers);

                throw new DataLoadingException(new IllegalValueException(detailedMessage, ive));
            }

            throw new DataLoadingException(ive);
        }
    }

    private String buildDuplicateIdentifierErrorMessage(Path filePath, List<String> duplicateIdentifiers) {
        if (duplicateIdentifiers.isEmpty()) {
            return MESSAGE_DUPLICATE_PRODUCT;
        }

        List<String> details = new ArrayList<>();
        for (String identifier : duplicateIdentifiers) {
            List<Integer> lineNumbers = findFieldLineNumbers(filePath, IDENTIFIER_FIELD, identifier);
            if (lineNumbers.isEmpty()) {
                details.add(MESSAGE_DUPLICATE_IDENTIFIER + identifier + "'");
            } else {
                details.add(MESSAGE_DUPLICATE_IDENTIFIER + identifier + "' at " + formatLineReference(lineNumbers));
            }
        }

        return String.join("; ", details) + ".";
    }

    @Override
    public void saveInventory(ReadOnlyInventory inventory) throws IOException {
        saveInventory(inventory, filePath);
    }

    /**
     * Similar to {@link #saveInventory(ReadOnlyInventory, Path)}
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
        requireNonNull(inventory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInventory(inventory), filePath);
    }
}
