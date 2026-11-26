package snw.mods.rfm.api.util.property;

import snw.mods.rfm.api.util.TypeTokens;

/**
 * Identifies a property stored on a {@link PropertyBoard}.
 * <p>
 * Two keys sharing the same {@link #name() name} address the same property on a board, so a key is
 * normally declared once and shared by every place which reads or writes that property.
 *
 * @param <D> The domain this key belongs to
 * @param <T> The type of the property value
 */
public interface PropertyKey<D extends PropertyDomain, T> {
    /**
     * Get the internal name of this key, which is what property boards use for storage.
     *
     * @return The name of this key
     */
    String name();

    /**
     * Get the class object of the value type of this key.
     * <p>
     * Boards use it to reject a value written through a key with a different type.
     *
     * @return The class object of {@code T}
     */
    Class<T> type();

    /**
     * Construct a property key, inferring the value type from the assignment target. <br>
     * The type argument <b>must</b> be resolvable from the target type, e.g.
     * {@code PropertyKey<SomeDomain, String> KEY = PropertyKey.of("name");}. Calling this through
     * {@code var} or a raw type will not work.
     *
     * @param name       Internal name of the property
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <D>        The domain this key belongs to
     * @param <T>        The type of the property value
     * @return The property key
     * @see TypeTokens#readTypeGetter
     */
    @SafeVarargs
    static <D extends PropertyDomain, T> PropertyKey<D, T> of(String name, T... typeGetter) {
        return new SimplePropertyKey<>(name, TypeTokens.readTypeGetter(typeGetter));
    }
}
