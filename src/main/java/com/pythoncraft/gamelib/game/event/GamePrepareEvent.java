package com.pythoncraft.gamelib.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * Event fired when a game is preparing to start, before the prepare timer begins.
 * (right after the player runs the command)
 */

public class GamePrepareEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public GamePrepareEvent() {}

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
