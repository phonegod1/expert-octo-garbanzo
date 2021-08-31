package me.ac.commands;

import me.ac.CryptoService.WithdrawService;
import me.ac.Main;
import me.ac.util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class withdraw implements CommandExecutor {
    private Main plugin;
    private Player p;
    public withdraw(Main plugin){
        this.plugin=plugin;
    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getLogger().warning(plugin.getConfig().getString("NoConsole"));
            return true;
        }
        p = (Player) sender;
        if (!p.hasPermission("AdvancedCasino.withdraw")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }
        if(args.length!=2){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdraw")));
            return true;
        }
        try
        {
            Double.parseDouble(args[0]);
        }
        catch(NumberFormatException e)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));
            return true;
            //not a double
        }
        double amount = Double.parseDouble(args[0]);
        if(amount<0){p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));return true;}
        if(amount<0.00009){p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageTooLittleMoney")));return true;}
        util z= new util(plugin);
        Double bal= z.getBalance(p.getUniqueId());
        if(amount>bal){
            //INSUFFICIENT FUNDs
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawIns")));
            return true;
        }
        if(Main.allowtransfer){
            Main.WS.withdraw(args[1],(long)(amount*100000000),p);
            z.removeFromAccount(p.getUniqueId(),Double.parseDouble(args[0]));

        }
        return true;
    }
}
