package com.github.fntlv.onlinereward.service.reward;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.api.reward.RewardManagerAPI;
import com.github.fntlv.onlinereward.api.time.OnlineTimeAPI;
import com.github.fntlv.onlinereward.reward.Reward;
import com.github.fntlv.onlinereward.reward.RewardHolder;
import com.github.fntlv.onlinereward.service.time.TimeDataManagerService;
import com.github.fntlv.onlinereward.time.OnlineTime;
import com.github.fntlv.onlinereward.time.OnlineTimeHolder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class RewardManagerService implements RewardManagerAPI {

    @Override
    public boolean isReceiveReward(UUID uuid, String rewardID) {
        Reward reward = RewardHolder.getRewardByID(rewardID);
        if (reward != null){
            OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(uuid);
            switch (reward.getType()){
                case DAY:
                    if (onlineTime.getDailyReward().contains(rewardID)) return true;
                    break;
                case WEEK:
                    if (onlineTime.getWeeklyReward().contains(rewardID)) return true;
                    break;
                case MONTH:
                    if (onlineTime.getMonthlyReward().contains(rewardID)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTimeEnoughReceiveReward(UUID uuid, String rewardId) {
        Reward reward = RewardHolder.getRewardByID(rewardId);
        if (reward != null){
            OnlineTimeAPI onlineTimeAPI = OnlineReward.getPlayerOnlineTimeAPI();
            long time = reward.getTime();
            switch (reward.getType()){
                case DAY:
                    if (onlineTimeAPI.getDailyOnlineTime(uuid)>time) return true;
                    break;
                case WEEK:
                    if (onlineTimeAPI.getWeeklyOnlineTime(uuid)>time) return true;
                    break;
                case MONTH:
                    if (onlineTimeAPI.getMonthlyOnlineTime(uuid)>time) return true;
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean giveReward(UUID uuid, String rewardId) {
        OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(uuid);
        if (onlineTime != null){
            OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(uuid);
            Reward reward = RewardHolder.getRewardByID(rewardId);
            if (reward != null){
                if (player.isOnline()){
                    for (String command : reward.getReward()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player,command));
                    }
                    switch (reward.getType()){
                        case DAY:
                            onlineTime.getDailyReward().add(rewardId);
                            break;
                        case WEEK:
                            onlineTime.getWeeklyReward().add(rewardId);
                            break;
                        case MONTH:
                            onlineTime.getMonthlyReward().add(rewardId);
                    }
                    new TimeDataManagerService().updateTheOnlinePlayerData(uuid);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public long getToRewardTime(UUID uuid, String rewardId) {
        Reward reward = RewardHolder.getRewardByID(rewardId);
        long time = -1;
        if (reward !=null){
            OnlineTimeAPI onlineTimeAPI = OnlineReward.getPlayerOnlineTimeAPI();
            long rewardTime = reward.getTime();
            switch (reward.getType()){
                case DAY:
                    time = rewardTime - onlineTimeAPI.getDailyOnlineTime(uuid);
                    break;
                case WEEK:
                    time = rewardTime - onlineTimeAPI.getWeeklyOnlineTime(uuid);
                    break;
                case MONTH:
                    time = rewardTime - onlineTimeAPI.getMonthlyOnlineTime(uuid);
                    break;
            }
        }
        if (time <0){
            return 0;
        } else {
            return time;
        }
    }



}
