package snw.mods.rfm.api.game;

import java.util.Optional;
import java.util.UUID;

/**
 * A player as seen by the game engine, either on the server or on the client.
 *
 * @see snw.mods.rfm.api.server.game.IServerGamePlayer
 * @see snw.mods.rfm.api.client.game.IClientGamePlayer
 */
public interface IGamePlayer {
    /**
     * Get the unique id of this player.
     * <p>
     * The id is stable across sessions and does not require the player to be online.
     *
     * @return The UUID of this player
     */
    UUID getUUID();

    /**
     * Get the Minecraft profile name of this player.
     *
     * @return The name of the player
     */
    Optional<String> getName();

    /**
     * Check whether this player is an operator of the mod's own op list.
     * <p>
     * This is <b>not</b> Minecraft's vanilla operator permission: it is the separate list the mod keeps to
     * decide who may review eliminations and change the game options.
     *
     * @return True if this player is a game operator
     */
    boolean isGameOp();
}
