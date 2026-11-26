package snw.mods.rfm.api.server.game;

import snw.mods.rfm.api.game.GamePlayerAbilities;
import snw.mods.rfm.api.game.IGamePlayer;
import snw.mods.rfm.api.game.Identity;

import java.util.Optional;

/**
 * A player as seen by the server side of the game engine.
 * <p>
 * In addition to the identity of the player, this view exposes the game data the engine tracks for them:
 * their team, their identity, and their abilities.
 */
public interface IServerGamePlayer extends IGamePlayer {
    /**
     * Check if this player has given identity. <br>
     *
     * @param identity The identity
     * @param exact if {@code true}, the player's identity must be exactly {@code identity}; if
     *               {@code false}, identities derived from {@code identity} match as well, see
     *               {@link Identity#isBasedOn}
     * @return True if the player has given identity
     */
    boolean isIdentifiedAs(Identity identity, boolean exact);

    /**
     * Get the team this player currently belongs to.
     *
     * @return The team of this player, or an empty optional if the player is in no team
     */
    Optional<? extends IServerGameTeam> getTeam();

    /**
     * Get abilities object bound to this player.
     *
     * @return Optional-wrapped {@link GamePlayerAbilities} instance bound to this player, empty optional
     *          will be returned if this player was not in any game
     */
    Optional<? extends GamePlayerAbilities> getAbilities();
}
