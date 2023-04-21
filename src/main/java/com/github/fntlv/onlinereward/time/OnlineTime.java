package com.github.fntlv.onlinereward.time;

import com.github.fntlv.onlinereward.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class OnlineTime {

    private String lastCalculationTime;
    private long dailyOnlineTimeRecord;
    private long weeklyOnlineTimeRecord;
    private long monthlyOnlineTimeRecord;
    private List<String> dailyReward = new ArrayList<>();
    private List<String> weeklyReward = new ArrayList<>();
    private List<String> monthlyReward = new ArrayList<>();

    public OnlineTime(String lastCalculationTime, long dailyOnlineTimeRecord, long weeklyOnlineTimeRecord, long monthlyOnlineTimeRecord){
        this.lastCalculationTime = lastCalculationTime;
        this.dailyOnlineTimeRecord = dailyOnlineTimeRecord;
        this.weeklyOnlineTimeRecord = weeklyOnlineTimeRecord;
        this.monthlyOnlineTimeRecord = monthlyOnlineTimeRecord;
    }

    public List<String> getDailyReward() {
        return dailyReward;
    }

    public List<String> getMonthlyReward() {
        return monthlyReward;
    }

    public List<String> getWeeklyReward() {
        return weeklyReward;
    }

    public long getDailyOnlineTimeRecord() {
        return dailyOnlineTimeRecord;
    }

    public long getMonthlyOnlineTimeRecord() {
        return monthlyOnlineTimeRecord;
    }

    public long getWeeklyOnlineTimeRecord() {
        return weeklyOnlineTimeRecord;
    }

    public String getLastCalculationTime() {
        return lastCalculationTime;
    }

    public void setDailyOnlineTimeRecord(long dailyOnlineTime) {
        this.dailyOnlineTimeRecord = dailyOnlineTime;
    }

    public void setLastCalculationTime(String lastCalculationTimeRecord) {
        this.lastCalculationTime = lastCalculationTimeRecord;
    }

    public void setMonthlyOnlineTimeRecord(long monthlyOnlineTimeRecord) {
        this.monthlyOnlineTimeRecord = monthlyOnlineTimeRecord;
    }

    public void setWeeklyOnlineTimeRecord(long weeklyOnlineTimeRecord) {
        this.weeklyOnlineTimeRecord = weeklyOnlineTimeRecord;
    }

    public void refreshAllTime(){
        long toSecond = getSecondsDifference();
        this.dailyOnlineTimeRecord += toSecond;
        this.weeklyOnlineTimeRecord += toSecond;
        this.monthlyOnlineTimeRecord += toSecond;
    }

    public long getSecondsDifference() {
        return TimeUtil.getToSeconds(getLastCalculationTime(), TimeUtil.getTime());
    }



}
