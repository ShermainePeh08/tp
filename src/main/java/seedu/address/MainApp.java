package seedu.address;

import static seedu.address.model.util.VendorVaultConsistencyUtil.validateOrThrow;
import static seedu.address.ui.Messages.MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_ADDRESS_BOOK;
import static seedu.address.ui.Messages.MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_ALIAS;
import static seedu.address.ui.Messages.MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_INVENTORY;
import static seedu.address.ui.Messages.MESSAGE_COULD_NOT_READ_DATA_IN;
import static seedu.address.ui.Messages.MESSAGE_CREATING_NEW_DATA_FILE;
import static seedu.address.ui.Messages.MESSAGE_DATA_FILE_AT;
import static seedu.address.ui.Messages.MESSAGE_ILLEGAL_VALUES_FOUND_IN;
import static seedu.address.ui.Messages.MESSAGE_INVALID_JSON_FORMAT;
import static seedu.address.ui.Messages.MESSAGE_INVALID_JSON_FORMAT_AT_LINE;
import static seedu.address.ui.Messages.MESSAGE_LOG_SEPARATOR;
import static seedu.address.ui.Messages.MESSAGE_POPULATED_EMPTY_ALIAS_FILE;
import static seedu.address.ui.Messages.MESSAGE_POPULATED_SAMPLE_ADDRESS_BOOK;
import static seedu.address.ui.Messages.MESSAGE_POPULATED_SAMPLE_INVENTORY;
import static seedu.address.ui.Messages.NEWLINE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Aliases;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAliases;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.VendorVault;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.AliasStorage;
import seedu.address.storage.InventoryStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonAliasStorage;
import seedu.address.storage.JsonInventoryStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 5, 1, true);
    public static final String LOG_HEADER =
            "=============================[ Initializing VendorVault ]===========================";
    public static final String LOG_FOOTER =
            "============================ [ Stopping VendorVault ] =============================";
    private static final Pattern UNRECOGNIZED_TOKEN_PATTERN =
        Pattern.compile("^Unrecognized token '([^']+)':.*");
    private static final String MESSAGE_UNRECOGNIZED_TOKEN = "Unrecognized token '";
    private static final String MESSAGE_PARSE_FALLBACK = "Malformed JSON.";
    private static final String MESSAGE_CONFIG_FILE_AT = "Config file at ";
    private static final String MESSAGE_PREFERENCE_FILE_AT = "Preference file at ";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info(LOG_HEADER);
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        InventoryStorage inventoryStorage = new JsonInventoryStorage(userPrefs.getProductsFilePath());
        AliasStorage aliasStorage = new JsonAliasStorage(userPrefs.getAliasFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, inventoryStorage, aliasStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        ReadOnlyAddressBook initialData = loadInitialAddressBook(storage);
        ReadOnlyInventory initialInventory = loadInitialInventory(storage, initialData);
        ReadOnlyAliases initialAliases = loadInitialAliases(storage);

        VendorVault initialVV = new VendorVault(initialData, initialInventory);
        return new ModelManager(initialVV, userPrefs, initialAliases);
    }

    private ReadOnlyAddressBook loadInitialAddressBook(Storage storage) {
        Path addressBookFilePath = storage.getAddressBookFilePath();

        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = storage.readAddressBook();
            logDataFileInitializationIfMissing(addressBookOptional, addressBookFilePath,
                    MESSAGE_POPULATED_SAMPLE_ADDRESS_BOOK);

            return addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logDataFileLoadingIssue(addressBookFilePath, e);
            logger.warning(buildCouldNotLoadWarning(addressBookFilePath,
                    MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_ADDRESS_BOOK));

            return new AddressBook();
        }
    }

    private ReadOnlyInventory loadInitialInventory(Storage storage, ReadOnlyAddressBook initialData) {
        Path inventoryFilePath = storage.getInventoryFilePath();

        try {
            Optional<ReadOnlyInventory> inventoryOptional = storage.readInventory();
            logDataFileInitializationIfMissing(inventoryOptional, inventoryFilePath,
                    MESSAGE_POPULATED_SAMPLE_INVENTORY);

            ReadOnlyInventory initialInventory = inventoryOptional.orElseGet(SampleDataUtil::getSampleInventory);
            return validateInitialInventory(inventoryFilePath, initialData, initialInventory);
        } catch (IllegalValueException e) {
            logDataValidationIssue(inventoryFilePath, e.getMessage());
            logger.warning(buildCouldNotLoadWarning(inventoryFilePath,
                    MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_INVENTORY));

            return new Inventory();
        } catch (DataLoadingException e) {
            logDataFileLoadingIssue(inventoryFilePath, e);
            logger.warning(buildCouldNotLoadWarning(inventoryFilePath,
                    MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_INVENTORY));

            return new Inventory();
        }
    }

    private ReadOnlyInventory validateInitialInventory(Path inventoryFilePath,
                                                       ReadOnlyAddressBook initialData,
                                                       ReadOnlyInventory initialInventory)
            throws IllegalValueException {
        validateOrThrow(initialData, initialInventory, inventoryFilePath);
        return initialInventory;
    }

    private ReadOnlyAliases loadInitialAliases(Storage storage) {
        Path aliasFilePath = storage.getAliasFilePath();

        try {
            Optional<ReadOnlyAliases> aliasesOptional = storage.readAliases();
            logDataFileInitializationIfMissing(aliasesOptional, aliasFilePath, MESSAGE_POPULATED_EMPTY_ALIAS_FILE);

            return aliasesOptional.orElseGet(Aliases::new);
        } catch (DataLoadingException e) {
            logDataFileLoadingIssue(aliasFilePath, e);
            logger.warning(buildCouldNotLoadWarning(aliasFilePath,
                    MESSAGE_COULD_NOT_LOAD_STARTING_EMPTY_ALIAS));

            return new Aliases();
        }
    }

    private void logDataFileInitializationIfMissing(Optional<?> dataOptional, Path filePath, String populationMessage) {
        if (!dataOptional.isPresent()) {
            logger.info(MESSAGE_CREATING_NEW_DATA_FILE + filePath + populationMessage);
        }
    }

    private String buildCouldNotLoadWarning(Path filePath, String fallbackMessage) {
        return MESSAGE_DATA_FILE_AT + filePath + fallbackMessage;
    }

    /**
     * Logs the most useful available loading issue detail for data files.
     *
     * <p>Preference order:
     * 1) Illegal value / JSON parse details if available.
     * 2) Concise root-cause summary otherwise.
     */
    private Optional<String> logDataFileLoadingIssue(Path filePath, DataLoadingException exception) {
        Optional<String> details = extractValidationOrParsingDetails(exception);
        if (details.isPresent()) {
            logDataValidationIssue(filePath, details.get());
            return details;
        }

        Optional<String> rootCauseSummary = extractRootCauseSummary(exception.getCause());
        rootCauseSummary.ifPresent(cause -> logDataReadCauseIssue(filePath, cause));
        return rootCauseSummary;
    }

    /**
     * Extracts detailed startup diagnostics from IllegalValue and JSON processing exceptions.
     */
    private Optional<String> extractValidationOrParsingDetails(DataLoadingException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof IllegalValueException) {
            String details = cause.getMessage();
            if (details == null) {
                return Optional.empty();
            }
            return Optional.of(details);
        }

        return extractJsonParsingDetails(cause);
    }

    /**
     * Produces a concise one-line summary for non-JSON root causes.
     */
    private Optional<String> extractRootCauseSummary(Throwable cause) {
        if (cause == null) {
            return Optional.empty();
        }

        Throwable root = cause;
        while (root.getCause() != null) {
            root = root.getCause();
        }

        String className = root.getClass().getSimpleName();
        String message = root.getMessage();
        if (message == null || message.trim().isEmpty()) {
            return Optional.of(className);
        }

        String firstLine = extractFirstLine(message);
        return Optional.of(className + MESSAGE_LOG_SEPARATOR + firstLine);
    }

    /**
     * Searches the exception chain for Jackson processing exceptions and formats them for logs.
     */
    private Optional<String> extractJsonParsingDetails(Throwable cause) {
        Throwable current = cause;
        while (current != null) {
            if (current instanceof JsonProcessingException) {
                String details = formatJsonParsingDetails((JsonProcessingException) current);
                return Optional.of(details);
            }
            current = current.getCause();
        }
        return Optional.empty();
    }

    /**
     * Builds a user-facing JSON parse diagnostic with line number when available.
     */
    private String formatJsonParsingDetails(JsonProcessingException exception) {
        String message = normalizeJsonParsingMessage(exception);
        JsonLocation location = exception.getLocation();

        boolean isValidLocation = (location != null) && (location.getLineNr() > 0);
        if (isValidLocation) {
            return MESSAGE_INVALID_JSON_FORMAT_AT_LINE + location.getLineNr() + MESSAGE_LOG_SEPARATOR + message;
        }

        return MESSAGE_INVALID_JSON_FORMAT + MESSAGE_LOG_SEPARATOR + message;
    }

    /**
     * Normalizes Jackson parse messages.
     */
    private String normalizeJsonParsingMessage(JsonProcessingException exception) {
        String originalMessage = exception.getOriginalMessage();
        String firstLine = extractFirstLine(originalMessage);

        Matcher matcher = UNRECOGNIZED_TOKEN_PATTERN.matcher(firstLine);
        if (matcher.matches()) {
            return MESSAGE_UNRECOGNIZED_TOKEN + matcher.group(1) + "'.";
        }

        if (firstLine.isEmpty()) {
            return MESSAGE_PARSE_FALLBACK;
        }
        return firstLine;
    }

    /**
     * Returns the first trimmed line of a possibly multi-line message.
     */
    private String extractFirstLine(String message) {
        if (message == null) {
            return "";
        }
        return message.replace("\r\n", NEWLINE).split(NEWLINE, 2)[0].trim();
    }

    private String flattenForLog(String details) {
        return details.replace(NEWLINE, " ");
    }

    private void logDataValidationIssue(Path filePath, String details) {
        logger.warning(MESSAGE_ILLEGAL_VALUES_FOUND_IN + filePath + MESSAGE_LOG_SEPARATOR
                + flattenForLog(details));
    }

    private void logDataReadCauseIssue(Path filePath, String details) {
        logger.warning(MESSAGE_COULD_NOT_READ_DATA_IN + filePath + MESSAGE_LOG_SEPARATOR
                + flattenForLog(details));
    }

    private String buildNamedLoadingIssueMessage(String sourcePrefix, Path filePath, String details) {
        return sourcePrefix + filePath + MESSAGE_LOG_SEPARATOR + flattenForLog(details);
    }

    /**
     * Logs config/prefs load failures.
     */
    private void logNamedSourceLoadingIssue(String sourcePrefix, Path filePath, DataLoadingException exception) {
        Optional<String> details = extractValidationOrParsingDetails(exception);
        if (details.isPresent()) {
            logger.warning(buildNamedLoadingIssueMessage(sourcePrefix, filePath, details.get()));
            return;
        }

        extractRootCauseSummary(exception.getCause()).ifPresent(
                cause -> logger.warning(buildNamedLoadingIssueMessage(sourcePrefix, filePath, cause))
        );
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logNamedSourceLoadingIssue(MESSAGE_CONFIG_FILE_AT, configFilePathUsed, e);
            logger.warning(MESSAGE_CONFIG_FILE_AT + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        // Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logNamedSourceLoadingIssue(MESSAGE_PREFERENCE_FILE_AT, prefsFilePath, e);
            logger.warning(MESSAGE_PREFERENCE_FILE_AT + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting VendorVault " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info(LOG_FOOTER);
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
