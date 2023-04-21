package com.github.fntlv.onlinereward;

import com.github.fntlv.onlinereward.api.reward.RewardManagerAPI;
import com.github.fntlv.onlinereward.api.time.TimeDataManagerAPI;
import com.github.fntlv.onlinereward.api.time.OnlineTimeAPI;
import com.github.fntlv.onlinereward.command.Commander;
import com.github.fntlv.onlinereward.command.CommandsHolder;
import com.github.fntlv.onlinereward.command.PlayerCommand;
import com.github.fntlv.onlinereward.config.GuiSetting;
import com.github.fntlv.onlinereward.config.RewardSetting;
import com.github.fntlv.onlinereward.listener.InventoryListener;
import com.github.fntlv.onlinereward.listener.PlayerListener;
import com.github.fntlv.onlinereward.papi.OnlineTimeExpansion;
import com.github.fntlv.onlinereward.papi.PAPI;
import com.github.fntlv.onlinereward.papi.PAPIHolder;
import com.github.fntlv.onlinereward.service.reward.RewardManagerService;
import com.github.fntlv.onlinereward.service.time.TimeDataManagerService;
import com.github.fntlv.onlinereward.service.time.OnlineTimeService;
import com.github.fntlv.onlinereward.task.TimeTask;
import com.github.fntlv.onlinereward.util.SQLiteUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public final class OnlineReward extends JavaPlugin {

    private static OnlineReward instance;
    private static OnlineTimeAPI playerOnlineTimeAPI;
    private static TimeDataManagerAPI timeDataManagerAPI;
    private static RewardManagerAPI rewardManagerAPI;

    @Override
    public void onEnable() {
        instance = this;
        initPAPI();
        init();
    }

    @Override
    public void onDisable() {

    }

    public void init(){
        this.registerCmd();
        this.registerListener();
        this.initAPI();
        this.loadData();
        TimeTask.startTimeRecord();                       
    }

    public void initAPI(){

        Bukkit.getServer().getServicesManager().register(OnlineTimeAPI.class,new OnlineTimeService(),this, ServicePriority.Normal);
        Bukkit.getServer().getServicesManager().register(TimeDataManagerAPI.class,new TimeDataManagerService(),this,ServicePriority.Normal);
        Bukkit.getServer().getServicesManager().register(RewardManagerAPI.class,new RewardManagerService(),this,ServicePriority.Normal);
        playerOnlineTimeAPI = Bukkit.getServer().getServicesManager().getRegistration(OnlineTimeAPI.class).getProvider();
        timeDataManagerAPI = Bukkit.getServer().getServicesManager().getRegistration(TimeDataManagerAPI.class).getProvider();
        rewardManagerAPI = Bukkit.getServer().getServicesManager().getRegistration(RewardManagerAPI.class).getProvider();
    }

    public void loadData(){

        RewardSetting.init();
        GuiSetting.init();
        SQLiteUtil.initSql();
        timeDataManagerAPI.readOnlineTimeData();
        timeDataManagerAPI.readServerTimeData();

    }


    public void registerListener(){
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(),this);
    }

    public void initPAPI(){
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getConsoleSender().sendMessage("§7[§6OnlineReward§7] §c未发现 PlaceholderAPI 插件");
        } else {
            for (Method method: PAPIHolder.class.getDeclaredMethods()){
                if (method.isAnnotationPresent(PAPI.class)){
                    PAPIHolder.papiMap.put(method.getAnnotation(PAPI.class),method);
                }
            }
            Bukkit.getConsoleSender().sendMessage("§7[§6OnlineReward§7] §aPlaceholderAPI扩展加载");
            new OnlineTimeExpansion().register();
        }
    }

    public void registerCmd(){
        if (Bukkit.getPluginCommand("onlinereward") != null){
            Bukkit.getPluginCommand("onlinereward").setExecutor(new Commander());
            for (Method method: CommandsHolder.class.getDeclaredMethods()){
                if (method.isAnnotationPresent(PlayerCommand.class)){
                    CommandsHolder.commandMap.put(method.getAnnotation(PlayerCommand.class),method);
                }
            }
        } else {
            Bukkit.getLogger().info("命令注册失败,请联系插件作者");
        }
    }

    public static OnlineReward getInstance(){
        return instance;
    }

    public static OnlineTimeAPI getPlayerOnlineTimeAPI(){
        return playerOnlineTimeAPI;
    }

    public static TimeDataManagerAPI getTimeDataManagerAPI(){
        return timeDataManagerAPI;
    }

    public static RewardManagerAPI getRewardManagerAPI(){
        return rewardManagerAPI;
    }

}
