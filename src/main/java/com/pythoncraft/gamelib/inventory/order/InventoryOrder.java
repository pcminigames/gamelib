package com.pythoncraft.gamelib.inventory.order;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class InventoryOrder {
    public HashMap<String, Integer> slots = new HashMap<>();
    public static HashMap<String, InventoryOrder> orders = new HashMap<>();
    public static HashMap<String, String> playerOrders = new HashMap<>();

    public InventoryOrder(HashMap<String, Integer> slots) {
        this.slots = slots;
    }

    public InventoryOrder() {}

    public void addSlot(String slotName, int slotIndex) {
        if (slots.containsKey(slotName)) {
            throw new IllegalArgumentException("Slot name '" + slotName + "' already exists in the inventory order.");
        }
        
        slots.put(slotName, slotIndex);
    }

    public int getSlot(String slotName) {
        if (!slots.containsKey(slotName)) {
            throw new IllegalArgumentException("Slot name '" + slotName + "' does not exist in the inventory order.");
        }
        
        return slots.get(slotName);
    }

    public static InventoryOrder get(String playerName) {
        if (!playerOrders.containsKey(playerName)) {
            throw new IllegalArgumentException("No inventory order found for player: " + playerName);
        }

        return orders.get(playerOrders.get(playerName));
    }

    public static InventoryOrder get(Player player) {
        return get(player.getName());
    }
}
