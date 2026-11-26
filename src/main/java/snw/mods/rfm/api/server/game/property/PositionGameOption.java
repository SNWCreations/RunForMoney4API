package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.NamespacedPath;
import snw.mods.rfm.api.util.Position;

/**
 * A shared option whose value is a {@link Position}.
 *
 * @param path The namespaced path identifying this option
 */
public record PositionGameOption(NamespacedPath path) implements GameOption<Position> {
    /**
     * Get the class object of the value type of this option.
     *
     * @return {@link Position}{@code .class}
     */
    @Override
    public Class<Position> type() {
        return Position.class;
    }
}
