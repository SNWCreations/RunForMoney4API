package snw.mods.rfm.api.event.server.game;

import snw.mods.rfm.api.event.CompoundEventResult;
import snw.mods.rfm.api.event.Event;
import snw.mods.rfm.api.event.EventResult;
import snw.mods.rfm.api.server.game.IServerGame;
import snw.mods.rfm.api.server.game.boot.GameBootContext;
import snw.mods.rfm.api.server.game.ending.GameEndingContext;
import snw.mods.rfm.api.server.game.task.common.IHunterReleaseTimer;
import snw.mods.rfm.api.server.game.win.WinResult;

/**
 * Events covering the lifecycle of a game, from the moment it is asked to start until it is stopped.
 * <p>
 * Every event is dispatched on the server thread.
 */
public interface ServerGameEvents {
    /**
     * Asked before the game finishes starting.
     *
     * @see GameStarting
     */
    Event<GameStarting> GAME_STARTING = Event.makeEventResult();
    /**
     * Called once the game has started and is running.
     *
     * @see GameStarted
     */
    Event<GameStarted> GAME_STARTED = Event.makeLoop();
    /**
     * Called when the game is about to stop, before its players are detached from it.
     *
     * @see GameStopping
     */
    Event<GameStopping> GAME_STOPPING = Event.makeLoop();
    /**
     * Called once the game has stopped.
     *
     * @see GameStopped
     */
    Event<GameStopped> GAME_STOPPED = Event.makeLoop();
    /**
     * Asked on every tick whether a win condition holds.
     *
     * @see GatherWinResult
     */
    Event<GatherWinResult> GATHER_WIN_RESULT = Event.makeCompoundEventResult();
    /**
     * Asked whether a gathered win result should be accepted.
     *
     * @see GotWinResult
     */
    Event<GotWinResult> GOT_WIN_RESULT = Event.makeEventResult();
    /**
     * Called when an accepted win result is about to be applied.
     *
     * @see HandleWinResult
     */
    Event<HandleWinResult> HANDLE_WIN_RESULT = Event.makeLoop();
    /**
     * Called once the hunter release timer of a game was created.
     *
     * @see HunterReleaseTimerInitialized
     */
    Event<HunterReleaseTimerInitialized> HUNTER_RELEASE_TIMER_INITIALIZED = Event.makeLoop();

    /**
     * Called once a game is being started. <br>
     * Return {@link EventResult#FALSE} to stop the game from starting, doing so leads {@link IServerGame#start} method
     *  of given object return false as result.
     * <p>
     * The game's state is already {@link snw.mods.rfm.api.game.GameState#RUNNING} and the properties shared
     * between the boot phase and gameplay have already been carried over when this event is dispatched, but
     * no player has been notified yet.
     */
    interface GameStarting {
        /**
         * Handle the event.
         *
         * @param game        The game being started
         * @param bootContext The boot context carrying the options the game was asked to start with
         * @return {@link EventResult#FALSE} to abort the start, any other result to let it continue
         */
        EventResult handle(IServerGame game, GameBootContext bootContext);
    }

    /**
     * Called once a game is started.
     */
    interface GameStarted {
        /**
         * Handle the event.
         *
         * @param game The game which was just started
         */
        void handle(IServerGame game);
    }

    /**
     * Called when a game is about to stop. <br>
     * The game still reports itself as running at this point: its players are detached, its tick tasks are
     * cleared and its state turns {@link snw.mods.rfm.api.game.GameState#OVER} only after this event returns.
     */
    interface GameStopping {
        /**
         * Handle the event.
         *
         * @param game The game being stopped
         * @param ctx  Why the game is stopping, and the winning result if there is one
         */
        void handle(IServerGame game, GameEndingContext ctx);
    }

    /**
     * Called once a game has stopped.
     */
    interface GameStopped {
        /**
         * Handle the event.
         *
         * @param game The game which was just stopped, already in
         *             {@link snw.mods.rfm.api.game.GameState#OVER}
         */
        void handle(IServerGame game);
    }

    /**
     * Asked once per server tick, while the game is running and not frozen, whether a win condition
     * holds right now.
     * <p>
     * Return {@link CompoundEventResult#interruptsTrue} or {@link CompoundEventResult#interruptsFalse}
     * with a {@link WinResult} to supply the win. A supplied result replaces the built-in conditions
     * entirely; there is no merging of winners.
     * <p>
     * Return {@link CompoundEventResult#pass} to defer to the next listener, and then to the built-in
     * conditions (a game with no one alive, or a finished game timer).
     * <p>
     * Return {@link CompoundEventResult#stop} to declare that this tick has <b>no</b> win and that the
     * built-in conditions must not be consulted, which is how a listener vetoes them.
     * <p>
     * The event is not asked at all while the game is frozen, so a win is not re-announced after a
     * {@link snw.mods.rfm.api.server.game.win.WinBehavior#FREEZE freeze win}.
     */
    interface GatherWinResult {
        /**
         * Handle the event.
         *
         * @param game The running game
         * @return The supplied win, or a result telling whether the built-in conditions may run
         */
        CompoundEventResult<WinResult> provide(IServerGame game);
    }

    /**
     * Called once a win result exists, before anything is done with it.
     * <p>
     * Return {@link EventResult#FALSE} to reject the result: it is then not handled, and the game
     * keeps running. Any other result accepts it.
     */
    interface GotWinResult {
        /**
         * Handle the event.
         *
         * @param game   The running game
         * @param result The win result which was gathered
         * @return {@link EventResult#FALSE} to reject the result, any other result to accept it
         */
        EventResult handle(IServerGame game, WinResult result);
    }

    /**
     * Called when a win result was accepted, right before it is applied to the game.
     * <p>
     * Informational: the result has already been decided, and returning nothing cannot change it.
     * A listener may still end the game itself; the accepted behavior is then not applied.
     */
    interface HandleWinResult {
        /**
         * Handle the event.
         *
         * @param game   The running game
         * @param result The accepted win result
         */
        void handle(IServerGame game, WinResult result);
    }

    /**
     * Called once a game created its hunter release timer, before the timer is registered for ticking.
     * <p>
     * Only games configured to release their hunters after a delay create such a timer: a game which
     * releases them immediately, or one with no release time configured, does not.
     */
    interface HunterReleaseTimerInitialized {
        /**
         * Handle the event.
         *
         * @param game  The game which created the timer
         * @param timer The created timer, which is not ticking yet
         */
        void handle(IServerGame game, IHunterReleaseTimer timer);
    }
}
