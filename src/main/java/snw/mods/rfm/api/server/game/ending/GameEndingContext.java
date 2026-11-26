package snw.mods.rfm.api.server.game.ending;

import org.jetbrains.annotations.Nullable;
import snw.mods.rfm.api.server.game.win.WinResult;

/**
 * Why a game is stopping, and the winning result if there is one.
 * <p>
 * This is the input of {@link snw.mods.rfm.api.server.game.IServerGame#stop} and is also handed to the
 * listeners of {@link snw.mods.rfm.api.event.server.game.ServerGameEvents#GAME_STOPPING}.
 */
public interface GameEndingContext {
    /**
     * Get why the game is stopping.
     *
     * @return The end type
     */
    GameEndType type();

    /**
     * Get the win result which ended the game.
     *
     * @return The win result, present only when {@link #type()} is {@link GameEndType#WIN_CONDITION}
     */
    @Nullable WinResult winResult();
}
