package snw.mods.rfm.api.server.game.win;

import snw.mods.rfm.api.server.game.IServerGamePlayer;

import java.util.Collection;

/**
 * The plain {@link WinResult}: a winner collection and the behavior to apply, with no additional
 * state.
 *
 * @param winners  The winners, empty if nobody won
 * @param behavior What the game should do with this win
 */
public record SimpleWinResult(Collection<? extends IServerGamePlayer> winners, WinBehavior behavior) implements WinResult {
}
