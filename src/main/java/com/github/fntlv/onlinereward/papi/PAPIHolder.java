package com.github.fntlv.onlinereward.papi;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.api.reward.RewardManagerAPI;
import com.github.fntlv.onlinereward.reward.Reward;
import com.github.fntlv.onlinereward.reward.RewardHolder;
import com.github.fntlv.onlinereward.util.TimeUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.HashMap;

public class PAPIHolder {
    public static HashMap<PAPI, Method> papiMap = new HashMap<>();

    @PAPI(
            hookName = "time_day_int"
    )
    public static String getDayTime(Player player){
        return String.valueOf(OnlineReward.getPlayerOnlineTimeAPI().getDailyOnlineTime(player.getUniqueId()));
    }
    @PAPI(
            hookName = "time_day"
    )
    public static String getDayTime2(Player player){
        return TimeUtil.formatDuration(OnlineReward.getPlayerOnlineTimeAPI().getDailyOnlineTime(player.getUniqueId()));
    }

    @PAPI(
            hookName = "time_week_int"
    )
    public static String getWeekTime(Player player){
        return String.valueOf(OnlineReward.getPlayerOnlineTimeAPI().getWeeklyOnlineTime(player.getUniqueId()));
    }
    @PAPI(
            hookName = "time_week"
    )
    public static String getWeekTime2(Player player){
        return TimeUtil.formatDuration(OnlineReward.getPlayerOnlineTimeAPI().getWeeklyOnlineTime(player.getUniqueId()));
    }

    @PAPI(
            hookName = "time_month_int"
    )
    public static String getMonthTime(Player player){
        return String.valueOf(OnlineReward.getPlayerOnlineTimeAPI().getMonthlyOnlineTime(player.getUniqueId()));
    }

    @PAPI(
            hookName = "time_month"
    )
    public static String getMonthTime2(Player player){
        return TimeUtil.formatDuration(OnlineReward.getPlayerOnlineTimeAPI().getMonthlyOnlineTime(player.getUniqueId()));
    }

    @PAPI(
            hookName = "lefttime",
            needReward = true
    )
    public static String getLeftTime(Player player,String rewardId){
        Reward reward = RewardHolder.getRewardByID(rewardId);
        RewardManagerAPI rewardManagerAPI = OnlineReward.getRewardManagerAPI();
        if (reward!=null){
            if (!rewardManagerAPI.isReceiveReward(player.getUniqueId(), rewardId)){
                long time = OnlineReward.getRewardManagerAPI().getToRewardTime(player.getUniqueId(),rewardId);
                if (time == 0){
                    return "可领取";
                }
                return TimeUtil.formatDuration(time);
            } else {
                return "奖励已领取";
            }
        }
        return "奖励不存在";
    }

}
