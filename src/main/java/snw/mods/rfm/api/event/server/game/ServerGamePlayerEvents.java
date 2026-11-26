package snw.mods.rfm.api.event.server.game;

import snw.mods.rfm.api.event.Event;
import snw.mods.rfm.api.event.EventResult;
import snw.mods.rfm.api.server.game.IServerGame;
import snw.mods.rfm.api.server.game.IServerGamePlayer;
import snw.mods.rfm.api.server.game.eliminate.Elimination;

/**
 * Events covering a player's participation in, and elimination from, a game.
 * <p>
 * Every event is dispatched on the server thread.
 */
public interface ServerGamePlayerEvents {
    /**
     * Asked before a player is added to a game.
     *
     * @see PreJoinGame
     */
    Event<PreJoinGame> PLAYER_PRE_JOIN_GAME = Event.makeEventResult();
    /**
     * Called once a player was added to a game.
     *
     * @see PostJoinGame
     */
    Event<PostJoinGame> PLAYER_POST_JOIN_GAME = Event.makeLoop();
    /**
     * Called when a player was removed from a game.
     *
     * @see LeaveGame
     */
    Event<LeaveGame> PLAYER_LEAVE_GAME = Event.makeLoop();
    /**
     * Called when a player connected to the server.
     *
     * @see Connected
     */
    Event<Connected> PLAYER_CONNECTED = Event.makeLoop();
    /**
     * Called when a player disconnected from the server.
     *
     * @see Disconnected
     */
    Event<Disconnected> PLAYER_DISCONNECTED = Event.makeLoop();
    /**
     * Called when a player hurt another player of the same game.
     *
     * @see PlayerPunch
     */
    Event<PlayerPunch> PLAYER_PUNCH = Event.makeEventResult();
    /**
     * Asked before an elimination is queued for review.
     *
     * @see PreQueuePlayerEliminate
     */
    Event<PreQueuePlayerEliminate> PRE_QUEUE_PLAYER_ELIMINATE = Event.makeEventResult();
    /**
     * Called once an elimination was queued for review.
     *
     * @see PostQueuePlayerEliminate
     */
    Event<PostQueuePlayerEliminate> POST_QUEUE_PLAYER_ELIMINATE = Event.makeLoop();
    /**
     * Asked before an elimination is evaluated.
     *
     * @see PreEvalPlayerEliminate
     */
    Event<PreEvalPlayerEliminate> PRE_EVAL_PLAYER_ELIMINATE = Event.makeEventResult();
    /**
     * Called once an elimination was evaluated and accepted.
     *
     * @see PostEvalPlayerEliminate
     */
    Event<PostEvalPlayerEliminate> POST_EVAL_PLAYER_ELIMINATE = Event.makeLoop();

    /**
     * Called when a player joining a game. <br>
     * Return {@link EventResult#FALSE} if you want to refuse the join.
     * <p>
     * A refusal leaves the player out of the game entirely: no post-join event is fired for them.
     */
    interface PreJoinGame {
        /**
         * Handle the event.
         *
         * @param player The player being added
         * @param game   The game the player is joining
         * @return {@link EventResult#FALSE} to refuse the join, any other result to accept it
         */
        EventResult handle(IServerGamePlayer player, IServerGame game);
    }

    /**
     * Called when a player joined a game. <br>
     * The player's {@linkplain IServerGamePlayer#getAbilities() abilities} already exist and reflect the
     * team the player is in, and the player's game reference is already set.
     */
    interface PostJoinGame {
        /**
         * Handle the event.
         *
         * @param player The player which was added
         * @param game   The game the player joined
         */
        void handle(IServerGamePlayer player, IServerGame game);
    }

    /**
     * Called when a player left a game. <br>
     * The player's team membership was already cleared and their abilities were already dropped, so the
     * event cannot read them back.
     */
    interface LeaveGame {
        /**
         * Handle the event.
         *
         * @param player The player which was removed
         * @param game   The game the player left
         */
        void handle(IServerGamePlayer player, IServerGame game);
    }

    /**
     * Called when a player connected to the server. <br>
     * This event does not guarantee that the player is in a running game.
     */
    interface Connected {
        /**
         * Handle the event.
         *
         * @param player The player which connected
         */
        void handle(IServerGamePlayer player);
    }

    /**
     * Called when a player disconnected from the server. <br>
     * This event does not guarantee that the player is in a running game.
     */
    interface Disconnected {
        /**
         * Handle the event.
         *
         * @param player The player which disconnected
         */
        void handle(IServerGamePlayer player);
    }

    /**
     * Called when a player got punched by another player.
     * <p>
     * The event is only dispatched when both players belong to the same game. It is asked <b>before</b> the
     * built-in rules (friendly fire and hunter-versus-runner handling), and a result carrying a value
     * replaces them entirely: return {@link EventResult#FALSE} to cancel the damage, or
     * {@link EventResult#TRUE} to allow it. A result carrying no value, including {@link EventResult#PASS}
     * and {@link EventResult#STOP}, lets the built-in rules decide.
     */
    interface PlayerPunch {
        /**
         * Handle the event.
         *
         * @param game         The game both players belong to
         * @param attacker     The player dealing the damage
         * @param attacked     The player receiving the damage
         * @param directDamage True if the damage came directly from the attacker, false if it was
         *                     indirect (for example a projectile or an explosion)
         * @return The decision, or {@link EventResult#PASS} to defer to the built-in rules
         */
        EventResult handle(IServerGame game, IServerGamePlayer attacker, IServerGamePlayer attacked, boolean directDamage);
    }

    /**
     * Called when a player is being queued for elimination.
     * The elimination queue events are <b>ONLY</b> expected to happen when the game is configured to have a timeout before elimination being automatically approved.
     * <p>
     * Returning {@link EventResult#FALSE} refuses the elimination, so the player is neither queued nor
     * eliminated.
     */
    interface PreQueuePlayerEliminate {
        /**
         * Handle the event.
         *
         * @param game        The game the elimination belongs to
         * @param elimination The elimination about to be queued
         * @return {@link EventResult#FALSE} to refuse the elimination, any other result to accept it
         */
        EventResult handle(IServerGame game, Elimination elimination);
    }

    /**
     * Called when a player is queued for elimination. <br>
     * The elimination queue events are <b>ONLY</b> expected to happen when the game is configured to have a timeout before elimination being automatically approved.
     * <p>
     * The elimination is already in the queue when this event is dispatched.
     */
    interface PostQueuePlayerEliminate {
        /**
         * Handle the event.
         *
         * @param game        The game the elimination belongs to
         * @param elimination The elimination which was queued
         */
        void handle(IServerGame game, Elimination elimination);
    }

    /**
     * Called when a player is being eliminated. <br>
     * The eliminate evaluation events are <b>EXPECTED</b> to be called for every player elimination.
     * <p>
     * Returning {@link EventResult#FALSE} rejects the elimination: the player stays in their current
     * state, and the post-evaluation event is not fired.
     */
    interface PreEvalPlayerEliminate {
        /**
         * Handle the event.
         *
         * @param game        The game the elimination belongs to
         * @param elimination The elimination about to be applied
         * @return {@link EventResult#FALSE} to reject the elimination, any other result to accept it
         */
        EventResult handle(IServerGame game, Elimination elimination);
    }

    /**
     * Called when a player is eliminated. <br>
     * The eliminate evaluation events are <b>EXPECTED</b> to be called for every player elimination.
     * <p>
     * The player has already been moved to the eliminated team and teleported to the room for eliminated
     * players, if one is configured.
     */
    interface PostEvalPlayerEliminate {
        /**
         * Handle the event.
         *
         * @param game        The game the elimination belongs to
         * @param elimination The elimination which was applied
         */
        void handle(IServerGame game, Elimination elimination);
    }
}
