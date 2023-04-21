package com.github.fntlv.onlinereward.config;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.util.ConfigUtil;
import com.github.fntlv.onlinereward.util.InventoryUtil;
import com.github.fntlv.onlinereward.util.ItemUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class GuiSetting {
    private static ConfigUtil configUtil;
    private static int size;
    private static String title;
    private static List<Slot> slots = new ArrayList<>();

    public static void init(){
        configUtil = new ConfigUtil(OnlineReward.getInstance(),"gui.yml");
        configUtil.load();
        loadGuiData();
    }

    public static void loadGuiData(){
        size = configUtil.getConfig().getInt("Gui.Size");
        title = replace(configUtil.getConfig().getString("Gui.Title"));
        for (String key :configUtil.getConfig().getConfigurationSection("Gui.Items").getKeys(false)){
            List<Integer> ids = new ArrayList<>();
            try {
                ids.add(Integer.parseInt(key));
            } catch (NumberFormatException e) {
                String[] idsStr = key.split(",");
                for (int i = 0; i<idsStr.length; i++ ){
                    ids.add(Integer.parseInt(idsStr[i]));
                }
            }
            String type = configUtil.getConfig().getString("Gui.Items."+key+".Type");
            String name = configUtil.getConfig().getString("Gui.Items."+key+".DisplayName");
            List<String> lore = configUtil.getConfig().getStringList("Gui.Items."+key+".Lore");
            List<String> cmd = configUtil.getConfig().getStringList("Gui.Items."+key+".Commands");
            slots.add(new Slot(ids,type,name,lore,cmd));
        }
    }

    public static void runCommand(Player player,int guiSlot){
        for (Slot slot: slots ){
            for (Integer id: slot.id){
                if (id == guiSlot){
                    if (slot.cmd.size() != 0){
                        for (String cmd: slot.cmd){
                            Bukkit.getServer().dispatchCommand(player,cmd);
                        }
                        player.closeInventory();
                        return;
                    }
                }
            }
        }
    }

    public static void openGui(Player player){
        new BukkitRunnable() {
            @Override
            public void run(){
                Inventory inventory = InventoryUtil.createInventory(player,size,title);
                for (Slot slot:slots){
                    String name = PlaceholderAPI.setPlaceholders(player,slot.name);
                    List<String> lore = PlaceholderAPI.setPlaceholders(player,slot.lore);
                    for (Integer id : slot.id){
                        InventoryUtil.setItem(inventory,id, ItemUtil.getItemStack(slot.type),name,lore);
                    }
                }
                player.openInventory(inventory);
            }
        }.runTaskAsynchronously(OnlineReward.getInstance());
    }

    public static String replace(String text){
        return text.replace("&","§");
    }

    public static String getTitle(){
        return title;
    }

}

class Slot{
    String type;
    String name;
    List<Integer> id;
    List<String> lore;
    List<String> cmd;

    public Slot(List<Integer> id,String type, String name,List<String> lore,List<String> cmd){
        this.id = id;
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.cmd = cmd;
    }
}
