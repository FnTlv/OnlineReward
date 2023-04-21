package com.github.fntlv.onlinereward.listener;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.time.OnlineTime;
import com.github.fntlv.onlinereward.time.OnlineTimeHolder;
import com.github.fntlv.onlinereward.util.TimeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(player.getUniqueId());
        if (onlineTime != null){
            onlineTime.setLastCalculationTime(TimeUtil.getTime());
        } else {
            OnlineTimeHolder.getOnlineTimeHashMap().put(
                    player.getUniqueId(),
                    new OnlineTime(
                            TimeUtil.getTime(),
                            0,
                            0,
                            0
                    )
            );
        }
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        OnlineReward.getTimeDataManagerAPI().updateTheOnlinePlayerData(event.getPlayer().getUniqueId());
        OnlineReward.getTimeDataManagerAPI().saveOnlineTimeData();
    }

}
