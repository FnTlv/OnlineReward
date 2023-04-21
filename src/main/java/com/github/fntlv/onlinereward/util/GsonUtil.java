package com.github.fntlv.onlinereward.util;

import com.github.fntlv.onlinereward.reward.RewardClaimRecord;
import com.github.fntlv.onlinereward.time.OnlineTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GsonUtil {

    public static String serialize(HashMap<UUID, OnlineTime> onlineTimeHashMap){
        Gson gson = new Gson();
        return gson.toJson(onlineTimeHashMap);
    }

    public static String serialize(List<UUID> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<UUID> deserialize2(String text){
        Type type = new TypeToken<List<UUID>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(text,type);
    }

    public static HashMap<UUID, OnlineTime> deserialize(String text){
        Type type = new TypeToken<HashMap<UUID, OnlineTime>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(text,type);
    }

}
