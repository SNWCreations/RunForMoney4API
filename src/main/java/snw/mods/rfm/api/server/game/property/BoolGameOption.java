package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.NamespacedPath;

import java.util.function.BooleanSupplier;

/**
 * A shared option whose value is a {@link BooleanSupplier}.
 *
 * @param path The namespaced path identifying this option
 */
public record BoolGameOption(NamespacedPath path) implements GameOption<BooleanSupplier> {
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
