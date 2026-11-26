package snw.mods.rfm.api.client.game;

import snw.mods.rfm.api.game.IGamePlayer;

/**
 * A player as seen by the client side of the game engine.
 * <p>
 * The client only knows a small part of what the server knows about a player; use this type when the
 * same code runs on both sides and the extra server-side data is not needed.
 */
public interface IClientGamePlayer extends IGamePlayer {
}
