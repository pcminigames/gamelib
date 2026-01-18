package com.pythoncraft.gamelib.inventory;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.pythoncraft.gamelib.Logger;

public class Kit {
    public String displayName;
    public Material material;
    public HashMap<String, ItemTemplate> items = new HashMap<>();

    public Kit(String displayName, Material material, HashMap<String, ItemTemplate> items) {
        this.displayName = displayName;
        this.material = material;
        this.items = items;
    }

    public void give(Player player, InventoryLayout inventoryLayout) {
        for (String slotName : items.keySet()) {
            if (inventoryLayout.hasSlot(slotName)) {
                int slot = inventoryLayout.getSlot(slotName);
                player.getInventory().setItem(slot, items.get(slotName).getFor(player));
            } else {
                Logger.warn("Slot name "  + slotName + " not found in inventory layout " + inventoryLayout.name + " for player " + player.getName() + ". Skipping item.");
                Logger.warn("Available slots: " + String.join(", ", inventoryLayout.slots.keySet()));
            }
        }
    }

    public void give(Player player) {
        give(player, InventoryLayout.get(player));
    }
}
