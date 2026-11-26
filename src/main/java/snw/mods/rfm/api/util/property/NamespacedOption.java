package snw.mods.rfm.api.util.property;

import snw.mods.rfm.api.util.NamespacedPath;

/**
 * A property key identified by a {@link NamespacedPath} instead of a bare name.
 * <p>
 * The namespaced form is what commands and serialized data use, so it can address keys from several
 * namespaces without collisions. {@link #name()} returns the string form of the path, which is what
 * property boards use for storage.
 *
 * @param <D> The domain this key belongs to
 * @param <T> The type of the property value
 */
public interface NamespacedOption<D extends PropertyDomain, T> extends PropertyKey<D, T> {
    /**
     * Get the namespaced path identifying this key.
     *
     * @return The path of this key, never null
     */
    NamespacedPath path();

    /**
     * Get the storage name of this key, which is the string form of {@link #path()}.
     *
     * @return The {@code namespace:path} form of this key
     */
    @Override
    default String name() {
        return path().toString();
    }
}
