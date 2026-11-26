package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.property.NamespacedOption;

/**
 * A property key shared between the boot phase and the gameplay phase of a game.
 * <p>
 * Unlike {@link snw.mods.rfm.api.server.game.boot.GameBootOption}, which is bound to the boot domain
 * and may only be written before a game starts, a {@code GameOption} belongs to the
 * {@link GameDomain} and is therefore accepted by both the boot board and the game play board.
 * <p>
 * To have a shared option carried from the boot board into a running game automatically, declare it
 * through {@link SharedGameProperties}.
 *
 * @param <T> The type of the option value
 */
public interface GameOption<T> extends NamespacedOption<GameDomain, T> {
}
