package snw.mods.rfm.api.server.game.eliminate;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The eliminations of a game which are waiting for review.
 * <p>
 * When a game is configured to delay eliminations, an elimination is not applied right away: it is added to
 * this sequence and applied only after the configured delay, or earlier if an operator approves it. Removing
 * an elimination from the sequence takes it out of the review, so it is neither applied automatically nor
 * when it is approved.
 * <p>
 * At most one elimination per player is pending at a time.
 */
public interface IEliminateSequence {
    /**
     * Check whether an elimination of the given player is pending.
     *
     * @param uuid The UUID of the player
     * @return True if an elimination of that player is waiting for review
     */
    boolean contains(UUID uuid);

    /**
     * Remove and return the pending elimination of the given player.
     * <p>
     * Removing does not apply the elimination by itself; the caller decides what to do with the returned
     * object.
     *
     * @param uuid The UUID of the player
     * @return The pending elimination, or {@code null} if none was pending for that player
     */
    @Nullable Elimination remove(UUID uuid);
}
