package snw.mods.rfm.api.util.property;

import org.junit.jupiter.api.Test;
import snw.mods.rfm.api.util.NamespacedPath;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionPathsTest {
    interface TestDomain extends PropertyDomain {}

    @Test
    void usesThePathCarriedByNamespacedOptions() {
        PropertyKey<TestDomain, String> key = new TestNamespacedKey<>(NamespacedPath.of("other_mod", "some_option"));
        assertEquals(NamespacedPath.of("other_mod", "some_option"), OptionPaths.of(key, "rfm"));
    }

    @Test
    void fallsBackToTheGivenNamespaceForPlainKeys() {
        PropertyKey<TestDomain, String> key = PropertyKey.of("plain_key");
        assertEquals(NamespacedPath.of("rfm", "plain_key"), OptionPaths.of(key, "rfm"));
    }

    @Test
    void fallbackNamespaceIsRespected() {
        PropertyKey<TestDomain, String> key = PropertyKey.of("plain_key");
        assertEquals(NamespacedPath.of("another_mod", "plain_key"), OptionPaths.of(key, "another_mod"));
    }

    /**
     * A minimal {@link NamespacedOption} so the API-level resolution can be tested without any
     * Minecraft class.
     */
    private record TestNamespacedKey<T>(NamespacedPath path) implements NamespacedOption<TestDomain, T> {
        @Override
        public Class<T> type() {
            throw new UnsupportedOperationException("not needed for path resolution");
        }
    }
}
