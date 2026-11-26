package snw.mods.rfm.api.util.property;

import snw.mods.rfm.api.util.NamespacedPath;

/**
 * Resolves the {@link NamespacedPath} that identifies an option key.
 * <p>
 * Keys implementing {@link NamespacedOption} carry their own path. Plain keys have no namespace, so
 * they are placed in the given fallback namespace under their bare name. This split keeps the
 * namespace resolution independent of Minecraft's {@code ResourceLocation}, so it can live in the API
 * module and be reused outside the command layer.
 */
public final class OptionPaths {
    private OptionPaths() {
    }

    /**
     * Resolve the namespaced path of an option key.
     * <p>
     * A key implementing {@link NamespacedOption} reports its own path; any other key is placed in the
     * fallback namespace under its bare name.
     *
     * @param key              The option key
     * @param fallbackNamespace The namespace used for keys that do not carry their own path
     * @return The path used to identify this option
     * @throws IllegalArgumentException Thrown if the fallback namespace does not match the namespace
     *                                  pattern, or if the bare name of the key does not match the path
     *                                  pattern
     */
    @SuppressWarnings("PatternValidation")
    public static NamespacedPath of(PropertyKey<?, ?> key, String fallbackNamespace) {
        if (key instanceof NamespacedOption<?, ?> option) {
            return option.path();
        }
        return NamespacedPath.of(fallbackNamespace, key.name());
    }
}
