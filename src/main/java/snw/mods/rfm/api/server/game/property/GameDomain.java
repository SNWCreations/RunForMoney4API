package snw.mods.rfm.api.server.game.property;

import snw.mods.rfm.api.util.property.PropertyDomain;

/**
 * A property domain shared by all phases of a game.
 * <p>
 * Keys declared in this domain are readable and writable on every game-related property board,
 * including the {@link snw.mods.rfm.api.server.game.boot.GameBootDomain boot domain} and the
 * runtime game play domain. Use it for values that are configured before the game starts but
 * may also be changed while it runs (e.g. the tick speed multiplier).
 * <p>
 * Keys that only make sense before the game starts (boot-only options) must stay in the boot
 * domain, so they cannot leak into a running game.
 */
public interface GameDomain extends PropertyDomain {
}
