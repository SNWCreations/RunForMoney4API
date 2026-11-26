package snw.mods.rfm.api.server.game.boot;

import snw.mods.rfm.api.server.game.property.GameDomain;

/**
 * The property domain owned by a {@link GameBootContext}.
 * <p>
 * Keys of this domain may only be used with the property board of a game boot context,
 * and keys of other domains may not be used with it. Keys declared in {@link GameDomain}
 * are also accepted here, because boot-time values may be shared with a running game.
 */
public interface GameBootDomain extends GameDomain {
}
