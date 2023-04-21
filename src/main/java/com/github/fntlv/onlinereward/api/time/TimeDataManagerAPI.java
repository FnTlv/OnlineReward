package com.github.fntlv.onlinereward.api.time;

import java.util.UUID;

public interface TimeDataManagerAPI {

    //更新某个玩家在线时间数据
    void updateTheOnlinePlayerData(UUID uuid);
    //更新在线玩家在线时间数据
    void updateAllOnlinePlayerData();
    //保存在线时间数据
    void saveOnlineTimeData();
    //读取在线时间数据
    void readOnlineTimeData();
    //保存服务器时间数据
    void saveServerTimeData();
    //读取服务器时间数据
    void readServerTimeData();

}
