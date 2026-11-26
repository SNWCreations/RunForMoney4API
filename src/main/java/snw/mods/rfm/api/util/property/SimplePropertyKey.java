package snw.mods.rfm.api.util.property;

/**
 * The plain {@link PropertyKey}: a name and a value type, with no other state.
 *
 * @param name The internal name of the property
 * @param type The class object of the value type
 * @param <D>  The domain this key belongs to
 * @param <T>  The type of the property value
 */
public record SimplePropertyKey<D extends PropertyDomain, T>(String name, Class<T> type)
        implements PropertyKey<D, T> {
}
