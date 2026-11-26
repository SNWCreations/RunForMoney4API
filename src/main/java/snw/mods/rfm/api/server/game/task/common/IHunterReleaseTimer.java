package snw.mods.rfm.api.server.game.task.common;

/**
 * The timer which delays the moment the hunters are released into the game.
 * <p>
 * It behaves exactly like any other {@link ISecondTimer}; the separate type exists so listeners of
 * {@link snw.mods.rfm.api.event.server.game.ServerGameEvents#HUNTER_RELEASE_TIMER_INITIALIZED} can name this
 * timer without depending on the general timer type.
 */
public interface IHunterReleaseTimer extends ISecondTimer<IHunterReleaseTimer.Events> {
    /**
     * The events of a hunter release timer.
     */
    interface Events extends ISecondTimer.Events {
    }
}
