package snw.mods.rfm.api.util.property;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A container for properties of a single domain.
 * <p>
 * Keys declared in a super-domain of {@link D} are accepted as well, which is how a value can be
 * shared between several property boards (e.g. a boot context and a running game). Keys declared
 * in a subdomain are rejected, so phase-specific keys cannot leak into another phase.
 * <p>
 * A board stores values by {@linkplain PropertyKey#name() key name}, so two keys with the same name
 * address the same property even when they are different objects. It is not thread-safe; the boards of a
 * game are expected to be read and written on the server thread.
 *
 * @param <D> The domain of the keys accepted by this board
 */
public interface PropertyBoard<D extends PropertyDomain> {
    /**
     * Get the value of a property.
     *
     * @param key The property key, whose domain must be {@code D} or one of its super-domains
     * @param <T> The type of the property value
     * @return The value, or empty if the property was not set
     */
    <T> Optional<T> getProperty(PropertyKey<? super D, T> key);

    /**
     * Get the value of a property without wrapping it into an {@link Optional}.
     * <p>
     * Same as {@link #getProperty} otherwise. Intended for hot paths which read a property on every
     * tick, where the optional allocation would otherwise dominate the cost of the read.
     *
     * @param key The property key, whose domain must be {@code D} or one of its super-domains
     * @param <T> The type of the property value
     * @return The value, or {@code null} if the property was not set
     */
    default <T> @Nullable T getPropertyOrNull(PropertyKey<? super D, T> key) {
        return getProperty(key).orElse(null);
    }

    /**
     * Set the value of a property. Setting {@code null} removes the property.
     *
     * @param key   The property key, whose domain must be {@code D} or one of its super-domains
     * @param value The value, or {@code null} to remove the property
     * @param <T>   The type of the property value
     * @throws IllegalArgumentException Thrown if the internal name of the property was already occupied
     *                                  by another key with an incompatible type
     */
    <T> void setProperty(PropertyKey<? super D, T> key, @Nullable T value) throws IllegalArgumentException;
}
