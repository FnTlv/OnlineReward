package com.github.fntlv.onlinereward.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class OnlineTimeExpansion extends PlaceholderExpansion {



    @Override
    public String onPlaceholderRequest(Player player, String params){
        for (PAPI papi:PAPIHolder.papiMap.keySet()){
            if (!papi.needReward()){
                if (papi.hookName().equals(params)){
                    try {
                        return String.valueOf(PAPIHolder.papiMap.get(papi).invoke(null,player));
                    } catch (IllegalAccessException|InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (params.contains(papi.hookName())){
                    try {
                        String[] list = params.split("_");
                        return String.valueOf(PAPIHolder.papiMap.get(papi).invoke(null,player,list[list.length-1]));
                    } catch (IllegalAccessException|InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "错误";
    }

    @Override
    public String getIdentifier() {
        return "onlinereward";
    }

    @Override
    public String getAuthor() {
        return "FnTlv";
    }

    @Override
    public  String getVersion() {
        return "1.0";
    }
}
