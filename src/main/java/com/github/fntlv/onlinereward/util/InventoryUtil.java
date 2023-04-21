package com.github.fntlv.onlinereward.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {

    public static Inventory createInventory(Player player, int size, String title) {
        return Bukkit.createInventory(player, size, title);
    }

    // 在指定位置设置一个物品
    public static void setItem(Inventory inventory, int slot, ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);

    }

    // 注册一个点击事件
    public static void registerItemClickEvent(Inventory inventory, int slot, String command) {
        inventory.getItem(slot).setAmount(1);
        inventory.getItem(slot).setType(inventory.getItem(slot).getType());
        inventory.getItem(slot).setDurability(inventory.getItem(slot).getDurability());

        ItemMeta meta = inventory.getItem(slot).getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(command);
        meta.setLore(lore);
        inventory.getItem(slot).setItemMeta(meta);
    }

    // 执行点击事件的命令
    public static void executeCommandOnClick(Player player, ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String line : lore) {
                if (line.startsWith("/")) {
                    String command = line.replaceFirst("/", "");
                    Bukkit.dispatchCommand((CommandSender) player, command);
                }
            }
        }
    }

}
