package snw.mods.rfm.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NamespacedPathTest {
    @Test
    @SuppressWarnings("PatternValidation") // we test the runtime check there so suppress IDE warnings
    void testPatterns() {
        assertDoesNotThrow(() -> NamespacedPath.of("abc", "edf"));
        assertThrows(IllegalArgumentException.class, () -> NamespacedPath.of("Abc", "Edf"));
        // each part is validated on its own, so one invalid part is enough to reject the path
        assertThrows(IllegalArgumentException.class, () -> NamespacedPath.of("Abc", "edf"));
        assertThrows(IllegalArgumentException.class, () -> NamespacedPath.of("abc", "Edf"));
        // Minecraft seemed to allow empty namespace and path, but we does not to prevent strange behaviors
        assertThrows(IllegalArgumentException.class, () -> NamespacedPath.of("", ""));
    }
}