package snw.mods.rfm.api.server.game.boot;

import snw.mods.rfm.api.util.property.NamespacedOption;

/**
 * A property key of the {@link GameBootDomain}, named by a {@link snw.mods.rfm.api.util.NamespacedPath}.
 * <p>
 * A boot option is only meaningful before a game starts; it is written to a boot context and read while
 * the game starts. Values which must stay available while the game runs belong in
 * {@link snw.mods.rfm.api.server.game.property.GameOption} instead.
 *
 * @param <T> The type of the option value
 */
public interface GameBootOption<T> extends NamespacedOption<GameBootDomain, T> {
}
