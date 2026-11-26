package snw.mods.rfm.api.server.game.property;

/**
 * The property domain owned by a running game.
 * <p>
 * Keys declared in {@link GameDomain} are accepted here as well, so values configured at boot time
 * can keep being read and updated while the game runs.
 */
public interface GamePlayDomain extends GameDomain {
}
