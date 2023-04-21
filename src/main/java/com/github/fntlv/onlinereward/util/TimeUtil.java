package com.github.fntlv.onlinereward.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    public static String getTime(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }

    public static int[] getDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int month = calendar.get(Calendar.MONTH)+1;
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return new int[]{
                month,week,day
        };
    }

    public static long getToSeconds(String startTime,String endTime){

        LocalDateTime localDateTimeStart = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Duration duration = Duration.between(localDateTimeStart,localDateTimeEnd);
        return duration.getSeconds();

    }

    public static int convertDurationToSeconds(String duration) {
        int days = 0;
        int hours = 0;
        int minutes = 0;
        Pattern pattern = Pattern.compile("(\\d+)(d|h|m)?");
        Matcher matcher = pattern.matcher(duration);
        while (matcher.find()) {
            String unit = matcher.group(2);
            int value = Integer.parseInt(matcher.group(1));
            switch (unit) {
                case "d":
                    days += value;
                    break;
                case "h":
                    hours += value;
                    break;
                case "m":
                default:
                    minutes += value;
                    break;
            }
        }
        return (days * 24 * 60 * 60) + (hours * 60 * 60) + (minutes * 60);
    }

    public static String formatDuration(long seconds) {
        long day = TimeUnit.SECONDS.toDays(seconds);
        long hour = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天");
        }
        if (hour > 0) {
            sb.append(hour).append("小时");
        }
        if (minute > 0) {
            sb.append(minute).append("分钟");
        }
        if (second > 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }

}
