package me.ac.Placeholder;

import me.ac.Main;
import me.ac.playerbets;
import me.ac.util;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CasinoPlaceholder extends PlaceholderExpansion {
    Main plugin;
    public CasinoPlaceholder(Main main) {
        this.plugin=main;
    }
    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }
    @Override
    public String getIdentifier(){
        return "casino";
    }
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        // %casino_roll%
        if(Main.initialized) {
            if (identifier.equals("roll")) {
                int timer=Main.timer.getcounter();
                //return plugin.getConfig().getString("placeholder1", "value doesnt exist");
                /*if (Main.time == 0||Main.time==-1) {
                    return "Rolling...";
                } else {
                    if (Main.time >= 10) {
                        return "Next roll in 00:" + Main.time;
                    } else {
                        return "Next roll in 00:0" + Main.time;
                    }
                }*/
                if(timer<=0){
                    return "Rolling...";
                } else {
                    if (timer >= 10) {
                        return "Next roll in 00:" + timer;
                    } else {
                        return "Next roll in 00:0" + timer;
                    }
                }
                //return value
            }
            if (identifier.equals("betplaced")) {
                int timer=Main.timer.getcounter();
                String a=new StringBuilder().append(net.md_5.bungee.api.ChatColor.AQUA).append("Bet: ").toString();
                String b=new StringBuilder().append(net.md_5.bungee.api.ChatColor.AQUA).append(" on ").toString();
                String green=new StringBuilder().append(net.md_5.bungee.api.ChatColor.GREEN).append(net.md_5.bungee.api.ChatColor.BOLD).append("GREEN").toString();
                String black=new StringBuilder().append(net.md_5.bungee.api.ChatColor.BLACK).append(net.md_5.bungee.api.ChatColor.BOLD).append("BLACK").toString();
                String red=new StringBuilder().append(net.md_5.bungee.api.ChatColor.RED).append(net.md_5.bungee.api.ChatColor.BOLD).append("RED").toString();
                String c=new StringBuilder().append(net.md_5.bungee.api.ChatColor.GRAY).append("Betting...").toString();
                for(playerbets x:Main.pb){

                    if(x.getplayer().equals(player.getUniqueId())){
                        String amount=new StringBuilder().append(net.md_5.bungee.api.ChatColor.GOLD).append(net.md_5.bungee.api.ChatColor.BOLD).append(x.getamount().toString()).toString();
                        playerbets.Color color=x.getcolor();
                        if(color== playerbets.Color.GREEN){
                            return a+amount+b+green;
                        }

                        if(color== playerbets.Color.BLACK){
                            return a+amount+b+black;
                        }

                        if(color== playerbets.Color.RED){
                            return a+amount+b+red;
                        }
                    }

                }
                for(playerbets x:Main.pb2){

                    if(x.getplayer().equals(player.getUniqueId())){
                        String amount=new StringBuilder().append(net.md_5.bungee.api.ChatColor.GOLD).append(net.md_5.bungee.api.ChatColor.BOLD).append(x.getamount().toString()).toString();
                        playerbets.Color color=x.getcolor();
                        if(color== playerbets.Color.GREEN){
                            return a+c;
                        }

                        if(color== playerbets.Color.BLACK){
                            return a+c;
                        }

                        if(color== playerbets.Color.RED){
                            return a+c;
                        }
                    }

                }
                return new StringBuilder().append(net.md_5.bungee.api.ChatColor.AQUA).append("No bet placed.").toString();
                //return value
            }
            if (identifier.equals("winratio")) {
                return Main.mainData.wintotalratio.getOrDefault(player.getUniqueId().toString(), "0/0");
            }
            if (identifier.equals("balance")) {
                util z = new util(plugin);
                //return plugin.getConfig().getString("placeholder1", "value doesnt exist");
                return String.format("%.8f",z.getBalance(player.getUniqueId()));
                //return value
            }
            if (identifier.equals("lastwoncolor")) {
                //util z = new util(plugin);
                //return plugin.getConfig().getString("placeholder1", "value doesnt exist");
                //return z.getBalance(player.getUniqueId()).toString();
                if(Main.LastWonColor== playerbets.Color.GREEN){
                    return new StringBuilder().append(net.md_5.bungee.api.ChatColor.GREEN).append(net.md_5.bungee.api.ChatColor.BOLD).append("GREEN").toString();
                }

                if(Main.LastWonColor== playerbets.Color.BLACK){
                    return new StringBuilder().append(net.md_5.bungee.api.ChatColor.BLACK).append(net.md_5.bungee.api.ChatColor.BOLD).append("BLACK").toString();
                }

                if(Main.LastWonColor== playerbets.Color.RED){
                    return new StringBuilder().append(net.md_5.bungee.api.ChatColor.RED).append(net.md_5.bungee.api.ChatColor.BOLD).append("RED").toString();
                }
                //return value
            }
        }


        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%)
        // was provided
        return null;
    }




}
