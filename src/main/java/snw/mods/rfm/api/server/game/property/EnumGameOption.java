package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.NamespacedPath;

/**
 * A shared option whose value is a constant of the given enum.
 *
 * @param path The namespaced path identifying this option
 * @param type The class object of the enum type
 * @param <E>  The enum type
 */
public record EnumGameOption<E extends Enum<E>>(NamespacedPath path, Class<E> type) implements GameOption<E> {
}
