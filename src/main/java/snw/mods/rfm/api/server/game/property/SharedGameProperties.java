package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.TypeTokens;
import snw.mods.rfm.api.util.property.PropertyBoard;
import snw.mods.rfm.api.util.property.PropertyKey;
import snw.mods.rfm.api.util.property.SimplePropertyKey;

import java.util.*;
import java.util.function.Supplier;

/**
 * Registry of property keys that are shared between a game boot context and the running game.
 * <p>
 * A key declared through {@link #declare(String, Object[])} belongs to the {@link GameDomain}, so it
 * can be written on the boot board before the game starts and read or overwritten on the game play
 * board while it runs. Every declared key is carried from the boot board to the game play board by
 * {@link #carryAll(PropertyBoard, PropertyBoard)} when a game starts, so adding a shared property
 * needs no changes to the game start code.
 * <p>
 * Declarations are expected to happen during mod initialization, e.g.
 * <pre>{@code
 * PropertyKey<GameDomain, DoubleSupplier> MY_KEY = SharedGameProperties.declare("my_key");
 * }</pre>
 * Declaring the same name twice is an error, which catches copy-paste mistakes early.
 * <p>
 * <b>Threading:</b> this registry is not synchronized. It must only be written from the mod
 * initialization thread, which is how every mod loader invokes entry points; the registry is not
 * frozen afterwards, so a key declared later is simply picked up by the next game start. Readers
 * always observe a fully built immutable snapshot through {@link #all()}.
 * <p>
 * Carrying is a shallow copy: the stored value object itself is reused, it is not cloned. Values are
 * carried in declaration order.
 */
public final class SharedGameProperties {
    private static final Map<String, PropertyKey<GameDomain, ?>> KEYS = new LinkedHashMap<>();
    /**
     * Immutable snapshot of {@link #KEYS}, replaced on every declaration. Volatile so the snapshot a
     * reader sees is always fully constructed, even if the reader is not the declaring thread.
     */
    private static volatile List<PropertyKey<GameDomain, ?>> snapshot = List.of();

    private SharedGameProperties() {
    }

    /**
     * Declare a shared property key and register it for carrying on game start.
     * <p>
     * The value type is inferred from the assignment target, exactly like {@link PropertyKey#of}:
     * <pre>{@code
     * PropertyKey<GameDomain, DoubleSupplier> KEY = SharedGameProperties.declare("my_key");
     * }</pre>
     * Must be called from the mod initialization thread.
     *
     * @param name       Internal name of the property, must be unique among shared properties and
     *                   must not be blank
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The type of the property value
     * @return The declared key
     * @throws IllegalArgumentException Thrown if the name is blank, or if the type getter was
     *                                  supplied explicitly or resolved to {@code Object}
     * @throws IllegalStateException    Thrown if a shared property with the same name was already declared
     */
    @SafeVarargs
    public static <T> PropertyKey<GameDomain, T> declare(String name, T... typeGetter) {
        Objects.requireNonNull(name, "name");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Shared property name must not be blank");
        }
        PropertyKey<GameDomain, T> key = new SimplePropertyKey<>(name, TypeTokens.readTypeGetter(typeGetter));
        register(key);
        return key;
    }

    /**
     * Declare a {@link GameOption} and register it for carrying on game start.
     * <p>
     * This is the preferred way to declare a shared option, because a {@code GameOption} also
     * carries a {@link snw.mods.rfm.api.util.NamespacedPath}, so the declaration site is the single
     * source of truth for both the identity and the value type:
     * <pre>{@code
     * DoubleGameOption MY_OPTION = SharedGameProperties.declareGameOption(
     *         () -> new DoubleGameOption(NamespacedPath.of("my_mod", "my_option")));
     * }</pre>
     * The supplier is invoked exactly once, at declaration time; the returned instance is the very
     * object recorded in the registry, and its concrete type is preserved. Must be called from the
     * mod initialization thread.
     *
     * @param optionSupplier Supplier of the option, must not return {@code null}
     * @param <O>            The concrete option type
     * @param <T>            The type of the option value
     * @return The declared option
     * @throws NullPointerException  Thrown if the supplier or the supplied option is {@code null}
     * @throws IllegalStateException Thrown if a shared property with the same name was already declared
     */
    public static <O extends GameOption<T>, T> O declareGameOption(Supplier<O> optionSupplier) {
        Objects.requireNonNull(optionSupplier, "optionSupplier");
        O option = Objects.requireNonNull(optionSupplier.get(), "optionSupplier supplied null");
        register(option);
        return option;
    }

    /**
     * Find a declared shared key by its name.
     *
     * @param name The name of the key, as returned by {@link PropertyKey#name()}
     * @return The key, or empty if nothing was declared under that name
     */
    public static Optional<PropertyKey<GameDomain, ?>> find(String name) {
        return Optional.ofNullable(KEYS.get(name));
    }

    private static void register(PropertyKey<GameDomain, ?> key) {
        if (KEYS.containsKey(key.name())) {
            throw new IllegalStateException("Shared property \"" + key.name() + "\" was already declared");
        }
        KEYS.put(key.name(), key);
        snapshot = List.copyOf(KEYS.values());
    }

    /**
     * Get every declared shared property key, in declaration order.
     *
     * @return An immutable snapshot of the shared keys
     */
    public static List<PropertyKey<GameDomain, ?>> all() {
        return snapshot;
    }

    /**
     * Copy every declared shared property that is present in {@code from} to {@code to}.
     * <p>
     * Properties absent from the source are skipped, so the destination keeps its own default.
     * Existing values on the destination are overwritten.
     *
     * @param from The source board, normally the boot board
     * @param to   The destination board, normally the game play board
     * @param <F>  The domain of the source board, must be able to accept shared keys
     * @param <T>  The domain of the destination board, must be able to accept shared keys
     */
    public static <F extends GameDomain, T extends GameDomain> void carryAll(PropertyBoard<F> from, PropertyBoard<T> to) {
        for (PropertyKey<GameDomain, ?> key : all()) {
            carry(from, to, key);
        }
    }

    private static <F extends GameDomain, T extends GameDomain, V> void carry(
            PropertyBoard<F> from, PropertyBoard<T> to, PropertyKey<GameDomain, V> key) {
        from.getProperty(key).ifPresent(value -> to.setProperty(key, value));
    }
}
