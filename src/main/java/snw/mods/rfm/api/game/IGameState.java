package snw.mods.rfm.api.game;

/**
 * The state of a game, as far as callers need to know it.
 *
 * @see GameState
 */
public interface IGameState {
    /**
     * Check whether the game is alive, i.e. started and not stopped yet.
     * <p>
     * A frozen game is alive as well; see {@link GameState}.
     *
     * @return True if the game was started and was not stopped
     */
    boolean isRunning();
}
