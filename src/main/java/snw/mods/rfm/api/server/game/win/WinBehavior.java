package snw.mods.rfm.api.server.game.win;

/**
 * What happens to the game once a win result is applied.
 * <p>
 * The {@code game_timer_up_win_behavior} option only selects the behavior of the <b>built-in</b>
 * win conditions. A win supplied by an API listener carries its own behavior in its
 * {@link WinResult}, so a listener can choose freely.
 */
public enum WinBehavior {
    /**
     * Freeze the game instead of ending it: the game stays bound to the server in
     * {@link snw.mods.rfm.api.game.GameState#FROZEN} and can be resumed.
     * <p>
     * Freezing pauses game time rather than resolving the win, so unfreezing a game whose win
     * condition still holds — every runner eliminated, for instance — freezes it again on the next
     * tick. To actually leave such a game, end it
     * ({@link snw.mods.rfm.api.server.game.IServerGame#stop}) or change the situation the condition
     * looks at, e.g. by adding a player back.
     */
    FREEZE,
    /**
     * End the game with {@link snw.mods.rfm.api.server.game.ending.GameEndType#WIN_CONDITION}.
     */
    STOP
}
