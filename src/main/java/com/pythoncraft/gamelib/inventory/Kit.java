package com.pythoncraft.gamelib.inventory;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.pythoncraft.gamelib.Logger;
import com.pythoncraft.gamelib.inventory.order.InventoryOrder;

public class Kit {
    public String displayName;
    public Material material;
    public HashMap<String, ItemTemplate> items = new HashMap<>();

    public Kit(String displayName, Material material, HashMap<String, ItemTemplate> items) {
        this.displayName = displayName;
        this.material = material;
        this.items = items;
    }

    public void give(Player player, InventoryOrder inventoryOrder) {
        for (String slotName : items.keySet()) {
            if (inventoryOrder.hasSlot(slotName)) {
                int slot = inventoryOrder.getSlot(slotName);
                player.getInventory().setItem(slot, items.get(slotName).getFor(player));
            } else {
                Logger.warn("Slot name '{0}' not found in inventory order '{1}' for player {2}. Skipping item.", slotName, inventoryOrder.name, player.getName());
            }
        }
    }

    public void give(Player player) {
        give(player, InventoryOrder.get(player));
    }
}
