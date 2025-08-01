package com.pythoncraft.gamelib.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.pythoncraft.gamelib.GameLib;

public class ItemTemplate {
    public List<ConditionalItem> items = new ArrayList<>();

    public ItemTemplate(List<ConditionalItem> items) {
        this.items = items;
    }

    public ItemStack getIndexed(int index) {
        if (index < 0 || index >= items.size()) {
            return GameLib.getItemStack("§c§o§lItemTemplate index out of bounds");
        }

        return items.get(index).getItem();
    }

    public ItemStack getFor(Player player) {
        for (ConditionalItem conditionalItem : items) {
            if (conditionalItem.test(player)) {
                return conditionalItem.getItem();
            }
        }

        return GameLib.getItemStack("§c§o§lNo item available for player");
    }
}
