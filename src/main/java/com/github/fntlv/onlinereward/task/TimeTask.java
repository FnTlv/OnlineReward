package com.github.fntlv.onlinereward.task;

import com.github.fntlv.onlinereward.OnlineReward;
import org.bukkit.Bukkit;

public class TimeTask {

    public static void startTimeRecord(){
        int timeRecord = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(OnlineReward.getInstance(), () -> {
            OnlineReward.getTimeDataManagerAPI().updateAllOnlinePlayerData();
            OnlineReward.getTimeDataManagerAPI().saveOnlineTimeData();
        }, 0L, 20L * 60L);
    }

}
