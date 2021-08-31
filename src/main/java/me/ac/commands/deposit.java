package me.ac.commands;

import me.ac.CryptoService.DepositService;
import me.ac.CryptoService.ForwardingService;
import me.ac.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deposit implements CommandExecutor{
    private Player p;
    Main plugin;
    public deposit(Main plugin){
        this.plugin=plugin;

    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getLogger().warning(plugin.getConfig().getString("NoConsole"));
            return true;
        }
        p = (Player) sender;
        if (!p.hasPermission("AdvancedCasino.deposit")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }
        if(args.length!=0){
            //error
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageDeposit")));
        }
        if(Main.allowtransfer) {
            DepositService v=new DepositService(plugin,p);

            //z = new ForwardingService(p, plugin);
            //z.start();
        }
        //check args is dgitis only


        return true;
    }
}
