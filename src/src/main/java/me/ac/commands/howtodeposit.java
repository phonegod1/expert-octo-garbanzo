package me.ac.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class howtodeposit implements CommandExecutor  {
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        Player p = (Player) sender;
        /*if (!p.hasPermission("AdvancedCasino.op")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }*/
        if (!p.hasPermission("AdvancedCasino.howtodeposit")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command!"));
            return true;
        }
        if(args.length!=0){
            //p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageCasino")));
            return true;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&3---&lHow To Deposit&r&3---"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bType /deposit"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bThis will prompt you with an address, send the amount of BTC you wish to deposit to your account to the address displayed. "));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bTo copy the address to your clipboard simply click on &n&3Click Here To Copy The Address"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &bPlease use a high btc fee to make sure the transaction sends within at most half an hour."));
       // p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7 - &b"));

        return true;
    }
}
