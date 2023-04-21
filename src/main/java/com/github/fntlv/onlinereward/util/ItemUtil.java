package com.github.fntlv.onlinereward.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static ItemStack getItemStack(String text){
        String[] name = text.split("-");
        Material material = Material.matchMaterial(name[0]);
        if (material != null){
            ItemStack itemStack = new ItemStack(material,1,Byte.parseByte(name[1]));
            return itemStack;
        } else {
            return new ItemStack(Material.matchMaterial("STAINED_GLASS_PANE"));
        }
    }

}
