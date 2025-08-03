package com.pythoncraft.gamelib.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pythoncraft.gamelib.Chat;
import com.pythoncraft.gamelib.GameLib;
import com.pythoncraft.gamelib.Logger;

public class ItemLoader {
    public static HashMap<String, ItemStack> loadItemsMap(ConfigurationSection itemsSection) {
        HashMap<String, ItemStack> items = new HashMap<>();
        if (itemsSection == null) {return items;}

        for (String key : itemsSection.getKeys(false)) {
            Logger.info("Processing key: " + key);
            
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
            ItemStack itemStack;
            
            if (itemSection == null) {
                // Short version: `stick * 64`
                itemStack = loadShortItemStack(itemsSection.getString(key));
            } else {
                // Full version: use loadItemSection method
                itemStack = loadItemSection(itemSection);
            }
            
            if (itemStack == null) {
                Logger.warn("Failed to load item for key: " + key);
                continue;
            }

            items.put(key, itemStack);
            Logger.info("Loaded item: " + itemStack.getType().toString());
        }

        return items;
    }

    public static List<ItemStack> loadItems(ConfigurationSection itemsSection) {
        return new ArrayList<>(loadItemsMap(itemsSection).values());
    }

    public static HashMap<String, ItemTemplate> loadConditionalItemsMap(ConfigurationSection templatesSection, HashMap<String, Predicate<Player>> conditions) {
        HashMap<String, ItemTemplate> templates = new HashMap<>();
        if (templatesSection == null) {return templates;}

        for (String itemKey : templatesSection.getKeys(false)) {
            ItemTemplate template;

            ConfigurationSection itemSection = templatesSection.getConfigurationSection(itemKey);
            
            if (itemSection == null) {
                // Short version: `stick * 64`
                template = loadShortItemTemplate(templatesSection.getString(itemKey));
            } else {
                // Full version
                template = loadConditionalItemSection(itemSection, conditions);
            }

            if (template == null) {
                Logger.warn("Failed to load item template for key: " + itemKey);
                continue;
            }

            templates.put(itemKey, template);
            Logger.info("Loaded item: " + template.getIndexed(0).getType().toString());
        }

        return templates;
    }

    public static List<ItemTemplate> loadConditionalItems(ConfigurationSection templatesSection, HashMap<String, Predicate<Player>> conditions) {
        return new ArrayList<>(loadConditionalItemsMap(templatesSection, conditions).values());
    }

    public static ItemStack loadShortItemStack(String item) {
        if (item == null || item.isEmpty()) {return null;}

        int count = 1;
        if (item.contains("*")) {
            String[] parts = item.split("\\*");
            item = parts[0].trim();
            count = Integer.parseInt(parts[1].trim());
        }
        Material itemMaterial = Material.getMaterial(item.toUpperCase());
        if (itemMaterial == null) {itemMaterial = GameLib.DEFAULT_MATERIAL;}
        ItemStack itemStack = new ItemStack(itemMaterial);
        itemStack.setAmount(count);

        return itemStack;
    }

    public static ItemTemplate loadShortItemTemplate(String item) {
        return new ItemTemplate(loadShortItemStack(item));
    }

    public static ItemStack loadItemSection(ConfigurationSection itemSection, ItemStack baseItem) {
        if (itemSection == null) {return null;}

        String itemId = itemSection.getString("id");
        String itemName = itemSection.getString("custom-name") != null ? itemSection.getString("custom-name") : null;
        Material itemMaterial = itemId != null ? Material.getMaterial(itemId.toUpperCase()) : null;
        if (itemMaterial == null) {itemMaterial = Material.STICK;}
        Integer count = itemSection.getInt("count", 1);
        HashMap<String, Integer> enchants = new HashMap<>();
        Integer durability = null;
        TrimMaterial trimMaterial = null;
        TrimPattern trimPattern = null;
        List<PotionEffect> potionEffects = new ArrayList<>();
        Color potionColor = null;

        ItemStack itemStack;

        if (baseItem != null) {
            itemStack = baseItem.clone();
        } else {
            itemStack = new ItemStack(itemMaterial);
        }

        // itemStack = new ItemStack(itemMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemName != null && !itemName.isEmpty()) {
            itemMeta.setDisplayName(Chat.c(itemName));
        }

        var enchantments = itemSection.getConfigurationSection("enchantments");
        if (enchantments != null) {
            for (String enchantKey : enchantments.getKeys(false)) {
                String enchantName = enchantKey.toUpperCase();
                int level = enchantments.getInt(enchantKey);
                enchants.put(enchantName, level);
            }
        }

        if (itemSection.contains("durability")) {durability = itemSection.getInt("durability");}

        if (itemSection.contains("trim-material")) {
            String trimMaterialName = itemSection.getString("trim-material").toLowerCase();
            trimMaterial = Registry.TRIM_MATERIAL.get(NamespacedKey.minecraft(trimMaterialName));
        }

        if (itemSection.contains("trim-pattern")) {
            String trimPatternName = itemSection.getString("trim-pattern").toLowerCase();
            trimPattern = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft(trimPatternName));
        }

        if (itemSection.contains("effects")) {
            var potionEffectsSection = itemSection.getConfigurationSection("effects");
            if (potionEffectsSection != null) {
                for (String effectKey : potionEffectsSection.getKeys(false)) {
                    var effectSection = potionEffectsSection.getConfigurationSection(effectKey);
                    if (effectSection == null) {continue;}

                    String effectName = (effectKey.matches("^[a-z_]+$")) ? effectKey : effectSection.getString("type");
                    if (effectName == null || effectName.isEmpty()) {continue;}
                    int duration = effectSection.getInt("duration", 0);
                    int amplifier = effectSection.getInt("amplifier", 0);
                    boolean hide = effectSection.getBoolean("hide", false);

                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(effectName), duration, amplifier, false, hide);
                    potionEffects.add(effect);
                }
            }
        }

        if (itemSection.contains("potion-color")) {
            String colorString = itemSection.getString("potion-color");
            if (colorString != null && !colorString.isEmpty()) {
                potionColor = Color.fromRGB(Integer.parseInt(colorString.replace("#", ""), 16));
            }
        }

        Logger.info("Loading item: {0} - name {1}, count {2}, durability {3}, base item {4}", itemMaterial, itemName, count, durability, baseItem);

        for (String enchantment : enchants.keySet()) {
            Enchantment enchant = Enchantment.getByName(enchantment);
            if (enchant == null) {continue;}
            itemMeta.addEnchant(enchant, enchants.get(enchantment), true);
        }

        if (durability != null) {
            if (durability == 0) {
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            } else if (itemMeta instanceof Damageable damageable) {
                itemMeta.setUnbreakable(false);
                damageable.setDamage(itemStack.getType().getMaxDurability() - durability);
            }
        }

        if (itemMeta instanceof ArmorMeta armorMeta) {
            if (trimPattern != null && trimMaterial != null) {
                armorMeta.setTrim(new ArmorTrim(trimMaterial, trimPattern));
            } else if (trimMaterial != null) {
                ArmorTrim trim = armorMeta.getTrim();
                TrimPattern oldPattern = TrimPattern.BOLT;
                if (trim != null) {oldPattern = trim.getPattern();}
                armorMeta.setTrim(new ArmorTrim(trimMaterial, oldPattern));
            } else if (trimPattern != null) {
                ArmorTrim trim = armorMeta.getTrim();
                TrimMaterial oldMaterial = TrimMaterial.DIAMOND;
                if (trim != null) {oldMaterial = trim.getMaterial();}
                armorMeta.setTrim(new ArmorTrim(oldMaterial, trimPattern));
            }
        }

        if (itemMeta instanceof PotionMeta potionMeta) {
            for (PotionEffect effect : potionEffects) {
                potionMeta.addCustomEffect(effect, true);
            }

            if (potionColor != null) {
                potionMeta.setColor(potionColor);
            }
        }

        itemStack.setAmount(count);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack loadItemSection(ConfigurationSection itemSection) {
        return loadItemSection(itemSection, null);
    }

    public static ItemTemplate loadConditionalItemSection(ConfigurationSection itemSection, HashMap<String, Predicate<Player>> conditions) {
        if (itemSection == null) {return null;}

        ItemTemplate template = new ItemTemplate();
        ItemStack itemStack = loadItemSection(itemSection);
        
        for (String conditionKey : conditions.keySet()) {
            if (itemSection.contains(conditionKey)) {
                ItemStack conditionalItemStack = loadItemSection(itemSection.getConfigurationSection(conditionKey), itemStack);
                template.addItem(conditionalItemStack, conditions.get(conditionKey));
            }
        }
        
        template.addItem(itemStack, player -> true);

        return template;
    }
}