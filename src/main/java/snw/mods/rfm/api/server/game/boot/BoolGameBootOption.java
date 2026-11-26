package snw.mods.rfm.api.server.game.boot;

import snw.mods.rfm.api.util.NamespacedPath;

import java.util.function.BooleanSupplier;

/**
 * A boot option whose value is a {@link BooleanSupplier}.
 * <p>
 * The supplier is read at start time, so a value which depends on live state is evaluated then rather than
 * at the moment it was written.
 *
 * @param path The namespaced path identifying this option
 */
public record BoolGameBootOption(NamespacedPath path) implements GameBootOption<BooleanSupplier> {
    /**
     * Get the class object of the value type of this option.
     *
     * @return {@link BooleanSupplier}{@code .class}
     */
    @Override
    public Class<BooleanSupplier> type() {
        return BooleanSupplier.class;
    }
}
