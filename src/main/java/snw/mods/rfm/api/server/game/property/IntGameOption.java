package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.NamespacedPath;

import java.util.function.IntSupplier;

/**
 * A shared option whose value is an {@link IntSupplier}.
 *
 * @param path The namespaced path identifying this option
 */
public record IntGameOption(NamespacedPath path) implements GameOption<IntSupplier> {
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
