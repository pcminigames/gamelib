package com.pythoncraft.gamelib.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * Event fired when a game starts. If there is a grace period, this event is fired at the beginning of the grace period.
 * (right after the prepare timer ends)
 */

public class GameStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public GameStartEvent() {}

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
