package com.github.fntlv.onlinereward.api.time;

import java.util.UUID;

public interface OnlineTimeAPI {

    //获取玩家每日在线时间
    long getDailyOnlineTime(UUID uuid);
    //获取玩家每周在线时间
    long getWeeklyOnlineTime(UUID uuid);
    //获取玩家每月在线时间
    long getMonthlyOnlineTime(UUID uuid);

}
