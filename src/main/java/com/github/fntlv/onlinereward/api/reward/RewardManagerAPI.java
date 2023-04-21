package com.github.fntlv.onlinereward.api.reward;


import java.util.UUID;

public interface RewardManagerAPI {
    //查询指定玩家是否领取过指定ID奖励
    boolean isReceiveReward(UUID uuid,String rewardID);
    //查看玩家在线时间是否足够领取该奖励
    boolean isTimeEnoughReceiveReward(UUID uuid, String rewardId);
    //给玩家可指定ID奖励,并获取是否成功给予奖励
    boolean giveReward(UUID uuid,String rewardId);
    //获取指定id奖励剩余领取时间
    long getToRewardTime(UUID uuid,String rewardId);


}
