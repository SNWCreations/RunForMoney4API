package snw.mods.rfm.api.util;

/**
 * Helpers for working with types at runtime.
 */
public final class TypeTokens {
    private TypeTokens() {
    }

    /**
     * Read the component type of the given empty varargs array. <br>
     * This is how type arguments can be captured without an explicit {@code Class} parameter,
     * by passing the value type through an empty varargs parameter, e.g.
     * {@code readTypeGetter(typeGetter)} where {@code typeGetter} is declared as {@code T... typeGetter}.
     * <p>
     * The trick works because the compiler allocates an array of the inferred type, so the component type
     * of that empty array is {@code T}. It only works when the type argument is resolvable at the call
     * site; calling through {@code var}, a raw type or an unbounded inference yields {@code Object} and is
     * rejected.
     *
     * @param typeGetter The empty varargs array, must not be supplied with an explicit value
     * @param <T>        The type to read
     * @return The class object of {@code T}
     * @throws IllegalArgumentException Thrown if the type getter was supplied with an explicit value
     *                                  or resolved to {@code Object}
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> Class<T> readTypeGetter(T... typeGetter) {
        if (typeGetter == null || typeGetter.length != 0) {
            throw new IllegalArgumentException("typeGetter was not expected to have an explicit value");
        }
        var result = typeGetter.getClass().getComponentType();
        if (result == Object.class) {
            throw new IllegalArgumentException("typeGetter resolved to Object, is typeGetter pattern used wrongly?");
        }
        return (Class<T>) result;
    }
}
