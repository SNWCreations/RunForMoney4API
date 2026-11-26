package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.NamespacedPath;

import java.util.function.DoubleSupplier;

/**
 * A shared option whose value is a {@link DoubleSupplier}.
 *
 * @param path The namespaced path identifying this option
 */
public record DoubleGameOption(NamespacedPath path) implements GameOption<DoubleSupplier> {
    /**
     * Get the class object of the value type of this option.
     *
     * @return {@link DoubleSupplier}{@code .class}
     */
    @Override
    public Class<DoubleSupplier> type() {
        return DoubleSupplier.class;
    }
}
