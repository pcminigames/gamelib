package com.pythoncraft.gamelib.inventory;

import java.util.function.Predicate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConditionalItem {
    private ItemStack itemStack;
    private Predicate<Player> condition;

    public ConditionalItem(ItemStack itemStack, Predicate<Player> condition) {
        this.itemStack = itemStack;
        this.condition = condition;
    }

    public ItemStack getItem() {
        return itemStack.clone();
    }

    public boolean test(Player player) {
        return condition.test(player);
    }
}
