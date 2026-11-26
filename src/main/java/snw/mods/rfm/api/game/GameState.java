package snw.mods.rfm.api.game;

/**
 * The lifecycle state of a game.
 *
 * @see IGameState
 */
public enum GameState implements IGameState {
    /**
     * The game was created but never started, or its failed start left it unused.
     */
    INACTIVE(false),
    /**
     * The game is running, and game time flows.
     */
    RUNNING(true),
    /**
     * The game is running but frozen: game time does not flow, so timers and win checks pause, and it can
     * still be resumed.
     */
    FROZEN(true),
    /**
     * The game was stopped and reached the end of its lifecycle; a stopped game cannot be started again.
     */
    OVER(false),
    ;
    private final boolean running;

    GameState(boolean running) {
        this.running = running;
    }

    /**
     * Check whether a game in this state is alive, i.e. whether it was started and not stopped yet.
     * <p>
     * Both {@link #RUNNING} and {@link #FROZEN} report true: a frozen game is paused, not ended.
     *
     * @return True if this state describes a started game which was not stopped
     */
    @Override
    public boolean isRunning() {
        return running;
    }
}
