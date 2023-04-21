package com.github.fntlv.onlinereward.listener;

import com.github.fntlv.onlinereward.config.GuiSetting;
import com.github.fntlv.onlinereward.config.RewardSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onPlayerOpenGui(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getTitle().equals(GuiSetting.getTitle())){
            event.setCancelled(true);
            GuiSetting.runCommand(player,event.getSlot());
        }
    }
}
