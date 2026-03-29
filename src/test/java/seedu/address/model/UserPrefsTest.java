package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void setProductsFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setProductsFilePath(null));
    }

    @Test
    public void setAliasFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAliasFilePath(null));
    }

    @Test
    public void checkEquals() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        Path addressBookPath = Path.of("addressBook");
        Path productsFilePath = Path.of("products");
        Path aliasFilePath = Path.of("aliases");

        UserPrefs userPrefs = new UserPrefs();
        UserPrefs userPrefs1 = new UserPrefs();

        userPrefs.setGuiSettings(guiSettings);
        userPrefs1.setGuiSettings(guiSettings);

        userPrefs.setAddressBookFilePath(addressBookPath);
        userPrefs1.setAddressBookFilePath(addressBookPath);

        userPrefs.setProductsFilePath(productsFilePath);
        userPrefs1.setProductsFilePath(productsFilePath);

        userPrefs.setAliasFilePath(aliasFilePath);
        userPrefs1.setAliasFilePath(aliasFilePath);

        userPrefs.setDefaultRestockThresholdValue(7);
        userPrefs1.setDefaultRestockThresholdValue(7);

        assertEquals(userPrefs, userPrefs1);
    }

    @Test
    public void setDefaultRestockThresholdValue_negativeValue_throwsIllegalArgumentException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(IllegalArgumentException.class, () -> userPrefs.setDefaultRestockThresholdValue(-1));
    }

    @Test
    public void equals_differentFields_returnsFalse() {
        UserPrefs base = new UserPrefs();

        UserPrefs differentGuiSettings = new UserPrefs();
        differentGuiSettings.setGuiSettings(new GuiSettings(
                1, 2, 3, 4));
        assertNotEquals(base, differentGuiSettings);

        UserPrefs differentAddressPath = new UserPrefs();
        differentAddressPath.setAddressBookFilePath(Path.of("addressBook"));
        assertNotEquals(base, differentAddressPath);

        UserPrefs differentProductsPath = new UserPrefs();
        differentProductsPath.setProductsFilePath(Path.of("products"));
        assertNotEquals(base, differentProductsPath);

        UserPrefs differentAliasPath = new UserPrefs();
        differentAliasPath.setAliasFilePath(Path.of("aliases"));
        assertNotEquals(base, differentAliasPath);

        UserPrefs differentThreshold = new UserPrefs();
        differentThreshold.setDefaultRestockThresholdValue(3);
        assertNotEquals(base, differentThreshold);
    }

    @Test
    public void hashCode_sameContent_sameHashCode() {
        UserPrefs first = new UserPrefs();
        UserPrefs second = new UserPrefs();

        assertEquals(first.hashCode(), second.hashCode());

        second.setDefaultRestockThresholdValue(3);
        assertNotEquals(first.hashCode(), second.hashCode());
    }
}
