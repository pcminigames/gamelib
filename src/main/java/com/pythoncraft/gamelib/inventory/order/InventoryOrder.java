package com.pythoncraft.gamelib.inventory.order;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class InventoryOrder {
    public HashMap<String, Integer> slots = new HashMap<>();
    public static HashMap<String, InventoryOrder> orders = new HashMap<>();

    public InventoryOrder(HashMap<String, Integer> slots) {
        this.slots = slots;
    }

    public int getSlot(String slotName) {
        if (!slots.containsKey(slotName)) {
            throw new IllegalArgumentException("Slot name '" + slotName + "' does not exist in the inventory order.");
        }
        
        return slots.get(slotName);
    }

    public static InventoryOrder get(String playerName) {
        if (!orders.containsKey(playerName)) {
            throw new IllegalArgumentException("No inventory order found for player: " + playerName);
        }

        return orders.get(playerName);
    }

    public static InventoryOrder get(Player player) {
        return get(player.getName());
    }
}
