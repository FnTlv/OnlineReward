package com.github.fntlv.onlinereward.time;

import java.util.HashMap;
import java.util.UUID;

public class OnlineTimeHolder {

    private static HashMap<UUID, OnlineTime> onlineTimeHashMap = new HashMap<>();

    public static HashMap<UUID, OnlineTime> getOnlineTimeHashMap() {
        return onlineTimeHashMap;
    }

    public static OnlineTime getOnlineTimeByUUID(UUID uuid){
        return onlineTimeHashMap.getOrDefault(uuid, null);
    }


}
