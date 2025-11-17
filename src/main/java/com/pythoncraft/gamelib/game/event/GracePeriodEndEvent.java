package com.pythoncraft.gamelib.game.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * Event fired when the grace period ends in a game.
 * Not fired during game setup or if there is no grace period.
 */

public class GracePeriodEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public GracePeriodEndEvent() {}

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}