package com.github.fntlv.onlinereward.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class Commander implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SenderType senderType = SenderType.CONSOLE;

        if (sender instanceof Player) {
            senderType = SenderType.PLAYER;
        }

        if (args.length == 0) {
            String[] help = {
                    "§6在线奖励帮助",
                    "§f/olr open §a打开在线奖励菜单"
            };
            sender.sendMessage(help);
        } else {
            for (PlayerCommand playerCommand : CommandsHolder.commandMap.keySet()) {
                if (playerCommand.cmd().equalsIgnoreCase(args[0])) {
                    if (playerCommand.type().equals(senderType) || playerCommand.type().equals(SenderType.ALL)) {
                        try {
                            CommandsHolder.commandMap.get(playerCommand).invoke(null, sender, args);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sender.sendMessage("§c你不能以当前身份执行该命令");
                    }
                    return true;
                }
            }
        }

        return false;
    }

}
