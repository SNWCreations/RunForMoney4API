package snw.mods.rfm.api.platform;

import org.jetbrains.annotations.ApiStatus;

/**
 * Holds the platform implementation the mod runs on.
 * <p>
 * The implementation is set by the mod itself during initialization; it is not meant to be provided or
 * replaced by other mods.
 */
public final class PlatformInstance {
    private PlatformInstance() {
    }

    private static IModPlatform instance;

    /**
     * Get the platform implementation the mod runs on.
     *
     * @return The bound platform implementation
     * @throws IllegalStateException Thrown if the mod has not initialized yet
     */
    public static IModPlatform getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Mod has not been initialized");
        }
        return instance;
    }

    /**
     * Bind the platform implementation to this class.
     * <p>
     * Only the mod itself calls this, exactly once during initialization.
     *
     * @param platform The platform implementation
     * @throws IllegalStateException Thrown if a platform implementation was already bound
     */
    @ApiStatus.Internal
    public static void setInstance(IModPlatform platform) {
        if (instance != null) {
            throw new IllegalStateException("Mod has already been initialized");
        }
        instance = platform;
    }
}
