package snw.mods.rfm.api.server.game;

import snw.mods.rfm.api.game.IGameState;
import snw.mods.rfm.api.server.game.boot.GameBootContext;
import snw.mods.rfm.api.server.game.eliminate.IEliminateSequence;
import snw.mods.rfm.api.server.game.ending.GameEndingContext;
import snw.mods.rfm.api.server.game.property.GamePlayDomain;
import snw.mods.rfm.api.server.game.task.ITickTaskManager;
import snw.mods.rfm.api.util.property.PropertyBoard;

import java.util.Collection;

/**
 * A game instance, running on the server.
 * <p>
 * A game goes through {@link #start(GameBootContext)}, then {@link #stop(GameEndingContext)}; a started
 * game may also be frozen and resumed before it stops. A game that was stopped cannot be started again, and
 * only one game can be bound to the server at a time.
 * <p>
 * Every method is expected to be called on the server thread.
 */
public interface IServerGame {

    /**
     * Start this game, binding it to the server.
     * <p>
     * A failed start never leaves anything behind: whether a listener vetoes it (returns {@code false})
     * or the start sequence throws, the game is released before this method returns or rethrows. Callers
     * only report the outcome; they must not clean up after this method.
     *
     * @param ctx The options the game should start with
     * @return False if a listener of
     *         {@link snw.mods.rfm.api.event.server.game.ServerGameEvents#GAME_STARTING} vetoed the start
     * @throws IllegalStateException Thrown if the game was already started, has already reached the end of
     *                               its lifecycle, or the server is already holding another game
     * @throws RuntimeException Thrown if the start sequence itself failed; an implementation may throw a
     *                          more specific type
     */
    boolean start(GameBootContext ctx);

    /**
     * Stop this game, releasing the server and detaching every player from it.
     *
     * @param ctx Why the game is stopping, and the winning result if there is one
     * @throws IllegalStateException Thrown if the game is not running, or was already stopped
     */
    void stop(GameEndingContext ctx);

    /**
     * Get the players which currently belong to this game.
     * <p>
     * The returned collection is a read-only view of a live set: later changes to the game's player set
     * are visible through it, and modifying it is rejected.
     *
     * @return A read-only view of the players of this game
     */
    Collection<? extends IServerGamePlayer> getPlayersView();

    /**
     * Get the current state of this game.
     *
     * @return The current state
     */
    IGameState getState();

    /**
     * Get the property board holding the values of the running game.
     * <p>
     * The board accepts the keys of {@link GamePlayDomain} and of its super-domain, so a value configured
     * before the game started can still be read — and changed — while it runs.
     *
     * @return The property board of this game
     */
    PropertyBoard<GamePlayDomain> getPropertyBoard();

    /**
     * Get the sequence holding the eliminations which are waiting for review.
     *
     * @return The elimination sequence of this game
     */
    IEliminateSequence getEliminateSequence();

    /**
     * Get the manager which runs the tick tasks of this game.
     *
     * @return The tick task manager of this game
     */
    ITickTaskManager getTickTaskManager();

    // todo extract methods for API use
}
