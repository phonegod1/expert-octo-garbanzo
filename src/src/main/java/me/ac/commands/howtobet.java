package me.ac.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class howtobet implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        Player p = (Player) sender;

        if (!p.hasPermission("AdvancedCasino.howtobet")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command!"));
            return true;
        }
        if(args.length!=0){
            //p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageCasino")));
            return true;
        }

        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3---&lHow To Bet&r&3---"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bThere are two ways to bet, using the command /bet or /bet <color> <amount>"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bUsing /bet <color> <amount>"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b<color> is the color which you are betting on. (RED,BLACK,GREEN) "));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b<amount> is the amount you wish to place your bet with."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bIf the wheel is rolling your bet is added to the next roll."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bUsing /bet opens a gui. Pick the color you want to bet on."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bYou are asked to input an amount. Enter the amount you wish to bet in chat, or type cancel to exit."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bThe color the arrow is pointing at on the wheel is on the winning color. If you placed a bet on that color, you're awarded."));
        //p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b"));
        //p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b"));
        return true;
    }
}
