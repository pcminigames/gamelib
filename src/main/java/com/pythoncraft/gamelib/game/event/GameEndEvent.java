package com.pythoncraft.gamelib.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * Event fired when the game ends.
 * If there is a winner, the winner Player object is provided; otherwise, it is null.
 */

public class GameEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player winner;

    public GameEndEvent(Player winner) {
        this.winner = winner;
    }

    public GameEndEvent() {
        this.winner = null;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
