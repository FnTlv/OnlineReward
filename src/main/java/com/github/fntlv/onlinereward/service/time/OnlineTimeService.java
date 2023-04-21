package com.github.fntlv.onlinereward.service.time;

import com.github.fntlv.onlinereward.api.time.OnlineTimeAPI;
import com.github.fntlv.onlinereward.time.OnlineTime;
import com.github.fntlv.onlinereward.time.OnlineTimeHolder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class OnlineTimeService implements OnlineTimeAPI {

    @Override
    public long getDailyOnlineTime(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(uuid);
        if (onlineTime != null){
            return player.isOnline() ? onlineTime.getDailyOnlineTimeRecord() + onlineTime.getSecondsDifference() : onlineTime.getDailyOnlineTimeRecord();
        }
        return 0;
    }

    @Override
    public long getWeeklyOnlineTime(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(uuid);
        if (onlineTime != null){
            return player.isOnline() ? onlineTime.getWeeklyOnlineTimeRecord() + onlineTime.getSecondsDifference() : onlineTime.getWeeklyOnlineTimeRecord();
        }
        return 0;
    }

    @Override
    public long getMonthlyOnlineTime(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(uuid);
        if (onlineTime != null){
            return player.isOnline() ? onlineTime.getMonthlyOnlineTimeRecord() + onlineTime.getSecondsDifference() : onlineTime.getMonthlyOnlineTimeRecord();
        }
        return 0;
    }
}
