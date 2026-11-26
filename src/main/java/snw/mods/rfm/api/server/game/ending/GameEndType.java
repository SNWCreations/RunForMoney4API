package snw.mods.rfm.api.server.game.ending;

/**
 * Why a game is stopping.
 *
 * @see GameEndingContext
 */
public enum GameEndType {
    /**
     * A win condition was met, so the game ends with a winner.
     */
    WIN_CONDITION,
    /**
     * The server itself is stopping, so the game ends without applying its usual end-of-game messages.
     */
    SERVER_STOPPING,
    /**
     * An administrator stopped the game on purpose.
     */
    ADMIN_COMMAND
}
