package com.github.fntlv.onlinereward.config;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.reward.Reward;
import com.github.fntlv.onlinereward.reward.RewardHolder;
import com.github.fntlv.onlinereward.reward.RewardType;
import com.github.fntlv.onlinereward.util.ConfigUtil;
import com.github.fntlv.onlinereward.util.TimeUtil;
import org.bukkit.Bukkit;

import java.util.List;

public class RewardSetting {
    private static ConfigUtil configUtil;

    public static void init(){
        configUtil = new ConfigUtil(OnlineReward.getInstance(),"reward.yml");
        configUtil.load();
        loadRewardData();
    }

    public static void loadRewardData(){

        for (String id :configUtil.getConfig().getConfigurationSection("RewardSettings").getKeys(false)){
            RewardType type;
            try {
                type = Enum.valueOf(RewardType.class,configUtil.getConfig().getString("RewardSettings."+id+".Type"));
            }catch (IllegalArgumentException e){
                Bukkit.getServer().getConsoleSender().sendMessage("§7[§6OnlineReward§7] §cError 配置出错: §7奖励§a"+id+"§7下§bType§7类型设置错误");
                continue;
            }
            long time = TimeUtil.convertDurationToSeconds(configUtil.getConfig().getString("RewardSettings."+id+".Time"));
            List<String> rewardList = configUtil.getConfig().getStringList("RewardSettings."+id+".Reward");
            RewardHolder.getRewardMap().put(id,new Reward(type,time,rewardList));
        }
    }

    public static ConfigUtil getConfigUtil(){
        return configUtil;
    }


}
