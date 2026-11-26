package snw.mods.rfm.api.server.game.win;

import snw.mods.rfm.api.server.game.IServerGamePlayer;

import java.util.Collection;

/**
 * A decided win: who won, and what the game should do about it.
 * <p>
 * Produced either by the built-in win conditions or by a listener of
 * {@link snw.mods.rfm.api.event.server.game.ServerGameEvents#GATHER_WIN_RESULT}; a listener-supplied
 * result replaces the built-in ones rather than merging with them.
 *
 * @see SimpleWinResult
 * @see snw.mods.rfm.api.event.server.game.ServerGameEvents#GOT_WIN_RESULT
 */
public interface WinResult {
    /**
     * Get the winning players.
     * <p>
     * Empty when no player won, e.g. a game that ended with nobody alive. The collection is not
     * necessarily modifiable.
     *
     * @return The winners
     */
    Collection<? extends IServerGamePlayer> winners();

    /**
     * Get what the game should do with this win.
     *
     * @return The behavior to apply
     */
    WinBehavior behavior();
}
