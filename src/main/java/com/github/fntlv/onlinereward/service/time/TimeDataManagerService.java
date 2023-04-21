package com.github.fntlv.onlinereward.service.time;

import com.github.fntlv.onlinereward.api.time.TimeDataManagerAPI;
import com.github.fntlv.onlinereward.time.OnlineTime;
import com.github.fntlv.onlinereward.time.OnlineTimeHolder;
import com.github.fntlv.onlinereward.time.ServerTime;
import com.github.fntlv.onlinereward.util.GsonUtil;
import com.github.fntlv.onlinereward.util.SQLiteUtil;
import com.github.fntlv.onlinereward.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class TimeDataManagerService implements TimeDataManagerAPI {

    @Override
    public void updateTheOnlinePlayerData(UUID uuid) {
        OfflinePlayer player = Bukkit.getServer().getPlayer(uuid);
        if (player.isOnline()) {
            OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(uuid);
            if (onlineTime != null) {
                ServerTime.emptyTime();
                onlineTime.refreshAllTime();
                onlineTime.setLastCalculationTime(TimeUtil.getTime());
            }
        }
    }

    @Override
    public void updateAllOnlinePlayerData() {
        ServerTime.emptyTime();
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            OnlineTime onlineTime = OnlineTimeHolder.getOnlineTimeByUUID(player.getUniqueId());
            if (onlineTime != null) {
                onlineTime.refreshAllTime();
                onlineTime.setLastCalculationTime(TimeUtil.getTime());
            }
        });
    }

    @Override
    public void saveOnlineTimeData() {
        String data = GsonUtil.serialize(OnlineTimeHolder.getOnlineTimeHashMap());
        try(Statement statement = SQLiteUtil.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT onlineTime FROM playerTimeTable;");
            if (resultSet.next()){
                statement.executeUpdate("UPDATE playerTimeTable SET onlineTime = '"+data+"';");
            } else {
                statement.executeUpdate("INSERT INTO playerTimeTable (onlineTime) VALUES ('"+data+"');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readOnlineTimeData() {
        try(Statement statement = SQLiteUtil.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT onlineTime FROM playerTimeTable;");
            if (resultSet.next()){
                String onlineTime = resultSet.getString("onlineTime");
                OnlineTimeHolder.getOnlineTimeHashMap().putAll(GsonUtil.deserialize(onlineTime));
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveServerTimeData() {
        try(Statement statement = SQLiteUtil.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT weekDate, monthDate, dayDate FROM serverTimeTable;");
            if (resultSet.next()){
                statement.executeUpdate("UPDATE serverTimeTable SET weekDate = "+ServerTime.getWeek()+", monthDate ="+ServerTime.getMonth()+",dayDate = "+ServerTime.getDay()+" WHERE id = 1;");
            } else {
                statement.executeUpdate("INSERT INTO serverTimeTable (id,weekDate, monthDate, dayDate) VALUES ( 1, "+ServerTime.getWeek()+", "+ServerTime.getMonth()+", "+ServerTime.getDay()+");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readServerTimeData() {
        try(Statement statement = SQLiteUtil.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT weekDate, monthDate, dayDate FROM serverTimeTable;");
            if (resultSet.next()){
                int weekDate = resultSet.getInt("weekDate");
                int monthDate = resultSet.getInt("monthDate");
                int dayDate = resultSet.getInt("dayDate");
                ServerTime.updateTime(monthDate,weekDate,dayDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
