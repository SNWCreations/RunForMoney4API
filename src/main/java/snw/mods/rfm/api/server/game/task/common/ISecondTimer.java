package snw.mods.rfm.api.server.game.task.common;

import snw.mods.rfm.api.event.Event;
import snw.mods.rfm.api.util.EventProvider;

/**
 * A countdown measured in game seconds.
 * <p>
 * The timer advances only while the game is running and not frozen, so freezing the game pauses it. Every
 * elapsed game second produces exactly one notification: the first {@code N - 1} seconds of an
 * {@code N}-second timer are reported through {@link Events#onSecondPassed()} as {@code N-1, N-2, ..., 1},
 * and the final second fires {@link Events#onTimeUp()} instead. Both happen on the server thread.
 * <p>
 * The events are reachable through {@link #events()}; the {@link Events} type lets a specific timer declare
 * its own event group without changing the rest of this contract.
 *
 * @param <E> The event group of this timer
 */
public interface ISecondTimer<E extends ISecondTimer.Events> extends EventProvider<E> {
    /**
     * Get how many game seconds are left.
     *
     * @return The remaining seconds, never negative
     */
    int getRemainingSeconds();

    /**
     * Check whether this timer reached zero.
     *
     * @return True if the countdown finished
     */
    boolean isDone();

    /**
     * The events of a second timer.
     */
    interface Events {
        /**
         * Get the event fired when the countdown reached zero.
         *
         * @return The time-up event
         */
        Event<TimeUp> onTimeUp();

        /**
         * Get the event fired for every elapsed game second, except the final one.
         *
         * @return The second-passed event
         */
        Event<SecondPassed> onSecondPassed();

        /**
         * The listener of {@link #onTimeUp()}.
         */
        interface TimeUp {
            /**
             * Handle the timer reaching zero.
             */
            void handle();
        }

        /**
         * The listener of {@link #onSecondPassed()}.
         */
        interface SecondPassed {
            /**
             * Handle one elapsed game second.
             *
             * @param remainingSeconds The seconds left after that second elapsed, always positive
             */
            void handle(int remainingSeconds);
        }
    }
}
