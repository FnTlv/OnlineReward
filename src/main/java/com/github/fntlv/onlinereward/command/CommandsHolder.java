package com.github.fntlv.onlinereward.command;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.api.reward.RewardManagerAPI;
import com.github.fntlv.onlinereward.config.GuiSetting;
import com.github.fntlv.onlinereward.config.RewardSetting;
import com.github.fntlv.onlinereward.time.OnlineTime;
import com.github.fntlv.onlinereward.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Material.STAINED_GLASS_PANE;

public class CommandsHolder {
    public static HashMap<PlayerCommand, Method> commandMap = new HashMap<>();

    @PlayerCommand(
            msg = "打开菜单",
            cmd = "open",
            type = SenderType.PLAYER
    )
    public static void openGui(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        GuiSetting.openGui(player);
    }

    @PlayerCommand(
            msg = "重载命令",
            cmd = "reload",
            type = SenderType.ALL
    )
    public static void reloadConfig(CommandSender sender,String[] args) {
        sender.sendMessage("§7[§6在线奖励§7] §a插件重载中");
        try {
            RewardSetting.init();
            GuiSetting.init();
            sender.sendMessage("§7[§6在线奖励§7] §b插件重载成功");
        } catch (Exception e){
            sender.sendMessage("§7[§6在线奖励§7] §c插件重载失败");
        }
    }

    @PlayerCommand(
            msg = "玩家领取奖励",
            cmd = "receive",
            type = SenderType.PLAYER
    )
    public static void receiveReward(CommandSender sender,String[] args){
        Player player = (Player) sender;
        if (args.length ==2){
            RewardManagerAPI rewardManagerAPI = OnlineReward.getRewardManagerAPI();
            if (!rewardManagerAPI.isReceiveReward(player.getUniqueId(), args[1])){
                if (rewardManagerAPI.isTimeEnoughReceiveReward(player.getUniqueId(),args[1])){
                    if (rewardManagerAPI.giveReward(player.getUniqueId(),args[1])){
                        player.sendMessage("§7[§6在线奖励§7] §a领取成功，成功领取该奖励");
                    }
                } else {
                    player.sendMessage("§7[§6在线奖励§7] §a在线时间不足,无法领取该奖励");
                }
            } else {
                player.sendMessage("§7[§6在线奖励§7] §a你已经领取过该奖励了");
            }
        } else {
            player.sendMessage("§7[§6在线奖励§7]§c指令参数错误");
        }
    }
}
