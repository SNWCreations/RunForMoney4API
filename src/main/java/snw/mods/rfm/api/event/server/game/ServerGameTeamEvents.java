package snw.mods.rfm.api.event.server.game;

import snw.mods.rfm.api.event.Event;
import snw.mods.rfm.api.event.EventResult;
import snw.mods.rfm.api.server.game.IServerGamePlayer;
import snw.mods.rfm.api.server.game.IServerGameTeam;

/**
 * Events covering a player's membership in a game team.
 * <p>
 * Every event is dispatched on the server thread. A player belongs to at most one team at a time:
 * moving a player to another team removes them from their previous one first.
 */
public interface ServerGameTeamEvents {
    /**
     * Asked before a player is added to a team.
     *
     * @see PreAddPlayer
     */
    Event<PreAddPlayer> PRE_ADD_PLAYER = Event.makeEventResult();
    /**
     * Called once a player was added to a team.
     *
     * @see PostAddPlayer
     */
    Event<PostAddPlayer> POST_ADD_PLAYER = Event.makeLoop();
    /**
     * Called when a player was removed from a team.
     *
     * @see PostRemovePlayer
     */
    Event<PostRemovePlayer> POST_REMOVE_PLAYER = Event.makeLoop();

    /**
     * Called when a player is joining a team. <br>
     * Return {@link EventResult#FALSE} to reject the player join.
     * <p>
     * The player is still in their previous team, if any, when this event is dispatched.
     */
    interface PreAddPlayer {
        /**
         * Handle the event.
         *
         * @param team   The team the player is joining
         * @param player The player being added
         * @return {@link EventResult#FALSE} to reject the join, any other result to accept it
         */
        EventResult handle(IServerGameTeam team, IServerGamePlayer player);
    }

    /**
     * Called when a player is joined a team. <br>
     * The player's team reference was already updated, and their abilities were already reconfigured for
     * the new team.
     */
    interface PostAddPlayer {
        /**
         * Handle the event.
         *
         * @param team   The team the player joined
         * @param player The player which was added
         */
        void handle(IServerGameTeam team, IServerGamePlayer player);
    }

    /**
     * Called when a player left a team. <br>
     * The player's team reference was already cleared, and their abilities were already reset to the
     * defaults of a player who is in no team.
     */
    interface PostRemovePlayer {
        /**
         * Handle the event.
         *
         * @param team   The team the player left
         * @param player The player which was removed
         */
        void handle(IServerGameTeam team, IServerGamePlayer player);
    }
}
