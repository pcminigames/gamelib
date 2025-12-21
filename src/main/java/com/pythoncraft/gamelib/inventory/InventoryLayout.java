package com.pythoncraft.gamelib.inventory;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class InventoryLayout {
    public HashMap<String, Integer> slots = new HashMap<>();
    public String name;
    public static HashMap<String, InventoryLayout> layouts = new HashMap<>();
    public static HashMap<String, String> playerLayouts = new HashMap<>();

    public InventoryLayout(String name, HashMap<String, Integer> slots) {
        this.name = name;
        this.slots = slots;
    }

    public InventoryLayout(String name) {
        this.name = name;
    }

    public void addSlot(String slotName, int slotIndex) {
        if (slots.containsKey(slotName)) {
            throw new IllegalArgumentException("Slot name '" + slotName + "' already exists in the inventory layout.");
        }
        
        slots.put(slotName, slotIndex);
    }

    public int getSlot(String slotName) {
        if (!slots.containsKey(slotName)) {
            throw new IllegalArgumentException("Slot name '" + slotName + "' does not exist in the inventory layout (" + this.name + "). Available slots: " + slots.keySet());
        }
        
        return slots.get(slotName);
    }
    
    public boolean hasSlot(String slotName) {
        return slots.containsKey(slotName);
    }

    public static InventoryLayout get(String playerName) {
        if (!playerLayouts.containsKey(playerName)) {
            throw new IllegalArgumentException("No inventory layout found for player: " + playerName);
        }

        return layouts.get(playerLayouts.get(playerName));
    }

    public static InventoryLayout get(Player player) {
        return get(player.getName());
    }
}
