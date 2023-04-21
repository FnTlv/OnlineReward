package com.github.fntlv.onlinereward.reward;

import java.util.HashMap;

public class RewardHolder {

    private static HashMap<String,Reward> rewardMap = new HashMap<>();

    public static HashMap<String, Reward> getRewardMap() {
        return rewardMap;
    }

    public static Reward getRewardByID(String id){
        return rewardMap.getOrDefault(id,null);
    }
}
