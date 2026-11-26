package snw.mods.rfm.api.server.game.boot;

import snw.mods.rfm.api.util.property.PropertyBoard;

/**
 * The options a game is asked to start with.
 * <p>
 * A boot context is the input of {@link snw.mods.rfm.api.server.game.IServerGame#start}; it is produced
 * before the game starts, filled with boot options, and then handed to the game. The game reads the values
 * it needs while starting, and the caller may keep the context around to reuse the same options for a later
 * start.
 */
public interface GameBootContext {
    /**
     * Get the property board holding the options of this game boot process.
     * <p>
     * The board only accepts keys of the {@link GameBootDomain}, so options cannot be mixed with
     * runtime state of a running game.
     *
     * @return The property board of this context
     */
    PropertyBoard<GameBootDomain> getPropertyBoard();
}
