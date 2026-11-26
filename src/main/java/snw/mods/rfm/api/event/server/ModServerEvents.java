package snw.mods.rfm.api.event.server;

import snw.mods.rfm.api.event.Event;
import snw.mods.rfm.api.platform.IModPlatform;
import snw.mods.rfm.api.platform.PlatformInstance;

/**
 * Events covering the lifecycle of the mod itself, as opposed to the lifecycle of a single game.
 */
public interface ModServerEvents {
    /**
     * Called once the platform implementation is ready and bound to {@link PlatformInstance}.
     *
     * @see ModPlatformDefined
     */
    Event<ModPlatformDefined> MOD_PLATFORM_DEFINED = Event.makeLoop();

    /**
     * Called once platform instance bound to {@link PlatformInstance},
     * {@link PlatformInstance#getInstance()} should return the same object
     * compared to the provided argument. <br>
     * The mod may <b>NOT</b> fully initialize yet at the call time of this event.
     */
    interface ModPlatformDefined {
        /**
         * Handle the event.
         *
         * @param platform The platform implementation, equal to {@link PlatformInstance#getInstance()}
         */
        void handle(IModPlatform platform);
    }
}
