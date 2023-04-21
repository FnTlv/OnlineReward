package com.github.fntlv.onlinereward.reward;

import java.util.List;

public class Reward {
    private RewardType type;
    private long time;
    private List<String> reward;

    public Reward(RewardType type,long time,List<String> reward){
        this.type = type;
        this.time = time;
        this.reward = reward;
    }

    public RewardType getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public List<String> getReward() {
        return reward;
    }

}
