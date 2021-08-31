package me.ac.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class howtowithdraw implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        Player p = (Player) sender;
        /*if (!p.hasPermission("AdvancedCasino.op")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }*/
        if (!p.hasPermission("AdvancedCasino.howtowithdraw")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command!"));
            return true;
        }
        if(args.length!=0){
            //p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageCasino")));
            return true;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3---&lHow To Withdraw&r&3---"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bTo withdraw type /withdraw <amount> <address>"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b<amount> is how much you want to withdraw in BTC."));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b<address> is the public address in which you want to send your BTC to."));
        //p.sendMessage(ChatColor.translateAlternateColorCodes('&',""));
        //p.sendMessage(ChatColor.translateAlternateColorCodes('&',""));
        return true;
    }
}
