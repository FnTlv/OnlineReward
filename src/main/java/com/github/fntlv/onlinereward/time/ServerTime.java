package com.github.fntlv.onlinereward.time;

import com.github.fntlv.onlinereward.OnlineReward;
import com.github.fntlv.onlinereward.api.reward.RewardManagerAPI;
import com.github.fntlv.onlinereward.reward.RewardClaimRecord;
import com.github.fntlv.onlinereward.service.time.OnlineTimeService;
import com.github.fntlv.onlinereward.service.time.TimeDataManagerService;
import com.github.fntlv.onlinereward.util.TimeUtil;

public class ServerTime {
    private static int month = 1;
    private static int week = 1;
    private static int day = 1;

    public static int getMonth(){
        return month;
    }

    public static int getWeek() {
        return week;
    }

    public static void setMonth(int iMonth) {
        month = iMonth;
    }

    public static void setWeek(int iWeek) {
        week = iWeek;
    }

    public static void setDay(int iDay) {
        day = iDay;
    }

    public static int getDay() {
        return day;
    }

    public static void updateTime(int newMonth,int newWeek,int newDay){
        month = newMonth;
        week = newWeek;
        day = newDay;
    }

    public static void emptyTime() {
        int[] date = TimeUtil.getDate();

        boolean isEmptyMonth = date[0] != ServerTime.getMonth();
        boolean isEmptyWeek = date[1] != ServerTime.getWeek();
        boolean isEmptyDay = date[2] != ServerTime.getDay();

        if (isEmptyDay) {
            for (OnlineTime onlineTime:OnlineTimeHolder.getOnlineTimeHashMap().values()){
                onlineTime.getDailyReward().clear();
            }
        }

        if (isEmptyWeek){
            for (OnlineTime onlineTime:OnlineTimeHolder.getOnlineTimeHashMap().values()){
                onlineTime.getWeeklyReward().clear();
            }
        }

        if (isEmptyMonth){
            for (OnlineTime onlineTime:OnlineTimeHolder.getOnlineTimeHashMap().values()){
                onlineTime.getMonthlyReward().clear();
            }
        }


        setDay(isEmptyDay ? date[2] : getDay());
        setMonth(isEmptyMonth ? date[0] : getMonth());
        setWeek(isEmptyWeek ? date[1] : getWeek());

        OnlineReward.getTimeDataManagerAPI().saveServerTimeData();
        OnlineTimeHolder.getOnlineTimeHashMap().values().forEach(onlineTime -> {
            if (isEmptyDay) onlineTime.setDailyOnlineTimeRecord(0);
            if (isEmptyMonth) onlineTime.setMonthlyOnlineTimeRecord(0);
            if (isEmptyWeek) onlineTime.setWeeklyOnlineTimeRecord(0);
        });
    }
}
