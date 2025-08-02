package com.pythoncraft.gamelib.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.pythoncraft.gamelib.GameLib;

public class ItemTemplate {
    public List<ConditionalItem> items = new ArrayList<>();

    public ItemTemplate(List<ConditionalItem> items) {
        this.items = items;
    }

    public ItemTemplate(ConditionalItem item) {
        this.items.add(item);
    }

    public ItemTemplate(ItemStack item, Predicate<Player> condition) {
        this.items.add(new ConditionalItem(item, condition));
    }

    public ItemTemplate(ItemStack item) {
        this.items.add(new ConditionalItem(item, player -> true));
    }
    
    public ItemTemplate() {}

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

    public void addItem(ConditionalItem item, boolean placeAtBeginning) {
        if (placeAtBeginning) {
            items.add(0, item); // Add to the beginning of the list
        } else {
            items.add(item); // Add to the end of the list
        }
    }

    public void addItem(ItemStack item, Predicate<Player> condition, boolean placeAtBeginning) {
        addItem(new ConditionalItem(item, condition), placeAtBeginning);
    }

    public void addItem(ConditionalItem item) {
        addItem(item, false);
    }

    public void addItem(ItemStack item, Predicate<Player> condition) {
        addItem(new ConditionalItem(item, condition));
    }
}
