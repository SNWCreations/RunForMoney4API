package snw.mods.rfm.api.platform;

/**
 * The running Minecraft server, as far as the mod needs it.
 * <p>
 * The interface deliberately exposes only a few values, so the API module does not have to depend on
 * Minecraft classes. Code that needs more should work through the mod's own abstraction instead.
 */
public interface IMinecraftServer {
    /**
     * Get (possibly rounded) tick rate of the game. <br>
     * For Minecraft 1.20.3 and later, this value could be changed by /tick command
     *  (introduced by gnembon, from Carpet mod). For Minecraft versions earlier than
     *  that, this method <b>may</b> always (but not always) return {@code 20} because that is vanilla design. <br>
     * If the running game instance has mods that could change tick rate (like Carpet) <b>we know</b>,
     *  we will read the value from their own API.
     *
     * @return The tick rate reported by the Minecraft instance or related mod
     */
    @SuppressWarnings("SpellCheckingInspection") // No need to check nickname used on Internet!
    int getTickRate();
}
