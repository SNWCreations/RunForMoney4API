package snw.mods.rfm.api.server.game.boot;

import snw.mods.rfm.api.util.NamespacedPath;

import java.util.function.IntSupplier;

/**
 * A boot option whose value is an {@link IntSupplier}.
 * <p>
 * The supplier is read at start time, so a value which depends on live state is evaluated then rather than
 * at the moment it was written.
 *
 * @param path The namespaced path identifying this option
 */
public record IntGameBootOption(NamespacedPath path) implements GameBootOption<IntSupplier> {
    /**
     * Get the class object of the value type of this option.
     *
     * @return {@link IntSupplier}{@code .class}
     */
    @Override
    public Class<IntSupplier> type() {
        return IntSupplier.class;
    }
}
