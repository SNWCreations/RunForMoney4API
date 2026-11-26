package snw.mods.rfm.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * A namespace and a path joined by a colon, formatted as {@code namespace:path}.
 * <p>
 * Both parts are validated when the instance is created, so an invalid path fails early instead of
 * surfacing later as a lookup that never matches.
 *
 * @param namespace The namespace, which must match {@link #NAMESPACE_PATTERN}
 * @param path      The path, which must match {@link #PATH_PATTERN}
 */
public record NamespacedPath(String namespace, String path) {
    /**
     * The pattern a namespace must match: lowercase letters, digits, dots and underscores, at least one
     * character.
     */
    public static final String NAMESPACE_PATTERN = "^[a-z0-9_.]+$";
    /**
     * The pattern a path must match: lowercase letters, digits, dots, underscores, slashes and dashes, at
     * least one character.
     */
    public static final String PATH_PATTERN = "^[a-z0-9_./-]+$";
    /**
     * Compiled form of {@link #NAMESPACE_PATTERN}.
     */
    public static final Pattern COMPILED_NAMESPACE_PATTERN = Pattern.compile(NAMESPACE_PATTERN);
    /**
     * Compiled form of {@link #PATH_PATTERN}.
     */
    public static final Pattern COMPILED_PATH_PATTERN = Pattern.compile(PATH_PATTERN);

    /**
     * Validate the parts of the given namespace and path.
     *
     * @throws IllegalArgumentException Thrown if the namespace or the path does not match its pattern
     */
    public NamespacedPath {
        if (!COMPILED_NAMESPACE_PATTERN.matcher(namespace).matches()) {
            throw new IllegalArgumentException("Invalid namespace " + namespace + ", non [a-z0-9_.] character detected");
        }
        if (!COMPILED_PATH_PATTERN.matcher(path).matches()) {
            throw new IllegalArgumentException("Invalid path " + path + ", non [a-z0-9_./-] character detected");
        }
    }

    /**
     * Construct a {@link NamespacedPath}. <br>
     * This is the preferred over calling the constructor directly because IDEs will help you to check
     * your namespace and path, so you will not fail at runtime.
     *
     * @param namespace The namespace of the constructed instance, must follow {@link #NAMESPACE_PATTERN namespace pattern}
     * @param path The path of the constructed instance, must follow {@link #PATH_PATTERN path pattern}
     * @return The constructed {@link NamespacedPath} instance
     */
    public static NamespacedPath of(@org.intellij.lang.annotations.Pattern(NAMESPACE_PATTERN) String namespace, @org.intellij.lang.annotations.Pattern(PATH_PATTERN) String path) {
        return new NamespacedPath(namespace, path);
    }

    /**
     * Get the {@code namespace:path} form of this instance.
     *
     * @return The string form of this path
     */
    @Override
    public @NotNull String toString() {
        return namespace + ":" + path;
    }
}
