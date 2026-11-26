package snw.mods.rfm.api.server.game.eliminate;

import org.jetbrains.annotations.Nullable;
import snw.mods.rfm.api.server.game.IServerGamePlayer;

/**
 * One elimination of a player, together with who caused it.
 * <p>
 * An elimination object travels through the elimination events: it is created when the elimination is
 * requested, may wait in the {@link IEliminateSequence} for review, and is finally evaluated against
 * {@link snw.mods.rfm.api.event.server.game.ServerGamePlayerEvents#PRE_EVAL_PLAYER_ELIMINATE} before it is
 * applied.
 */
public interface Elimination {
    /**
     * Get the player being eliminated.
     *
     * @return The eliminated player
     */
    IServerGamePlayer getPlayer();

    /**
     * Get the player who caused the elimination.
     *
     * @return The eliminator, or {@code null} if the elimination was not caused by a player
     */
    @Nullable IServerGamePlayer getEliminator();
}
