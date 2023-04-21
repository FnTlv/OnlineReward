package com.github.fntlv.onlinereward.util;

import com.github.fntlv.onlinereward.OnlineReward;

import java.io.File;
import java.sql.*;

public class SQLiteUtil {
    private static Connection connection;
    private static final String url = OnlineReward.getInstance().getDataFolder().getAbsolutePath() + File.separator + "data.db";

    public static void initSql() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (!OnlineReward.getInstance().getDataFolder().exists()){
                OnlineReward.getInstance().getDataFolder().mkdir();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            initTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initTable(){
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS playerTimeTable (onlineTime TEXT);"
            );
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS serverTimeTable (id PRIMARY KEY,weekDate INT,monthDate INT,dayDate INT);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

}
