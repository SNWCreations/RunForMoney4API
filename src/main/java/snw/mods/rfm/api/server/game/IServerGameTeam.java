package snw.mods.rfm.api.server.game;

import org.jetbrains.annotations.Nullable;
import snw.mods.rfm.api.game.IGameTeam;
import snw.mods.rfm.api.game.Identity;

/**
 * A team as seen by the server side of the game engine.
 * <p>
 * A team may carry an identity, which is what decides the default abilities of its members. A team without
 * an identity is a plain group of players: joining it does not grant or take away any identity-specific
 * ability.
 */
public interface IServerGameTeam extends IGameTeam {
    /**
     * Get the identity this team represents.
     *
     * @return The identity of this team, or {@code null} if the team does not represent an identity
     */
    @Nullable Identity getIdentity();

    /**
     * Check whether members of this team may damage each other.
     *
     * @return True if friendly fire is allowed among the members of this team
     */
    boolean isAllowFriendlyFire();
}
