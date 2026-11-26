package snw.mods.rfm.api.platform;

import snw.mods.rfm.api.event.Event;

/**
 * Intermediate interface for providing unified platform-specific functionality access.
 * <p>
 * A mod loader implementation of this interface is bound to {@link PlatformInstance} during mod
 * initialization, and every API call that needs platform-specific behaviour goes through it.
 */
@SuppressWarnings("unchecked")
public interface IModPlatform {
    /**
     * Get the active Minecraft server instance. <br>
     * On a dedicated server this never returns null once the server is running. On a client it returns
     * null while no integrated server is active, so callers on the client side must check for that.
     *
     * @return The active Minecraft server instance running this mod, or null if there is none
     */
    IMinecraftServer getMinecraftServer();

    /**
     * Create an event which calls every listener and ignores their return values.
     *
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The listener type
     * @return The created event
     * @see Event#makeLoop
     */
    <T> Event<T> makeLoopEvent(T... typeGetter);

    /**
     * Create an event whose listeners may decide whether the remaining ones are still evaluated.
     *
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The listener type
     * @return The created event
     * @see Event#makeEventResult
     */
    <T> Event<T> makeEventResult(T... typeGetter);

    /**
     * Create an event whose listeners carry both a control value and a payload.
     *
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The listener type
     * @return The created event
     * @see Event#makeCompoundEventResult
     */
    <T> Event<T> makeCompoundEventResult(T... typeGetter);
}
